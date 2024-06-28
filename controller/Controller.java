package controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import domein.Vestiging;
import exceptions.PoiException;
import gui.simulatie.VisualizerControllerInterface;

/**
 * Controller
 * regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller implements ModelBedrijf, ModelBedrijfssimulatie, VisualizerControllerInterface {
	
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

	@Override
	public void barClicked(String plaatsnaam, Integer aantal_klanten) {
	   if(!mbs.isVestigingOpen(plaatsnaam)) {
	     mbs.openVestiging(plaatsnaam);
	     System.out.println("Status voor opening:" + plaatsnaam + ", " + aantal_klanten);
	   } else {
	     mbs.sluitVestiging(plaatsnaam);
	     System.out.println("Status voor sluiting:" + plaatsnaam + ", " + aantal_klanten);
	   }
	}

	/**
	 * Geeft een map met key value pairs plaatsnaam en het aantal klanten in die plaats
	 * @return lijst met plaatsnamen met het aantal klanten
	 */
	
	@Override
	public Map<String, Integer> getBarInfo() {  
        return this.getSimVestigingenMap();
	}

	@Override
	public void sluitVestiging(String plaats) {
		mbs.sluitVestiging(plaats);
		
	}

	@Override
	public void openVestiging(String plaats) {
		mbs.openVestiging(plaats);
	}

	@Override
	public Map<String, Integer> getSimVestigingenMap() {
		return mbs.getSimVestigingenMap();
	}

	@Override
	public Boolean isVestigingOpen(String plaatsnaam) {
		return mbs.isVestigingOpen(plaatsnaam);
	}

} 





