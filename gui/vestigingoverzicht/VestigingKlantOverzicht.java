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

import controller.Controller;
import observer.Observer;
import observer.Subject;

/**
 * Klantenoverzicht component
 */
public class VestigingKlantOverzicht extends JPanel implements Observer {

	private static final long serialVersionUID = 1L;
	private Collection<String> klantData;
	
	private String[] COLUMN_NAMES = {"Rij", "Klantnr"};

	private DefaultTableModel tableModel;
	private Controller fc;
	private JTable klantTable;
	private JLabel klantaantal;
	private String plaatsnaam;
	
	/**
	 * Initialiseert een overzicht met klantdata
	 * @param klantData lijst met klant id's
	 */
	VestigingKlantOverzicht(Collection<String> klantData, String plaatsnaam){
		this.setLayout(new BorderLayout());
		this.klantData = klantData;
		this.plaatsnaam = plaatsnaam;
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
		klantaantal = new JLabel(plaatsnaam + " Klantaantal: "  + klantData.size());
		columnPanel.add(klantaantal);
		
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
		JScrollPane vestigingKlantOverzicht = new JScrollPane(klantTable);
		
		this.add(vestigingKlantOverzicht, BorderLayout.CENTER);
	}

	public void updateKlantTable(Collection<String> klantData){
		Iterator<String> kIt = klantData.iterator();
		tableModel.setRowCount(0);
		for(int i=0;i<klantData.size();i++) {
			tableModel.addRow(new String[]{String.valueOf(i), kIt.next()});
		}
		//String plaatsnaam = fc.getActiveVestigingPlaatsnaam();
        //klantaantal = new JLabel(plaatsnaam + " Klantaantal: "  + klantData.size());
	}
	
	@Override
	public void update(Subject s, Object arg) {
		//updateKlantTable((Collection<String>) arg);
		//System.out.println("plaats: "+fc.getActiveVestigingPlaatsnaam());
	}
	
	
	/**
	 * Dit werkt niet goed om een of andere reden wordt de verkeerde active plaats getoond
	 * @param klanten
	 * @param plaatsnaam
	 * @param activePlaats
	 */
	public void pasAan(Collection<String> klanten, String plaatsnaam, String activePlaats) {
//	  System.out.println("pas aan : "+activePlaats);
//	  updateKlantTable((Collection<String>) klanten);
//      klantaantal = new JLabel(activePlaats + " Klantaantal: "  + klantData.size());

	}
	
	
	
	
}
