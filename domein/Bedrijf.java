package domein;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;

import data.Mapper;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;
import observer.Subject;

/**
 * Deze klasse stelt het bedrijf voor
 * Beheert vervolgens alle vestigingen en regelt de communocatie met de mapper
 */
public class Bedrijf extends Subject implements ModelBedrijf {
	public static List<Vestiging> vestigingenSnapshot;
	private List<Vestiging> vestigingenCrrnt;
	private static Mapper m;

	/**
	 * houdt bij of een vestiging open is; true voor open, false voor dicht. Deze lijst moet alle instanties behouden
	 */
	private Map<Vestiging, Boolean> vestigingenChecklist;
	
	/**
	 *  houdt de oorspronkelijke vestiging(en) bij en de huidige
	 */
	private Map<String, Entry<List<Vestiging>, List<Vestiging>>> klantenChecklist;

	/**
	 * Initialiseert een bedrijf
	 * maakt een associatie met de mapper en vestigingen
	 * @throws PoiException bij DB fout
	 */
	public Bedrijf() throws PoiException {
		if(m == null) {
			Bedrijf.m = new Mapper();
			Bedrijf.vestigingenSnapshot = m.getVestigingen();
		}
		vestigingenCrrnt = Bedrijf.getDeepCopy();
		setupKlantenChecklist();
		setupVestigingenChecklist();
	}

	/**
	 * Maakt een diepe kloon van alle vestigingen 
	 * @return een lijst met kopien van alle vestigingen
	 * @throws PoiException geeft een fout als er fouten blijken bij creatie
	 */
	public static List<Vestiging> getDeepCopy() throws PoiException{
        //return m.getVestigingen();
        List<Vestiging> copyvestigingen = new ArrayList<>();

        for(Vestiging v:Bedrijf.vestigingenSnapshot) {
          copyvestigingen.add(Vestiging.copy(v));
        }
        return copyvestigingen;
    }

	/**
	 * Valideert of het vestigingsnapshot niet null is
	 * @throws PoiException exceptie als vestigingsnapshot null is
	 */
	public static void validate() throws PoiException {
		if (Bedrijf.vestigingenSnapshot == null) {
			throw new PoiException(PoiExceptionCode.BEDRIJF_VESTIGINGEN_SNAPSHOT_NULL, null);
		}
	}


	/**
	 * Haalt een lijst van de locatienamen op van vestigingen
	 * @return lijst van vestiginglocaties als string
	 */
	@Override
    public List<String> getVestigingPlaatsen(){
        List<String> lijstPlaatsenNamen = new ArrayList<>();

        // lvp, lijst vestiging plaatsen
        for(Vestiging v: vestigingenCrrnt) {
            lijstPlaatsenNamen.add(v.getPlaats());
        }
        return lijstPlaatsenNamen;
    }

	/**
	 * Haalt een lijst van de id's van klanten op
	 * @param plaats ook wel vestiginglocatie
	 * @return lijst van id's als string
	 */
	@Override
    public List<String> getVestigingKlanten(String plaats) {
        List<String> vestigingKlantenData = null;
        Vestiging vestigingSelectie = null;

        // select from collection vestigingen
        for(Vestiging v: vestigingenCrrnt) {
            if(v.getPlaats() == plaats) {
                vestigingSelectie = v;
                break;
            }
        }

        if(vestigingSelectie != null) {
            vestigingKlantenData = new ArrayList<>();
            vestigingKlantenData = vestigingSelectie.getKlantenStrings();
        }

        return vestigingKlantenData;
    }

	/**
	 * Methode werkt vestiging checklist bij, de aanroep naar vestiging werkt de
	 * klantenchecklist bij.
	 */
	@Override
	public void sluitVestiging(String plaats) {
		Vestiging geslotenVestiging = Vestiging.select(plaats, vestigingenCrrnt);

		// Zet status vestiging op gesloten
		vestigingenChecklist.replace(geslotenVestiging, false);

		// Compileer lijst van open vestigingen waarmee gerekend wordt
		List<Vestiging> openVestigingen = new ArrayList<>();
		Set<Entry<Vestiging, Boolean>> cleSet = vestigingenChecklist.entrySet();
		for (Entry<Vestiging, Boolean> cle : cleSet) {
			if (cle.getValue()) {
				openVestigingen.add(cle.getKey());
			}
		}

		Vestiging.migratieSluitenVestiging(geslotenVestiging, openVestigingen, klantenChecklist);
		notifyObservers();
	}

	/**
	 * Methode werkt vestiging checklist bij, de aanroep naar vestiging werkt de
	 * klantenchecklist bij.
	 */
	@Override
	public void openVestiging(String plaats) {
		Vestiging geopendeVestiging = Vestiging.select(plaats, vestigingenCrrnt);

		// Zet status vestiging op open
		vestigingenChecklist.replace(geopendeVestiging, true);
		Vestiging.migratieOpenenVestiging(geopendeVestiging, Bedrijf.vestigingenSnapshot, klantenChecklist);
		notifyObservers();
	}

	/**
	 * geeft de lijst met vestigingen 
	 * @return de lijst met vestigingen
	 */
	public List<Vestiging> getVestigingen(){
		return this.vestigingenCrrnt;
	}

	/**
	 * maakt klanten checklist
	 */
	public void setupKlantenChecklist() {
		klantenChecklist = new HashMap<>();
		Entry<List<Vestiging>, List<Vestiging>> klantEntry;

		List<String> klanten;
		for (Vestiging v : vestigingenCrrnt) {
			klanten = v.getKlantenStrings();
			for (String k : klanten) {
				if (!klantenChecklist.containsKey(k)) { // maak entry voor klant indien nog niet bestaand
					klantenChecklist.put(k, Map.entry(new ArrayList<>(), new ArrayList<>()));
				}
				klantEntry = klantenChecklist.get(k);
				klantEntry.getKey().add(v);	 	// onthoud oorspronkelijke vestiging(en)
				klantEntry.getValue().add(v);	// onthoud huidige vestiging(en)
			}
		}
	}

	/**
	 * geeft klantenchecklist
	 * @return de klantenchecklist
	 */
	public Map<String, Entry<List<Vestiging>, List<Vestiging>>> getKlantenChecklist() {
		return this.klantenChecklist;
	}

	/**
	 * maakt vestigingchecklist
	 */
	public void setupVestigingenChecklist() {
		vestigingenChecklist = new HashMap<>();
		for (Vestiging v : vestigingenCrrnt) {
			vestigingenChecklist.put(v, true);
		}
	}

	/**
	 * geeft vestiging checklist. dit bestaat uit een vestiging en bool waarde of deze open is
	 * @return vestigingchecklist
	 */
	public Map<Vestiging, Boolean> getVestigingenChecklist() {
		return this.vestigingenChecklist;
	}

	/**
	 * geeft bool waarde weer of een vestiging open is
	 * @param plaatsnaam de plaats van de vestiging
	 * @return true als de vestiging open is
	 */
	public Boolean isVestigingOpen(String plaatsnaam){
		return this.vestigingenChecklist.get(Vestiging.select(plaatsnaam, vestigingenCrrnt));
	}

	/**
	 * Geeft een map terug met de geupdate plaatsnamen en het aantal klanten in die vestiging
	 * @return map met plaatsnaam en aantal klanten
	 */
	public Map<String, Integer> getVestigingenMap() {
	    Map<String, Integer> map  = new TreeMap<>();
	    for(Vestiging v: vestigingenCrrnt) {
	      String plaatsnaam = v.getPlaats();
	      map.put(plaatsnaam, v.getKlantenStrings().size());
	    }
	    return map;
	}
}
