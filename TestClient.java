import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextField;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryonet.Client;
import com.esotericsoftware.kryonet.Connection;
import com.esotericsoftware.kryonet.Listener;


public class TestClient extends JFrame {

	public Client client;
	JTextField blah = new JTextField();
	JButton blah2 = new JButton("Press me");
	
	public static void main(String[] args) {
		new TestClient("192.168.2.7", 54555, 54777);
	}
	
	public TestClient(String ipAddress, int portIn, int portOut) {
		setLayout(new GridLayout(1, 2));
		blah = new JTextField();
		blah2 = new JButton("Press me");
		blah2.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println("Client was pressed");
				Request bleh = new Request();
				bleh.setMessage(blah.getText());
				client.sendTCP(bleh);
				System.out.println("We sent something!");
			}
		});
		add(blah);
		add(blah2);
		
		setSize(300, 300);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		
		client = new Client();
		
		Kryo kryo = client.getKryo();
		kryo.register(Request.class);
		
		client.start();
		
		
		
		try {
			client.connect(5000, "192.168.2.7", 54555, 54777);
			System.out.println("We connected!");
		} catch (IOException e1) {
			System.out.println("Could not connect.");
		}
		
		client.addListener(new Listener() {
			public void received (Connection connection, Object object) {
				if (object instanceof Request) {
					Request response = (Request) object;
					changeText(response.getMessage());
				}
			}
		});

		setVisible(true);

	}
	
	public void changeText(String text) {
		blah.setText(text);
	}
	
	
}
