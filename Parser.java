import java.io.File;
import java.io.IOException;
import java.util.Scanner;


public class Parser {
	
	Game game;
	Scanner scanner;
	public String currentLine;
	
	public Parser(Game game, String filename) throws NullBoardException {
		this.game = game;
		try {
			scanner = new Scanner(new File(filename));
		}
		catch (IOException ioe) { throw new NullBoardException("File not found."); }
		catch (Exception e) { throw new NullBoardException("Some error not accounted for."); }
		
		
		while (scanner.hasNextLine()) {
			currentLine = scanner.nextLine();
			determineType(currentLine);
		}
		
	}
	
	/**
	 * @param currentLine
	 * Determines the type of card being added to the game and adds it appropriately.
	 */
	public void determineType(String currentLine) {
		String[] stuff = currentLine.split(";");
		if (stuff[0].startsWith("/*")) { return; }
		if (stuff[0].equals("DC")) {
			game.addDevCard(stuff[1], stuff[2]);
		}
		else if (stuff[0].equals("Hex")) {
			int hexagon = Integer.parseInt(stuff[1]);
			int dieToRoll = Integer.parseInt(stuff[2]);
			int type = Integer.parseInt(stuff[3]);			
			game.setHexagon(hexagon, dieToRoll, type);
		}
	}

}
