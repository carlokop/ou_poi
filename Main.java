import javax.swing.SwingUtilities;

import Controller.Facade;
import Domein.Bedrijf;
import gui.Gui;
import gui.VestigingKlanten;

public class Main {

  /**
   * Maakt gui, controller, model en koppelt deze.
   * @param args
   */
  public static void main(String[] args) {
     
    SwingUtilities.invokeLater(new Runnable() {
      @Override
      public void run() {
        Bedrijf bedrijf = new Bedrijf();
        Facade facade = new Facade(bedrijf);
        Gui gui = new Gui(facade);
        
        bedrijf.attach(gui);
      }
    });
  
    
  }
  
  
  
  
}
