package Domein;

import java.util.Collection;

public class Vestiging {

	private Collection<Klant> klanten;
	private Postcode postcodeInfo;
	private String plaats;
	
	
	/**
	 * Maakt een nieuwe vestiging
	 * @param plaats   vestiging plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  collectieobject met klanten
	 * @throws IllegalArgumentException bij ongeldige parameters
	 * 
	 * @contract happy
	 *  @requires plaats != null of lege string
	 *  @requires postcode != null
	 *  @requires klanten != null
	 */
	public Vestiging(String  plaats, Postcode postcode, Collection<Klant> klanten) throws IllegalArgumentException {
	  //test plaats is niet null
	  
	  //test postcode is niet null
	  
	  //test collectie is niet null
	  
	  //set data
	  
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
	 * Geeft de postcode als string
	 * @contract happy
	 *   @requires postcode info is gecreerd en niet null
	 *   @ensures /result is de postcode string
	 */
	public String getPostcode() {
	  return postcodeInfo.getPostcode();
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
	 */
	public boolean voegKlantToe(Klant klant) {
	  if(klanten.contains(klant)) {
	    return false;
	  }
	  return klanten.add(klant);
	}
	
//	@Override
//	public String toString() {
//	  return "" + plaats + " : " + klanten.size();
//	}
	
	
	
} //class
