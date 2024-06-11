package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;

class BedrijfTest {

	private static Bedrijf b = null;

	@BeforeAll
	static void setup() {
		b = new Bedrijf();
	}

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
	
	@Test
	void foutieveInvoer() {
		Collection<String> klanten;
		klanten = b.getVestigingKlanten("Disneyland");
		assertNull(klanten);
	}
}
