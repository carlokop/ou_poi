package domein;

import java.util.Collection;

/**
 * Bevat informatie van een vestigiging en beheert de klanten van die vestiging
 */
public class Vestiging {

	private Collection<Klant> klanten;
	private PostcodeInfo postcodeInfo;
	private String plaats;

	/**
	 * Maakt een nieuwe vestiging
	 * 
	 * @param plaats   vestiging plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  collectieobject met klanten
	 * @throws IllegalArgumentException bij ongeldige parameters
	 */
	/*@
	 @ @contract happy {
	 @   @requires plaats != null of lege string
	 @   @requires postcode != null
	 @   @requires klanten != null && klanten.size() >= 0
	 @   @ensures \result is een klanten instantie 
	 @ }
	 @ @contract ongeldigeplaats {
	 @   @requires plaats == null
	 @   @signals IllegalArgumentException("Plaats mag niet null zijn") 
	 @ }
	 @ @contract plaats_leeg {
	 @   @requires plaats == lege string of alleen spaties
	 @   @signals IllegalArgumentException("Plaats mag niet leeg zijn") 
	 @ }
	 @ @contract postcode_null {
	 @   @requires postcode == null
	 @   @signals IllegalArgumentException("Postcode mag niet null zijn") 
	 @ }
	 @ @contract klanten_null {
	 @   @requires klanten == null
	 @   @signals IllegalArgumentException("Klanten mag niet null zijn") 
	 @ }
	 @ 
	 */
	public Vestiging(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws IllegalArgumentException {
		validate(plaats, postcode, klanten);
		this.plaats = plaats;
		this.postcodeInfo = postcode;
		this.klanten = klanten;
	}

	/**
	 * Helper die valideert of een geldige plaats of postcode is opgegeven
	 * Controleert hierbij op nullwaarden en lege strings
	 * @param plaats plaatsnaam
	 * @param postcode postcode instantie 
	 * @param klanten klantenlijst (mag leeg zijn)
	 * @throws IllegalArgumentException als een ongeldige string is opgegeven
	 */
	public static void validate(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) {
		// test plaats is niet null
		if (plaats == null) {
			throw new IllegalArgumentException("Plaats mag niet null zijn");
		}
		// test plaats is niet leeg of allen maar spaties
		if (plaats.isBlank() || plaats.isEmpty()) {
			throw new IllegalArgumentException("Plaats mag niet leeg zijn");
		}
		// test postcode is niet null
		if (postcode == null) {
			throw new IllegalArgumentException("Postcode mag niet null zijn");
		}
		// klantenlist is null
		if (klanten == null) {
			throw new IllegalArgumentException("Klanten mag niet null zijn");
		}
	}

	/**
	 * Geeft de plaatsnaam
	 * 
	 * @return de plaatsnaam
	 */
	public String getPlaats() {
		return plaats;
	}

	/**
	 * Geeft de postcode instantie
	 * @return de postcodeInfo instantie
	 */
	public PostcodeInfo getPostcode() {
		return postcodeInfo;
	}

	/**
	 * Geeft de klantenlijst
	 * 
	 * @return collectie met klanten
	 */
	public Collection<Klant> getKlanten() {
		return klanten;
	}


} // class
