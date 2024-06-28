package domein;

import java.util.ArrayList;
import java.util.Collection;

import controller.ModelBedrijf;
import data.Mapper;
import exceptions.PoiException;
import observer.Subject;

/**
 * Deze klasse stelt het bedrijf voor
 * Bedrijf is een singleton
 * Beheert vervolgens alle vestigingen en regelt de communocatie met de mapper
 */
public class Bedrijf extends Subject implements ModelBedrijf {
	private static Collection<Vestiging> vestigingen;
	private static Mapper m;
	
	/**
	 * Initialiseert een bedrijf
	 * maakt een associatie met de mapper en vestigingen 
	 * @throws PoiException bij DB fout
	 */
	public Bedrijf() throws PoiException {
		if(m == null) {
			Bedrijf.m = new Mapper();
			Bedrijf.vestigingen = m.getVestigingen();
		}
	}
	
	//TODO: ZIE MAIN COMMENTAAR
	public Bedrijf(Mapper m) throws PoiException {
		Bedrijf.m = m;
		Bedrijf.vestigingen = m.getVestigingen();
	}
	
	/**
	 * Haalt een lijst van de locatienamen op van vestigingen
	 * @return lijst van vestiginglocaties als string
	 */
	@Override
    public Collection<String> getVestigingPlaatsen(){
        Collection<String> lijstPlaatsenNamen = new ArrayList<>();

        // lvp, lijst vestiging plaatsen
        for(Vestiging v: Bedrijf.vestigingen) {
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
        Collection<Klant> klantCache = null;

        // select from collection vestigingen
        for(Vestiging v: Bedrijf.vestigingen) {
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
	
	public static Collection<Vestiging> getVestigingen() {
		return Bedrijf.vestigingen;
	}
	
	/**
	 * Retourneert diepe kopie
	 * @return diepe kopie van Vestigingen collectie
	 * @throws PoiException
	 */
	public static Collection<Vestiging> getDeepCopy() throws PoiException{
		//return m.getVestigingen();
	    Collection<Vestiging> copyvestigingen = new ArrayList<>();
	    	    
	    for(Vestiging v:vestigingen) {
	      copyvestigingen.add(Vestiging.copy(v));
	    }
	    return copyvestigingen;
	}
}
