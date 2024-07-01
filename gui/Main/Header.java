package gui.Main;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionListener;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 * voegt de header toe met een menubalk
 */
public class Header extends JMenuBar {

	private static final long serialVersionUID = 1L;
	JMenu mainMenu;
	JMenuItem jmiVestigingenInzage;
	JMenuItem jmiVestigingenSimulatie;

	/**
	 * instantieert de header
	 */
	public Header() {
		this.setLayout(new FlowLayout(FlowLayout.LEFT));
		this.setBackground(new Color(163, 175, 192));
		mainMenu = new JMenu("Activiteit");
		//voeg twee menuitems toe
		jmiVestigingenInzage = new JMenuItem("Start inzage vestigingen");
		jmiVestigingenSimulatie = new JMenuItem("Start simulatie vestigingen");

		mainMenu.add(jmiVestigingenInzage);
		mainMenu.add(jmiVestigingenSimulatie);

		this.add(mainMenu);
	}

	/**
	 * voeg eventlistener toe voor de vestiginginzage
	 * @param l de eventlistenen
	 */
	public void attachJMIVestigingenInzageListener(ActionListener l){
		jmiVestigingenInzage.addActionListener(l);

	}

	/**
	 * voegt eventlistener toe om de simulator te starten
	 * @param l de eventlistener
	 */
	public void attachJMIVestigingenSimulatieListener(ActionListener l){
		jmiVestigingenSimulatie.addActionListener(l);
	}
}
