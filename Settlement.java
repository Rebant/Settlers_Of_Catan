
public class Settlement {

	int amountToDraw;
	int type;
	Player owner;
	
	/**
	 * @param amountToDraw
	 * @param owner
	 * Creates an instance of Settlement with the amount to draw as 'amountToDraw',
	 * type 'type, and owner 'owner'.
	 */
	public Settlement(int amountToDraw, int type, Player owner) {
		this.amountToDraw = amountToDraw;
		this.type = type;
		this.owner = owner;
	}
	
	
	/**
	 * Increases the amount of cards to draw for this Settlement.
	 */
	public void incrementAmountToDraw() {
		amountToDraw++;
	}


	public int getType() {
		return type;
	}


	public Player getOwner() {
		return owner;
	}
	
	
}
