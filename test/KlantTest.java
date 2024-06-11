package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Klant;
import domein.PostcodeInfo;
import exceptions.IllegalArgumentExceptionCode;
import exceptions.PostcodeException;

/**
 * Test de Klant classe

 */
class KlantTest {
  
  private Klant klant = null;
  private PostcodeInfo postcode = null;
  private IllegalArgumentException iae;
  
  
    @BeforeEach
    void setup() {
      try {
        postcode = new PostcodeInfo("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
      } catch (PostcodeException pce) {
        pce.printStackTrace();
        fail();
      }
    }
    
    @Test
    void happyTest() {
      try {
        klant = new Klant(123, postcode);
        assertEquals(123,klant.getKlantnr());
        assertEquals("8701GH",klant.getPostcode().getPostcode()); 
      } catch(IllegalArgumentException iae) {
        iae.printStackTrace();
        fail();
      }
      
    }
    
    @Test 
    void foutieveInvoerTest() {
      //negatief klant nr
      iae = assertThrows(IllegalArgumentException.class, () -> { 
        new Klant(-1, postcode); 
      });
      assertEquals(iae.getMessage(), IllegalArgumentExceptionCode.KLANTNUMMER_NIET_POSITIEF.getErrMessage());
      
      
      //klantnr == 0
      iae = assertThrows(IllegalArgumentException.class, () -> { 
        new Klant(0, postcode); 
      });
      assertEquals(iae.getMessage(), IllegalArgumentExceptionCode.KLANTNUMMER_NIET_POSITIEF.getErrMessage());
      
      //postcode == null
      iae = assertThrows(IllegalArgumentException.class, () -> { 
        new Klant(123, null); 
      });
      assertEquals(iae.getMessage(), IllegalArgumentExceptionCode.POSTCODE_NULL.getErrMessage());
      
    }

}
