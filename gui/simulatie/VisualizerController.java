package gui.simulatie;

import java.util.Map;
import java.util.TreeMap;

/**
 * Voorbeeld van een implementatie klasse
 * @author Medewerker OU 
 * 
 *
 */
public class VisualizerController implements VisualizerControllerInterface {

  

  @Override
  public void barClicked(String naam, Integer aantal) {
   System.out.println(naam + "  " + aantal);
    
  }

  @Override
  public Map<String, Integer> getBarInfo() {  
    return null;
  }

}
