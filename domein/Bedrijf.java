package domein;

import java.util.ArrayList;
import java.util.Collection;

import controller.Model;
import data.Mapper;
import exceptions.PoiException;
import observerPatroon.Subject;

/**
 * Beheert de mapper en alle bedrijven
 */
public class Bedrijf extends Subject implements Model{
	private Collection<Vestiging> vestigingen;
	private Mapper m;
	
	/**
	 * Initialiseert een bedrijf
	 * maakt een associatie met de mapper en vestigingen 
	 */
	public Bedrijf() {
		try {
			m = new Mapper();
			vestigingen = m.getVestigingen();
		} catch (PoiException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Haalt een lijst van de locatienamen op van vestigingen
	 * @return lijst van vestiginglocaties als string
	 */
	@Override
    public Collection<String> getVestigingPlaatsen(){
        Collection<String> lijstPlaatsenNamen = new ArrayList<>();

        // lvp, lijst vestiging plaatsen
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
	@Override
    public Collection<String> getVestigingKlanten(String plaats) {
        Collection<String> vestigingKlantenData = null;
        Vestiging vestigingSelectie = null;
        Collection<Klant> klantCache = null;

        // select from collection vestigingen
        for(Vestiging v: vestigingen) {
            if(v.getPlaats() == plaats) {
                vestigingSelectie = v;
                break;
            }
        }

        if(vestigingSelectie != null) {
            vestigingKlantenData = new ArrayList<>();
            // getKlanten, lsk
            klantCache = vestigingSelectie.getKlanten();
            for(Klant k: klantCache) {
                vestigingKlantenData.add(String.valueOf(k.getKlantnr()));
            }
        }
        
        return vestigingKlantenData;
    }
}
