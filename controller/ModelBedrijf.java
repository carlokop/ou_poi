package controller;

import java.util.Collection;
import java.util.Map;

/**
 * Markeer interface
 */
public interface ModelBedrijf {

	/**
	 * geeft een lijst met plaatsnamen van vestigingen
	 * 
	 * @return lijst met plaatsnamen
	 */
	public Collection<String> getVestigingPlaatsen();

	/**
	 * Geeft de klanten voor de opgegeven vestiging
	 * 
	 * @param plaats vestiging plaatsnaam
	 * @return lijst met klant id's
	 */
	public Collection<String> getVestigingKlanten(String plaats);
	
	/**
	 * Sluit een vestiging
	 * @param plaats plaatsnaam van de te sluiten vestiging
	 */
	public abstract void sluitVestiging(String plaats);

	/**
	 * Heropend een reeds gesloten vestiging
	 * @param plaats   plaatsnaam van de te openen vestiging
	 */
	public abstract void openVestiging(String plaats);

	/**
	 * Maakt een lijst met key value pairs van vestiging plaats en klantnummer
	 * @return list met key value pairs tussen vestigingnaam en klantnummer
	 */
	public abstract Map<String, Integer> getVestigingenMap();

	/**
	 * Geeft aan of een vestiging open is
	 * @param plaatsnaam   plaats van de vestiging
	 * @return true als vestiging open is
	 */
	public abstract Boolean isVestigingOpen(String plaatsnaam);
}
