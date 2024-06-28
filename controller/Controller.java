package controller;

import java.util.Collection;
import java.util.Map;
import java.util.TreeMap;

import domein.Vestiging;
import exceptions.PoiException;
import gui.simulatie.VisualizerControllerInterface;

/**
 * Controller regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller implements ModelBedrijf, VisualizerControllerInterface {

	private ModelBedrijf mb;

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
		System.out.println("Status voor opening:" + plaatsnaam + ", " + aantal_klanten);
		if (!mb.isVestigingOpen(plaatsnaam)) {
			mb.openVestiging(plaatsnaam);
		} else {
			System.out.println("Status voor sluiting:" + plaatsnaam + ", " + aantal_klanten);

			mb.sluitVestiging(plaatsnaam);
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

	}

	@Override
	public void openVestiging(String plaats) {
		mb.openVestiging(plaats);
	}

	@Override
	public Map<String, Integer> getVestigingenMap() {
		return mb.getVestigingenMap();
	}

	@Override
	public Boolean isVestigingOpen(String plaatsnaam) {
		return mb.isVestigingOpen(plaatsnaam);
	}

}
