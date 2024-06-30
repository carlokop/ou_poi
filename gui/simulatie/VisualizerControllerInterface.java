package gui.simulatie;

import java.util.Map;

/**
 * 
 * Intermediair tussen de userinterface (klasse Visualizer met daarop bars (staafjes)) 
 * en de domeinklassen 
 * @author Mederwerker OU
 *
 */
public interface VisualizerControllerInterface {
  
  /**
   * Bepaald als er op een bar in de visualizer wordt geklikt of een vestiging gesloten of heropend moet worden
   * Een vestiging wordt gesloten als die open is en heropend als die gesloten is
   * deze functie print tevens het aantal klanten in de vestiging naar het console
   * @param naam naam bij de bar
   * @param aantal  het aantal dat de bar hoog is
   */
  public void barClicked(String naam, Integer aantal);
  
  /**
  * Geeft een map met key value pairs plaatsnaam en het aantal klanten in die
  * plaats
  * 
  * @return lijst met namen met een gegeven aantal
  */
  public Map<String, Integer> getBarInfo();

  
}

