
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
	 * Increases the draw amount for the settlement at 'hexagon's 'space' if there
	 * is a city there and it is owned by Player 'player'; otherwise, adds a settlement
	 * to onSpot 'space' if there is no settlement there.
	 */
	public void addDrawToHexagon(int hexagon, int space, Player player) {
		allSpaces[hexagon].addDraw(space, player);
	}
	
	/**
	 * @param hexagon
	 * @param settlement
	 * @param player
	 * Removes the settlement on hexagon 'hexagon' and the onSpot 'settlement'
	 * if Player 'player' owns it.
	 */
	public void removeSettlement(int hexagon, int settlement, Player player) {
		allSpaces[hexagon].removeSettlement(settlement, player);
	}
	
	/**
	 * @param hexagon
	 * @param road
	 * @param player
	 * Removes the road on hexagon 'hexagon' and the road 'road' if Player
	 * 'player' owns it.
	 */
	public void removeRoad(int hexagon, int road, Player player) {
		allSpaces[hexagon].removeRoad(road, player);
	}
	
	/**
	 * @return An array of all the types for the hexagons in order.
	 */
	public int[] getAllTypes() {
		int[] toReturn = new int[allSpaces.length];
		for (int i = 0; i < allSpaces.length; i++) {
			toReturn[i] = allSpaces[i].getType();
		}
		return toReturn;
	}
	
	/**
	 * @param hexagon
	 * @return Name of settlements for settlements on hexagon 'hexagon'
	 */
	public String[] getSettlementNames(int hexagon) {
		return allSpaces[hexagon].getSettlementNames();
	}
	
	/**
	 * @param hexagon
	 * @return Name of roads for roads on hexagon 'hexagon'
	 */
	public String[] getRoadNames(int hexagon) {
		return allSpaces[hexagon].getRoadNames();
	}

	public Hexagon[] getAllSpaces() {
		return allSpaces;
	}
	
}