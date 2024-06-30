package controller;

import java.util.Collection;
import java.util.Map;

import domein.Bedrijf;
import domein.ModelBedrijf;

/**
 * Controller regelt communicatie tussen de GUI en de domeinlaag
 */
public class Controller implements ModelBedrijf {

	private Bedrijf b;

	public Controller(Bedrijf b) {
		this.b = b;
	}

	/**
	 * Haalt alle plaatsnamen van vestigingen op
	 *
	 * @return lijst met plaatsnamen
	 */
	@Override
	public Collection<String> getVestigingPlaatsen() {
		return b.getVestigingPlaatsen();
	}

	/**
	 * Haalt een lijst op met alle klant id's die in de opgegeven vestiging zitten
	 *
	 * @param plaats vestiging plaatsnaam
	 * @return lijst met klant id's
	 */
	@Override
	public Collection<String> getVestigingKlanten(String plaats) {
		return b.getVestigingKlanten(plaats);
	}

	@Override
	public void openVestiging(String plaats) {
		b.openVestiging(plaats);
	}

	@Override
	public void sluitVestiging(String plaats) {
		b.sluitVestiging(plaats);
	}

	/**
	 * Geeft een map met key value pairs plaatsnaam en het aantal klanten in die
	 * plaats
	 *
	 * @return lijst met plaatsnamen met het aantal klanten
	 */
	public Map<String, Integer> getVestigingenMap(){
		return b.getVestigingenMap();
	}

	public Boolean isVestigingOpen(String plaatsnaam) {
		return b.isVestigingOpen(plaatsnaam);
	}
}
