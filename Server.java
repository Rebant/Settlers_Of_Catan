import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;

/**
 * @author supervisor
 * A server contains an instance of game which is used
 * by all players of the game. All requests are passed to
 * the Server with the game.
 */
public class Server {
	
	com.esotericsoftware.kryonet.Server server;
	Game game; //Game for this Server
	int whoseTurn;
	LinkedBlockingQueue<RequestConnection> allRequests = new LinkedBlockingQueue<RequestConnection>();
	ArrayList<Connection> allConnections = new ArrayList<Connection>();
	ArrayList<Integer> allHashes = new ArrayList<Integer>();
	public int numOfPlayers = 0;
	public boolean initialized = false;
	
	public static void main(String[] args) throws IOException {
//		InputStreamReader istream = new InputStreamReader(System.in);
//		BufferedReader reader = new BufferedReader(istream);
//		
//		int numOfPlayers;
//		System.out.println("Input the number of players:");
//		while (true) {
//			try {
//				numOfPlayers = Integer.parseInt(reader.readLine()); break;
//			}
//			catch (NumberFormatException nfe) {
//				System.out.println("Please enter an integer.");
//			}
//		}
//		
//		System.out.println("Input the filename of the game:");
//		String filename = reader.readLine();
//		
//		int portIn = 0;
//		int portOut = 0;
//		boolean in = false;
//		while (true) {
//			try {
//				if (!in) { System.out.println("Enter the port in:"); portIn = Integer.parseInt(reader.readLine()); in = !in; }
//				else { System.out.println("Enter the port out:"); portOut = Integer.parseInt(reader.readLine()); break; }
//			}
//			catch (Exception e) {
//				System.out.println("Please enter an integer.");
//			}
//		}
//		
//		new Server(numOfPlayers, filename, portIn, portOut);
		new Server(2, "OriginalGame.txt", 53777, 54777);
	}
	
	/**
	 * @param numOfPlayers
	 * @param filename
	 * Creates an instance of Server with 'numOfPlayers' players
	 * and the game name 'filename'.
	 */
	public Server(int numOfPlayers, String filename, int portIn, int portOut) {
		try {
			game = new Game(numOfPlayers, filename);
		}
		catch (NullBoardException nbe) {
			System.out.println("Creating the game failed.");
			return;
		}
		
		System.out.println("Starting server...");
		server = new com.esotericsoftware.kryonet.Server();
		
		Kryo kryo = server.getKryo();
		kryo.register(Request.class); //Unsupported by 1.6 Java - need 1.7
		kryo.register(int[].class);
		kryo.register(String[].class);
		System.out.println("Registered all pertinent classes...");
		
		server.start();
		System.out.println("Server thread is up...");
		
		System.out.println("Binding server to " + portIn + " and " + portOut + "...");
		try {
			server.bind(portIn, portOut);
		}
		catch (IOException e) {
			System.out.println("Server could not bind to the ports.");
			return;
		}
		catch (Exception e) {
			System.out.println("Some sort of error occured which has not yet been implemented.");
			return;
		}
		System.out.println("Server successfully binded to the ports...");
		
		server.addListener(new Listener() {
			public void received(Connection connection, Object object) {
				if (object instanceof Request) {
					addIfNotIn(connection); //TODO Do not need to check this everytime right?
					Request bleh = (Request) object;

					System.out.println("Got something: " + bleh.getRequest());
					allRequests.add(new RequestConnection(bleh, connection));
				}
			}
		});
		
		System.out.println("Server has been started!");
		System.out.println("Your IP Address is " + server.getKryo().toString());
		
		System.out.println("The number of players is " + game.allPlayers.length);
		System.out.println("We have so far " + this.numOfPlayers);
		
		
		/* Setting up names */
		while (this.numOfPlayers != game.allPlayers.length) {
			if (allRequests.isEmpty()) { continue; }
			handleRequest(allRequests.remove());
			//TODO Better memory management
		}
		System.out.println("All players have joined the game...");
		
		System.out.println("Randomizing order...");
		game.randomizeOrder();
		System.out.println("The order is " + game.getOrder());
		game.begin();
		
		System.out.println("Starting the game!");
		startGame();
		
		/* Main function */
		while (!game.done) {
			if (allRequests.isEmpty()) { continue; }
			handleRequest(allRequests.remove());
		}
		
		
		
		
	}
	
	
	
	
	
	/*
	 * Things to request
	 * =================
	 * 0. Setup a player - 99
	 * 1. Draw a Dev card
	 * 2. Roll dice - covers drawing from roll
	 * 3. Draw a resource card
	 * 4. Put back resource card
	 * 5. Next turn
	 * 6. Remove a Dev card
	 * 7. Add city
	 * 8. Add road
	 * 9. Remove city
	 * 10. Remove road
	 * 11. Get hexagon information - 98. Receive hexagon information
	 * 998. Get information about hexagon
	 * 999. Error - print the message
	 * 1001. Start the game
	 * 1002. Update stats for clients
	 */	
	
	public void handleRequest(RequestConnection toHandle) {
		Request theRequest = toHandle.getRequest();
		Connection connection = toHandle.getConnection();

		int request = theRequest.getRequest();
		
		switch (request) {
			case 0: gameSetupPlayer(theRequest, connection); break;
			case 1: gameDrawDev(theRequest); break;
			case 2: gameRollDie(theRequest, connection); break;
			case 3: gameDrawResource(theRequest); break;
			case 4: gamePutBackResource(theRequest.getType(), theRequest.getWhichPlayer()); break;
			case 5: gameNextTurn(theRequest, connection); break;
			case 6: gameRemoveDev(theRequest); break;
			case 7: gameAddCity(theRequest, connection); break;
			case 8: gameAddRoad(theRequest, connection); break;
			case 9: gameRemoveCity(theRequest, connection); break;
			case 10: gameRemoveRoad(theRequest, connection); break;
			case 11: gameGetHexInformation(theRequest, connection); break;
			default: System.out.println("Someone is trying to hack this game!"); System.exit(-100); break;
		}
		
		if (initialized) { updateStats(); }
		
	}

	private void gameGetHexInformation(Request theRequest, Connection connection) {
		int hexagon = theRequest.getHexagonSelected();
		String[] settlementNames = game.getSettlementName(hexagon);
		String[] roadNames = game.getRoadName(hexagon);
		Request toSend = new Request();
		toSend.setSettlementNames(settlementNames);
		toSend.setRoadNames(roadNames);
		toSend.setHexagonSelected(hexagon);
		toSend.setRequest(98);
		connection.sendTCP(toSend);
	}

	private void gameRemoveRoad(Request theRequest, Connection connection) {
		System.out.println("You just removed something.");
		if (!isTurn(theRequest.getWhichPlayer())) { sendError("It is not your turn.", connection); return; }
		int whichPlayer = theRequest.getWhichPlayer();
		Player thePlayer = game.getPlayerFromHash(whichPlayer);
		game.removeRoad(theRequest.getHexagonSelected(), theRequest.getRoadsSpotSelected(), thePlayer);
	}

	private void gameRemoveCity(Request theRequest, Connection connection) {
		System.out.println("You just removed something.");
		if (!isTurn(theRequest.getWhichPlayer())) { sendError("It is not your turn.", connection); return; }
		int whichPlayer = theRequest.getWhichPlayer();
		Player thePlayer = game.getPlayerFromHash(whichPlayer);
		game.removeCity(theRequest.getHexagonSelected(), theRequest.getOnSpotSelected(), thePlayer);
	}

	private void gameAddRoad(Request theRequest, Connection connection) {
		System.out.println("Someone is trying to buy a road.");
		if (!isTurn(theRequest.getWhichPlayer())) { sendError("It is not your turn.", connection); return; }
		int whichPlayer = theRequest.getWhichPlayer();
		Player thePlayer = game.getPlayerFromHash(whichPlayer);
		game.addRoad(theRequest.getHexagonSelected(), theRequest.getRoadsSpotSelected(), thePlayer);
	}

	private void gameAddCity(Request theRequest, Connection connection) {
		System.out.println("Someone is trying to buy a city.");
		if (!isTurn(theRequest.getWhichPlayer())) { sendError("It is not your turn.", connection); return; }
		int whichPlayer = theRequest.getWhichPlayer();
		Player thePlayer = game.getPlayerFromHash(whichPlayer);
		game.addCity(theRequest.getHexagonSelected(), theRequest.getOnSpotSelected(), thePlayer);
	}

	private void gameRemoveDev(Request theRequest) {
		// TODO Have to get the Dev Card being selected...
		// TODO Auto-generated method stub
		
	}





	private void gameNextTurn(Request theRequest, Connection connection) {
		if (!isTurn(theRequest.getWhichPlayer())) { sendError("Not your turn to end the turn.", connection); return; }
		game.nextTurn();
		whoseTurn = (whoseTurn + 1) % game.allPlayers.length;
	}





	private void gamePutBackResource(int type, int hash) {
		Player player = game.getPlayerFromHash(hash);
		game.putResourceBack(type, player);
	}





	private void gameDrawResource(Request theRequest) {
		int whichPlayer = theRequest.getWhichPlayer();
		Player thePlayer = game.getPlayerFromHash(whichPlayer);
		game.drawResourceCard(theRequest.getType(), thePlayer);		
	}





	private void gameRollDie(Request theRequest, Connection connection) {
		if (!isTurn(theRequest.getWhichPlayer())) { sendError("Not your turn to roll the dice.", connection); return; }
		if (game.rolledDie) { sendError("You already rolled!", connection); return; }
		game.rollDie();
		// TODO Auto-generated method stub
		
	}





	private void gameDrawDev(Request theRequest) {
		int whichPlayer = theRequest.getWhichPlayer();
		Player thePlayer = game.getPlayerFromHash(whichPlayer);
		game.drawDevCard(thePlayer);
	}
	
	
	
	private void gameSetupPlayer(Request request, Connection connection) {
		String name = request.getRequestString();
		Player created = game.setupPlayer(name);
		if (created == null) { connection.sendTCP(new Request()); return; } //TODO Tell user to send in the name again?
		Request toSendBack = new Request();
		int hashCode = created.hashCode();
		allHashes.add(hashCode); //TODO Should do this someplace else in case get more than the amount of hashcodes
		toSendBack.setReturnName(hashCode);
		toSendBack.setRequest(99);
		connection.sendTCP(toSendBack);
		numOfPlayers++;
	}
	

	private boolean isTurn(int hash) {
		return game.onPlayer == hash;
	}

	
	private void sendError(String message, Connection connection) {
		Request toSend = new Request();
		toSend.setMessage(message);
		toSend.setRequest(999);
		connection.sendTCP(toSend);
	}
	
	
	private void addIfNotIn(Connection connection) {
		int thisID = connection.getID();
		for (int i = 0; i < allConnections.size(); i++) {
			if (allConnections.get(i).getID() == thisID) { return; }
		}
		allConnections.add(connection);
	}
	
	public void startGame() {
			Request startGame = new Request();
			startGame.setRequest(1001);
			startGame.setMapTypes(game.map.getAllTypes());
			for (int i = 0; i < allConnections.size(); i++) { allConnections.get(i).sendTCP(startGame); }
			initialized = true;
			updateStats();
	}
	
	public void updateStats() {
		Player player;
		int[] stats;
		int[] dieRolled = (game.rolledDice == null ? new int[] {0, 0} : game.rolledDice);
		Request toSend;
		for (int i = 0; i < allConnections.size(); i++) {
			player = game.getPlayerFromHash(allHashes.get(i));
			stats = player.getAllCards();
			toSend = new Request();
			toSend.setRequest(1002);
			toSend.setResourceStats(stats);
			toSend.setDieRolled(dieRolled);
			allConnections.get(i).sendTCP(toSend);
			if (player.hashCode() == game.onPlayer) {
				toSend = new Request(); toSend.setRequest(69); allConnections.get(i).sendTCP(toSend);
			}
		}
		//TODO Update dev cards
		//TODO Update map
		//TODO Update chat
	}
	
}

class RequestConnection {

	Request request;
	Connection connection;
	
	public RequestConnection(Request request, Connection connection) {
		this.request = request;
		this.connection = connection;
	}
	
	public Connection getConnection() {
		return connection;
	}
	
	public Request getRequest() {
		return request;
	}
	
}