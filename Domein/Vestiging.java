package Domein;

import java.util.Collection;

/**
 * 
 * @author carlo
 *
 */
public class Vestiging {

	private Collection<Klant> klanten;
	private PostcodeInfo postcodeInfo;
	private String plaats;
	
	
	/**
	 * Maakt een nieuwe vestiging
	 * @param plaats   vestiging plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  collectieobject met klanten >= 0
	 * @throws IllegalArgumentException bij ongeldige parameters
	 * 
	 * @contract happy {
	 *  @requires plaats != null of lege string
	 *  @requires postcode != null
	 *  @requires klanten != null && klanten.size() >= 0
	 *  @ensures \result is een klanten instantie
	 * }
	 * @contract ongeldigeplaats {
	 *   @requires plaats == null
	 *   @signals IllegalArgumentException("Plaats mag niet null zijn")
	 * }
	 * @contract plaats_leeg {
     *   @requires plaats == lege string of alleen spaties
     *   @signals IllegalArgumentException("Plaats mag niet leeg zijn")
     * }
     * @contract postcode_null {
     *   @requires postcode == null
     *   @signals IllegalArgumentException("Postcode mag niet null zijn")
     * }
     * @contract klanten_null {
     *   @requires klanten == null
     *   @signals IllegalArgumentException("Klanten mag niet null zijn")
     * }
	 *   
	 */
	public Vestiging(String  plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws IllegalArgumentException {
	  //test plaats is niet null
	  if(plaats == null) {
	    throw new IllegalArgumentException("Plaats mag niet null zijn");
	  }
	  //test plaats is niet leeg of allen maar spaties
	  if(plaats.isBlank() || plaats.isEmpty()) {
	    throw new IllegalArgumentException("Plaats mag niet leeg zijn");
	  }
	  //test postcode is niet null
	  if(postcode == null) {
	    throw new IllegalArgumentException("Postcode mag niet null zijn");
	  }
	  //klantenlist is null
	  if(klanten == null) {
        throw new IllegalArgumentException("Klanten mag niet null zijn");
      }
	  
	  this.plaats  = plaats;
	  this.postcodeInfo = postcode;
	  this.klanten = klanten;
	}
	
	/**
	 * Geeft de plaatsnaam
	 * @return de plaatsnaam
	 */
	public String getPlaats() {
	  return plaats;
	}
	
	/**
	 * Geeft de postcode instantie
	 */
	public PostcodeInfo getPostcode() {
	  return postcodeInfo;
	}
	
	/**
	 * Geeft de klantenlijst
	 * @return collectie met klanten
	 */
	public Collection<Klant> getKlanten() {
	  return this.klanten;
	}
	
	/**
	 * voegt een klant toe aan de klantenlijst
	 * @param klant  een klanten object
	 * @return true als de klant toe is gevoegd
	 * @contract happy {
	 *   @requires klant is een instance
	 *   @requires klant niet in klantenlijst
	 *   @ensures klant aan lijst toegevoegd
	 *   @ensures \result true
	 * }
	 * @contract klant_in_list {
	 *   @requires klanten.contains(klant) == true
	 *   @requires instanties moeten gelijk zijn
	 *   @ensures \result false; niet toegevoegd
	 * }
	 * @contract klant_null {
     *   @requires klant = null
     *   @ensures \result false; niet toegevoegd
     * }
	 */
	public boolean voegKlantToe(Klant klant) { // <-- ?? navragen
	  if(klanten.contains(klant) || klant == null) {
	    return false;
	  }
	  return klanten.add(klant);
	}
	
//	@Override
//	public String toString() {
//	  return "" + plaats + " : " + klanten.size();
//	}
	
	
	
} //class
