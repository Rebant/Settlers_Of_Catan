
public class Hexagon {

	/*
	 * An instance has the following properties
	 * ========================================
	 * Each Hexagon has an appropriate resource type starting from 0 to 4
	 * as: Wood, Sheep, Brick, Rock, Wheat; if the type is -1, then it is
	 * a desert hexagon
	 * Starting from the top left corner, the corners are numbered starting
	 * at 0 and go clockwise incrementing by 1 as shown in the diagram below
    		0__1
   		   5/  \2
   		   4\__/3
   	 * There is a number for each Hexagon inside that states the roll which
   	 * gives a player on that Hexagon that resource
	 */
 
	
	public Settlement[] onSpot; //Which settlement is on this spot with a house or city
	public int dieToRoll; //Number needed to get this resource
	public int type; //Type of resource one can get from this Hexagon - Wood, Sheep, Brick, Rock, Wheat
	
	/**
	 * Creates an instance of Hexagon with no players on any spot.
	 */
	public Hexagon() {
		onSpot = new Settlement[6];
	}
	
	/**
	 * @param dieToRoll
	 * Creates an instance of Hexagon with the dieToRoll number as
	 * 'dieToRoll' and no settlements on any spot.
	 */
	public Hexagon(int dieToRoll) {
		this();
		this.dieToRoll = dieToRoll;
	}
	
	/**
	 * @param dieToRoll
	 * @param type
	 * Creates an instance of Hexagon with the resource type 'type', dieToRoll number as
	 * 'dieToRoll', and no settlements on any spot.
	 */
	public Hexagon(int dieToRoll, int type) {
		this(dieToRoll);
		this.type = type;
	}
	
	/**
	 * @param type
	 * Creates an instance of Hexagon with type -1.
	 */
	public Hexagon(String desert) {
		this.type = -1;
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
