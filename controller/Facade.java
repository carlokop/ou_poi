package controller;

import java.util.Collection;

/**
 * Facade controller
 */
public class Facade {
	
	private Model m;
	
	/**
	 * Inistantieert de facade en stelt het model in
	 * @param m    model
	 */
	public Facade(Model m){
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
	 * @param plaats vestiging plaatsnaam
	 * @return lijst met klant id's
	 */
	public Collection<String> getVestigingKlanten(String plaats) {
	  return m.getVestigingKlanten(plaats);
	}

}
