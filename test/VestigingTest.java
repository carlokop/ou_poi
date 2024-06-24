package test;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

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

	private static Collection<Vestiging> testVestigingen;
	private static Iterator<Vestiging> tvIterator;
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
			new Bedrijf();
			postcode = new PostcodeInfo("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
			klantenlijst = new ArrayList<>();
			klantenlijst.add(new Klant(123, postcode));
			klantenlijst.add(new Klant(124, postcode));
			klantenlijst.add(new Klant(125, postcode));

		} catch (PoiException e) {
			fail(e.getMessage());
		}
	}

	/**
	 * Test correctheid van corresponderende methode voor het opzoeken vd dichtstbijzijnde vestiging
	 */
	@Test
	void klantDichtsteVestigingTest() {
		try {
			Vestiging vestigingResultaat;
			Vestiging vestiging0 = new Vestiging("A", new PostcodeInfo("0000AA", "A", -10, -10), new ArrayList<>());
			Vestiging vestiging1 = new Vestiging("B", new PostcodeInfo("0000BB", "B", 10, 10), new ArrayList<>());
			Vestiging vestiging2 = new Vestiging("C", new PostcodeInfo("0000CC", "C", 0, 0), new ArrayList<>());
			Vestiging vestiging3 = new Vestiging("D", new PostcodeInfo("0000DD", "D", -10, 10), new ArrayList<>());
			Vestiging vestiging4 = new Vestiging("E", new PostcodeInfo("0000EE", "E", 10, -10), new ArrayList<>());
			Klant klant1 = new Klant(1, new PostcodeInfo("1234AB", "Willekeurig", 11, 11));
			Klant klant2 = new Klant(2, new PostcodeInfo("1234AB", "Willekeurig", -11, 11));
			Klant klant3 = new Klant(3, new PostcodeInfo("1234AB", "Willekeurig", 11, -11));
			Klant klant4 = new Klant(4, new PostcodeInfo("1234AB", "Willekeurig", 0, 0));
			Klant klant5 = new Klant(5, new PostcodeInfo("1234AB", "Willekeurig", 5, 5));

			// Zelfde afstand naar 2 of meer vestiging, selecteert de eerste van de vestigingen die deze ziet
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant4, new ArrayList<Vestiging>(List.of(vestiging0,vestiging1,vestiging3,vestiging4)));
			assertEquals(vestigingResultaat, vestiging0);
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant4, new ArrayList<Vestiging>(List.of(vestiging1,vestiging3,vestiging4,vestiging0)));
			assertEquals(vestigingResultaat, vestiging1);
			
			// Selecteert dichtste afstand uit selectie
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant5, new ArrayList<Vestiging>(List.of(vestiging2,vestiging1,vestiging0)));
			assertEquals(vestigingResultaat, vestiging2);
			
			// Selectie uit 1 enkele vestiging moet dezelfde vestiging opleveren
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant5, new ArrayList<Vestiging>(List.of(vestiging0)));
			assertEquals(vestigingResultaat, vestiging0);
			
			// Selecteert dichtste afstand uit alle vestigingen
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant1, new ArrayList<Vestiging>(List.of(vestiging0,vestiging1,vestiging2, vestiging3,vestiging4)));
			assertEquals(vestigingResultaat, vestiging1);
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant2, new ArrayList<Vestiging>(List.of(vestiging0,vestiging1,vestiging2, vestiging3,vestiging4)));
			assertEquals(vestigingResultaat, vestiging3);
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant3, new ArrayList<Vestiging>(List.of(vestiging0,vestiging1,vestiging2, vestiging3,vestiging4)));
			assertEquals(vestigingResultaat, vestiging4);
			
			// Bij geen opgegeven vestiging retourneer null als gevonden dichtsbijzijnde vestiging
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant1, new ArrayList<Vestiging>());
			assertEquals(vestigingResultaat, null);
		} catch (PoiException e) {
			// Aanmaak testobjecten mislukt.
			fail(e.getMessage());
		}
	}
	
	/**
	 * Let op: Deze testresultaten zijn databasespecifiek.
	 * Test implementatie corresponderende methode
	 */
	@Test
	void migratieSluitenTest() {
		testVestigingen = new ArrayList<>(Bedrijf.getVestigingen());
		assert(testVestigingen.size() > 1);

		tvIterator = testVestigingen.iterator();
		vestiging = testVestigingen.iterator().next();
		Collection<Klant> KlantenLijst = new ArrayList<>(vestiging.getKlanten());
		testVestigingen.remove(vestiging);

		Vestiging.migratieSluitenVestiging(vestiging, testVestigingen);
		Vestiging dichtsteVestiging;
		
		assertEquals(vestiging.getKlanten().size(), 0);
		for(Klant k: KlantenLijst) {
			dichtsteVestiging = Vestiging.getKlantDichtsteVestiging(k, testVestigingen);
			assert(dichtsteVestiging.getKlanten().contains(k));
		}
	}

	/**
	 * TODO:
	 * Let op: Deze testresultaten zijn databasespecifiek.
	 * Test implementatie corresponderende methode
	 */
	@Test
	void migratieOpenenTest() {
		testVestigingen = new ArrayList<>(Bedrijf.getVestigingen());
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
