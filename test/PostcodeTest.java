package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThrows;

import org.junit.Test;

import Domein.Postcode;

/**
 * Test de Postcode classe
 * @author carlo
 *
 */
public class PostcodeTest {
  
  private Postcode postcode = null;
  
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
