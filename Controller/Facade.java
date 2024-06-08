package Controller;

import java.util.Collection;

import ObserverPatroon.Observer;
import gui.View;

public class Facade {
	
	Model m;
	
	public Facade(Model m){
		this.m = m;
	}
	
	/**
	 * Regelt welke view getoond moet worden
	 * @param view
	 */
	public void toonView(View view) {
	  m.toonView(view);
	}
	
	/**
	 * Voegt observer toe
	 * @param observer
	 */
	public void attach(Observer observer) {
	  m.attach(observer);
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
