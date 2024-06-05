package Domein;

public class Klant  {
	private Postcode postcode;
	private int klantnr;
	
	/**
	 * Maakt nieuwe klant
	 * @param klantnr          het klantnummer
	 * @param postcodeInfo     postcodeinfo object
	 * @throws IllegalArgumentException als foutive waarde wordt meegegeven
	 * 
	 * @contract happy {
	 *  @requires klantnr > 0
	 *  @requires postcodeinfo != null
	 *  @ensures /result is een nieuwe klant
	 * }
	 * @contract postcode null {
	 *   @requires postcode == null
	 *   @signals IllegalArgumentException("Postcode mag niet null zijn")
	 * }
	 * @contract klantnummer onjuist {
	 *   @requires klantnummer <= 0
	 *   @signals IllegalArgumentException("Klantknummer moet een positief getal zijn")
	 * } 
	 */
	public Klant(int klantnr, Postcode postcode) throws IllegalArgumentException {
	  //test klantnummer positief > 0
	  if(klantnr <= 0) {
	    throw new IllegalArgumentException("Klantknummer moet een positief getal zijn");
	  }
	  
	  //test null postcode
	  if(postcode == null) {
	    throw new IllegalArgumentException("Postcode mag niet null zijn");
	  }
	  
	  this.klantnr = klantnr;
	  this.postcode = postcode;
	  
	}
	
	/**
	 * Geeft het klantnummer
	 * @return klantnr
	 */
	public int getKlantnr() {
	  return klantnr;
	}
	
	/**
     * Geeft het postcodeinstantie
     * @return postcode
     */
    public Postcode getPostcode() {
      return postcode;
    }
	
	public Postcode getPostcode(){
		return this.postcode;
	}
//	@Override
//	public Klant clone(Object klant) throws CloneNotSupportedException {
//	  
//	  
//	}
	
}