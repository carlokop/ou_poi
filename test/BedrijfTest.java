package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;

/**
 * Test voor bedrijf
 */
class BedrijfTest {

	private Bedrijf b = null;

	/**
	 * Inits bedrijf
	 */
	@BeforeAll
	void setup() {
		b = new Bedrijf();
	}

	/**
	 * Voert test uit voor happy path
	 */
	@Test
	void happy() {
		Collection<String> plaatsnamen = b.getVestigingPlaatsen();
		// test of er 12 plaatsnamen zijn
		assertEquals(12, plaatsnamen.size());

		for (String plaats : plaatsnamen) {
			assertNotEquals("", plaats);
		}

	}

	
//	@Test
//	void test() {
//		// fail("Not yet implemented");
//	}

}
