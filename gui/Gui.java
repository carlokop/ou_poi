package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import exceptions.PoiExceptionCode;
import gui.Main.Footer;
import gui.Main.Header;
import gui.VestigingAnyOverzichtPlugin.visualizer.Visualizer;
import gui.VestigingOverzicht.VestigingOverzicht;
import observerOU.Observer;
import observerOU.Subject;

/**
 * Standaardframe van de Grafische userinterface
*/
public class Gui extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	// Hoofdgui
	private Controller fc = null;
	private Container pane;
	private Component header;
	private Component footer;
	private static final int PANE_WIDTH = 1200;
	private static final int PANE_HEIGHT = 520;
	
	// Domeingui
	private Component vestigingOverzicht;
	private Component vestigingSimulatieOverzicht;
	
	private Visualizer visualizer; // visualizer is een subcomponent van een domeingui, deze hoort niet gedeclareerd te worden hier
	

	/**
	 * Initialiseert en stelt de contentpane in
	 * @param fc facade controller
	 */
	public Gui(Controller fc) {
		super();
		this.fc = fc;
		pane = getContentPane();
		init();
	}
	
	/**
	 * Toont gui met kritische foutmelding die het systeem afsluit
	 * Deze constructor aanroepen als er in main exepties ontstaan 
	 * @param error de foutmelding
	 */
	public Gui(PoiExceptionCode error) {
      super();
      pane = getContentPane();
      init();
      toonNotificatie(error.getErrMessage(),true, "Applicatie afsluiten"); //TODO onnodige koppeling met Exception, string is genoeg?
  }
	
	/**
     * Gets the width in px of the content pane
     * @return the width
     */
    public static int getPaneWidth() {
      return PANE_WIDTH;
    }
    
    /**
     * Gets the height in px of the content pane
     * @return the height
     */
    public static int getPaneHeight() {
      return PANE_HEIGHT;
    }

	/**
	 * Initialiseert de contentpane met een header, footer en een gedeelte in het midden
	 * In het middelste stuk wordt een vestiging onverzicht geplaatst
	 */
	public void init() {
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
		((Footer) footer).attachStopActivityListener(new stopActiviteitListener());
		pane.add(footer, BorderLayout.SOUTH);       
                
        this.pane.revalidate();
        this.pane.repaint();

	}

	/**
	 * Handler voor tonen van kiesvestiging taak
	 */
	class VestigingenInzageMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		    stopActiviteit();
	        if(vestigingOverzicht == null && fc != null) {
	          vestigingOverzicht = new VestigingOverzicht(fc);
	        }
			pane.add(vestigingOverzicht, BorderLayout.WEST);
			footer.setVisible(true);
			Gui.this.pane.revalidate();
			Gui.this.pane.repaint();
		}
	}

	/**
	 * Dit is voor taak 5 wordt later geimplementeerd
	 * Zorgt dat het frame voor het simuleren om vestigingen te sluiten en heropenen
	 */
	class VestigingenSimulatieMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		  stopActiviteit();
//		  if(visualizer == null && fc != null) {
//    		  visualizer= new Visualizer(fc); 
//		  }
//		  
//		  pane.add(visualizer,BorderLayout.CENTER);
//		  footer.setVisible(true);

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
	 * Dit sluit alle actieve use cases en brengt de GUI terug naar de begintoestand
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
		footer.setVisible(false);
		this.pane.revalidate();
		this.pane.repaint();
	}
	
	/**
	 * Updates wijzigingen in de observers
	 */
	@Override
	public void update(Subject s, Object arg) {
		// Taak 5
	}
	
	/**
	* TODO heeft nog geen functie
	* Toont sluitbare notificatie
	* @param bericht het bericht in de notificatie
	*/
	private void toonNotificatie(String bericht) {
	    toonNotificatie(bericht, false, "mmkay");
	}
	
	/**
	 * Toont een foutmelding op het scherm
	 * Er wordt om bevestiging gevraagd en sluit daarna de applicatie af
	 * @param bericht  de string met de foutmelding
	 * @param sluitAf  true sluit de applicatie af
	 * @param btnText  de tekst in de button
	 */
  	private void toonNotificatie(String bericht, boolean sluitAf, String btnText) {
      // Maak de dialog
      JDialog notificatieDialog = new JDialog();
      notificatieDialog.setTitle("Foutmelding");
      notificatieDialog.setLayout(new BorderLayout());
      notificatieDialog.setSize(300, 125);
      notificatieDialog.setVisible(true);
      //dit even opgezocht zag er niet fraai uit
      notificatieDialog.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
      notificatieDialog.setLocationRelativeTo(pane);
    
      //bericht
      JTextArea notificatieLabel = new JTextArea(bericht);
      notificatieDialog.add(notificatieLabel, BorderLayout.CENTER);
      notificatieLabel.setBackground(null);
      //dit even opgezocht zag er niet fraai uit
      notificatieLabel.setLineWrap(true); 
      notificatieLabel.setWrapStyleWord(true);
    
      //sluitknop
      JButton sluitKnop = new JButton(btnText);
      notificatieDialog.add(sluitKnop, BorderLayout.SOUTH);
      
      //sluit de applicatie als je op de sluitknop klikt
      sluitKnop.addActionListener(new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
          notificatieDialog.dispose();
          if(sluitAf) {
            System.exit(ABORT);
          }
        }
      });
      
      //sluit de applicatie af als je het sluit kruisje gebruikt
      notificatieDialog.addWindowListener(new WindowAdapter() {
        @Override
        public void windowClosing(WindowEvent e) {
          notificatieDialog.dispose();
          if(sluitAf) {
            System.exit(ABORT);
          }
        }
      });
      
 
  }
	
}
