import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;


public class CardPane extends JPanel {
	
	 public BufferedImage image; 
	 public JButton btnNewButton;
	 public JLabel lblNewLabel;

	/**
	 * Create the panel.
	 */
	public CardPane() {
		setLayout(null);
		
		JPanel panel = new JPanel();
		panel.setBounds(0, 0, 120, 130);
		add(panel);
		panel.setLayout(null);
		
		lblNewLabel = new JLabel("New label");
		lblNewLabel.setBounds(0, 0, 46, 14);
		panel.add(lblNewLabel);
		
		btnNewButton = new JButton("New button");
		btnNewButton.setBounds(0, 141, 120, 23);
		add(btnNewButton);

	}
	
	public CardPane(String lblNewLabel, String imageName) {
		this();
		this.lblNewLabel.setText(lblNewLabel);
		try {
			image = ImageIO.read(new File(imageName));
		}
		catch (Exception e) {
			
		}
	}
	
	public CardPane(String lblNewLabel, String imageName, String buttonLabel) {
		this(lblNewLabel, imageName);
		btnNewButton.setText(buttonLabel);
	}
	
	 public void setImage(String filename) {
		  try { image = ImageIO.read(new File(filename)); }
		  catch (Exception e) { System.out.println("Fail."); }
	  }
	  
	  public void paintComponent(Graphics g) 
	  {
	    g.drawImage(image, 0, 0, null); 
	    repaint(); 
	  }

}
