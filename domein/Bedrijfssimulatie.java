package domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

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
		
			validate();
			vestigingen = new ArrayList<Vestiging>(Bedrijf.getVestigingen());
			initKlantenChecklist();
	}
	
	@Override
	public void startSimulatie() {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void sluitVestiging(String plaats) {
		// TODO Auto-generated method stub
		
	}
	@Override
	public void openVestiging(String plaats) {
		// TODO Auto-generated method stub
		
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
//				klantenChecklist.put();
			}
		}
	}
}
