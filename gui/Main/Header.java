package gui.Main;

import java.awt.Color;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * Maakt een header met een menubalk 
 */
public class Header extends JMenuBar {

	private static final long serialVersionUID = 1L;
	JMenu mainMenu;
	JMenuItem jmiVestigingenInzage;
	JMenuItem jmiVestigingenSimulatie;
	
	/**
	 * Initialiseert de header en voegt daar menuitems aan toe
	 */
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
	
	/**
	 * Voegt een actionlistener toe aan het menuitem voor vestiging inzage
	 * @param l de actionlistenr
	 */
	public void attachJMIVestigingenInzageListener(ActionListener l){
		jmiVestigingenInzage.addActionListener(l);

	}
	
	/**
	 * voegt een actionlistener toe aan het menuitem voor de simulatie
	 * @param l de actionlistener
	 */
	public void attachJMIVestigingenSimulatieListener(ActionListener l){
		jmiVestigingenSimulatie.addActionListener(l);
	}
}
