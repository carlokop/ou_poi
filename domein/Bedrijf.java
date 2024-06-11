package domein;

import java.util.ArrayList;
import java.util.Collection;

import exceptions.MapperException;
import controller.Model;
import data.Mapper;
import observerPatroon.Subject;

public class Bedrijf extends Subject implements Model{
	private Collection<Vestiging> vestigingen;
	private Mapper m;
	
	public Bedrijf() {
		try {
			m = new Mapper();
			vestigingen = m.getVestigingen();
		} catch (MapperException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Haalt een lijst van de locatienamen op van vestigingen
	 * @return lijst van vestiginglocaties als string
	 */
	public Collection<String> getVestigingPlaatsen(){
		Collection<String> lijstPlaatsenNamen = new ArrayList<>();
		for(Vestiging v: vestigingen) {
			lijstPlaatsenNamen.add(v.getPlaats());
		}
		return lijstPlaatsenNamen;
	}
	
	/**
	 * Haalt een lijst van de id's van klanten op
	 * @param plaats ookwel vestiginglocatie
	 * @return lijst van id's als string
	 */
	public Collection<String> getVestigingKlanten(String plaats){
		Collection<String> vestigingKlantenData = new ArrayList<>();
		Vestiging vestigingSelectie = null;
		Collection<Klant> klantCache = null;
		
		for(Vestiging v: vestigingen) {
			if(v.getPlaats() == plaats) {
				vestigingSelectie = v;
				break;
			}
		}
		
		klantCache = vestigingSelectie.getKlanten();
		for(Klant k: klantCache) {
			vestigingKlantenData.add(String.valueOf(k.getKlantnr()));
		}
		return vestigingKlantenData;
	}
}
