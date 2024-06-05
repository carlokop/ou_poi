package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domein.Klant;
import Domein.Postcode;

/**
 * Test de Klant classe
 * @author carlo
 *
 */
class KlantTest {
  
  private Klant klant = null;
  private Postcode postcode = null;
  
  
    @BeforeEach
    void setup() {
      postcode = new Postcode("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
    }
    
    @Test
    void happyTest() {
      klant = new Klant(123, postcode);
      assertEquals(123,klant.getKlantnr());
      assertEquals("8701GH",klant.getPostcode().getPostcode()); 
    }
    
    @Test 
    void fouteveInvoerTest() {
      //negatief klanr nr
      assertThrows(IllegalArgumentException.class, () -> { new Klant(-1, postcode); });
      
      //klantnr = 0
      assertThrows(IllegalArgumentException.class, () -> { new Klant(0, postcode); });
      
      //postcode == null
      assertThrows(IllegalArgumentException.class, () -> { new Klant(123, null); });
      
    }

}
