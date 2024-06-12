package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;

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
      b = new Bedrijf();
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


}
