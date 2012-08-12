import javax.swing.JPanel;
import java.awt.FlowLayout;
import javax.swing.JCheckBox;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JSlider;
import java.awt.Color;
import javax.swing.JToggleButton;


public class CardPanel extends JPanel {
	
	public static void main(String[] args) {
		CardPanel a = new CardPanel();
		a.setVisible(true);
	}

	/**
	 * Create the panel.
	 */
	public CardPanel() {
		setLayout(null);
		
		CardPane woodPane = new CardPane("0", "images/woodLogo", "Lumber");
		woodPane.setBounds(0, 0, 120, 165);
		add(woodPane);
		
		CardPane sheepPane = new CardPane("0", "images/sheepLogo", "Sheep");
		sheepPane.setBounds(130, 0, 120, 165);
		add(sheepPane);
		
		CardPane brickPane = new CardPane("0", "images/brickLogo", "Brick");
		brickPane.setBounds(260, 0, 120, 165);
		add(brickPane);
		
		CardPane rockPane = new CardPane("0", "images/sheepLogo", "Rock");
		rockPane.setBounds(390, 0, 120, 165);
		add(rockPane);
		
		CardPane wheatPane = new CardPane("0", "images/wheatLogo", "Wheat");
		wheatPane.setBounds(520, 0, 120, 165);
		add(wheatPane);
		
		CardPane devCardPane = new CardPane("0", "images/devCardLogo", "Dev Card");
		devCardPane.setBounds(650, 0, 120, 165);
		add(devCardPane);
		
		JCheckBox chckbxMyTurn = new JCheckBox("My Turn");
		chckbxMyTurn.setEnabled(false);
		chckbxMyTurn.setBounds(0, 172, 94, 23);
		add(chckbxMyTurn);
		
		JButton btnRollDice = new JButton("Roll Dice");
		btnRollDice.setBounds(0, 202, 94, 23);
		add(btnRollDice);
		
		JLabel lblRolled = new JLabel("Rolled: ");
		lblRolled.setForeground(Color.RED);
		lblRolled.setBounds(104, 206, 46, 14);
		add(lblRolled);
		
		JToggleButton tglbtnRemove = new JToggleButton("Remove");
		tglbtnRemove.setBounds(-1, 236, 95, 23);
		add(tglbtnRemove);

	}
}
