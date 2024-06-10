package Controller;

import java.util.Collection;

import ObserverPatroon.Observer;

/**
 * Markeer interface
 */
public interface Model {

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

  /**
   * Sluit de applicatie af
   */
  public void sluitAf();

}
