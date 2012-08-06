
public class DevCard {

	String name;
	String description;
	
	public DevCard(String name, String description) {
		this.name = name;
		this.description = description;
	}
	
	
	public String toString() {
		return name + ":\n" + description;
	}
	
}
