package gui7;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JPanel;

import controller.Facade;

public class VestigingOverzicht extends JPanel{

	private static final long serialVersionUID = 1L;
	Facade fc;
	HashMap<String, Component> vestigingKlantOverzicht;

	
	VestigingOverzicht(Facade fc){
		this.setLayout(new BorderLayout());
		this.setSize(500, 500);
		this.fc = fc;
		this.vestigingKlantOverzicht = new HashMap<>();
		
	}
	
	public void createVestigingLijst() {
		JPanel jpVestigingLijst = new JPanel();
		jpVestigingLijst.setLayout(new GridLayout(0, 1));
		Collection<String> vestigingPlaatsData = fc.getVestigingPlaatsen();

		toonVestigingKlantenListener tvkListener = new toonVestigingKlantenListener();
		
		// maak alle knoppen
		for (String plaats : vestigingPlaatsData) {
			JButton knop = new JButton(plaats);
			jpVestigingLijst.add(knop);
			knop.addActionListener(tvkListener);
		}
		this.add(jpVestigingLijst, BorderLayout.WEST);
	}
	
	public class toonVestigingKlantenListener implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton selectedButton = (JButton) e.getSource();
			String plaats = selectedButton.getText();
			Component vestigingKlantComponent;
			Collection<String> vestigingKlantData = fc.getVestigingKlanten(plaats);

			if (!vestigingKlantOverzicht.containsKey(plaats)) {
				vestigingKlantOverzicht.put(plaats, new VestigingKlantOverzicht(vestigingKlantData));
			}

			System.out.println("Test");
			vestigingKlantComponent = vestigingKlantOverzicht.get(plaats);
			VestigingOverzicht.this.add(vestigingKlantComponent, BorderLayout.CENTER);
		}
	}
}
