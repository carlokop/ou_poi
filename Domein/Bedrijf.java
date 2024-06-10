package Domein;

import java.util.ArrayList;
import java.util.Collection;
import Controller.Model;
import Data.Mapper;
import Exceptions.MapperException;
import ObserverPatroon.Subject;

public class Bedrijf extends Subject implements Model{
	Collection<Vestiging> vestigingen;
	Mapper m;
	
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
	public Collection<Integer> getVestigingKlanten(String plaats){
		Collection<Integer> vestigingKlantenData = new ArrayList<>();
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
			vestigingKlantenData.add(k.getKlantnr());
		}
		return vestigingKlantenData;
	}
	
	/**
	 * verbreekt de verbinding en sluit de applicatie af
	 */
	public void sluitAf() {
	  try {
	    m.disconnect();
	    System.exit(0);
	  } catch(MapperException ignore) {
	    
	  }
	  
	}
	
	
}
