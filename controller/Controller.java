package controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import gui.VestigingAnyOverzichtPlugin.visualizer.VisualizerControllerInterface;

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

	public void startSimulatie() {
		mbs.setupSimulatie();
	}
	
	//TODO implementeren voor visualizer
	@Override
	public void barClicked(String plaatsnaam, Integer aantal_klanten) {
	   //we moeten testen of ergens afvangen dat plaatsnaam niet null is of iets waar geen vestiging van is
	   //testen of afdwingen dat aantal_klanten niet null of negatief kan zijn
	   if(aantal_klanten == 0) {
	     mbs.openVestiging(plaatsnaam);
	     System.out.println("Heropen vestiging: " + plaatsnaam + "  " + aantal_klanten);
	     
	     //wat als er geen vestiging wordt gevonden?
	   } else {
	     System.out.println("Sluit vestiging: " + plaatsnaam + "  " + aantal_klanten);
	     mbs.sluitVestiging(plaatsnaam);
	   }
	}

	//TODO implementeren voor visualizer
	@Override
	public Map<String, Integer> getBarInfo() {  
  	    Map<String,Integer> map = new TreeMap<>(); // TreeMap?
        map.put("Hogezand", 60);// dit wordt een bar met naam "aap" en relatieve hoogt 100
        map.put("Veendam", 76);
        map.put("Stadskanaal", 54);
        map.put("Delfzijl", 45);
        map.put("Zuidhorn", 88);
        map.put("Groningen", 156);
        map.put("Bolsward", 76);
        map.put("Leeuwarden", 103);
        map.put("Heerenveen", 95);
        map.put("Dokkum", 75);
        map.put("Sneek", 84);
        map.put("Drachten", 92);
        return map;
	}
	
	

	
	
	
	
	
	
	
	
	
	
	
	
	
} //class





