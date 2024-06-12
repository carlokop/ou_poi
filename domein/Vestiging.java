package domein;

import java.util.Collection;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

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
     * @throws PoiException bij ongeldige parameters
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
     @   @signals PoiException, PoiExceptionCode.PLAATSNAAM_NULL
     @ }
     @ @contract plaats_leeg {
     @   @requires plaats == lege string of alleen spaties
     @   @signals PoiException, PoiExceptionCode. ("Plaats mag niet leeg zijn") 
     @ }
     @ @contract postcode_null {
     @   @requires postcode == null
     @   @signals PoiException, PoiExceptionCode.POSTCODE_NULL
     @ }
     @ @contract klanten_null {
     @   @requires klanten == null
     @   @signals PoiException, PoiExceptionCode.KLANTENLIJST_NULL 
     @ }
     @ 
     */
	public Vestiging(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws PoiException {
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
     * @throws PoiException als een ongeldige string is opgegeven
     */
	public static void validate(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws PoiException {
		// test plaats is niet null
		if (plaats == null) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_NULL, plaats);
		}
		
		if(plaats.isEmpty()) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_LEEG, plaats);
		}
		
		// test plaats is niet leeg of allen maar spaties
		if (plaats.isBlank()) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_ALLEEN_SPATIES, plaats);
		}
		// test postcode is niet null
		if (postcode == null) {
			throw new PoiException(PoiExceptionCode.POSTCODE_NULL, plaats);
		}
		// klantenlist is null
		if (klanten == null) {
			throw new PoiException(PoiExceptionCode.KLANTENLIJST_NULL, plaats + ":" + postcode.toString());
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
	public PostcodeInfo getPostcodeInfo() {
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

	/**
	 * Geeft de string representatie van de plaats en het aantal klanten 
	 */
	@Override
	public String toString() {
		return plaats + " : " + klanten.size();
	}

}
