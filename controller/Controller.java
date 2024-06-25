package controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import domein.Vestiging;
import exceptions.PoiException;
import gui.Plugin.visualizer.VisualizerControllerInterface;

/**
 * Controller
 * regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller implements VisualizerControllerInterface {
	
	private ModelBedrijf mb;
	private ModelBedrijfssimulatie mbs;
	
	/**
	 * TODO: Aanpassen voor het model van de simulatie
	 * Instantieert de controller en stelt het model in
	 * @param mb  ModelBedrijf
	 * @param mbs ModelBedrijfssimulatie
	 */
	public Controller(ModelBedrijf mb, ModelBedrijfssimulatie mbs){
		this.mb = mb;
		this.mbs = mbs;
	}

	/**
	 * Haalt alle plaatsnamen van vestigingen op
	 * @return lijst met plaatsnamen
	 */
	public Collection<String> getVestigingPlaatsen() {
		return mb.getVestigingPlaatsen();
	}
	
	/**
	 * Haalt een lijst op met alle klant id's die in de opgegeven vestiging zitten
	 * @param plaats vestiging plaatsnaam
	 * @return lijst met klant id's
	 */
	public Collection<String> getVestigingKlanten(String plaats) {
		return mb.getVestigingKlanten(plaats);
	}

	public void startSimulatie() throws PoiException {
		mbs.setupSimulatie();
	}
	
	//TODO implementeren voor visualizer
	@Override
	public void barClicked(String plaatsnaam, Integer aantal_klanten) {
	   //we moeten testen of ergens afvangen dat plaatsnaam niet null is of iets waar geen vestiging van is
	   //testen of afdwingen dat aantal_klanten niet null of negatief kan zijn
	   if(aantal_klanten == 0) {
	     mbs.openVestiging(plaatsnaam);
	     System.out.println("Heropen vestiging: " + plaatsnaam + ";" + aantal_klanten);
	     
	     //wat als er geen vestiging wordt gevonden?
	   } else {
	     System.out.println("Sluit vestiging: " + plaatsnaam + ";" + aantal_klanten);
	     mbs.sluitVestiging(plaatsnaam);
	   }
	}

	/**
	 * Geeft een map met key value pairs plaatsnaam en het aantal klanten in die plaats
	 * @return lijst met plaatsnamen met het aantal klanten
	 */
	@Override
	public Map<String, Integer> getBarInfo() {  
        return mbs.getSimVestigingenMap();
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
} //class





