package controller;

import java.util.Collection;
import java.util.Map;
import gui.simulatie.VisualizerControllerInterface;

/**
 * Controller regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller implements ModelBedrijf, VisualizerControllerInterface {

    /**
     * Bedrijfsmodel
     */
	private ModelBedrijf mb;

	/**
	 * TODO: Aanpassen voor het model van de simulatie Instantieert de controller en
	 * stelt het model in
	 * 
	 * @param mb  ModelBedrijf
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

	/**
	   * Bepaald als er op een bar in de visualizer wordt geklikt of een vestiging gesloten of heropend moet worden
	   * Een vestiging wordt gesloten als die open is en heropend als die gesloten is
	   * deze functie print tevens het aantal klanten in de vestiging naar het console
	   * @param plaatsnaam naam bij de bar
	   * @param aantal_klanten  het aantal dat de bar hoog is
	   */
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
	 * @return lijst met namen bij de bar en het aantal dat die hoog moet zijn
	 */
	@Override
	public Map<String, Integer> getBarInfo() {
		return this.getVestigingenMap();
	}

	/**
	 * Sluit een vestiging
	 * @param plaats de vestiging plaatsnaam
	 */
	@Override
	public void sluitVestiging(String plaats) {
		mb.sluitVestiging(plaats);

	}

	/**
	 * Heropend een reeds gesloten vestiging
	 * @param plaats de vestiging plaatsnaam
	 */
	@Override
	public void openVestiging(String plaats) {
		mb.openVestiging(plaats);
	}

	/**
	 * Maakt een map met key value pairs van de vesttigig plaats en het klantnummer
	 * @return lijst met key value pairs
	 */
	@Override
	public Map<String, Integer> getVestigingenMap() {
		return mb.getVestigingenMap();
	}

	/**
	 * Geeft aan of een vestiging open is
	 * @param plaatsnaam van een vestiging
	 * @return true als de vestiging open is
	 */
	@Override
	public Boolean isVestigingOpen(String plaatsnaam) {
		return mb.isVestigingOpen(plaatsnaam);
	}

}
