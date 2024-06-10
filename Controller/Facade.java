package Controller;

import java.util.Collection;

import Gui.ViewSelection;

public class Facade {
	
	Model m;
	ViewSelection vs;
	
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
	 * @param plaats
	 * @return lijst met klant id's
	 */
	public Collection<Integer> getVestigingKlanten(String plaats) {
	  return m.getVestigingKlanten(plaats);
	}
		
}
