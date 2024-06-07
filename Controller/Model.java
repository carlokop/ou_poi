package Controller;

import java.util.Collection;

import ObserverPatroon.Observer;

/**
 * Markeer interface voor de controller/facade
 */
public interface Model {
  
  /**
   * regelt welke view wordt getoond
   * String moet enum worden 
   * @param view
   */
  public void toonView(String view);

  public void attach(Observer observer);

  public Collection<String> getVestigingPlaatsen();
  
  
}
