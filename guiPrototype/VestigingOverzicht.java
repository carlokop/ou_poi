package guiPrototype;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Facade;

/**
 * Vestiging overzichtcomponent
 */
public class VestigingOverzicht extends JPanel {

	private static final long serialVersionUID = 1L;
	private Facade fc;
	private HashMap<String, Component> vestigingKlantOverzicht;

	/**
	 * Initialiseert het overzicht met vestigingen
	 * @param fc de facade controller
	 */
	VestigingOverzicht(Facade fc) {
		this.setLayout(new BorderLayout());
		this.fc = fc;
		this.vestigingKlantOverzicht = new HashMap<>();
		init();
	}

	/**
	 * Initialiseert het overzicht
	 * Haalt vestiging plaatsnamen op 
	 * maakt hier buttons van en voegt deze toe aan het component aan de linkerkant
	 */
	public void init() {
		JPanel jpVestigingLijst = new JPanel();
		jpVestigingLijst.setLayout(new GridLayout(0, 1));
		Collection<String> vestigingPlaatsData = fc.getVestigingPlaatsen();
		toonVestigingKlantenListener tvkListener = new toonVestigingKlantenListener();
		
		for (String plaats : vestigingPlaatsData) {
			JButton knop = new JButton(plaats);
			jpVestigingLijst.add(knop);
			knop.addActionListener(tvkListener);
		}
		this.add(jpVestigingLijst, BorderLayout.WEST);
	}

	/**
	 * Toont in een lijst alle klanten van opgegeven vestiging
	 * maakt overzicht van klanten en voegt deze in het midden van het component toe
	 * @param plaats plaatsnaam van gekozen vestiging
	 */
	public void toonVestigingKlanten(String plaats){
		Collection<String> vestigingKlantData = fc.getVestigingKlanten(plaats);
		BorderLayout bLayout = (BorderLayout) VestigingOverzicht.this.getLayout();
		Component cCache;
		
		cCache = bLayout.getLayoutComponent(BorderLayout.CENTER);
		if (cCache != null) {
			VestigingOverzicht.this.remove(cCache);
		}

		if (!vestigingKlantOverzicht.containsKey(plaats)) {
			vestigingKlantOverzicht.put(plaats, new VestigingKlantOverzicht(vestigingKlantData));
		}
	}
	
	/**
	 * Event handler als een vestiging is gekozen
	 * Zorgt voor het maken van een rapport van klanten van de gekozen vestiging
	 */
	public class toonVestigingKlantenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton selectedButton = (JButton) e.getSource();
			String plaats = selectedButton.getText();
			toonVestigingKlanten(plaats);
			VestigingOverzicht.this.add(vestigingKlantOverzicht.get(plaats), BorderLayout.CENTER);
			VestigingOverzicht.this.revalidate();
			VestigingOverzicht.this.repaint();
		}
	}
}
