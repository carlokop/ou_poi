package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.Test;

import Domein.PostcodeInfo;
import Exceptions.PostcodeException;
import Exceptions.PostcodeExceptionCode;

/**
 * Test Postcodeinfo, instanties in testen kunnen fictief zijn.
 */
public class PostcodeTest {
	PostcodeException pce;

	/**
	 * Er zijn 4 parameters voor postcode: postcode, bestaande uit: 4-cijferreeks:
	 * 1000-9999 2-letterreeks: AA-ZZ plaatsnaam, niet lege string; zonder getallen,
	 * met apostrof en spaties. 1 <= string.length <= 30, langste adres in NL is 28
	 * lang maar we kiezen even 30. latitude, -90 <= lat <= 90 longitude, -180 <=
	 * lng <= 180
	 * 
	 * We testen hierin elke geldige randwaarde
	 */
	@Test
	public void validInput() {
		try {
			// Postcode cijferreeks ondergrens 1000
			new PostcodeInfo("1000EG", "Amsterdam", 52.377778951201, 4.9055895401203);
			// Postcode cijferreeks bovengrens 9999
			new PostcodeInfo("9999IH", "Stitswerd", 53.3609, 6.5993);
			// Postcode letterreeks ondergrens AA
			new PostcodeInfo("5555AA", "Fictiefistan", 52.377778951201, 4.9055895401203);
			// Postcode letterreeks bovengrens ZZ
			new PostcodeInfo("5555ZZ", "Fictiefistan", 52.377778951201, 4.9055895401203);

			// Plaatsnaam apostrof en spatie(s)
			new PostcodeInfo("5555EG", " '", 52.377778951201, 4.9055895401203);
			// Plaatsnaam alleen letters
			new PostcodeInfo("5555EG", "Abc", 52.377778951201, 4.9055895401203);
			// Plaatsnaam lengte 1
			new PostcodeInfo("5555EG", "O", 52.377778951201, 4.9055895401203);
			// Plaatsnaam lengte 30
			new PostcodeInfo("5555EG", "Langeplaatsnaamdiedertiglangis", 52.377779, 4.9058954);
			assertEquals(30, "Langeplaatsnaamdiedertiglangis".length());

			// Latitude ondergrens -90
			new PostcodeInfo("5555EG", "O", -90, 4.9055895401203);
			// Latitude bovengrens 90
			new PostcodeInfo("5555EG", "O", 90, 4.9055895401203);
			// longitude ondergrens -180
			new PostcodeInfo("5555EG", "O", 52.377778951201, -180);
			// longitude bovengrens 180
			new PostcodeInfo("5555EG", "O", 52.377778951201, 180);
		} catch (PostcodeException pce) {
			pce.printStackTrace();
			fail();
		}
		assertTrue(true);
	}

	/**
	 * postcode, bestaande uit: 4-cijferreeks: 1000-9999 2-letterreeks: AA-ZZ
	 * 
	 * We testen hierin elke ongeldige randwaarden
	 */
	@Test
	public void invalidPostcode() {
		// postcode null
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo(null, "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_NULL);

		// postcode 3-cijferreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("100EG", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_LENGTE);

		// postcode 5-cijferreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("55000EG", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_LENGTE);

		// postcode niet-cijfer in cijferreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5A00EG", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_VELDEN);

		// postcode cijferreeks ondergrens overschrijding
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("0999AB", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_NUL_START);

		// postcode cijferreeks bovengrens overschrijding, wordt 10000, zelfde als
		// 5-cijferreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("10000AB", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_LENGTE);

		// postcode 1-letterreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("1000A", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_LENGTE);

		// postcode 3-letterreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("1000ABC", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_LENGTE);

		// postcode niet-letter in letterreeks
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("1000A2", "Amsterdam", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.POSTCODE_VELDEN);
	}

	/**
	 * plaatsnaam: niet lege string; zonder getallen, met apostrof en spaties. 1 <=
	 * string.length <= 30, langste adres in NL is 28 lang maar we kiezen even 30.
	 * 
	 * We testen hierin elke ongeldige randwaarden
	 */
	@Test
	public void invalidPlaatsnaam() {
		// plaatsnaam null
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AB", null, 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.PLAATSNAAM_NULL);

		// plaatsnaam legestring, length = 0
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AB", "", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.PLAATSNAAM_LEEG);

		// plaatsnaam bestaat uit alleen spaties
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AB", "   ", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.PLAATSNAAM_ALLEEN_SPATIES);

		// plaatsnaam met ander symbool dan letter, spatie of apostrof
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AB", "naam@at", 52.377778951201, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.PLAATSNAAM_ILLEGAL_CHAR);

		// plaatsnaam langer dan 30 karakters
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AB", "Langeplaatsnaamdielangerdandertigis", 52.377778951201, 4.9055895401203);
		});
		assertTrue(30 < "Langeplaatsnaamdielangerdandertigis".length());
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.PLAATSNAAM_TE_GROOT);
	}

	/**
	 * Geografische coordinaten: latitude, -90 <= lat <= 90 longitude, -180 <= lng
	 * <= 180
	 *
	 * We testen hierin elke ongeldige randwaarden
	 */
	@Test
	public void invalidGGCoords() {
		// latitude < -90
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AA", "Fictiefistan", -91, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.GGCOORDS_LAT_OVERSCHRIJDING);

		// latitude > 90
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AA", "Fictiefistan", 91, 4.9055895401203);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.GGCOORDS_LAT_OVERSCHRIJDING);

		// latitude < -180
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AA", "Fictiefistan", 45, -181);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.GGCOORDS_LNG_OVERSCHRIJDING);

		// latitude > 180
		pce = assertThrows(PostcodeException.class, () -> {
			new PostcodeInfo("5555AA", "Fictiefistan", 45, 181);
		});
		assertEquals(pce.getErrCode(), PostcodeExceptionCode.GGCOORDS_LNG_OVERSCHRIJDING);
	}

}