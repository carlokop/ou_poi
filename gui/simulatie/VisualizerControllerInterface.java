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
  
  public void barClicked(String naam, Integer aantal);
  
  public Map<String, Integer> getBarInfo();

  public boolean isVestigingOpen(String name);
  
  public void setVisualizerActive(boolean isActive);
  
  public boolean getVisualizerActive();
  
  public void setActiveVestigingPlaatsnaam(String plaatsnaam);

  public String getActiveVestigingPlaatsnaam();
}

