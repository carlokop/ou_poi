package domein;

import java.util.Collection;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Bevat informatie van een vestiging en beheert de klanten van die vestiging
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
	 * 
	 * @param plaats   plaatsnaam
	 * @param postcode postcode instantie
	 * @param klanten  klantenlijst (mag leeg zijn)
	 * @throws PoiException als een ongeldige string is opgegeven
	 */
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
			throw new PoiException(PoiExceptionCode.POSTCODE_NULL, plaats);
		}
		// klantenlist is null
		if (klanten == null) {
			throw new PoiException(PoiExceptionCode.KLANTENLIJST_NULL, plaats + ":" + postcode.toString());
		}
	}

	/**
	 * Sluit een vestiging en voer de corresponderende migratie uit "Klanten van een
	 * vestiging die wordt gesloten gaam de – voor de klant – dichtstbijzijnde open
	 * vestiging."
	 * 
	 * @param geslotenVestiging
	 * @param openVestigingen
	 */
	public static void migratieSluitenVestiging(Vestiging geslotenVestiging, Collection<Vestiging> openVestigingen) {
		Collection<Klant> klanten = geslotenVestiging.getKlanten();
//		double klantMinAfstand;
//		PostcodeInfo klantPci;

		Vestiging dichtsteVestiging = geslotenVestiging;
//		double vestigingAfstand;
//		PostcodeInfo vestigingPCI;

		if (openVestigingen.size() == 1) {
			// eenvoudige verplaatsing, case optimalisatie
			dichtsteVestiging = openVestigingen.iterator().next();
			for (Klant k : klanten) {
				dichtsteVestiging.addKlant(k);
			}
		} else if (openVestigingen.size() > 1) {
			// berekenen dichtste vestiging, deze methode alleen is in principe genoeg, size
			// 0,1 zijn optimalisaties
			for (Klant k : klanten) {
//				klantPci = k.getPostcodeInfo();
//				klantMinAfstand = PostcodeInfo.MAX_AFSTAND;
//				for (Vestiging v : openVestigingen) {
//					vestigingPCI = v.getPostcodeInfo();
//					vestigingAfstand = getAfstand(klantPci, vestigingPCI);
//					if (klantMinAfstand > vestigingAfstand) {
//						klantMinAfstand = vestigingAfstand;
//						dichtsteVestiging = v;
//					}
//				}
				
				dichtsteVestiging = getKlantDichtsteVestiging(k, openVestigingen);
				dichtsteVestiging.addKlant(k);
			}
		}
		// gewoon vestiging legen, ook een case 0 optimalisatie
		geslotenVestiging.clearKlanten();
	}

	/**
	 * TODO: Afmaken "Als een vestiging weer wordt geopend, dan gaan alle
	 * oorspronkelijke klanten van deze vestiging weer terug naar deze vestiging."
	 * 
	 * @param geopendeVestiging
	 * @param openVestigingen
	 * @param bedrijfVestigingenLijst
	 * @param klantenChecklist
	 */
	public static void migratieOpenenVestiging(Vestiging geopendeVestiging, Collection<Vestiging> openVestigingen,
			Collection<Vestiging> bedrijfVestigingenLijst, // oorspronkelijke lijst uit non-simulatie
			Map<Klant, Entry<Vestiging, Vestiging>> klantenChecklist) {

		Vestiging vOrigineel = Vestiging.select(geopendeVestiging, bedrijfVestigingenLijst);
		Collection<Klant> kOrigineel = vOrigineel.getKlanten();
		Entry<Vestiging, Vestiging> klantDossier;
		Vestiging KlantHuidigeVestiging;

		for (Klant k : kOrigineel) {
			klantDossier = klantenChecklist.get(k);
			KlantHuidigeVestiging = klantDossier.getValue();
			if (!KlantHuidigeVestiging.equals(vOrigineel)) {
				geopendeVestiging.addKlant(k);
				KlantHuidigeVestiging.removeKlant(k);
			}
		}
	}

	/**
	 * Zoekt naar een bepaalde vestiging uit een lijst van vestigingen, de gevraagde
	 * instantie kan op inhoud verschillen.
	 * 
	 * @param vestKeuze
	 * @param vestigingen
	 * @return
	 */
	public static Vestiging select(Vestiging vestKeuze, Collection<Vestiging> vestigingen) {
		for (Vestiging v : vestigingen) {
			if (v == vestKeuze) {
				return v;
			}
		}
		return null;
	}

	public static double getAfstand(PostcodeInfo pciA, PostcodeInfo pciB) {
		return Math.sqrt(Math.pow(pciA.getLat() - pciB.getLat(), 2) + Math.pow(pciA.getLat() - pciB.getLat(), 2));
	}

	/**
	 * 
	 * @param k
	 * @param vestigingKeuzes
	 * @return
	 */
	public static Vestiging getKlantDichtsteVestiging(Klant k, Collection<Vestiging> vestigingKeuzes) {
		double klantMinAfstand = PostcodeInfo.MAX_AFSTAND;
		PostcodeInfo klantPci = k.getPostcodeInfo();

		Vestiging dichtsteVestiging = null;
		double vestigingAfstand;
		PostcodeInfo vestigingPCI;

		for (Vestiging v : vestigingKeuzes) {
			vestigingPCI = v.getPostcodeInfo();
			vestigingAfstand = getAfstand(klantPci, vestigingPCI);
			if (klantMinAfstand > vestigingAfstand) {
				klantMinAfstand = vestigingAfstand;
				dichtsteVestiging = v;
			}
		}
		return dichtsteVestiging;
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
	 * 
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
	 * In dit project is de plaatsnaam voldoende identificerend
	 */
	@Override
	public boolean equals(Object obj) {
		if (!(obj instanceof Vestiging)) {
			return false;
		}
		Vestiging v = (Vestiging) obj;
		return v.getPlaats() == this.plaats;
	}

	/**
	 * Geeft de hashcode van een vestiging
	 */
	@Override
	public int hashCode() {
		return Objects.hash(plaats);
	}

	/**
	 * Geeft de string representatie van de plaats en het aantal klanten
	 */
	@Override
	public String toString() {
		return plaats + " : " + klanten.size();
	}

}
