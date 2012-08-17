import java.util.ArrayList;
import java.util.Random;

public class Game {

	Player[] allPlayers; int currentPlayerInt;
	int[] resourceBank; //Wood, Sheep, Brick, Rock, Wheat
	ArrayList<DevCard> allDevCards = new ArrayList<DevCard>();
	Parser parser;
	boolean done = false;
	Map map; //Map for this game
	
	/* Game stuff */
	Player currentPlayer; //Current player's turn
	int onPlayer;
	int[] rolledDice;
	boolean rolledDie;
	
	/**
	 * Creates an instance of Game with 30 starting resource cards for each type
	 * and 19 total spaces (original game).
	 */
	public Game() {
		resourceBank = new int[5];
		for (int i = 0; i < 5; i++) { resourceBank[i] = 30; } //30 resource cards each
		map = new Map(19);
	}
	
	/**
	 * @param numOfPlayers
	 * Setup a game with numOfPlayers players, 30 starting resource cards for each
	 * type and 19 total spaces (original game).
	 */
	public Game(int numOfPlayers) {
		this();
		allPlayers = new Player[numOfPlayers];
	}
	
	public Game(int numofPlayers, String filename) throws NullBoardException {
		this(numofPlayers);
		parser = new Parser(this, filename);
	}
	
	/**
	 * @param name
	 * Setup the next player.
	 */
	public Player setupPlayer(String name) {
		for (int i = 0; i < allPlayers.length; i++) {
			if (allPlayers[i] == null) { allPlayers[i] = new Player(System.nanoTime(), name); return allPlayers[i]; }
		}
		return null;
	}
	
	/**
	 * @param name
	 * @param description
	 * Adds a development card to this game.
	 */
	public void addDevCard(String name, String description) {
		allDevCards.add(new DevCard(name, description));
	}
	
	/**
	 * @param hexagon
	 * @param dieToRoll
	 * @param type
	 * Sets hexagon 'hexagon'.
	 */
	public void setHexagon(int hexagon, int dieToRoll, int type) {
		map.setHexagon(hexagon, dieToRoll, type);
	}
	
	/* Game play actions */
	/**
	 * @return The two die rolls rolled by this player.
	 */
	public void rollDie() {
		Random generator = new Random();
		rolledDice = new int[] {generator.nextInt(6) + 1, generator.nextInt(6) + 1};
		rolledDie = true;
	}
	
	/**
	 * @param type
	 * @param player
	 * @return True if can draw the card, false otherwise
	 * Draws the appropriate type of card from the bank if there is at least one
	 * card of that type in the bank. 
	 */
	public void drawResourceCard(int type, Player player) {
		System.out.println("Player " + player.name + " is drawing.");
		if (resourceBank[type] <= 0) { System.out.println(player.hashCode() + " cannot draw a card."); return; }
		player.addCard(type);
		drawCard(type);
	}
	
	/**
	 * @param type
	 * Draws the appropriate type of card from the bank for the current player.
	 */
	public void drawResourceCard(int type) {
		System.out.println("Drawing a card!");
		drawResourceCard(type, currentPlayer);
	}
	
	/**
	 * Draws the appropriate type of card from the bank.
	 * Precondition: Must have at least one card for this type of card.
	 */
	public void drawCard(int type) {
		resourceBank[type]--;
	}
	
	/**
	 * The current player draws a card from the pile of development cards.
	 * This is a random draw every time - depends on when they draw it because our
	 * instance of the generator is created again and again.
	 */
	public void drawDevCard(Player player) {
		Random generator = new Random();
		player.addDevCard(allDevCards.remove(generator.nextInt(allDevCards.size())));
	}
	
	/**
	 * Puts back the selected DevCard from 'player's collection.
	 */
	public void putDevCardBack(Player player, DevCard devCard) {
		if (!player.devCards.contains(devCard)) { return; }
		addDevCard(devCard.name, devCard.description);
		player.devCards.remove(devCard);
	}
	
	/**
	 * @param type
	 * Returns the appropriate card type back in to the bank.
	 */
	public void putResourceBack(int type) {
		putResourceBack(type, currentPlayer);
	}
	
	public void putResourceBack(int type, Player player) {
		System.out.println("Putting a card back.");
		if (player.removeCard(type)) { resourceBank[type]++; }
	}
	
	/**
	 * @param dieRoll
	 * Randomly draws cards for all players who have a settlement on the Hexagon with
	 * 'dieRoll' as its dieToRoll; if 'dieRoll' is 7 then nothing happens.
	 */
	public void drawFromRoll(int dieRoll) {
		if (dieRoll == 7) { return; }
		ArrayList<Settlement> playersToDraw = new ArrayList<Settlement>();
		Hexagon[] allSpaces = map.getAllSpaces();
		
		for (int i = 0; i < allSpaces.length; i++) {
			if (allSpaces[i].getDieToRoll() == dieRoll) { //If this Hexagon has the right number
				for (int j = 0; j < 6; j++) { //Look in all of the spots and add the settlement
					if (allSpaces[i].getOnSpot(j) != null) { //If it exists, of course
						playersToDraw.add(allSpaces[i].getOnSpot(j));
					}
				}
			}
		}
		
		Random generator = new Random();
		while (playersToDraw.size() > 0) {
			Settlement nextInLine = playersToDraw.remove(generator.nextInt(playersToDraw.size()));
			Player nextPerson = nextInLine.getOwner();
			int nextType = nextInLine.getType();
			drawResourceCard(nextType, nextPerson);
		}
		
	}
	
	/**
	 * @param hexagon
	 * @return Name of settlements for settlements on hexagon 'hexagon'
	 */
	public String[] getSettlementName(int hexagon) {
		return map.getSettlementNames(hexagon);
	}
	
	/**
	 * @param hexagon
	 * @return Name of roads for roads on hexagon 'hexagon'
	 */
	public String[] getRoadName(int hexagon) {
		return map.getRoadNames(hexagon);
	}
	
	/**
	 * @param hexagon
	 * @param space
	 * @param player
	 * Increases the draw amount for the settlement at 'hexagon's 'space' if there
	 * is a city there and it is owned by Player 'player'; otherwise, adds a settlement
	 * to onSpot 'space' if there is no settlement there.
	 */
	public void addCity(int hexagon, int space, Player player) {
		map.addDrawToHexagon(hexagon, space, player);
	}
	
	/**
	 * @param hexagon
	 * @param road
	 * @param player
	 * Sets the road of hexagon 'hexagon's 'road' to a new road if
	 * it does not currently have a road on it.
	 */
	public void addRoad(int hexagon, int road, Player player) {
		map.addRoad(hexagon, road, player);
	}
	
	/**
	 * @param hexagon
	 * @param settlement
	 * @param player
	 * Removes the settlement on hexagon 'hexagon' and the onSpot 'settlement'
	 * if Player 'player' owns it.
	 */
	public void removeCity(int hexagon, int settlement, Player player) {
		map.removeSettlement(hexagon, settlement, player);
	}
	
	/**
	 * @param hexagon
	 * @param road
	 * @param player
	 * Removes the road on hexagon 'hexagon' and the road 'road' if Player
	 * 'player' owns it.
	 */
	public void removeRoad(int hexagon, int road, Player player) {
		map.removeRoad(hexagon, road, player);
	}
	
	/**
	 * Advances the turn to the next player.
	 */
	public void nextTurn() {
		currentPlayerInt = (currentPlayerInt + 1) % allPlayers.length;
		currentPlayer = allPlayers[currentPlayerInt];
		onPlayer = currentPlayer.hashCode();
		rolledDie = false;
		rolledDice = null;
	}
	
	/**
	 * Starts the game by making the current player the first one in the array.
	 */
	public void begin() {
		currentPlayer = allPlayers[0];
		System.out.println("The first player is " + currentPlayer.name);
		onPlayer = currentPlayer.hashCode();
		currentPlayerInt = 0;
	}
	
	/**
	 * @param hash
	 * @return The Player with the associated hash 'hash'.
	 */
	public Player getPlayerFromHash(int hash) {
		for (int i = 0; i < allPlayers.length; i++) {
			if (allPlayers[i].hashCode() == hash) { return allPlayers[i]; }
		}
		return null;
	}
	
	/**
	 * Randomizes the order in which the players are in.
	 */
	public void randomizeOrder() {
		ArrayList<Player> settingUp = new ArrayList<Player>();
		for (int i = 0; i < allPlayers.length; i++) { settingUp.add(allPlayers[i]); }
		Random generator = new Random();
		Player[] newArrangement = new Player[allPlayers.length];
		int i = 0;
		while (hasNull(newArrangement)) {
			Player toAdd = settingUp.remove(generator.nextInt(settingUp.size()));
			newArrangement[i] = toAdd; i++;
		}
		allPlayers = newArrangement;
	}
	
	public String getOrder() {
		String order = "";
		for (int i = 0; i < allPlayers.length; i++) { order = order + allPlayers[i].name + " "; }
		return order;
	}
	
	/**
	 * @param player
	 * @return True if there is a null in 'player'.
	 */
	public boolean hasNull(Player[] player) {
		for (int i = 0; i < player.length; i++) { if (player[i] == null) { return true; } }
		return false;
	}
	
}
