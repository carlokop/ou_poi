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

public class VestigingOverzicht extends JPanel {

	private static final long serialVersionUID = 1L;
	private Facade fc;
	private HashMap<String, Component> vestigingKlantOverzicht;

	VestigingOverzicht(Facade fc) {
		this.setLayout(new BorderLayout());
		this.fc = fc;
		this.vestigingKlantOverzicht = new HashMap<>();
		init();
	}

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
