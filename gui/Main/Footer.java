package gui.Main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JPanel;

public class Footer extends JPanel{
	
	JButton stopActivityButton;
	
	public Footer() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));

		stopActivityButton = new JButton("Huidige activiteit stoppen.");
		stopActivityButton.setBackground(Color.RED);
		stopActivityButton.setForeground(Color.WHITE);

		this.add(stopActivityButton);
		this.setVisible(false);
	}
	
	public void attachStopActivityListener(ActionListener l){
		stopActivityButton.addActionListener(l);
	}
}
