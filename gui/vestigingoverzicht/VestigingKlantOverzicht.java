package gui.vestigingoverzicht;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Iterator;
import java.util.List;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

/**
 * Klantenoverzicht component
 */
public class VestigingKlantOverzicht extends JPanel {

	private static final long serialVersionUID = 1L;
	private List<String> klantData;

	private String[] COLUMN_NAMES = {"Rij", "Klantnr"};

	private DefaultTableModel tableModel;
	private JTable klantTable;
	private JScrollPane vestigingKlantOverzicht;
	private JLabel labelKlantAantal;

	/**
	 * Initialiseert een overzicht met klantdata
	 * @param klantData lijst met klant id's
	 */
	VestigingKlantOverzicht(List<String> klantData){
		this.setLayout(new BorderLayout());
		this.klantData = klantData;
		createOverview();
		createKlantTable();
	}

	/**
	 * Maakt een JPanel en vult die met het aantal klanten
	 * Dit wordt bovenaan de component geplaatst
	 */
	public void createOverview(){
		JPanel columnPanel = new JPanel();
		columnPanel.setLayout(new GridLayout(1, 1, 0, 0));
		labelKlantAantal = new JLabel("Klantaantal: "  + klantData.size());
		columnPanel.add(labelKlantAantal);
		this.add(columnPanel, BorderLayout.NORTH);
	}

	/**
	 * Maakt een tabel met klantinformatie
	 * en voegt die toe in het midden van het componnent
	 * Dit bevat twee rijen: een rijnummer en de klantnummers van de klanten in gekozen vestiging
	 */
	public void createKlantTable() {
		tableModel = new DefaultTableModel(COLUMN_NAMES, 0);
		Iterator<String> kIt = klantData.iterator();
		for(int i=0;i<klantData.size();i++) {
			tableModel.addRow(new String[]{String.valueOf(i), kIt.next()});
		}
		klantTable = new JTable(tableModel);
		vestigingKlantOverzicht = new JScrollPane(klantTable);

		this.add(vestigingKlantOverzicht, BorderLayout.CENTER);
	}

	/**
	 * updates de klanten tabel met een nieuwe klanten tabel
	 * @param newKlantData de nieuwe klantentabel
	 */
	public void updateKlantTable(List<String> newKlantData){
		Iterator<String> kIt = newKlantData.iterator();
		tableModel.setRowCount(0);
		for(int i=0;i<newKlantData.size();i++) {
			tableModel.addRow(new String[]{String.valueOf(i), kIt.next()});
		}
		labelKlantAantal.setText("Klantaantal: " + newKlantData.size());
	}
}
