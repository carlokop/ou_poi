package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;
import domein.Klant;
import domein.PostcodeInfo;
import domein.Vestiging;
import exceptions.PoiException;

/**
 * Test voor bedrijf
 */
class BedrijfTest {

  private static Bedrijf b = null;

  /**
   * Initialiseert het bedrijf
   */
  @BeforeAll
  static void setup() {
      try {
		b = new Bedrijf();
	} catch (PoiException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
  }

  /**
   * Tests happy flow
   */
  @Test
  void happy() {
      Collection<String> plaatsnamen = b.getVestigingPlaatsen();
      Collection<String> klanten;
      // test of er 12 plaatsnamen zijn
      assertEquals(12, plaatsnamen.size());
  
      for (String plaats : plaatsnamen) {
          assertFalse(plaats.isBlank());
          
          klanten = b.getVestigingKlanten(plaats);
          assertTrue(klanten.size()>0);
      }
  }
  
  /**
   * Tests onjuiste invoer
   */
  @Test
  void foutieveInvoer() {
      Collection<String> klanten;
      klanten = b.getVestigingKlanten("Disneyland");
      assertNull(klanten);
  }
  
  /**
   * Tests diepe kopie van vestigingen
   * @throws PoiException 
   */
//  @Test
//  public void testCopy() throws PoiException {
//    ArrayList<Vestiging> vestigingen = (ArrayList<Vestiging>) Bedrijf.getVestigingenSnapshot();
//    ArrayList<Vestiging> kopie_vestigingen = (ArrayList<Vestiging>) Bedrijf.getDeepCopy();
//    
//    for(int i=0; i<vestigingen.size(); i++ ) {
//      //geen referentie
//      assertNotSame(vestigingen.get(i), kopie_vestigingen.get(i));
//      //zelfde aantal klanten
//      assertEquals(vestigingen.get(i).getKlanten().size(), kopie_vestigingen.get(i).getKlanten().size());
//      //zelfde inhoud
//      assertEquals(vestigingen.get(i).getPlaats(), kopie_vestigingen.get(i).getPlaats());
//      assertEquals(vestigingen.get(i).getPostcodeInfo().getPostcode(), kopie_vestigingen.get(i).getPostcodeInfo().getPostcode());
//    }
//    
//  }


}
