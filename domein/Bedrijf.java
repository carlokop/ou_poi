package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Map.Entry;

import controller.ModelBedrijf;
import data.Mapper;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;
import observer.Subject;

/**
 * Deze klasse stelt het bedrijf voor
 * Beheert vervolgens alle vestigingen en regelt de communocatie met de mapper
 */
public class Bedrijf extends Subject implements ModelBedrijf {
	public static Collection<Vestiging> vestigingenSnapshot;
	private Collection<Vestiging> vestigingenCrrnt;
	private static Mapper m;
	
	// houdt bij of een vestiging open is; true voor open, false voor dicht. Deze lijst moet alle instanties behouden
		private Map<Vestiging, Boolean> vestigingenChecklist;
		// houdt de oorspronkelijke vestiging(en) bij en de huidige
		private Map<String, Entry<Collection<Vestiging>, Collection<Vestiging>>> klantenChecklist;

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
	 * maakt een diepe kopie van alle vestigignen en alle onderliggende objecten
	 * @return een kopie van vestigingen
	 * @throws PoiException als er fouten zijn
	 */
	public static Collection<Vestiging> getDeepCopy() throws PoiException{
        //return m.getVestigingen();
        Collection<Vestiging> copyvestigingen = new ArrayList<>();
                
        for(Vestiging v:Bedrijf.vestigingenSnapshot) {
          copyvestigingen.add(Vestiging.copy(v));
        }
        return copyvestigingen;
    }
	
	public static void validate() throws PoiException {
		if (Bedrijf.vestigingenSnapshot == null) {
			throw new PoiException(PoiExceptionCode.BEDRIJF_VESTIGINGEN_SNAPSHOT_NULL, null);
		}
	}
	
	//TODO: ZIE MAIN COMMENTAAR
//	public Bedrijf(Mapper m) throws PoiException {
//		Bedrijf.m = m;
//		Bedrijf.vestigingenSnapshot = m.getVestigingen();
//	}
	
	/**
	 * Haalt een lijst van de locatienamen op van vestigingen
	 * @return lijst van vestiginglocaties als string
	 */
	@Override
    public Collection<String> getVestigingPlaatsen(){
        Collection<String> lijstPlaatsenNamen = new ArrayList<>();

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
    public Collection<String> getVestigingKlanten(String plaats) {
        Collection<String> vestigingKlantenData = null;
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
	 * Geeft een lijst met alle vestigingen
	 * @return de lijst met vestigingen
	 */
	public Collection<Vestiging> getVestigingen(){
		return this.vestigingenCrrnt;
	}
	
	/**
	 * Maakt een checklist voor alle klanten bestaande uit een oude en nieuwe vestiging
	 */
	public void setupKlantenChecklist() {
		klantenChecklist = new HashMap<>();
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;

		Collection<String> klanten;
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
	 * maakt een map entry voor een klantnummer met oude en nieuwe vestignign
	 * @return een entry met klantnummer oude en nieuwe vestiging
	 */
	public Map<String, Entry<Collection<Vestiging>, Collection<Vestiging>>> getKlantenChecklist() {
		return this.klantenChecklist;
	}
	
	/**
	 * Doorloopt de huidige vestigingen en maarkt een map die alle vestigingen als open bestempeld
	 */
	public void setupVestigingenChecklist() {
		vestigingenChecklist = new HashMap<Vestiging, Boolean>();
		for (Vestiging v : vestigingenCrrnt) {
			vestigingenChecklist.put(v, true);
		}
	}

	/**
	 * Geeft de vestigingenchecklist
	 * @return de vestigingenchecklist
	 */
	public Map<Vestiging, Boolean> getVestigingenChecklist() {
		return this.vestigingenChecklist;
	}

	/**
	 * Geeft aan of opgegeven vestiging open is
	 * @param plaatsnaam van vestiging die opgezocht moet worden
	 * @return true als de vestiging open is
	 */
	public Boolean isVestigingOpen(String plaatsnaam){
		return this.vestigingenChecklist.get(Vestiging.select(plaatsnaam, vestigingenCrrnt));
	}
	
	/**
	 * Methode werkt vestiging checklist bij, de aanroep naar vestiging werkt de
	 * klantenchecklist bij.
	 * @param plaats de plaatsnaam van de te sluiten vestinging
	 */
	@Override
	public void sluitVestiging(String plaats) {
		Vestiging geslotenVestiging = Vestiging.select(plaats, vestigingenCrrnt);
		
		// Zet status vestiging op gesloten
		vestigingenChecklist.replace(geslotenVestiging, false);

		// Compileer lijst van open vestigingen waarmee gerekend wordt
		Collection<Vestiging> openVestigingen = new ArrayList<Vestiging>();
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
	 * @param plaats de plaatsnaam van de te openen vestiging
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
