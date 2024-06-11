package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import domein.Klant;
import domein.PostcodeInfo;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Test de Klant classe
 */

class KlantTest {

	private Klant klant = null;
	private PostcodeInfo postcode = null;
	private PoiException pe;
	
	@BeforeEach
	void setup() throws PoiException {
		postcode = new PostcodeInfo("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
	}

	@Test
	void happyTest() {
		try {
			klant = new Klant(1, postcode);
			assertEquals(1, klant.getKlantnr());
			assertEquals("8701GH", klant.getPostcode().getPostcode());
		} catch (PoiException e) {
			e.printStackTrace();
		}
	}

	@Test
	void foutieveInvoerTest() {
		// negatief klant nr
		pe = assertThrows(PoiException.class, () -> {
			new Klant(-1, postcode);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.KLANTNUMMER_NIET_POSITIEF);
		
		// klantnr = 0
		pe = assertThrows(PoiException.class, () -> {
			new Klant(0, postcode);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.KLANTNUMMER_NIET_POSITIEF);

		// postcode == null
		pe = assertThrows(PoiException.class, () -> {
			new Klant(123, null);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.POSTCODE_NULL);

	}

}
