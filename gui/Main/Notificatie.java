package gui.Main;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JTextArea;
import javax.swing.border.EmptyBorder;

public class Notificatie extends JDialog {

    private static final long serialVersionUID = 1L;
  
    /**
     * Toont een sluitbare notificatie
     * @param bericht  het bericht wat getoond moet worden
     */
    public Notificatie(String bericht) {
      this(bericht, false, "MMKAY");
    }
    
    /**
     * Toont een sluitbare notificatie niet eventueel de applicatie afsluit
     * @param bericht   het te tonen bericht
     * @param sluitAf   true om de applicatie af te sluiten
     * @param btnText   de text in de sluit button
     */
    public Notificatie(String bericht, boolean sluitAf, String btnText) {
      init();
      toonNotificatie(bericht, sluitAf, btnText);
    }
  
    /**
     * Inits de notificatie
     */
    private void init() {
      this.setTitle("Foutmelding");
      this.setLayout(new BorderLayout());
      this.setSize(300, 125);
      this.setVisible(true);
      //dit even opgezocht zag er niet fraai uit
      this.getRootPane().setBorder(new EmptyBorder(10, 10, 10, 10));
    }
  
   /**
    * Toont een foutmelding op het scherm
    * Er wordt om bevestiging gevraagd en sluit daarna de applicatie af
    * @param bericht  de string met de foutmelding
    * @param sluitAf  true sluits de applicatie af
    * @param btnText  de tekst in de button
    */
   private void toonNotificatie(String bericht, boolean sluitAf, String btnText) {
   
     //bericht
     JTextArea notificatieLabel = new JTextArea(bericht);
     this.add(notificatieLabel, BorderLayout.CENTER);
     notificatieLabel.setBackground(null);
     //dit even opgezocht zag er niet fraai uit
     notificatieLabel.setLineWrap(true); 
     notificatieLabel.setWrapStyleWord(true);
        
     //sluitknop
     JButton sluitKnop = new JButton(btnText);
     this.add(sluitKnop, BorderLayout.SOUTH);
     
     //sluit de applicatie als op de sluitknop wordt geklikt
     sluitKnop.addActionListener(new ActionListener() {
     @Override
     public void actionPerformed(ActionEvent e) {
         dispose();
         if(sluitAf) {
           System.exit(ABORT);
         }
       }
     });
     
     //sluit de applicatie af als je het sluit kruisje gebruikt
     this.addWindowListener(new WindowAdapter() {
       @Override
       public void windowClosing(WindowEvent e) {
         dispose();
         if(sluitAf) {
           System.exit(ABORT);
         }
       }
     });
     
     
   }
 

}
