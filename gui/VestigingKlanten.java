package gui;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Collection;

import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import Controller.Facade;
import ObserverPatroon.Observer;

/**
 * Deze klasse regelt het tonen van klanten per verstigingen (Taak 4)
 * @author carlo
 *
 */
public class VestigingKlanten extends JPanel implements Observer {
  
  private JPanel col_links = null;
  private JPanel col_rechts = null;
  private JComboBox<String> dropdown;
  private static String DROPDOWNPLACEHOLDER = "---";
  
  private static final long serialVersionUID = 1L;
  private Facade fc = null;

  public VestigingKlanten(Facade fc) {
    super();
    this.fc = fc;
    fc.attach(this);
    init();
  }
  
  /**
   * Maakt de algemene design 
   * Een rij met twee kolommen naast elkaar
   * Links komen keuzemogelijkheden
   * Rechts komen de resultaten
   */
  private void init() {
    setLayout(new BorderLayout());
    col_links = new JPanel();
    col_rechts = new JPanel();
    
    col_links.setLayout(new FlowLayout(FlowLayout.LEFT));
    col_rechts.setLayout(new FlowLayout(FlowLayout.LEFT));
    
    this.add(col_links, BorderLayout.WEST);
    this.add(col_rechts, BorderLayout.CENTER);
  }
  
  /**
   * Maakt layout elementen voor het maken van het rapport van klanten per vestiging
   * Maakt een dropdown, haalt alle vestigingen op en vult deze met alle namen van vestiginginen 
   * en roept een eventlistener aan bij na het selecteren van een vestiging
   */
  private void startInzageVestiging() {
    
    //links tekst uitleg en een dropdown met vestigingen
    JLabel titel = new JLabel("Selecteer een vestiging");
    
    //get data en vull dropdown
    Collection<String> vestigingen = fc.getVestigingPlaatsen();
    
    /*
     * JCombobox accepteert geen arraylist met strings
     * Voer transformatie uit en voeg gelijk default waarde toe op positie 0
     */
    String[] vestigingen_lijst = new String[vestigingen.size()+1];
    vestigingen_lijst[0] = DROPDOWNPLACEHOLDER;
    int i = 1;
    for(String v:vestigingen) {
      vestigingen_lijst[i++] = v;
    }
    
    dropdown = new JComboBox<>(vestigingen_lijst);
    
    col_links.add(titel);
    col_links.add(dropdown);
    
    dropdown.addActionListener(new DropdownVestegingLuisteraar());
    
  }
   
  /**
   * Maakt het rapport van klanten voor de opgegeven vestiging
   * Dit rapport wordt in de col_rechts geplaatst
   * @param plaats  plaatsnaam van de vestiging
   */
  private void toonVestigingKlanten(String plaats) {
    if(!DROPDOWNPLACEHOLDER.equals(plaats)) {
      //haal klanten ids op van geselecteerde vestiging
      Collection<Integer> klantids = fc.getVestigingKlanten(plaats);
      
      JLabel label = new JLabel("Aantal klanten voor vestiging "+plaats + " :"+ klantids.size());
      col_rechts.add(label);  
    }
  }

  @Override
  public void update() {
    // TODO Auto-generated method stub
    System.out.println("Update in VestigingKlanten");
  }

  @Override
  public void update(View view) {
    if(View.VESTIGINGKLANTEN.equals(view)) {
      startInzageVestiging();
      revalidate();
    }
  }
  
  
  /**
  * Handler die na het selecteren van een vestiging toonVestigingKlanten aanroept
  * @author carlo
  *
  */
 class DropdownVestegingLuisteraar implements ActionListener {
   @Override
   public void actionPerformed(ActionEvent e) {
     /**
      * De plaatsnaam van het geselecteerde item wordt opgehaald en doorgegeven aan 
      * de methode die het rapport maakt voor het aantal klanten van de gegeven vestiging
      */
     String plaatsnaam = (String) dropdown.getSelectedItem();
     col_rechts.removeAll();
     toonVestigingKlanten(plaatsnaam);
     revalidate();
     repaint();
   }
 }
  
  

}
