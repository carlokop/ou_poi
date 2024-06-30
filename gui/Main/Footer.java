package gui.Main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * Maakt een footer element als jpanel
 */
public class Footer extends JPanel{
	private static final long serialVersionUID = 1L;
	JButton stopActivityButton;
	
	/**
	 * Instantieert de footer
	 */
	public Footer() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		//maak een stopknop
		stopActivityButton = new JButton("Huidige inzage stoppen.");
		stopActivityButton.setBackground(Color.RED);
		stopActivityButton.setForeground(Color.WHITE);

		this.add(stopActivityButton);
		this.setVisible(false);
	}
	
	/**
	 * voegt actionlistener toe aan de stopbutton
	 * @param l de actionlistener
	 */
	public void attachStopInzageListener(ActionListener l){
		stopActivityButton.addActionListener(l);
	}
}
