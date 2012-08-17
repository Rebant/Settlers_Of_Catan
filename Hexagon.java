
public class Hexagon {

	/*
	 * An instance has the following properties
	 * ========================================
	 * Each Hexagon has an appropriate resource type starting from 0 to 4
	 * as: Wood, Sheep, Brick, Rock, Wheat; if the type is -1, then it is
	 * a desert hexagon
	 * Starting from the top left corner, the vertices are numbered starting
	 * at 0 and go clockwise incrementing by 1 as shown in the diagram below
	 * and represent the places where the settlements are
    		   0__1                            _0_
   		      5/  \2    Settlements | Roads  5/   \1
   		       \__/                          4\_3_/2
   		       4  3
   	 * There is a number for each Hexagon inside that states the roll which
   	 * gives a player on that Hexagon that resource
   	 * Roads are round between the 'onSpot's - they are to the right of each
   	 * of the numbers on the edges - for example, road 0 is in the middle of
   	 * 'onSpot' 0 and 1; essentially, the sides of the hexagon correspond to
   	 * a road as shown in the diagram above
   	 * A bandit can be on the spot and prevents all settlements on the slot
   	 * from collecting any resources
	 */
 
	
	public Settlement[] onSpot; //Which Settlement is on this spot with a house or city
	public Road[] roads; //Which Road is on this road
	public int dieToRoll; //Number needed to get this resource
	public int type; //Type of resource one can get from this Hexagon - Wood, Sheep, Brick, Rock, Wheat
	public boolean bandit; //True if bandit is on the spot; false otherwise
	
	/**
	 * Creates an instance of Hexagon with no players on any spot.
	 */
	public Hexagon() {
		onSpot = new Settlement[6];
		roads = new Road[6];
	}
	
	/**
	 * @param dieToRoll
	 * Creates an instance of Hexagon with the dieToRoll number as
	 * 'dieToRoll' and no settlements on any spot; if 'dieToRoll' is
	 * -1, then this hexagon is a desert
	 */
	public Hexagon(int dieToRoll) {
		this();
		if (dieToRoll == -1) { type = -1; }
		this.dieToRoll = dieToRoll;
	}
	
	/**
	 * @param dieToRoll
	 * @param type
	 * Creates an instance of Hexagon with the resource type 'type', dieToRoll
	 * number as 'dieToRoll', and no settlements on any spot; if 'dieToRoll' is
	 * -1, then this hexagon is a desert.
	 */
	public Hexagon(int dieToRoll, int type) {
		this(dieToRoll);
		this.type = type;
	}
	
	/**
	 * @param space
	 * @param player
	 * Sets the onSpot of 'space' to a new settlement with one draw if it
	 * currently does not have a settlement on it.
	 */
	public void setSpace(int space, Player player) {
		if (onSpot[space] != null) { return; }
		System.out.println("Someone just bought a settlement.");
		onSpot[space] = new Settlement(1, type, player);
	}
	
	/**
	 * @param road
	 * @param player
	 * Sets the road of 'road' to a new road with owner 'player' if
	 * it currently does not have a road on it.
	 */
	public void setRoad(int road, Player player) {
		if (roads[road] != null) { return; }
		System.out.println("Someone just bought a road.");
		roads[road] = new Road(player);
	}
	
	/**
	 * @param space
	 * @param player
	 * Increases the draw amount for the settlement at 'hexagon's 'space' if there
	 * is a city there and it is owned by Player 'player'; otherwise, adds a settlement
	 * to onSpot 'space' if there is no settlement there.
	 */
	public void addDraw(int space, Player player) {
		if (onSpot[space] != null) {
			if (player.equals(onSpot[space].getOwner())) {
				System.out.println("Someone just bought a city.");
				onSpot[space].incrementAmountToDraw();
			}
			else { return; }
		}
		if (onSpot[space] == null) {
			setSpace(space, player);
		}
	}
	
	
	/**
	 * @return The names of all the Players that each of the onSpot's are owned by.
	 */
	public String[] getSettlementNames() {
		String[] toReturn = new String[6];
		for (int i = 0; i < 6; i++) {
			toReturn[i] = onSpot[i] == null || onSpot[i].getOwner().getName() == null ? "Not bought" : onSpot[i].getOwner().getName();
			toReturn[i] = i + ": " + toReturn[i];
		}
		return toReturn;
	}
	
	/**
	 * @return The names of all the Players that each of the roads' are owned by
	 */
	public String[] getRoadNames() {
		String[] toReturn = new String[6];
		for (int i = 0; i < 6; i++) {
			toReturn[i] = roads[i] == null || roads[i].getOwner().getName() == null ? "Not bought" : roads[i].getOwner().getName();
			toReturn[i] = i + ": " + toReturn[i];
		}
		return toReturn;
	}

	public void removeSettlement(int onSpot, Player player) {
		if (this.onSpot[onSpot] == null || !this.onSpot[onSpot].getOwner().equals(player)) { return; }
		this.onSpot[onSpot] = null;
	}
	
	public void removeRoad(int road, Player player) {
		if (this.roads[road] == null || !this.roads[road].getOwner().equals(player)) { return; }
		roads[road] = null;
	}
	
	public Settlement[] getOnSpot() {
		return onSpot;
	}
	
	public Settlement getOnSpot(int spot) {
		return onSpot[spot];
	}

	public int getType() {
		return type;
	}

	public int getDieToRoll() {
		return dieToRoll;
	}
	
	
	
	
	
	
	
	
}
