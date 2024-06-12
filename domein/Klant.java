package domein;

import java.util.Objects;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

public class Klant implements Comparable<Klant> {
	private PostcodeInfo postcode;
	private int klantnr;

	/**
	 * Maakt nieuwe klant
	 *
	 * @param klantnr      het klantnummer
	 * @param postcodeInfo postcodeinfo object
	 * @throws PoiException als foutive waarde wordt meegegeven
	 *
	 * @contract happy {
	 * @requires klantnr > 0
	 * @requires postcodeinfo != null
	 * @ensures /result is een nieuwe klant }
	 * @contract postcode null {
	 * @requires postcode == null
	 * @signals PoiException, PoiExceptionCode.POSTCODE_NULL
	 * @contract klantnummer onjuist {
	 * @requires klantnummer <= 0
	 * @signals PoiException, PoiExceptionCode.KLANTNUMMER_NIET_POSITIEF
	 */
	public Klant(int klantnr, PostcodeInfo postcode) throws PoiException {
		validate(klantnr, postcode);
		this.klantnr = klantnr;
		this.postcode = postcode;

	}

	public static void validate(int klantnr, PostcodeInfo postcode) throws PoiException {
		// test klantnummer positief
		if (klantnr < 1) {
			throw new PoiException(PoiExceptionCode.KLANTNUMMER_NIET_POSITIEF, String.valueOf(klantnr));
		}
		// test null postcode
		if (postcode == null) {
			throw new PoiException(PoiExceptionCode.POSTCODE_NULL, "Postcode is null");
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
	public PostcodeInfo getPostcodeInfo() {
		return postcode;
	}

	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Klant)) {
			return false;
		}
		Klant k = (Klant) obj;
		return k.getKlantnr() == klantnr;
	}

	@Override
	public int hashCode() {
		return Objects.hash(klantnr);
	}

	@Override
	public int compareTo(Klant o) {
		return this.klantnr - o.getKlantnr();
	}

}