package guiPrototype;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

public class VestigingKlantOverzicht extends JPanel {

	private static final long serialVersionUID = 1L;
	Collection<String> klantData;

	VestigingKlantOverzicht(Collection<String> klantData){
		this.setLayout(new BorderLayout());
		this.klantData = klantData;
		createOverview();
		createKlantTable();
	}

	public void createOverview(){
		JPanel columnPanel = new JPanel();
		columnPanel.setLayout(new GridLayout(1, 1, 0, 0));
		columnPanel.add(new JLabel("Klantaantal: "  + klantData.size()));

		this.add(columnPanel, BorderLayout.NORTH);
	}

	public void createKlantTable() {
		String[] columnNames = {"Rij", "Klantnr"};
		DefaultTableModel tableModel = new DefaultTableModel(columnNames, 0);
		Iterator<String> kIt = klantData.iterator();
		for(int i=0;i<klantData.size();i++) {
			tableModel.addRow(new String[]{String.valueOf(i), kIt.next()});
		}
		JTable klantTable = new JTable(tableModel);
		JScrollPane vestigingKlantOverzicht = new JScrollPane(klantTable);

		this.add(vestigingKlantOverzicht, BorderLayout.CENTER);
	}
}
