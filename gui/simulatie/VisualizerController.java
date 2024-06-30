package gui.simulatie;

import java.util.Map;

/**
 * Voorbeeld van een implementatie klasse
 * @author Medewerker OU 
 * 
 *
 */
public class VisualizerController implements VisualizerControllerInterface {

  
  /**
   * Bepaald als er op een bar in de visualizer wordt geklikt of een vestiging gesloten of heropend moet worden
   * Een vestiging wordt gesloten als die open is en heropend als die gesloten is
   * deze functie print tevens het aantal klanten in de vestiging naar het console
   * @param naam    de naam van de bvar
   * @param aantal  het aantal dat de bar hoog is
   */
  @Override
  public void barClicked(String naam, Integer aantal) {
   System.out.println(naam + "  " + aantal);
    
  }

  /**
   * Geeft een map met key value pairs plaatsnaam en het aantal klanten in die
   * plaats
   * 
   * @return lijst met namen met het aantal dat de bar hoog is
   */
  @Override
  public Map<String, Integer> getBarInfo() {  
    return null;
  }

}
