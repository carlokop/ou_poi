package gui.Main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Footer extends JPanel{
	private static final long serialVersionUID = 1L;
	JButton stopActivityButton;
	
	public Footer() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		stopActivityButton = new JButton("Huidige inzage stoppen.");
		stopActivityButton.setBackground(Color.RED);
		stopActivityButton.setForeground(Color.WHITE);

		this.add(stopActivityButton);
		this.setVisible(false);
	}
	
	public void attachStopInzageListener(ActionListener l){
		stopActivityButton.addActionListener(l);
	}
}
