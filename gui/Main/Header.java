package gui.Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

public class Header extends JMenuBar {

	JMenu mainMenu;
	JMenuItem jmiVestigingenInzage;
	JMenuItem jmiVestigingenSimulatie;
	
	public Header() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(163, 175, 192));
		mainMenu = new JMenu("Activiteit");
		jmiVestigingenInzage = new JMenuItem("Start inzage vestigingen");
		jmiVestigingenSimulatie = new JMenuItem("Start simulatie vestigingen");

		mainMenu.add(jmiVestigingenInzage);
		mainMenu.add(jmiVestigingenSimulatie);

		this.add(mainMenu);
	}
	
	public void attachJMIVestigingenInzage(ActionListener l){
		jmiVestigingenInzage.addActionListener(l);

	}
	
	public void attachJMIVestigingenSimulatie(ActionListener l){
		jmiVestigingenSimulatie.addActionListener(l);
	}
}
