import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.PriorityQueue;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class ClientSide {

	public Client client; //To connect with the server
	public int nameHash; //The name for this client
	public ClientSideGUIFinal theGUI;
	
	public static void main(String[] args) throws IOException {
//		InputStreamReader istream = new InputStreamReader(System.in);
//		BufferedReader reader = new BufferedReader(istream);
//		
//		String ipAddress;
//		System.out.println("Input the IP Address you want to connect to:");
//		ipAddress = reader.readLine();
//		
//		int portIn = 0;
//		int portOut = 0;
//		boolean in = false;
//		while (true) {
//			try {
//				if (!in) { System.out.println("Enter the port in:"); portIn = Integer.parseInt(reader.readLine()); in = !in; }
//				else { System.out.println("Enter the port out:"); portOut = Integer.parseInt(reader.readLine()); break; }
//			}
//			catch (NumberFormatException nfe) {
//				System.out.println("Please enter an integer.");
//			}
//		}
//		
//		System.out.println("What is your name?");
//		String name = reader.readLine();
//		
//		new ClientSide(ipAddress, portIn, portOut, name);
		
		new ClientSide("127.0.0.1", 53777, 54777, "Rebant");
	}
	
	
	public ClientSide(String ipAddress, int portIn, int portOut, String name) {
		client = new Client();
		
				
		Kryo kryo = client.getKryo();
		kryo.register(Request.class);
		kryo.register(int[].class);
		
		client.start();
		
		
		System.out.println("Trying to connect to the server...");
		try {
			client.connect(5000, ipAddress, portIn, portOut);
		} catch (IOException e1) {
			System.out.println("Could not connect.");
			System.exit(-100);
		}
		System.out.println("We have connected to the server!");
		
		
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof Request) {
					Request response = (Request) object;
					handleRequest(response);
				}
			}
		});
		
		Request nameRequest = new Request();
		nameRequest.setRequest(0);
		nameRequest.setRequestString(name);
		client.sendTCP(nameRequest);
		System.out.println("I should have sent it...");
		
		while (true) {
			//Bogus line so this continues forever...
			//TODO Better memory management
		}
		
		
	}
	
	public void handleRequest(Request toHandle) {
		int request = toHandle.getRequest();
		System.out.println("Handling request: " + request);
		switch (request) {
			case 99: nameHash = toHandle.getReturnName(); System.out.println("Our hash is " + nameHash); break;
			case 999: System.out.println("Error: " + toHandle.getMessage()); break;
			case 1001: System.out.println("Woot."); theGUI = new ClientSideGUIFinal(this, nameHash, toHandle.getMapTypes()); break;
			case 1002: System.out.println("Updating stats..."); theGUI.updateResourceNumber(toHandle.getResourceStats()); break;
			default: System.out.println("Something bad happened."); System.exit(-100); break;
		}
		
		
	}
	
	public void sendRequest(Request request) {
		request.setWhichPlayer(nameHash);
		client.sendTCP(request);
	}
	
	
}
