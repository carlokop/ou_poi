package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import controller.ModelBedrijfssimulatie;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;

public class Bedrijfssimulatie extends Bedrijf implements ModelBedrijfssimulatie {

	private Collection<Vestiging> vestigingen;
	// houdt bij of een vestiging open is; true voor open, false voor dicht. Deze lijst moet alle instanties behouden
	private Map<Vestiging, Boolean> vestigingenChecklist;
	// houdt de oorspronkelijke vestiging(en) bij en de huidige
	private Map<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> klantenChecklist;

	public Bedrijfssimulatie() throws PoiException {
		setupSimulatie();
		initKlantenChecklist();
		initVestigingenChecklist();
	}

	/**
	 * Moet alleen worden aangeroepen bij de init, we willen de gewijzigde data
	 * behouden bij het heropenen van de view
	 * @throws PoiException 
	 */
	@Override
	public void setupSimulatie() throws PoiException {
		vestigingen = Bedrijf.getNewCopy();
	}

	public Map<Vestiging, Boolean> getVestigingenChecklist() {
		return this.vestigingenChecklist;
	}

	public Map<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> getKlantenChecklist() {
		return this.klantenChecklist;
	}

	/**
	 * Methode werkt vestiging checklist bij, de aanroep naar vestiging werkt de
	 * klantenchecklist bij.
	 */
	@Override
	public void sluitVestiging(String plaats) {
		Vestiging geslotenVestiging = Vestiging.select(plaats, vestigingen);
		
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
		notifyObservers(plaats);
	}

	/**
	 * Methode werkt vestiging checklist bij, de aanroep naar vestiging werkt de
	 * klantenchecklist bij.
	 */
	@Override
	public void openVestiging(String plaats) {
		Vestiging geopendeVestiging = Vestiging.select(plaats, vestigingen);

		// Zet status vestiging op open
		vestigingenChecklist.replace(geopendeVestiging, true);
		System.out.println(Bedrijf.getVestigingen() == null);
		Vestiging.migratieOpenenVestiging(geopendeVestiging, Bedrijf.getVestigingen(), klantenChecklist);
		notifyObservers(plaats);
	}

	@Override
	public void validate() throws PoiException {
		if (Bedrijf.getVestigingen() == null) {
			throw new PoiException(PoiExceptionCode.BEDRIJFSSIM_BEDRIJF_VESTIGINGEN_NULL, null);
		}
	}

	public Collection<Vestiging> getSimVestigingen() {
		return this.vestigingen;
	}

	public void initKlantenChecklist() {
		klantenChecklist = new HashMap<>();
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;

		Collection<Klant> klanten;
		for (Vestiging v : vestigingen) {
			klanten = v.getKlanten();
			for (Klant k : klanten) {
				if (!klantenChecklist.containsKey(k)) { // maak entry voor klant indien nog niet bestaand
					klantenChecklist.put(k, Map.entry(new ArrayList<>(), new ArrayList<>()));
				} 
				klantEntry = klantenChecklist.get(k);
				klantEntry.getKey().add(v);	 	// onthoud oorspronkelijke vestiging(en)
				klantEntry.getValue().add(v);	// onthoud huidige vestiging(en)
			}
		}
	}

	public void resetKlantenCheckList() {
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;
		Set<Klant> klanten = klantenChecklist.keySet();
		for (Klant k : klanten) {
			klantEntry = klantenChecklist.get(k); // lijst met oorspronkelijke vestiging(en)
			klantEntry.setValue(klantEntry.getKey());
		}
	}

	public void initVestigingenChecklist() {
		vestigingenChecklist = new HashMap<Vestiging, Boolean>();
		for (Vestiging v : vestigingen) {
			vestigingenChecklist.put(v, true);
		}
	}

	public void resetVestigingenChecklist() {
		for (Vestiging v : vestigingen) {
			vestigingenChecklist.replace(v, true);
		}
	}
	
	/**
	 * Geeft een map terug met de geupdate plaatsnamen en het aantal klanten in die vestiging
	 * @return map met plaatsnaam en aantal klanten
	 */
	public Map<String, Integer> getSimVestigingenMap() {
	    Map<String, Integer> map  = new TreeMap<>();
	    for(Vestiging v: this.vestigingen) {
	      String plaatsnaam = v.getPlaats();
	      int aantal_klanten = v.getKlanten().size();
	      map.put(plaatsnaam, aantal_klanten);
	    }
	    return map;
	}
	
	
	
	
}
