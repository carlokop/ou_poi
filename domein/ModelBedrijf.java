package domein;

import java.util.Collection;

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
	 * sluit een vestiging
	 * @param plaats plaatsnaam van de vestiging
	 */
	public abstract void sluitVestiging(String plaats);

	/**
	 * opend een vestiging
	 * @param plaats plaatsnaam van de vestiging
	 */
	public abstract void openVestiging(String plaats);
}
