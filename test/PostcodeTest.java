package test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

  @Test
  public void happyTest() {
    postcode = new Postcode("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
    assertEquals("8701GH",postcode.getPostcode());
    assertEquals("Bolsward",postcode.getPlaats());
    double tolerance = 1e-9;
    assertEquals(53.0673994187339,postcode.getLat(), tolerance);
    assertEquals(5.5274963648489,postcode.getLng(), tolerance);
    
    //lat == 90
    postcode = new Postcode("8701GH", "Bolsward", 90, 5.5274963648489);
    assertEquals(90,postcode.getLat(), tolerance);
    
    //lat == -90
    postcode = new Postcode("8701GH", "Bolsward", -90, 5.5274963648489);
    assertEquals(-90,postcode.getLat(), tolerance);
    
    //lng == 180
    postcode = new Postcode("8701GH", "Bolsward", 53.0673994187339, 180);
    assertEquals(180,postcode.getLng(), tolerance);
    
    //lng == -180
    postcode = new Postcode("8701GH", "Bolsward", 53.0673994187339, -180);
    assertEquals(-180,postcode.getLng(), tolerance);
    
  }
  
  @Test
  public void ongelgigeInvoerTest() {
    //postcode null
    assertThrows(IllegalArgumentException.class, () -> { new Postcode(null, "Bolsward", 53.0673994187339, 5.5274963648489); });
    
    //postcode lege string
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("", "Bolsward", 53.0673994187339, 5.5274963648489); });
    
    //postcode string van alleen spaties
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("  ", "Bolsward", 53.0673994187339, 5.5274963648489); });
    
    //plaatsnaam null
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", null, 53.0673994187339, 5.5274963648489); });
    
    //plaatsnaam lege string
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", "", 53.0673994187339, 5.5274963648489); });
    
    //plaatsnaam string van spaties
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", "  ", 53.0673994187339, 5.5274963648489); });
    
    //lat > 90
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", "Bolsward", 90.001, 5.5274963648489); });
    
    //lat < -90
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", "Bolsward", -90.001, 5.5274963648489); });
    
    //lng < -180
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", "Bolsward", 53.0673994187339, -180.001); });
    
    //lng > 180
    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701GH", "Bolsward", 53.0673994187339, 180.001); });
    
    //voldoet niet aan regex
//    assertThrows(IllegalArgumentException.class, () -> { new Postcode("8701 GH", "Bolsward", 53.0673994187339, 5.5274963648489); });
//    assertThrows(IllegalArgumentException.class, () -> { new Postcode("870GH", "Bolsward", 53.0673994187339, 5.5274963648489); });
//    assertThrows(IllegalArgumentException.class, () -> { new Postcode("870111", "Bolsward", 53.0673994187339, 5.5274963648489); });
//    assertThrows(IllegalArgumentException.class, () -> { new Postcode("GHHHHH", "Bolsward", 53.0673994187339, 5.5274963648489); });
//    assertThrows(IllegalArgumentException.class, () -> { new Postcode("GH8701", "Bolsward", 53.0673994187339, 5.5274963648489); });
  }
  

}
