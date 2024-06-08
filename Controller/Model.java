package Controller;

import java.util.Collection;

import ObserverPatroon.Observer;
import gui.View;

/**
 * Markeer interface voor de controller/facade
 */
public interface Model {
  
  /**
   * regelt welke view wordt getoond
   * @param view de gekozen view
   */
  public void toonView(View view);

  /**
   * Voegt observer toe 
   * @param observer
   */
  public void attach(Observer observer);

  /**
   * geeft een lijst met plaatsnamen van vestigingen
   * @return lijst met plaatsnamen
   */
  public Collection<String> getVestigingPlaatsen();

  /**
   * Geeft de klanten voor de opgegeven vestiging
   * @param plaats
   * @return lijst met klant id's
   */
  public Collection<Integer> getVestigingKlanten(String plaats);

  
}
