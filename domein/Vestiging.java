package domein;

import java.util.Collection;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

public class Vestiging {

	private Collection<Klant> klanten;
	private PostcodeInfo postcodeInfo;
	private String plaats;

	/**
	 * Maakt een nieuwe vestiging
<<<<<<< Updated upstream
	 *
	 * @param plaats   vestiging plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  collectieobject met klanten >= 0
	 * @throws PoiException bij ongeldige parameters
	 *
	 * @contract happy {
	 * @requires plaats != null of lege string
	 * @requires postcode != null
	 * @requires klanten != null && klanten.size() >= 0
	 * @ensures \result is een klanten instantie }
	 * @contract ongeldigeplaats {
	 * @requires plaats == null
	 * @signals PoiException, PoiExceptionCode.PLAATSNAAM_NULL
	 * @contract plaats_leeg {
	 * @requires plaats == lege string of alleen spaties
	 * @signals PoiException, PoiExceptionCode. ("Plaats mag niet leeg zijn") }
	 * @contract postcode_null {
	 * @requires postcode == null
	 * @signals PoiException, PoiExceptionCode.("Postcode mag niet null zijn") }
	 * @contract klanten_null {
	 * @requires klanten == null
	 * @signals PoiException, PoiExceptionCode.KLANTENLIJST_NULL
	 *
=======
	 * 
	 * @param plaats   vestiging plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  collectieobject met klanten
	 * @throws PoiException bij ongeldige parameters
	 */
	/*
	 * @
	 * 
	 * @ @contract happy {
	 * 
	 * @ @requires plaats != null of lege string
	 * 
	 * @ @requires postcode != null
	 * 
	 * @ @requires klanten != null && klanten.size() >= 0
	 * 
	 * @ @ensures \result is een klanten instantie
	 * 
	 * @ }
	 * 
	 * @ @contract ongeldigeplaats {
	 * 
	 * @ @requires plaats == null
	 * 
	 * @ @signals PoiException, PoiExceptionCode.PLAATSNAAM_NULL
	 * 
	 * @ }
	 * 
	 * @ @contract plaats_leeg {
	 * 
	 * @ @requires plaats == lege string of alleen spaties
	 * 
	 * @ @signals PoiException, PoiExceptionCode. ("Plaats mag niet leeg zijn")
	 * 
	 * @ }
	 * 
	 * @ @contract postcode_null {
	 * 
	 * @ @requires postcode == null
	 * 
	 * @ @signals PoiException, PoiExceptionCode.POSTCODE_NULL
	 * 
	 * @ }
	 * 
	 * @ @contract klanten_null {
	 * 
	 * @ @requires klanten == null
	 * 
	 * @ @signals PoiException, PoiExceptionCode.KLANTENLIJST_NULL
	 * 
	 * @ }
	 * 
	 * @
>>>>>>> Stashed changes
	 */
	public Vestiging(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws PoiException {
		validate(plaats, postcode, klanten);
		this.plaats = plaats;
		this.postcodeInfo = postcode;
		this.klanten = klanten;
	}

<<<<<<< Updated upstream
=======
	public static void migratieSluitenVestiging(Vestiging geslotenVestiging, Collection<Vestiging> openVestigingen) {
		Collection<Klant> klanten = geslotenVestiging.getKlanten();
		double klantMinAfstand;
		PostcodeInfo klantPci;

		Vestiging dichtsteVestiging = null;
		double vestigingAfstand;
		PostcodeInfo vestigingPCI;

		switch (openVestigingen.size()) {
		case 0: // gewoon vestiging legen
			geslotenVestiging.clearKlanten();
			break;
		case 1: // eenvoudige verplaatsing
			dichtsteVestiging = openVestigingen.iterator().next();
			for (Klant k : klanten) {
				dichtsteVestiging.addKlant(k);
			}
			geslotenVestiging.clearKlanten();
			break;
		default:// berekenen dichtste vestiging
			if (openVestigingen.size() == 0) {
				for (Klant k : klanten) {
					klantPci = k.getPostcodeInfo();
					klantMinAfstand = PostcodeInfo.MAX_AFSTAND;
					for (Vestiging v : openVestigingen) {
						vestigingPCI = v.getPostcodeInfo();
						vestigingAfstand = getAfstand(klantPci, vestigingPCI);
						if (klantMinAfstand > vestigingAfstand) {
							klantMinAfstand = vestigingAfstand;
							dichtsteVestiging = v;
						}
					}
					dichtsteVestiging.addKlant(k);
				}
			}
			break;
		}
	}

	public static void migratieOpenenVestiging(Vestiging geopendeVestiging, Collection<Vestiging> openVestigingen) {

	}

	public static double getAfstand(PostcodeInfo pciA, PostcodeInfo pciB) {
		return Math.sqrt(Math.pow(pciA.getLat() - pciB.getLat(), 2) + Math.pow(pciA.getLat() - pciB.getLat(), 2));
	}

	/**
	 * Helper die valideert of een geldige plaats of postcode is opgegeven
	 * Controleert hierbij op nullwaarden en lege strings
	 * 
	 * @param plaats   plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  klantenlijst (mag leeg zijn)
	 * @throws PoiException als een ongeldige string is opgegeven
	 */
>>>>>>> Stashed changes
	public static void validate(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws PoiException {
		// test plaats is niet null
		if (plaats == null) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_NULL, plaats);
		}

		if (plaats.isEmpty()) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_LEEG, plaats);
		}

		// test plaats is niet leeg of allen maar spaties
		if (plaats.isBlank()) {
			throw new PoiException(PoiExceptionCode.PLAATSNAAM_ALLEEN_SPATIES, plaats);
		}
		// test postcode is niet null
		if (postcode == null) {
			throw new PoiException(PoiExceptionCode.POSTCODE_NULL, postcode.toString());
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
<<<<<<< Updated upstream
=======
	 * 
	 * @return de postcodeInfo instantie
>>>>>>> Stashed changes
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

<<<<<<< Updated upstream
=======
	public void addKlant(Klant k) {
		this.klanten.add(k);
	}

	public void removeKlant(Klant k) {
		this.klanten.remove(k);
	}

	public void clearKlanten() {
		this.klanten.clear();
	}

	/**
	 * Geeft de string representatie van de plaats en het aantal klanten
	 */
>>>>>>> Stashed changes
	@Override
	public String toString() {
		return "" + plaats + " : " + klanten.size();
	}

}
