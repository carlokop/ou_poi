package gui.Main;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenuBar;

import gui.simulatie.Visualizer;
import gui.vestigingoverzicht.VestigingOverzicht;

/**
 * Standaardframe van de Grafische userinterface
*/
public class ViewManager extends JFrame {

	private static final long serialVersionUID = 1L;

	// Hoofdgui
	private Container pane;
	private JMenuBar header;
	private Component footer;
	private static final int PANE_WIDTH = 600;
	private static final int PANE_HEIGHT = 600;

	// Domeingui
	private VestigingOverzicht vestigingOverzicht;	// vest gui
	private Visualizer visualizer; 			// simulatie gui

	/**
	 * Initialiseert en stelt de contentpane in
	 * @param vestigingOverzicht   het panel met vestigingoverzicht
	 * @param visualizer           het frame met de visualizer
	 */
	public ViewManager(VestigingOverzicht vestigingOverzicht, Visualizer visualizer) {
		super();
		pane = getContentPane();
		init();
		this.vestigingOverzicht = vestigingOverzicht;
		this.visualizer = visualizer;
	}

	/**
	 * Toont gui met kritische foutmelding die het systeem afsluit
	 * Deze constructor aanroepen als er in main exepties ontstaan
	 * @param error de foutmelding
	 */
	public ViewManager(String error) {
      super();
      pane = getContentPane();
      init();
      JDialog notificatie = new Notificatie(error, true, "Applicatie afsluiten");
      notificatie.setLocationRelativeTo(pane);
  }

	/**
	 * Initialiseert de contentpane met een header, footer en een gedeelte in het midden
	 * In het middelste stuk wordt een vestiging onverzicht geplaatst
	 */
	public void init() {
		// native components
		setVisible(true);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(PANE_WIDTH, PANE_HEIGHT);
		setTitle("Practicum Ontwerpen en implementeren");
		pane.setBackground(Color.white);
		pane.setLayout(new BorderLayout());

		header = new Header();

		((Header) header).attachJMIVestigingenInzageListener(new VestigingenInzageMenuItemLuisteraar());
		((Header) header).attachJMIVestigingenSimulatieListener(new VestigingenSimulatieMenuItemLuisteraar());
		pane.add(header, BorderLayout.NORTH);

		footer = new Footer();
		((Footer) footer).attachStopInzageListener(new stopInzageListener());
		pane.add(footer, BorderLayout.SOUTH);

        this.pane.revalidate();
        this.pane.repaint();

	}

	/**
	 * Handler voor tonen van kiesvestiging taak
	 */
	class VestigingenInzageMenuItemLuisteraar implements ActionListener {
	    /**
	     * regelt het tonen van het vestigingenoverzicht
	     */
		@Override
		public void actionPerformed(ActionEvent e) {
		    startInzage();
		}
	}

	/**
	 * Zorgt dat het frame voor het simuleren om vestigingen te sluiten en heropenen getoond wordt
	 */
	class VestigingenSimulatieMenuItemLuisteraar implements ActionListener {
	    /**
	     * maakt de visualizer actief 
	     */
		@Override
		public void actionPerformed(ActionEvent e) {
		  startSimulatie();
		}
	}

	/**
	 * Handler voor de sluit rapport knop
	 */
	class stopInzageListener implements ActionListener {
	    /**
	     * sluit de het scherm binnen deze gui 
	     */
		@Override
		public void actionPerformed(ActionEvent e) {
		  stopInzage();
		}
	}
	
	/**
	 * Start de inzage
	 */
	private void startInzage() {
	  stopInzage();
      pane.add(vestigingOverzicht, BorderLayout.WEST);
      footer.setVisible(true);
      ViewManager.this.pane.revalidate();
      ViewManager.this.pane.repaint();
	}
	
	/**
	 * Start de simulatie en toont de visualizer
	 */
	private void startSimulatie() {
	  visualizer.setVisible(true);
	}

	/***
	 * Maakt de layout m.u.v. de header en footer leeg
	 * Dit sluit alle actieve use cases en brengt de GUI terug naar de begintoestand
	 */
	public void stopInzage() {
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
		footer.setVisible(false);
		this.pane.revalidate();
		this.pane.repaint();
	}
}
