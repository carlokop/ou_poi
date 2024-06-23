package guiPrototype;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

import controller.Controller;
import exceptions.PoiExceptionCode;
import observerPatroon.Observer;

/**
 * Standaardframe van de Grafische userinterface
*/
public class Gui extends JFrame implements Observer {

	private static final long serialVersionUID = 1L;

	private Controller fc = null;
	private Container pane;
	private Component header;
	private JPanel footer;
	private Component vestigingOverzicht;
	private JPanel vizualizer;
	private static final int PANE_WIDTH = 1200;
	private static final int PANE_HEIGHT = 520;

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
      toonNotificatie(error.getErrMessage(),true, "Applicatie afsluiten");
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

		header = createHeader();
		pane.add(header, BorderLayout.NORTH);

		footer = createFooter();
		pane.add(footer, BorderLayout.SOUTH);       
                
        this.pane.revalidate();
        this.pane.repaint();

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
	private JPanel createFooter() {
		footer = new JPanel();
		footer.setLayout(new FlowLayout(FlowLayout.LEFT));

		JButton sluitbtn = new JButton("Huidige activiteit stoppen.");
		sluitbtn.setBackground(Color.RED);
		sluitbtn.setForeground(Color.WHITE);
		sluitbtn.addActionListener(new stopActiviteitListener());

		footer.add(sluitbtn);
		footer.setVisible(false);
		return footer;
	}

	/**
	 * Handler voor tonen van kiesvestiging taak
	 */
	class VestigingMenuItemLuisteraar implements ActionListener {
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
	class VestigingenSluitenMenuItemLuisteraar implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
		  stopActiviteit();
		  
		  if(vizualizer == null && fc != null) {
    		  vizualizer= new Visualizer(fc);
		  }
		  
		  pane.add(vizualizer,BorderLayout.CENTER);
		  footer.setVisible(true);

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
		footer.setVisible(false);
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
	
  
	
	
	
	
}  //class
