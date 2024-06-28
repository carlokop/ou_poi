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
	
	public abstract void sluitVestiging(String plaats);

	public abstract void openVestiging(String plaats);

	public abstract Map<String, Integer> getVestigingenMap();

	public abstract Boolean isVestigingOpen(String plaatsnaam);
}
