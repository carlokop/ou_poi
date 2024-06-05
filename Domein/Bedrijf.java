package Domein;

import java.util.ArrayList;
import java.util.Collection;
import java.util.TreeMap;

import Controller.Model;
import ObserverPatroon.Subject;

public class Bedrijf extends Subject implements Model{
	Collection<Vestiging> vestigingen;
	
	public Bedrijf() {
		vestigingen = new ArrayList<>();
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
			}
		}
		
		klantCache = vestigingSelectie.getKlanten();
		for(Klant k: klantCache) {
			vestigingKlantenData.add(String.valueOf(k.getKlantnr()));
		}
		return vestigingKlantenData;
	}
}
