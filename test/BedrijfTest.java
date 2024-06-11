package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;

class BedrijfTest {
  
  private Bedrijf b = null;
  
  @BeforeEach
  void setup() {
    b = new Bedrijf();
  }
  
  @Test
  void happy() {
    Collection<String> plaatsnamen = b.getVestigingPlaatsen();
    //test of er 12 plaatsnamen zijn
    assertEquals(12, plaatsnamen.size());
    
    for(String plaats: plaatsnamen) {
      assertNotEquals("",plaats);
    }
    
  }

	@Test
	void test() {
		//fail("Not yet implemented");
	}

}
