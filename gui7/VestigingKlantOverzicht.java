package gui7;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Collection;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

public class VestigingKlantOverzicht extends JPanel {

	private int columnWidth = 250;
	private static final long serialVersionUID = 1L;
	Collection<String> klantData;
	
	VestigingKlantOverzicht(Collection<String> klantData){
		this.setLayout(new BorderLayout());
		this.klantData = klantData;
		createColumns();
		createRows();
	}
	
	public void createColumns(){
		JPanel columnPanel = new JPanel();
		columnPanel.setLayout(new GridLayout());
		columnPanel.add(new JLabel("Klantnr"));
		columnPanel.add(new JLabel("Overig"));
		columnPanel.setW.
		this.add(columnPanel, BorderLayout.NORTH);
	}
	
	public void createRows(){
		JPanel rowPanel = new JPanel();
		DefaultListModel<String> vestigingKlantModel = new DefaultListModel<>();
		vestigingKlantModel.addAll(klantData);
		JList<String> klantenLijst = new JList<>(vestigingKlantModel);
		JScrollPane vestigingKlantOverzicht = new JScrollPane(klantenLijst);
		vestigingKlantOverzicht.setSize(columnWidth, this.HEIGHT);
		this.add(vestigingKlantOverzicht, BorderLayout.CENTER);
	}
}
