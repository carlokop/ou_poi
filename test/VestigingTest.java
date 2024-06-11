package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Klant;
import domein.PostcodeInfo;
import domein.Vestiging;
import exceptions.PostcodeException;

/**
 * Test voor vestigingen
 */
class VestigingTest {
  
  private Vestiging vestiging = null;
  private PostcodeInfo postcode = null;
  private ArrayList<Klant> klantenlijst = null;
  
  /**
   * Initialiseert het postcodeinfo object en een lijstje met drie klanten
   * @throws PostcodeException
   */
  @BeforeEach
  void setup() throws PostcodeException {
    postcode = new PostcodeInfo("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
    klantenlijst = new ArrayList<>();
    klantenlijst.add(new Klant(123, postcode));
    klantenlijst.add(new Klant(124, postcode));
    klantenlijst.add(new Klant(125, postcode));
  }

  /**
   * Test happy path
   */
  @Test
  void happy() {
    ArrayList<Klant> klantenlijst = new ArrayList<>();
    klantenlijst.add(new Klant(123, postcode));
    klantenlijst.add(new Klant(124, postcode));
    klantenlijst.add(new Klant(125, postcode));
    vestiging = new Vestiging("Bolsward", postcode, klantenlijst);
    
    assertEquals("Bolsward",vestiging.getPlaats());
    assertEquals("8701GH",vestiging.getPostcode());
    assertEquals("8701GH",vestiging.getPostcode());
    
    assertEquals(3,vestiging.getKlanten().size());
//    vestiging.voegKlantToe(new Klant(126, postcode));
//    assertEquals(4,vestiging.getKlanten().size());
    
    //klantenlijst mag leeg zijn
    vestiging = new Vestiging("Bolsward", postcode, new ArrayList<Klant>());
    assertEquals(0,vestiging.getKlanten().size());
    
//    //toevoegen geeft true
//    Klant klant = new Klant(126, postcode);
//    assertTrue(vestiging.voegKlantToe(klant));
//    
//    //dubbele klant toevoegen geeft alleen false
//    assertFalse(vestiging.voegKlantToe(klant));
//    
//    //null toevoegen geeft false
//    assertFalse(vestiging.voegKlantToe(null));
  }
  
  /**
   * Test of er ongeldige invoer is
   * Nulls, lege stringen of alleen spaties
   */
  @Test 
  void foutiveInvoer() {
    //plaats null
    assertThrows(IllegalArgumentException.class, () -> { new Vestiging(null, postcode, klantenlijst); });
    
    //plaats lege string
    assertThrows(IllegalArgumentException.class, () -> { new Vestiging("", postcode, klantenlijst); });
    
    //plaats string met spaties
    assertThrows(IllegalArgumentException.class, () -> { new Vestiging(" ", postcode, klantenlijst); });
    
    //postcode = null
    assertThrows(IllegalArgumentException.class, () -> { new Vestiging("Bolsward", null, klantenlijst); });
    
    //klantenlijst is null
    assertThrows(IllegalArgumentException.class, () -> { new Vestiging("Bolsward", postcode, null); });
    
  }

}
