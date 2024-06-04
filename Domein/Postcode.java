package Domein;

public class Postcode {
  private String postcode;
  private String plaats;
  private double lat;
  private double lng;
  
  /**
   * Maakt postcode instantie
   * @param postcode    de postcode
   * @param plaats      de plaatsnaam
   * @param lat         latitude
   * @param len         longtitude
   * @throws IllegalArgumentException bij ongeldige invoer
   * 
   *  @contract happy
   *   @requires postcode != null && postcode.length == 6 bestaande uit eerst 4 cijfers dan 2 letters
   *   @requires plaats is not null en trim(plaats) != ""
   *   @requires -180 > lat > 180 
   *   @requires -90 > len > 90
   *   @ensures /result
   *  @contract null waarden
   *    @requires postcode == null of plaats == null
   *    @signals IllegalArgumentException("Postcode kan niet null zijn")
   *  @contract lege str plaatsnaam
   *    @requires plaatsnaam is een lege string
   *    @signals IllegalArgumentException("Plaatsnaam mag geen lege string zijn")
   *  @contract lat of len out of range
   *    @requires lat < -180 || lat > 180 
   *    @requires len < -90 || lat > 90 
   *    @signals IllegalArgumentException("latitude out of range moet tussen de -180 en 180 zijn maar was: ...")
   *  @contract ongeldige postcode
   *    @requires postcode voldoet niet aan regex ^\d{4}[a-zA-Z]{2}$ (4 cijfers gevolgd door 2 letters)
   *    @signals IllegalArgumentException("Ongeldig formaat voor postcode")
   */
  public Postcode(String postcode, String plaats, double lat, double lng) throws IllegalArgumentException {
    //test null postcode
    if(postcode == null) {
      throw new IllegalArgumentException("Postcode mag niet null zijn");
    }
    //test null plaats
    if(plaats == null) {
      throw new IllegalArgumentException("Plaatsnaam mag niet null zijn");
    }
    //test lege string plaatsnaam
    if("".trim().equals(plaats)) {
      throw new IllegalArgumentException("Plaatsnaam mag geen lege string zijn");
    }
    //valideer regex postcode
//    if(!valideerPostcode(postcode)) {
//      throw new IllegalArgumentException("Ongeldig formaat voor postcode: "+postcode);
//    }
    //test geldige waarde lat
    if(lat < -180 || lat > 180) {
      throw new IllegalArgumentException("latitude out of range moet tussen de -180 en 180 zijn maar was: "+lat);
    }    
    //test geldige waarde len
    if(lng < -90 || lng > 90) {
      throw new IllegalArgumentException("longtitude out of range moet tussen de -90 en 90 zijn maar was: "+lat);
    }
    
    this.postcode = postcode;
    this.plaats = plaats;
    this.lng = lng;
    this.lat = lat;
  }
  
  /**
   * Valideert of postcode voldoet aan regexpatroon voor een postcode
   * 
   * @param postcode
   * @return boolean
   * 
   * Gebaseerd op een codevoorbeeld van ChatGPT
   * 
   * @contract happy
   *  @requires postcode voldoet aan het patroon 4 cijfer gevolgd door 2 letters zonder spatie
   *  @ensures /result true
   */
  private boolean valideerPostcode(String postcode) {
    String postcodeRegex = "\\b\\d{4}[A-Za-z]{2}\\b"; 
    return postcode.matches(postcodeRegex);
  }
  
  /**
   * Geeft de postcode
   * @return de postcode
   */
  public String getPostcode() {
    return postcode;
  }
  
  /**
   * Geeft de plaatsnaam
   * @return de plaats
   */
  public String getPlaats() {
    return plaats;
  }
  
  /**
   * Geeft de latitude
   * @return de lat
   */
  public double getLat() {
    return lat;
  }
  
  /**
   * Geeft de longtitude
   * @return de longtiude
   */
  public double getLng() {
    return lng;
  }
  
  
  
} //class
