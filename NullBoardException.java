
public class NullBoardException extends Exception {

	/** IDK	 */
	private static final long serialVersionUID = 1L;
	
	String error;
	
	public NullBoardException(String error) {
		super();
		this.error = error;
	}
	
}
