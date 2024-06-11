package domein;

import java.util.Objects;

/**
 * Een klant
 */
public class Klant implements Comparable<Klant> {
	private PostcodeInfo postcode;
	private int klantnr;

	/**
	 * Maakt nieuwe klant
	 * 
	 * @param klantnr      het klantnummer
	 * @param postcode     object
	 * @throws IllegalArgumentException als foutive waarde wordt meegegeven
	 */
	/*@
	 @ @contract happy {
	 @   @requires klantnr > 0
	 @   @requires postcodeinfo != null
	 @   @ensures /result is een nieuwe klant 
	 @ }
	 @ @contract postcode null {
	 @   @requires postcode == null
	 @   @signals IllegalArgumentException("Postcode mag niet null zijn") 
	 @ }
	 @ @contract klantnummer onjuist {
	 @   @requires klantnummer <= 0
	 @   @signals IllegalArgumentException("Klantknummer moet een positief getal
	 @          zijn") 
	 @ }
	 */
	public Klant(int klantnr, PostcodeInfo postcode) throws IllegalArgumentException {
		validate(klantnr, postcode);
		this.klantnr = klantnr;
		this.postcode = postcode;

	}

	/**
	 * Valideert of opgegeven klantnr en postcode een geldige invoer hebben
	 * @param klantnr  het klantnummer
	 * @param postcode de postcodeinfo instantie
	 * @throws IllegalArgumentException als input ongeldig is
	 * @see Klant(int, PostcodeInfo)
	 */
	public static void validate(int klantnr, PostcodeInfo postcode) {
		// test klantnummer positief > 0
		if (klantnr <= 0) {
			throw new IllegalArgumentException("Klantknummer moet een positief getal zijn");
		}
		// test null postcode
		if (postcode == null) {
			throw new IllegalArgumentException("Postcode mag niet null zijn");
		}
	}
	
	/**
	 * Geeft het klantnummer
	 * 
	 * @return klantnr
	 */
	public int getKlantnr() {
		return klantnr;
	}

	/**
	 * Geeft het postcodeinstantie
	 * 
	 * @return postcode
	 */
	public PostcodeInfo getPostcode() {
		return postcode;
	}

	/**
	 * vergelijkt of een instantie hetzelfde klantnummer heeft
	 * @param obj  object instantie die vergeleken moet worden met this
	 * @return true als klantnummers gelijk zijn
	 */
	/*@
	 @ @Contract happy {
	 @   @requires instantie van klant
	 @   @requires klantnummer komt overeen
	 @   @ensures \result = true
	 @ }
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Klant)) {
			return false;
		}
		Klant k = (Klant) obj;
		return k.getKlantnr() == klantnr;
	}

	/**
	 * Geeft de hashcode van een klant
	 */
	@Override
	public int hashCode() {
		return Objects.hash(klantnr);
	}

	/**
	 * Vergelijkt een klantnummer van opgegeven klant met het actuele klantnummer
	 * @param o    klant instantie waarbij het klantnummer vergeleken moet worden
	 * @return int met verschil in klantnummer. 0 is hetzelfde klantnummer
	 */
	@Override
	public int compareTo(Klant o) {
		return this.klantnr - o.getKlantnr();
	}

}