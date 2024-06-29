package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Map.Entry;

import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Bevat informatie van een vestiging en beheert de klanten van die vestiging.
 * Klanten hebben status "wachtend" als deze geen vestigingen hebben die ze kunnen bezoeken.
 * Deze klanten hebben lege lijst van huidige vestigingen die ze kunnen bezoeken.
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
	 * @contract happy {
	 * @requires plaats != null of lege string
	 * @requires postcode != null
	 * @requires klanten != null && klanten.size() >= 0
	 * @ensures \result is een klanten instantie
	 * }
	 * 
	 * @contract ongeldigeplaats {
	 * @requires plaats == null
	 * @signals PoiException, PoiExceptionCode.PLAATSNAAM_NULL
	 * }
	 * 
	 * @contract plaats_leeg {
	 * @requires plaats == lege string of alleen spaties
	 * @signals PoiException, PoiExceptionCode. ("Plaats mag niet leeg zijn")
	 * }
	 * 
	 * @contract postcode_null {
	 * @requires postcode == null
	 * @signals PoiException, PoiExceptionCode.POSTCODE_NULL
	 * }
	 * 
	 * @contract klanten_null {
	 * @requires klanten == null
	 * @signals PoiException, PoiExceptionCode.KLANTENLIJST_NULL
	 * }
	 */
	public Vestiging(String plaats, PostcodeInfo postcode, Collection<Klant> klanten) throws PoiException {
		validate(plaats, postcode, klanten);
		this.plaats = plaats;
		this.postcodeInfo = postcode;
		this.klanten = klanten;
	}
	
	/**
	 * Maakt een diepe kloon van vestiging
	 * @param vestiging    vestiging instantie om te kopieren   
	 * @return diepe kloon van vestiging instantie
	 * @throws PoiException    bij fouten bij creatie
	 */
	public static Vestiging copy(Vestiging vestiging) throws PoiException {
	  //originele objecten
	  PostcodeInfo p = vestiging.getPostcodeInfo();
	  Collection<Klant> klantenlijst = vestiging.getKlanten();
	  
	  //kopieen
	  PostcodeInfo postcodeInfoCopy = p.copy(p);
	  Collection<Klant> klantenlijstCopy = new ArrayList<>();
	  
	  for(Klant k: klantenlijst) {
	    //maak kopie klant
	    Klant kl = k.copy(k);
	    klantenlijstCopy.add(kl);
	  }
	 
	  return new Vestiging(vestiging.getPlaats(), postcodeInfoCopy, klantenlijstCopy);
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
	 * Sluit een vestiging en voer de corresponderende migratieregel uit: "Klanten van een
	 * vestiging die wordt gesloten gaam de – voor de klant – dichtstbijzijnde open
	 * vestiging."
	 * 
	 * @param geslotenVestiging Vestiging die aangemerkt wordt voor sluiting
	 * @param openVestigingen	Open vestigingen waar klanten naar toe kunnen, kan leeg zijn.
	 */
	public static void migratieSluitenVestiging(
			Vestiging geslotenVestiging, 
			Collection<Vestiging> openVestigingen, 
			Map<String, Entry<Collection<Vestiging>, Collection<Vestiging>>> klantenChecklist
			) {
		Collection<Klant> gvKlanten = geslotenVestiging.getKlanten();
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;
		Vestiging dichtsteVestiging;

		if(openVestigingen.size() == 0) {
			for (Klant k : gvKlanten) {				
				// werk klantchecklist bij 
				klantEntry = klantenChecklist.get(String.valueOf(k.getKlantnr()));
				klantEntry.getValue().remove(geslotenVestiging); //verwijder uit lijst van huidige vestigingen, wordt leeg als het goed is
			}
		}
		else if(openVestigingen.size() == 1) {
			dichtsteVestiging = openVestigingen.iterator().next();
			for (Klant k : gvKlanten) {
				
				// werk klantchecklist bij 
				klantEntry = klantenChecklist.get(String.valueOf(k.getKlantnr()));
				klantEntry.getValue().remove(geslotenVestiging); 		//verwijder gesloten uit lijst van huidige vestigingen
				if(!klantEntry.getValue().contains(dichtsteVestiging)) {//voeg dichtste toe aan lijst van huidige vestigingen
					klantEntry.getValue().add(dichtsteVestiging); 
				}
				// voer migratie uit, klanten verwijderen gebeurt op het einde
				// controleer op duplicaten, kan voorkomen bij klanten met meerdere vestigingen
				if(!dichtsteVestiging.getKlanten().contains(k)) {
					dichtsteVestiging.addKlant(k);
				}
			}
		} 
		else {
			for (Klant k : gvKlanten) {
				// zoek naar dichtste vestiging
				dichtsteVestiging = getKlantDichtsteVestiging(k, openVestigingen);
				// werk klantchecklist bij 
				klantEntry = klantenChecklist.get(String.valueOf(k.getKlantnr()));
				klantEntry.getValue().remove(geslotenVestiging); 		//verwijder gesloten uit lijst van huidige vestigingen
				if(!klantEntry.getValue().contains(dichtsteVestiging)) {//voeg dichtste toe aan lijst van huidige vestigingen
					klantEntry.getValue().add(dichtsteVestiging); 
				}
				// voer migratie uit, klanten verwijderen uit gesloten gebeurt op het einde
				// controleer op duplicaten, kan voorkomen bij klanten met meerdere vestigingen
				if(!dichtsteVestiging.getKlanten().contains(k)) {// checklist houdt meerdere vestigingen bij per entry in lijst
					dichtsteVestiging.addKlant(k);
				}
			}
		}
		// achteraf gesloten vestiging legen
		geslotenVestiging.clearKlanten();
	}

	/**
	 * Open een vestiging en voer de corresponderende migratieregel uit:
	 * "Als een vestiging weer wordt geopend, dan gaan alle
	 * oorspronkelijke klanten van deze vestiging weer terug naar deze vestiging."
	 * 
	 * @param geopendeVestiging 		Vestiging aangemerkt voor opening
	 * @param openVestigingen			Vestigingen die nog open zijn
	 * @param vestigingenSnapshot	Oorspronkelijke lijst van vestigingen, versnelt selectie oorspronkelijke klanten
	 * @param klantenChecklist			Hulplijst voor versnelde migratie
	 */
	public static void migratieOpenenVestiging(
			Vestiging geopendeVestiging, 
			Collection<Vestiging> vestigingenSnapshot, // oorspronkelijke lijst uit non-simulatie
			Map<String, Entry<Collection<Vestiging>, Collection<Vestiging>>> klantenChecklist
			) {
		Vestiging vOrigineel = Vestiging.select(geopendeVestiging.getPlaats(), vestigingenSnapshot); 
		Collection<Klant> kOrigineel = vOrigineel.getKlanten();
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;
		Collection<Vestiging> klantHuidigeVestigingen, klantOrigineleVestigingen; // vestigingen die de klant nu bezoekt, oorspronkelijk bezocht
		Collection<Vestiging> removeList;
		Iterator<Vestiging> removeListIt;
		Vestiging removeListVestiging;
		
		// migreer klanten en werk gelijk checklist bij
		for (Klant k : kOrigineel) {
			klantEntry = klantenChecklist.get(String.valueOf(k.getKlantnr()));
			klantHuidigeVestigingen = klantEntry.getValue();
			klantOrigineleVestigingen = klantEntry.getKey();

			// registreer huidige vestiging bij klant
			klantHuidigeVestigingen.add(geopendeVestiging);
			geopendeVestiging.addKlant(k);
			
			// verplaats klant bij niet originele vestiging of wachtlijst
			removeList = new ArrayList<>(); // om concurrent modification exception tegen te gaan
			for(Vestiging hv: klantHuidigeVestigingen) {
				if (!klantOrigineleVestigingen.contains(hv)) {
					// checklist bijwerkingen onthouden
					removeList.add(hv);
				}
			}
			removeListIt = removeList.iterator();
			while(removeListIt.hasNext()) {
				removeListVestiging = removeListIt.next();
				klantHuidigeVestigingen.remove(removeListVestiging);
				removeListVestiging.removeKlant(k);
			}			
		}
	}

	/**
	 * Zoekt naar een bepaalde vestiging uit een lijst van vestigingen.
	 * 
	 * @param vestKeuze		Vestigingkeus die gezocht wordt in alternatieve lijst
	 * @param vestigingen	Lijst van instanties waarin een vestiging met zelfde plaatsnaam
	 * @return 				Gevonden instantie met zelfde plaatsnaam
	 */
	public static Vestiging select(String vestKeuze, Collection<Vestiging> vestigingen) {
		for (Vestiging v : vestigingen) {
			if (v.getPlaats().equals(vestKeuze) ) {
				return v;
			}
		}
		return null;
	}

	public static double getAfstand(PostcodeInfo pciA, PostcodeInfo pciB) {
		return Math.sqrt(Math.pow(pciA.getLat() - pciB.getLat(), 2) + Math.pow(pciA.getLng() - pciB.getLng(), 2));
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
	
	public Collection<String> getKlantenStrings() {
		Collection<String> klantenStrings = new ArrayList<>();
		for(Klant k:klanten) {
			klantenStrings.add(k.toString());
		}
		return klantenStrings;
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
		return plaats + ":" + klanten.size();
	}

}
