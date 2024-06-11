package test;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import java.util.Collection;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;

class BedrijfTest {

	private Bedrijf b = null;

	@BeforeAll
	void setup() {
		b = new Bedrijf();
	}

	@Test
	void happy() {
		Collection<String> plaatsnamen = b.getVestigingPlaatsen();
		// test of er 12 plaatsnamen zijn
		assertEquals(12, plaatsnamen.size());

		for (String plaats : plaatsnamen) {
			assertNotEquals("", plaats);
		}

	}

	@Test
	void test() {
		// fail("Not yet implemented");
	}

}
