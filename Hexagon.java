
public class Hexagon {

	/*
	 * An instance has the following properties
	 * ========================================
	 * Each Hexagon has an appropriate resource type starting from 0 to 4
	 * as: Wood, Sheep, Brick, Rock, Wheat; if the type is -1, then it is
	 * a desert hexagon
	 * Starting from the top left corner, the corners are numbered starting
	 * at 0 and go clockwise incrementing by 1 as shown in the diagram below
    		0__1                            _0_
   		   5/  \2    Settlements | Roads  5/   \1
   		   4\__/3                         4\_3_/2
   	 * There is a number for each Hexagon inside that states the roll which
   	 * gives a player on that Hexagon that resource
   	 * Roads are round between the 'onSpot's - they are to the right of each
   	 * of the numbers on the edges - for example, road 0 is in the middle of
   	 * 'onSpot' 0 and 1.
	 */
 
	
	public Settlement[] onSpot; //Which settlement is on this spot with a house or city
	public Road[] roads;
	public int dieToRoll; //Number needed to get this resource
	public int type; //Type of resource one can get from this Hexagon - Wood, Sheep, Brick, Rock, Wheat
	
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
		roads[road] = new Road(player);
	}
	
	/**
	 * @param space
	 * @param player
	 * Increments the amount drawn on the settlement on space 'space' if the
	 * player owns the spot.
	 */
	public void addDraw(int space, Player player) {
		if (onSpot[space] == null || !player.equals(onSpot[space].getOwner())) { return; }
		onSpot[space].incrementAmountToDraw();
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
