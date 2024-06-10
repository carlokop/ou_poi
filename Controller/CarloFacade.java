package Controller;

import java.util.Collection;

public class CarloFacade {

  Model m;
  //ViewSelection vs;
  
  public CarloFacade(Model m){
      this.m = m;
  }

  /**
   * Haalt alle plaatsnamen van vestigingen op
   * @return lijst met plaatsnamen
   */
  public Collection<String> getVestigingPlaatsen() {
    return m.getVestigingPlaatsen();
  }
  
  /**
   * Haalt een lijst op met alle klant id's die in de opgegeven vestiging zitten
   * @param plaats
   * @return lijst met klant id's
   */
  public Collection<Integer> getVestigingKlanten(String plaats) {
    return m.getVestigingKlanten(plaats);
  }
  
  /**
   * Geeft afsluitsignaal die de verbinding verbreekt
   */
  public void sluitAf() {
    m.sluitAf();
  }

}
