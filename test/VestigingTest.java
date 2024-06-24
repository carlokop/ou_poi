package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import domein.Bedrijf;
import domein.Klant;
import domein.PostcodeInfo;
import domein.Vestiging;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Test voor vestigingen
 */
class VestigingTest {

	private static Bedrijf testBedrijf;
	private static Collection<Vestiging> testVestigingen;
	private static Iterator tvIterator;
	private static Vestiging vestiging = null;
	private static PostcodeInfo postcode = null;
	private static ArrayList<Klant> klantenlijst = null;
	private static PoiException pe;
	

	/**
	 * Initialiseert het postcodeinfo object en een lijstje met drie klanten
	 */
	@BeforeAll
	static void setup() {
		try {
			testBedrijf = new Bedrijf();
			
			postcode = new PostcodeInfo("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
			klantenlijst = new ArrayList<>();
			klantenlijst.add(new Klant(123, postcode));
			klantenlijst.add(new Klant(124, postcode));
			klantenlijst.add(new Klant(125, postcode));

		} catch (PoiException e) {
			fail(e.getMessage());
		}
	}

	@Test
	void klantDichtsteVestigingTest() {
		//TODO: 
	}
	
	@Test
	void migratieSluitenTest() {
		testVestigingen = new ArrayList<>(Bedrijf.getVestigingen());
		tvIterator = testVestigingen.iterator();
		
		assert(tvIterator.hasNext());
		vestiging = testVestigingen.iterator().next();
		Vestiging.migratieSluitenVestiging(vestiging, testVestigingen);
		//TODO: 
	}

	@Test
	void migratieOpenenTest() {
		testVestigingen = new ArrayList<>(testBedrijf.getVestigingen());
		tvIterator = testVestigingen.iterator();
		
		assert(tvIterator.hasNext());
		vestiging = testVestigingen.iterator().next();
		//TODO: 
	}

	/**
	 * Test happy path
	 */
	@Test
	void happy() {
		ArrayList<Klant> klantenlijst = new ArrayList<>();
		try {
			klantenlijst.add(new Klant(1, postcode));
			klantenlijst.add(new Klant(124, postcode));
			klantenlijst.add(new Klant(125, postcode));
			vestiging = new Vestiging("Bolsward", postcode, klantenlijst);

			assertEquals("Bolsward", vestiging.getPlaats());
			assertEquals("8701GH", vestiging.getPostcodeInfo().getPostcode());
			assertEquals("8701GH", vestiging.getPostcodeInfo().getPostcode());

			assertEquals(3, vestiging.getKlanten().size());
//    vestiging.voegKlantToe(new Klant(126, postcode));  //taak 5
//    assertEquals(4,vestiging.getKlanten().size());

			// klantenlijst mag leeg zijn
			vestiging = new Vestiging("Bolsward", postcode, new ArrayList<>());
			assertEquals(0, vestiging.getKlanten().size());

//    //toevoegen geeft true
//    Klant klant = new Klant(126, postcode);
//    assertTrue(vestiging.voegKlantToe(klant));
//
//    //dubbele klant toevoegen geeft alleen false
//    assertFalse(vestiging.voegKlantToe(klant));
//
//    //null toevoegen geeft false
//    assertFalse(vestiging.voegKlantToe(null));
		} catch (PoiException e) {
			fail();
			e.printStackTrace();
		}
	}

	/**
	 * Test op ongeldige invoer Nulls, lege stringen of alleen spaties
	 */
	@Test
	void foutiveInvoer() {

		// plaats null
		pe = assertThrows(PoiException.class, () -> {
			new Vestiging(null, postcode, klantenlijst);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.PLAATSNAAM_NULL);

		// plaats lege string
		pe = assertThrows(PoiException.class, () -> {
			new Vestiging("", postcode, klantenlijst);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.PLAATSNAAM_LEEG);

		// plaats string met spaties
		pe = assertThrows(PoiException.class, () -> {
			new Vestiging(" ", postcode, klantenlijst);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.PLAATSNAAM_ALLEEN_SPATIES);

		// postcode = null. Als de postcode validatie uitstaat, slaagt deze niet.
		pe = assertThrows(PoiException.class, () -> {
			new Vestiging("Bolsward", null, klantenlijst);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.POSTCODE_NULL);

		// klantenlijst is null
		pe = assertThrows(PoiException.class, () -> {
			new Vestiging("Bolsward", postcode, null);
		});
		assertEquals(pe.getErrCode(), PoiExceptionCode.KLANTENLIJST_NULL);

	}

}
