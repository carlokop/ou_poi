package test;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domein.Klant;
import Domein.Postcode;
import Domein.Vestiging;

public class PostcodeTest {
	
	/**
	 * TODO: 
	 * 	1. KWESTIE, test statische data bij runtime of bij test
	 *  2. Wijzig naam postcode naar postcodeInfo, ook voor klasse?
	 * Postcodes worden of zijn ingeladen
	 * Toegang hiertoe wordt verkregen via vestiging.
	 */
	
	Collection<Vestiging> vestigingen;
	Collection<Klant> klanten;
	Postcode postcode;
	
	@BeforeEach
	public void init() {
    	vestigingen = null;
	}
	
	@Test
	public void vestigingenZijnIngeladen() {
		assert(vestigingen.size()>0);
		for(Vestiging v: vestigingen) {
			klanten = v.getKlanten();
			assert(klanten.size()>0);
		}
	}
	
	@Test
	public void isGeldigPostcode() {
		for(Vestiging v: vestigingen) {
			klanten = v.getKlanten();
			for(Klant k:klanten) {
				postcode = k.getPostcode();
				
			}
		}
	}
}
