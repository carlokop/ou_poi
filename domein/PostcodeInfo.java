package domein;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Postcode informatie object
 * Bevat informatie over een postcode en regelt en valideert postcode informatie
 */
public class PostcodeInfo {

	public static final double LAT_MAX = 90;
	public static final double LAT_MIN = -90;
	public static final double LNG_MAX = 180;
	public static final double LNG_MIN = -180;
	public static final double MAX_AFSTAND = Math.sqrt(Math.pow(PostcodeInfo.LAT_MAX - PostcodeInfo.LAT_MIN, 2) + Math.pow(PostcodeInfo.LNG_MAX - PostcodeInfo.LNG_MIN, 2));

	private String postcode;
	private String plaatsnaam;
	private double lat;
	private double lng;

	/**
     * Maakt postcode instantie
     *
     * @param postcode      de postcode
     * @param plaatsnaam    de plaatsnaam
     * @param lat           latitude
     * @param lng           longtitude
     * @throws PoiException bij ongeldige invoer
     */
    /*@
     @ @contract happy {
     @   @requires postcode != null && postcode.length == 6 bestaande uit eerst 4
     @           cijfers dan 2 letters
     @   @requires plaats is not null en trim(plaats) != ""
     @   @requires -90 > lat > 90
     @   @requires -180 > lng > 180
     @   @ensures /result
     @ }
     @ @contract null waarden {
     @   @requires postcode == null of plaats == null
     @   @signals PoiException, PoiExceptionCode.POSTCODE_NULL
     @ }
     @ @contract lege str plaatsnaam {
     @   @requires plaatsnaam is een lege string
     @   @signals PoiException, PoiExceptionCode.PLAATSNAAM_LEEG
     @ }
     @ @contract lat of lng out of range {
     @   @requires lat < -90 || lat > 90
     @   @requires lng < -180 || lat > 180
     @   @signals PoiException, PoiExceptionCode.GGCOORDS_LAT_OVERSCHRIJDING
     @ }
     */
	public PostcodeInfo(String postcode, String plaatsnaam, double lat, double lng) throws PoiException {
//		validatePostcode(postcode); //dit doen we niet meer. Volgen schema uit de database ook al staan daar fouten in alleen null testen
    	if (postcode == null) {
            throw new PoiException(PoiExceptionCode.POSTCODE_NULL, postcode);
        }
		validatePlaatsnaam(plaatsnaam);
		validateGeographicCoords(lat, lng);
		this.postcode = postcode;
		this.plaatsnaam = plaatsnaam;
		this.lng = lng;
		this.lat = lat;
	}

	/**
	 * Maakt een kopie van dit object
	 * @param p   instantie van postcodeinfo die gekopieerd moet worden
	 * @throws PoiException als er fouten bij creatie zijn
	 * @return een kopie van het postcodeinfo object
	 */
	public PostcodeInfo copy(PostcodeInfo p) throws PoiException {
	  return new PostcodeInfo(p.getPostcode(), p.getPlaatsnaam(), p.getLat(), p.getLng());
    }

	/*
	 * DEZE METHODE HEBBEN WE WEL GESCHREVEN MAAR WORDT NIET MEER GEBRUIKT
     * WE VOLGEN HET FORMAAT ZOALS IN DE DATABASE STAAT>
     * VOOR DE VOLLEDIGHEID HIER WEL LATEN STAAN
	 */
	/**
     * Helper die controleert of de postcode string in een geldig formaat is
     * gooit een postcode exceptie op als dat niet het geval is
     *
     * @param postcode string
     * @throws PoiException als een ongeldige postcode opgegeven wordt
     * @see PostcodeInfo(String, String, double, double )
     */
     /*
     @ @contract postcode null {
     @   @requires postcode == null
     @   @signals PoiException, PoiExceptionCode.POSTCODE_NULL
     @ }
     @ @contract ongeldige postcode lengte {
     @   @requires postcode char.length != 6
     @   @signals PoiException, PoiExceptionCode.POSTCODE_LENGTE
     @ }
     @ @contract ongeldige postcode {
     @   @requires postcode voldoet niet aan partoon (4 cijfers gevolgd door 2 letters)
     @   @signals PoiException, PoiExceptionCode.POSTCODE_VELDE
     * }
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
     * @throws PoiException als ongeldige plaatsnaam is opgegeven
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
     * @throws PoiException als ongeldige lat on lng zijn opgegeven
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
	 * Berekent de afstand tussen twee postcodes 
	 * @param pciA postcodeinfo a
	 * @param pciB postcodeinfo b
	 * @return de afstand
	 */
    public static double getAfstand(PostcodeInfo pciA, PostcodeInfo pciB) {
		return Math.sqrt(Math.pow(pciA.getLat() - pciB.getLat(), 2) + Math.pow(pciA.getLng() - pciB.getLng(), 2));
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
     * @return de plaats
     */
    public String getPlaatsnaam() {
        return plaatsnaam;
    }

	/**
     * Geeft de latitude
     * @return de latitude
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

	/**
	 * Geeft de string representatie van postcode, plaatsnaam en latitude en longditude
	 * @return de string
	 */
	@Override
	public String toString() {
		return postcode + ", " + plaatsnaam + ", " + String.valueOf(lat) + ", " + String.valueOf(lng);
	}
}
