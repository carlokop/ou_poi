package guiPrototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import controller.Facade;
import observerPatroon.Observer;

/**
 * Standaardframe van de Grafische userinterface
*/
public class Gui extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private Facade fc = null;
	private Container pane;
	private Component header, footer;
	private Component vestigingOverzicht;

	/**
	 * Initialiseert en stelt de contentpane in
	 * @param fc facade controller
	 */
	public Gui(Facade fc) {
		super();
		this.fc = fc;
		pane = getContentPane();
		init();
	}

	/**
	 * Initialiseert de contentpane met een header, footer en een gedeelte in het midden
	 * In het middelste stuk wordt een vestiging onverzicht geplaatst
	 */
	public void init() {
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(600, 500);
		setTitle("Practicum Ontwerpen en implementeren");
		pane.setBackground(Color.white);
		pane.setLayout(new BorderLayout());

		header = createHeader();
		pane.add(header, BorderLayout.NORTH);

		footer = createFooter();
		pane.add(footer, BorderLayout.SOUTH);

		vestigingOverzicht = new VestigingOverzicht(fc);
	}

	/**
	 * Voegt header met hoofdmenu toe aan de contentpane
	 * @return de header
	 */
	private Container createHeader() {
		JMenuBar headermenu = new JMenuBar();
		headermenu.setLayout(new FlowLayout(FlowLayout.LEFT));
		headermenu.setBackground(new Color(163, 175, 192));
		JMenu mainMenu = new JMenu("Activiteit");
		JMenuItem jmiInzageVestiging = new JMenuItem("Start Inzage Vestiging");
		JMenuItem jmiVestigingenSluiten = new JMenuItem("Vestigingen sluiten");

		mainMenu.add(jmiInzageVestiging);
		mainMenu.add(jmiVestigingenSluiten);

		jmiInzageVestiging.addActionListener(new VestigingMenuItemLuisteraar());
		jmiVestigingenSluiten.addActionListener(new VestigingenSluitenMenuItemLuisteraar());

		headermenu.add(mainMenu);

		return headermenu;
	}

	/**
	 * Voegt een footer toe aan de content pane met een sluit button
	 * @return de footer
	 */
	private Component createFooter() {
		JPanel footer = new JPanel();
		footer.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton sluitbtn = new JButton("Huidige activiteit stoppen.");
		sluitbtn.setBackground(Color.RED);
		sluitbtn.setForeground(Color.WHITE);
		sluitbtn.addActionListener(new stopActiviteitListener());

		footer.add(sluitbtn);
		return footer;
	}

	/**
	 * Handler voor tonen van kiesvestiging taak
	 */
	class VestigingMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    stopActiviteit();
			pane.add(vestigingOverzicht, BorderLayout.WEST);
			Gui.this.pane.revalidate();
			Gui.this.pane.repaint();
		}
	}

	/**
	 * Dit is voor taak 5 wordt later geimplementeerd
	 */
	class VestigingenSluitenMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		  stopActiviteit();
		}
	}

	/**
	 * Handler voor de sluit rapport knop
	 */
	class stopActiviteitListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		  stopActiviteit();
		}
	}

	/***
	 * Maakt de layout m.u.v. de header en footer leeg 
	 * Dit sluit alle actieve use cases en brengt de GUI terug naar de begintoestant
	 */
	public void stopActiviteit() {
		BorderLayout bLayout = (BorderLayout) this.pane.getLayout();
		Component cCache;
		cCache = bLayout.getLayoutComponent(BorderLayout.WEST);
		if (cCache != null) {
			this.pane.remove(cCache);
		}
		cCache = bLayout.getLayoutComponent(BorderLayout.CENTER);
		if (cCache != null) {
			this.pane.remove(cCache);
		}
		cCache = bLayout.getLayoutComponent(BorderLayout.EAST);
		if (cCache != null) {
			this.pane.remove(cCache);
		}
		this.pane.revalidate();
		this.pane.repaint();
	}
	
	/**
	 * Updates wijzigingen in de observers
	 */
	@Override
	public void update() {
		// Taak 5
	}
}
