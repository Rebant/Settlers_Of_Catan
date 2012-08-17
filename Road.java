
public class Road {

	Player owner; //Owner of this road
	
	/**
	 * @param owner
	 * Creates an instance of Road with the owner 'owner'.
	 */
	public Road(Player owner) {
		this.owner = owner;
	}
	
	public Player getOwner() {
		return owner;
	}
}
