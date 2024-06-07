package Controller;

import java.util.Collection;

import ObserverPatroon.Observer;

public class Facade {
	
	Model m;
	
	public Facade(Model m){
		this.m = m;
	}
	
	/**
	 * Regelt welke view getoond moet worden
	 * @param view
	 */
	public void toonVestigingOverzicht(String view) {
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
	
	
}
