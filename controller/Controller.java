package controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import domein.Vestiging;
import exceptions.PoiException;
import gui.simulatie.VisualizerControllerInterface;
import observer.Subject;

/**
 * Controller regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller extends Subject implements ModelBedrijf, VisualizerControllerInterface {

	private ModelBedrijf mb;
	private boolean visualizerActive = false;
	private String activeVestiging = "";

	/**
	 * TODO: Aanpassen voor het model van de simulatie Instantieert de controller en
	 * stelt het model in
	 * 
	 * @param mb  ModelBedrijf
	 * @param mbs ModelBedrijfssimulatie
	 */
	public Controller(ModelBedrijf mb) {
		this.mb = mb;
	}

	/**
	 * Haalt alle plaatsnamen van vestigingen op
	 * 
	 * @return lijst met plaatsnamen
	 */
	public Collection<String> getVestigingPlaatsen() {
		return mb.getVestigingPlaatsen();
	}

	/**
	 * Haalt een lijst op met alle klant id's die in de opgegeven vestiging zitten
	 * 
	 * @param plaats vestiging plaatsnaam
	 * @return lijst met klant id's
	 */
	public Collection<String> getVestigingKlanten(String plaats) {
		return mb.getVestigingKlanten(plaats);
	}

	@Override
	public void barClicked(String plaatsnaam, Integer aantal_klanten) {
		if (!mb.isVestigingOpen(plaatsnaam)) {
		  System.out.println("Status voor opening:" + plaatsnaam + ", " + aantal_klanten);
			mb.openVestiging(plaatsnaam);
			notifyObservers();
		} else {
			System.out.println("Status voor sluiting:" + plaatsnaam + ", " + aantal_klanten);
			mb.sluitVestiging(plaatsnaam);
			notifyObservers();
		}
	}

	/**
	 * Geeft een map met key value pairs plaatsnaam en het aantal klanten in die
	 * plaats
	 * 
	 * @return lijst met plaatsnamen met het aantal klanten
	 */

	@Override
	public Map<String, Integer> getBarInfo() {
		return this.getVestigingenMap();
	}

	@Override
	public void sluitVestiging(String plaats) {
		mb.sluitVestiging(plaats);
		notifyObservers();
	}

	@Override
	public void openVestiging(String plaats) {
		mb.openVestiging(plaats);
		notifyObservers();
	}

	@Override
	public Map<String, Integer> getVestigingenMap() {
		return mb.getVestigingenMap();
	}

	@Override
	public boolean isVestigingOpen(String plaatsnaam) {
		return mb.isVestigingOpen(plaatsnaam);
	}
	
	
	/**
	 * Sets visualizer state 
	 * @param isActive  sets of de visualizer getoond moet worden
	 */
	@Override
	public void setVisualizerActive(boolean isActive) {
	  this.visualizerActive = isActive;
	  notifyObservers();
	}
	
	/**
     * gets visualizer state 
     * @param isActive  sets of de visualizer getoond moet worden
     */
	@Override
    public boolean getVisualizerActive() {
      return this.visualizerActive;
    }
	
	/**
     * Sets visualizer state 
     * @param plaatsnaam  sets of de visualizer getoond moet worden
     */
    @Override
    public void setActiveVestigingPlaatsnaam(String plaatsnaam) {
      this.activeVestiging = plaatsnaam;
      notifyObservers();
    }
    
    /**
     * gets plaatsnaam active vestiging 
     * @param isActive  sets of de visualizer getoond moet worden
     */
    @Override
    public String getActiveVestigingPlaatsnaam() {
      return this.activeVestiging;
    }






}
