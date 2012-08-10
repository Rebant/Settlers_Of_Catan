
/**   
 *                            Original Game
 *                                  __
                                 __/0 \__
                              __/1 \__/2 \__
                             /3 \__/4 \__/5 \
                             \__/6 \__/7 \__/
                             /8 \__/9 \__/10\
                             \__/11\__/12\__/
                             /13\__/14\__/15\
                             \__/16\__/17\__/
                                \__/18\__/
                                   \__/
 * 
 * 
 */
public class Map {

	Hexagon[] allSpaces;
	
	
	public Map() {
		//Nothing
	}
	
	/**
	 * @param numSpaces
	 * Creates an instance of Map with 'numSpaces' Hexagons.
	 */
	public Map(int numSpaces) {
		allSpaces = new Hexagon[numSpaces];
	}
	
	/**
	 * @param hexagon
	 * @param dieToRoll
	 * @param type
	 * Sets up hexagon 'hexagon' to have the roll 'dieToRoll' and
	 * type of 'type'.
	 */
	public void setHexagon(int hexagon, int dieToRoll, int type) {
		if (allSpaces[hexagon] != null) { return; }
		allSpaces[hexagon] = new Hexagon(dieToRoll, type);
	}
	
	/**
	 * @param hexagon
	 * @param space
	 * @param player
	 * Sets the onSpot of hexagon 'hexagon's 'space' to a new settlement with one
	 * draw if it does not currently have a settlement on it.
	 */
	public void setNewSettlement(int hexagon, int space, Player player) {
		allSpaces[hexagon].setSpace(space, player);
	}
	
	/**
	 * @param hexagon
	 * @param road
	 * @param player
	 * Sets the road of hexagon 'hexagon's 'road' to a new road if
	 * it does not currently have a road on it.
	 */
	public void addRoad(int hexagon, int road, Player player) {
		allSpaces[hexagon].setRoad(road, player);
	}
	
	/**
	 * @param hexagon
	 * @param space
	 * @param player
	 * Adds a draw to hexagon 'hexagon's 'space'.
	 */
	public void addDrawToHexagon(int hexagon, int space, Player player) {
		allSpaces[hexagon].addDraw(space, player);
	}
	
	public Hexagon[] getAllSpaces() {
		return allSpaces;
	}
	
}