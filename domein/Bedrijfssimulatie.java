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
	// houdt bij of een vestiging open is
	private Map<Vestiging, Boolean> vestigingenChecklist; 
	// houdt de oorspronkelijke vestiging(en) bij en de huidige
	private Map<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> klantenChecklist;
	
	public Bedrijfssimulatie() throws PoiException {
			setupSimulatie();
//			initKlantenChecklist();
//			initVestigingenChecklist();
	}
	
	@Override
	public void setupSimulatie() {
		vestigingen = new ArrayList<Vestiging>(Bedrijf.getVestigingen());
	}
	
	@Override
	public void sluitVestiging(String plaats) {
		// TODO Auto-generated method stub
		Collection<Vestiging> openVestigingen = new ArrayList<Vestiging>();
		Vestiging.migratieSluitenVestiging(null, vestigingen);
	}
	
	@Override
	public void openVestiging(String plaats) {
		// TODO Auto-generated method stub
		Vestiging.migratieOpenenVestiging(null, vestigingen, vestigingen, null);
	}

	@Override
	public void validate() throws PoiException {
		// TODO Auto-generated method stub
		if(Bedrijf.getVestigingen() == null) {
			throw new PoiException(PoiExceptionCode.BEDRIJFSSIM_BEDRIJF_VESTIGINGEN_NULL, Bedrijf.getVestigingen().toString());
		}
	} 
	
	public void initKlantenChecklist() {
		klantenChecklist = new HashMap<>();
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;
		
		Collection<Klant> klanten;
		for(Vestiging v: vestigingen) {
			klanten = v.getKlanten();
			for(Klant k : klanten) {
				klantEntry = Map.entry(new ArrayList<>(), new ArrayList<>());
				klantenChecklist.put(k, klantEntry);
			}
		}
	}
	
	public void resetKlantenCheckList() {
		Entry<Collection<Vestiging>, Collection<Vestiging>> klantEntry;
		Set<Klant> klanten = klantenChecklist.keySet();
		for(Klant k: klanten) {
			klantEntry = klantenChecklist.get(k);
			klantEntry.setValue(klantEntry.getKey());
//			klantenChecklist.put(k, klantEntry); // misschien niet nodig als de entry via referentie in map wordt bijgewerkt
		}
	}
	
	public void initVestigingenChecklist() {
		vestigingenChecklist = new HashMap<Vestiging, Boolean>();
		for(Vestiging v: vestigingen) {
			vestigingenChecklist.put(v, false);
		}
	}
	
	public void resetVestigingenChecklist() {
		for(Vestiging v: vestigingen) {
			vestigingenChecklist.replace(v, false);
		}
	}
}
