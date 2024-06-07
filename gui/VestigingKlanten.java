package gui;


import java.awt.BorderLayout;
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
    
    this.add(col_links, BorderLayout.WEST);
    this.add(col_rechts, BorderLayout.EAST);
  }
  
  /**
   * Toont veld met keus om een vestiging te selecteren
   */
  private void toonVestigingenKeuze() {
    
    //links tekst uitleg en een dropdown met vestigingen
    col_links.setLayout(new FlowLayout(FlowLayout.LEFT));
    JLabel titel = new JLabel("Selecteer een vestiging");
    
    //get data en vull dropdown
    Collection<String> vestigingen = fc.getVestigingPlaatsen();
    
    /*
     * JCombobox accepteert geen arraylist met strings
     * Voer transformatie uit en voeg gelijk default waarde toe op positie 0
     */
    String[] vestigingen_lijst = new String[vestigingen.size()+1];
    vestigingen_lijst[0] = "---";
    int i = 1;
    for(String v:vestigingen) {
      vestigingen_lijst[i++] = v;
    }
    
    dropdown = new JComboBox<>(vestigingen_lijst);
    
    col_links.add(titel);
    col_links.add(dropdown);
    
    dropdown.addActionListener(new DropdownVestegingLuisteraar());
    
  }
   

  @Override
  public void update() {
    // TODO Auto-generated method stub
    System.out.println("Update in VestigingKlanten");
  }

  @Override
  public void update(String args) {
    if("klantenVestiging".equals(args)) {
      toonVestigingenKeuze();
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
      * Doe iets
      */
     String plaatsnaam = (String) dropdown.getSelectedItem();
     
     /**
      * Hier moet een aanroep naar een nieuwe functie komen die de klanten van de vestiging toont en het aan aantal berekend
      */
     
     System.out.println(plaatsnaam);  
   }
 }
  
  

}
