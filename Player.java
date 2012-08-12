import java.util.ArrayList;


public class Player {

	public String name;
	public int numOfCards;
	public int[] allCards; //Wood, Sheep, Brick, Rock, Wheat
	public int victoryPoints;
	ArrayList<DevCard> devCards;
	public long time;
	
	/**
	 * Creates an instance of Player with no cards and victory points.
	 */
	public Player() {
		numOfCards = 0;
		allCards = new int[5];
		devCards = new ArrayList<DevCard>();
		victoryPoints = 0;
	}
	
	/**
	 * @param name
	 * Creates an instance of Player with name 'name', and no cards and victory points.
	 */
	public Player(String name) {
		this();
		this.name = name;
	}
	
	/**
	 * 
	 * @param time
	 * @param name
	 * Creates an instance of Player with time 'time', and no cards and victory points.
	 */
	public Player(long time, String name) {
		this(name);
		this.time = time;
	}
	
	/**
	 * @param type
	 * Adds the appropriate card type to this player's collection.
	 */
	public void addCard(int type) {
		allCards[type]++;
		numOfCards++;
	}

	/**
	 * @param type
	 * @return True if can remove the card, false otherwise
	 * Tries to remove the appropriate card type from this player's collection
	 * and if it cannot, returns false; otherwise true.
	 */
	public boolean removeCard(int type) {
		if (allCards[type] <= 0) { return false; }
		allCards[type]--; numOfCards--;
		return true;
	}
	
	/**
	 * Adds a victory point for this player.
	 */
	public void addVictoryPoint() {
		victoryPoints++;
	}
	
	/**
	 * Removes a victory point from this current player.
	 */
	public void removeVictoryPoint() {
		victoryPoints--;
	}
	
	/**
	 * @param devCard
	 * Adds the devCard to this player.
	 */
	public void addDevCard(DevCard devCard) {
		devCards.add(devCard);
	}
	
	public String allCardsString() {
		String toReturn = "" + allCards[0];
		for (int i = 1; i < 5; i++) {
			toReturn = toReturn + ", " + allCards[i];
		}
		return "[" + toReturn + "]";
	}
	
	public int[] getAllCards() {
		return allCards;
	}
	
	
	public String toString() {
		return name + "\nNumber of Cards: " + numOfCards + "\n" + "Cards (Wood, Sheep, Brick, Rock, Wheat)\n" + allCardsString() +
				"\nVictory Points: " + victoryPoints + "\nDev Cards " + devCards.toString();
	}
	
	public int hashCode() {
		return name.hashCode() + (int) time;
	}
	
	public boolean equals(Object object) {
		if (object == null) { return false; }
		if (!(object instanceof Player)) { return false; }
		Player isSame = (Player) object;
		if (isSame.hashCode() == this.hashCode()) { return true; }
		return false;
	}
	
}
