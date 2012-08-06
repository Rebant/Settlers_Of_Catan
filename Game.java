import java.util.ArrayList;
import java.util.Random;

public class Game {

	Player[] allPlayers;
	int[] resourceBank; //Wood, Sheep, Brick, Rock, Wheat
	ArrayList<DevCard> allDevCards = new ArrayList<DevCard>();
	Parser parser;
	
	/* Game stuff */
	Player currentPlayer; //Current player's turn
	Map map; //Map for this game
	int onPlayer;
	
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
	public void setupPlayer(String name) {
		for (int i = 0; i < allPlayers.length; i++) {
			if (allPlayers[i] == null) { allPlayers[i] = new Player(name); return; }
		}	
	}
	
	/**
	 * @param name
	 * @param description
	 * Adds a development card to this game.
	 */
	public void addDevCard(String name, String description) {
		allDevCards.add(new DevCard(name, description));
	}
	
	/* Game play actions */
	/**
	 * @return The two die rolls rolled by this player.
	 */
	public int[] rollDie() {
		Random generator = new Random();
		int[] rolledDie = {generator.nextInt(6) + 1, generator.nextInt(6) + 1};
		return rolledDie;
	}
	
	/**
	 * @param type
	 * @param player
	 * @return True if can draw the card, false otherwise
	 * Draws the appropriate type of card from the bank if there is at least one
	 * card of that type in the bank. 
	 */
	public boolean drawResourceCard(int type, Player player) {
		if (resourceBank[type] <= 0) { return false; }
		player.addCard(type); drawCard(type);
		return true;
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
	public void drawDevCard() {
		Random generator = new Random();
		currentPlayer.addDevCard(allDevCards.remove(generator.nextInt(allDevCards.size())));
	}
	
	/**
	 * @param type
	 * Returns the appropriate card type back in to the bank.
	 */
	public void putResourceBack(int type) {
		if (currentPlayer.removeCard(type)) { resourceBank[type]++; }
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
				for (int j = 0; j < 6; j++) { //Look in all of the spots and add the player
					if (allSpaces[i].getOnSpot(j) != null) {
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
	 * Advances the turn to the next player.
	 */
	public void nextTurn() {
		currentPlayer = allPlayers[(onPlayer + 1) % allPlayers.length];
	}

	
	
	
}
