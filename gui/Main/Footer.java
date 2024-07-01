package gui.Main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

/**
 * maakt een footer met een sluitknop 
 */
public class Footer extends JPanel{
	private static final long serialVersionUID = 1L;
	JButton stopActivityButton;

	/**
	 * instantieert de footer
	 */
	public Footer() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		//voeg jbutton toe aan de footer
		stopActivityButton = new JButton("Huidige inzage stoppen.");
		stopActivityButton.setBackground(Color.RED);
		stopActivityButton.setForeground(Color.WHITE);

		this.add(stopActivityButton);
		this.setVisible(false);
	}

	/**
	 * voegt eventlistener toe voor de footer button
	 * @param l eventlistener
	 */
	public void attachStopInzageListener(ActionListener l){
		stopActivityButton.addActionListener(l);
	}
}
