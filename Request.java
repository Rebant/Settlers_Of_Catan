
/**
 * @author supervisor
 * Requests are passed to a server for handling game requests.
 */
public class Request implements Comparable<Request> {
	
	/*
	 * Things to request
	 * =================
	 * 0. Setup a player - 99
	 * 1. Draw a Dev card
	 * 2. Roll dice - covers drawing from roll
	 * 3. Draw a resource card
	 * 4. Put back resource card
	 * 5. Next turn
	 * 6. Remove a Dev card
	 * 7. Add settlement
	 * 8. Add city
	 * 9. Add road
	 * 999. Error - print the message
	 * 1001. Start the game
	 * 1002. Update stats for clients
	 */
	
	private boolean goodRequest = false;
	private int request;
	private String requestString;
	private int whichPlayer;
	private int type;
	private String message;
	private int returnName;
	private int hexagonSelected;
	private int onSpotSelected;
	private int roadsSpotSelected;
	private String chatText;
	private int[] mapTypes;
	private int[] resourceStats;

	public void setRequest(int request) {
		this.request = request;
	}
	
	public int getRequest() {
		return request;
	}
	
	public void setRequestString(String requestString) {
		this.requestString = requestString;
	}
	
	public String getRequestString() {
		return requestString;
	}
	
	public void setWhichPlayer(int whichPlayer) {
		this.whichPlayer = whichPlayer;
	}
	
	public int getWhichPlayer() {
		return whichPlayer;
	}
	
	public void setType(int type) {
		this.type = type;
	}
	
	public int getType() {
		return type;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setReturnName(int returnName) {
		this.returnName = returnName;
	}
	
	public int getReturnName() {
		return returnName;
	}

	public int getHexagonSelected() {
		return hexagonSelected;
	}

	public void setHexagonSelected(int hexagonSelected) {
		this.hexagonSelected = hexagonSelected;
	}

	public int getOnSpotSelected() {
		return onSpotSelected;
	}

	public void setOnSpotSelected(int onSpotSelected) {
		this.onSpotSelected = onSpotSelected;
	}

	public int getRoadsSpotSelected() {
		return roadsSpotSelected;
	}

	public void setRoadsSpotSelected(int roadsSpotSelected) {
		this.roadsSpotSelected = roadsSpotSelected;
	}
	
	public String getChatText() {
		return chatText;
	}

	public void setChatText(String chatText) {
		this.chatText = chatText;
	}
	
	
	public int[] getMapTypes() {
		return mapTypes;
	}

	public void setMapTypes(int[] mapTypes) {
		this.mapTypes = mapTypes;
	}

	public int[] getResourceStats() {
		return resourceStats;
	}

	public void setResourceStats(int[] resourceStats) {
		this.resourceStats = resourceStats;
	}

	// TODO Do I need this?
	@Override
	public int compareTo(Request o) {
		// TODO Auto-generated method stub
		return 1;
	}

}
