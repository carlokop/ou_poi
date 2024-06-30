package gui.vestigingoverzicht;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.Collection;
import java.util.Iterator;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;

import observer.Observer;
import observer.Subject;

/**
 * Klantenoverzicht component
 */
public class VestigingKlantOverzicht extends JPanel implements Observer{

	private static final long serialVersionUID = 1L;
	Collection<String> klantData;
	
	String[] COLUMN_NAMES = {"Rij", "Klantnr"};

	DefaultTableModel tableModel;
	JTable klantTable;
	JScrollPane vestigingKlantOverzicht;
	JLabel labelKlantAantal;
	
	/**
	 * Initialiseert een overzicht met klantdata
	 * @param klantData lijst met klant id's
	 */
	VestigingKlantOverzicht(Collection<String> klantData){
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
	 * updates de klanten tabel met klantdata
	 * @param klantData lijst met klantnummers
	 */
	public void updateKlantTable(Collection<String> klantData){
		Iterator<String> kIt = klantData.iterator();
		tableModel.setRowCount(0);
		for(int i=0;i<klantData.size();i++) {
			tableModel.addRow(new String[]{String.valueOf(i), kIt.next()});
		}
	}

	/**
	 * Updates het klantenoverzicht na wijzigingen in de observer
	 * @param s het subject
	 * @param arg argumenten
	 */
	@Override
	public void update(Subject s, Object arg) {
		// Hoeft geen typecheck uit te voeren, kan slechts op 1 manier momenteel
		Collection<String> newKlantData = (Collection<String>) arg;
		updateKlantTable(newKlantData);
		labelKlantAantal.setText("Klantaantal: " + newKlantData.size());
	}
}
