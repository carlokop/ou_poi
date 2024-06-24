package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import controller.ModelBedrijfssimulatie;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;

public class Bedrijfssimulatie extends Bedrijf implements ModelBedrijfssimulatie  {
	
	private Collection<Vestiging> vestigingen;
	// houdt bij of een vestiging open is; true voor open, false voor dicht
	private Map<Vestiging, Boolean> vestigingenChecklist; 
	// houdt de oorspronkelijke vestiging(en) bij en de huidige
	private Map<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> klantenChecklist;
	
	public Bedrijfssimulatie() throws PoiException {
			setupSimulatie();
			initKlantenChecklist();
			initVestigingenChecklist();
	}
	
	/**
	 * Moet alleen worden aangeroepen bij de init, we willen de gewijzigde data behouden bij het heropenen van de view
	 */
	@Override
	public void setupSimulatie() {
		vestigingen = new ArrayList<Vestiging>(Bedrijf.getVestigingen());
	}
	
	public Map<Vestiging, Boolean> getVestigingenChecklist(){
		return this.vestigingenChecklist;
	}
	
	public Map<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> getKlantenChecklist(){
		return this.klantenChecklist;
	}
	
	@Override
	public void sluitVestiging(String plaats) {
		Vestiging geslotenVestiging = Vestiging.select(plaats, vestigingen);
		Collection<Vestiging> openVestigingen = new ArrayList<Vestiging>();
		
		// Zet status vestiging op gesloten
		vestigingenChecklist.replace(geslotenVestiging, false);
		
		// Compileer lijst van open vestigingen waarmee gerekend wordt
		Set<Entry<Vestiging, Boolean>> cleSet = vestigingenChecklist.entrySet();
		for(Entry<Vestiging, Boolean> cle : cleSet) {
			if(cle.getValue()) {
				openVestigingen.add(cle.getKey());
			}
		}
		Vestiging.migratieSluitenVestiging(geslotenVestiging, openVestigingen, klantenChecklist);
	}
	
	@Override
	public void openVestiging(String plaats) {
		// TODO Auto-generated method stub
		Vestiging.migratieOpenenVestiging(null, vestigingen, vestigingen, null);
	}

	@Override
	public void validate() throws PoiException {
		if(Bedrijf.getVestigingen() == null) {
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
		for(Vestiging v: vestigingen) {
			klanten = v.getKlanten();
			for(Klant k : klanten) {
				if(klantenChecklist.containsKey(k)) {
					klantEntry = klantenChecklist.get(k);
					klantEntry.getKey().add(v); 	// onthoud oorspronkelijke vestiging(en)
					klantEntry.getValue().add(v);	// onthoud huidige vestiging(en)
				} else {
					klantEntry = Map.entry(new ArrayList<>(), new ArrayList<>());
					klantenChecklist.put(k, klantEntry);
				}
				
			}
		}
	}
	
	public void resetKlantenCheckList() {
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;
		Set<Klant> klanten = klantenChecklist.keySet();
		for(Klant k: klanten) {
			klantEntry = klantenChecklist.get(k); // lijst met oorspronkelijke vestiging(en)
			klantEntry.setValue(klantEntry.getKey());
//			klantenChecklist.put(k, klantEntry); // misschien niet nodig als de entry via referentie in map wordt bijgewerkt
		}
	}
	
	public void initVestigingenChecklist() {
		vestigingenChecklist = new HashMap<Vestiging, Boolean>();
		for(Vestiging v: vestigingen) {
			vestigingenChecklist.put(v, true);
		}
	}
	
	public void resetVestigingenChecklist() {
		for(Vestiging v: vestigingen) {
			vestigingenChecklist.replace(v, true);
		}
	}
}
