package domein;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Postcode informatie object
 * Bevat informatie over een postcode 
 */
public class PostcodeInfo {
	private String postcode;
	private String plaatsnaam;
	private double lat;
	private double lng;

	/**
	 * Maakt postcode instantie
	 * 
	 * @param postcode     de postcode
	 * @param plaatsnaam   de plaatsnaam
	 * @param lat          latitude
	 * @param lng          longtitude
	 * @throws PostcodeException bij ongeldige invoer
	 */
	/*@
	 @ @contract happy {
	 @   @requires postcode != null && postcode.length == 6 bestaande uit eerst 4
	 @           cijfers dan 2 letters
	 @   @requires plaats is not null en trim(plaats) != ""
	 @   @requires -90 > lat > 90
	 @   @requires -180 > len > 180
	 @   @ensures /result 
	 @ }
	 @ @contract null waarden {
	 @   @requires postcode == null of plaats == null
	 @   @signals IllegalArgumentException("Postcode mag niet leeg zijn") 
	 @ }
	 @ @contract lege str plaatsnaam {
	 @   @requires plaatsnaam is een lege string
	 @   @signals IllegalArgumentException("Plaatsnaam mag niet leeg zijn") 
	 @ }
	 @ @contract lat of len out of range {
	 @   @requires lat < -90 || lat > 90
	 @   @requires len < -180 || lat > 180
	 @   @signals IllegalArgumentException("latitude out of range moet tussen de -180
	 @          en 180 zijn maar was: ...") 
	 @ }
	 @ @contract ongeldige postcode {
	 @   @requires postcode voldoet niet aan regex ^\d{4}[a-zA-Z]{2}$ (4 cijfers
	 @           gevolgd door 2 letters)
	 @   @signals IllegalArgumentException("Ongeldig formaat voor postcode") 
	 * }
	 */
	public PostcodeInfo(String postcode, String plaatsnaam, double lat, double lng) throws PoiException {
//		validatePostcode(postcode);
		validatePlaatsnaam(plaatsnaam);
		validateGeographicCoords(lat, lng);
		this.postcode = postcode;
		this.plaatsnaam = plaatsnaam;
		this.lng = lng;
		this.lat = lat;
	}

	/**
	 * Helper die controleert of de postcode string in een geldig formaat is
	 * gooit een postcode exceptie op als dat niet het geval is
	 * 
	 * @param postcode string
	 * @throws PostcodeException als een ongeldige postcode opgegeven wordt
	 * @see PostcodeInfo(String, String, double, double )
	 */
	public static void validatePostcode(String postcode) throws PoiException {
		char c;
		// postcode null
		if (postcode == null) {
			throw new PoiException(PoiExceptionCode.POSTCODE_NULL, postcode);
		}
		// postcode ongeldige lengte
		if (postcode.length() != 6) {
			throw new PoiException(PoiExceptionCode.POSTCODE_LENGTE, postcode);
		}
		// postcode ongeldig startgetal
		c = postcode.charAt(0);
		if (c == '0') {
			throw new PoiException(PoiExceptionCode.POSTCODE_NUL_START, postcode);
		}
		// postcode ongeldig formaat eerste 4 velden
		for (int i = 0; i < 4; i++) {
			c = postcode.charAt(i);
			if (!Character.isDigit(c)) {
				throw new PoiException(PoiExceptionCode.POSTCODE_VELDEN, postcode);
			}
		}
		// postcode ongeldig formaat laatste 2 velden
		for (int i = 4; i < 6; i++) {
			c = postcode.charAt(i);
			if (!Character.isAlphabetic(c) || !Character.isUpperCase(c)) {
				throw new PoiException(PoiExceptionCode.POSTCODE_VELDEN, postcode);
			}
		}
	}

	/**
	 * helper die valideert of de plaatsnaam in een geldig formaat is
	 * @param plaatsnaam naam van de plaats
	 * @throws PostcodeException als ongeldige plaatsnaam is opgegeven
	 * @see PostcodeInfo(String, String, double, double )
	 */
	public static void validatePlaatsnaam(String plaatsnaam) throws PoiException {
		char c;
		// Plaatsnaam null
		if(plaatsnaam == null) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_NULL, plaatsnaam);
		}
		// Plaatsnaam leeg
		if(plaatsnaam.isEmpty()) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_LEEG, plaatsnaam);
		}
		// Plaatsnaam alleen spaties
		if(plaatsnaam.isBlank()) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_ALLEEN_SPATIES, plaatsnaam);
		}
		// Plaatsnaam ander teken dan letter, apostrof of spatie(s)
		for(int i=0;i<plaatsnaam.length();i++) {
			c = plaatsnaam.charAt(i);
			if(!Character.isLetter(c) && c != '\''  && c != ' ') {
				throw new PoiException(PoiExceptionCode.PLAATSNAAM_ILLEGAL_CHAR, plaatsnaam);
			}
		}
		// plaatsnaam langer dan 30 karakters
		if(plaatsnaam.length() > 30) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_TE_GROOT, plaatsnaam);
		}
	}

	/**
	 * helper die valideert of lat en lng in een geldig formaat zijn
	 * @param lat  latitude
	 * @param lng  longtitude
	 * @throws PostcodeException als ongeldige lat on lng zijn opgegeven
	 * @see PostcodeInfo(String, String, double, double )
	 */
	public static void validateGeographicCoords(double lat, double lng) throws PoiException {
		if(lat < -90 || lat > 90) {
			throw new PoiException(PoiExceptionCode.GGCOORDS_LAT_OVERSCHRIJDING, Double.toString(lat));
		}
		if(lng < -180 || lng > 180) {
			throw new PoiException(PoiExceptionCode.GGCOORDS_LNG_OVERSCHRIJDING, Double.toString(lng));
		}
	}

	/**
	 * Geeft de postcode
	 * 
	 * @return de postcode
	 */
	public String getPostcode() {
		return postcode;
	}

	/**
	 * Geeft de plaatsnaam
	 * 
	 * @return de plaats
	 */
	public String getPlaatsnaam() {
		return plaatsnaam;
	}

	/**
	 * Geeft de latitude
	 * 
	 * @return de lat
	 */
	public double getLat() {
		return lat;
	}

	/**
	 * Geeft de longtitude
	 * 
	 * @return de longtiude
	 */
	public double getLng() {
		return lng;
	}
	
}
