package gui.vestigingoverzicht;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Controller;
import observer.Observer;
import observer.Subject;

/**
 * Vestiging overzichtcomponent
 */
public class VestigingOverzicht extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Controller fc;
	private HashMap<String, VestigingKlantOverzicht> vestigingKlantOverzichtLijst;
	Collection<String> vestigingPlaatsNamen;
	Collection<String> vestigingKlantData;
	private List<JButton> VestigingLijst = new ArrayList<>();

	/**
	 * Initialiseert het overzicht met vestigingen
	 * 
	 * @param fc de facade controller
	 */
	public VestigingOverzicht(Controller fc) {
		this.setLayout(new BorderLayout());
		this.fc = fc;
		init();
	}

	/**
	 * Initialiseert het overzicht Haalt vestiging plaatsnamen op maakt hier buttons
	 * van en voegt deze toe aan het component aan de linkerkant
	 */
	public void init() {
		this.vestigingKlantOverzichtLijst = new HashMap<>();
		JPanel jpVestigingLijst = new JPanel();
		jpVestigingLijst.setLayout(new GridLayout(0, 1));
		vestigingPlaatsNamen = fc.getVestigingPlaatsen();
		toonVestigingKlantenListener tvkListener = new toonVestigingKlantenListener();

		for (String plaats : vestigingPlaatsNamen) {
			JButton knop = new JButton(plaats);
			VestigingLijst.add(knop);
			jpVestigingLijst.add(knop);
			knop.addActionListener(tvkListener);
			vestigingKlantData = fc.getVestigingKlanten(plaats);
			vestigingKlantOverzichtLijst.put(plaats, new VestigingKlantOverzicht(vestigingKlantData));
		}
		
		this.add(jpVestigingLijst, BorderLayout.WEST);
	}

	/**
	 * Toont in een lijst alle klanten van opgegeven vestiging maakt overzicht van
	 * klanten en voegt deze in het midden van het component toe
	 * 
	 * @param plaats plaatsnaam van gekozen vestiging
	 */
	public void toonVestigingKlanten(String plaats) {
		Collection<String> vestigingKlantData = fc.getVestigingKlanten(plaats);
		BorderLayout bLayout = (BorderLayout) VestigingOverzicht.this.getLayout();
		Component cCache;

		cCache = bLayout.getLayoutComponent(BorderLayout.CENTER);
		if (cCache != null) {
			VestigingOverzicht.this.remove(cCache);
		}

		VestigingOverzicht.this.add(vestigingKlantOverzichtLijst.get(plaats), BorderLayout.CENTER);
	}

	/**
	 * Event handler als een vestiging is gekozen Zorgt voor het maken van een
	 * rapport van klanten van de gekozen vestiging
	 */
	public class toonVestigingKlantenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton selectedButton = (JButton) e.getSource();
			String plaats = selectedButton.getText();
			toonVestigingKlanten(plaats);
			selectedButton.setFocusPainted(false);

			// geeft alleen de geselecteerde button een kleur
			for (JButton button : VestigingLijst) {
				button.setBackground(null);
				button.setForeground(null);
			}
			selectedButton.setBackground(Color.BLUE);
			selectedButton.setForeground(Color.WHITE);

			VestigingOverzicht.this.revalidate();
			VestigingOverzicht.this.repaint();
		}
	}

	@Override
	public void update(Subject s, Object arg) {
		VestigingKlantOverzicht crrntOverzicht;
		for(String vestigingNaam: vestigingPlaatsNamen) {
			vestigingKlantData = fc.getVestigingKlanten(vestigingNaam);
			crrntOverzicht = vestigingKlantOverzichtLijst.get(vestigingNaam);
			((Observer) crrntOverzicht).update(s, vestigingKlantData);
		}
	}
}
