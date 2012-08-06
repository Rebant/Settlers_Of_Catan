
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
	
	
	/**
	 * @param numSpaces
	 * Creates an instance of Map with 'numSpaces' Hexagons.
	 */
	public Map(int numSpaces) {
		allSpaces = new Hexagon[numSpaces];
	}
	
	
	
	
	public Hexagon[] getAllSpaces() {
		return allSpaces;
	}

}