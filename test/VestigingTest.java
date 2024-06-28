package test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertThrows;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

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
	 * Test correctheid van corresponderende methode voor het opzoeken vd
	 * dichtstbijzijnde vestiging
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

			// Zelfde afstand naar 2 of meer vestiging, selecteert de eerste van de
			// vestigingen die deze ziet
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant4,
					new ArrayList<Vestiging>(List.of(vestiging0, vestiging1, vestiging3, vestiging4)));
			assertEquals(vestigingResultaat, vestiging0);
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant4,
					new ArrayList<Vestiging>(List.of(vestiging1, vestiging3, vestiging4, vestiging0)));
			assertEquals(vestigingResultaat, vestiging1);

			// Selecteert dichtste afstand uit selectie
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant5,
					new ArrayList<Vestiging>(List.of(vestiging2, vestiging1, vestiging0)));
			assertEquals(vestigingResultaat, vestiging2);

			// Selectie uit 1 enkele vestiging moet dezelfde vestiging opleveren
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant5,
					new ArrayList<Vestiging>(List.of(vestiging0)));
			assertEquals(vestigingResultaat, vestiging0);

			// Selecteert dichtste afstand uit alle vestigingen
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant1,
					new ArrayList<Vestiging>(List.of(vestiging0, vestiging1, vestiging2, vestiging3, vestiging4)));
			assertEquals(vestigingResultaat, vestiging1);
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant2,
					new ArrayList<Vestiging>(List.of(vestiging0, vestiging1, vestiging2, vestiging3, vestiging4)));
			assertEquals(vestigingResultaat, vestiging3);
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant3,
					new ArrayList<Vestiging>(List.of(vestiging0, vestiging1, vestiging2, vestiging3, vestiging4)));
			assertEquals(vestigingResultaat, vestiging4);

			// Bij geen opgegeven vestiging retourneer null als gevonden dichtsbijzijnde
			// vestiging
			vestigingResultaat = Vestiging.getKlantDichtsteVestiging(klant1, new ArrayList<Vestiging>());
			assertEquals(vestigingResultaat, null);
		} catch (PoiException e) {
			// Aanmaak testobjecten mislukt.
			fail(e.getMessage());
		}
	}
	
	/**
	 * Test implementatie corresponderende methodes voor 2 of meer vestigingen; 
	 * sluitVestiging(...) van klasse Bedrijfssimulatie en
	 * migratieSluitenVestiging(...) van klasse Vestiging
	 */
//	@Test
//	void migratieSluiten2VestigingenOfMeerTest() {
//		Bedrijfssimulatie bs;
//		try {
//			bs = new Bedrijfssimulatie();
//
//			Map<Vestiging, Boolean> vChecklist = bs.getVestigingenChecklist();
//			testVestigingen = new ArrayList<Vestiging>(bs.getSimVestigingen());		// lijst met alle vestigingen
//
//			// Minstens 2 vestigingen nodig om de migratie binnen de functie te testen
//			assert (testVestigingen.size() > 1);
//
//			// Kies vestiging om te sluiten, en verwijder deze uit de lijst met open vestigingen
//			vestiging = testVestigingen.iterator().next();	// de te sluiten vestiging
//			testVestigingen.remove(vestiging); 				// lijst met open vestigingen
//
//			// Haal lijst met klanten op van de te sluiten vestiging
//			Collection<Klant> KlantenLijst = new ArrayList<>(vestiging.getKlanten());
//			
//			// Voer sluiting uit
//			System.out.println(vestiging.getPlaats());
//			bs.sluitVestiging(vestiging.getPlaats());
//
//			// Bevestig dat voor elke klant van de gesloten vestiging dat deze naar de dichtstbijzijnde vestiging is gegaan.
//			Vestiging dichtsteVestiging;
//			assertEquals(vestiging.getKlanten().size(), 0);
//			for (Klant k : KlantenLijst) {
//				dichtsteVestiging = Vestiging.getKlantDichtsteVestiging(k, testVestigingen);
//				assert (dichtsteVestiging.getKlanten().contains(k));
//			}
//			
//			assertFalse(vChecklist.get(vestiging));
//			
//		} catch (PoiException e) {
//			fail("Simulatie start mislukt");
//			e.printStackTrace();
//		}
//	}

	/**
	 * 	Test implementatie corresponderende methodes;
	 * 	openVestiging(...) van klasse Bedrijfssimulatie en
	 *	migratieOpenenVestiging(...) van klasse Vestiging	
	 * 
	 *  Hierin wordt het sluiten van alle vestigingen getest en het openen van 1 vestiging
	 */
//	@Test
//	void migratieOpenenKlant1InitieleVestigingTest() {
//		Bedrijfssimulatie bs;
//
//		try {
//			bs = new Bedrijfssimulatie();
//			testVestigingen = bs.getSimVestigingen();
//			
//			// Minstens 1 vestigingen nodig om deze functie te testen
//			assert (testVestigingen.size() > 0);
//			
//			tvIterator = testVestigingen.iterator();
//			Map<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> kcl = bs.getKlantenChecklist();
//			Collection<Klant> klantenlijst;
//			Klant testKlant = null;
//			Entry<Collection<Vestiging>, Collection<Vestiging>> testKlantEntry = null;
//			Vestiging vestigingTeOpenen;
//			
//			// Van alles gesloten naar 1 geopend
//			
//			// Zoek naar testklant met 1 initiele vestiging
//			for(Entry<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> kclEntry: kcl.entrySet()) {
//				if(kclEntry.getValue().getKey().size() == 1) {
//					testKlant = kclEntry.getKey();
//					testKlantEntry = kclEntry.getValue();
//				}
//			}
//			// Verifeer het bestaan van deze klant
//			assertNotNull(testKlant);
//			
//			// Sluit vestigingen en controleer dat deze ook leeg zijn
//			while(tvIterator.hasNext()) {
//				vestiging = tvIterator.next();
//				bs.sluitVestiging(vestiging.getPlaats());
//				assertEquals(vestiging.getKlanten().size(), 0);
//			}			
//			
//			// Controleer dat de checklist is bijgewerkt
//			Map<Vestiging, Boolean> vcl = bs.getVestigingenChecklist();
//			for( Entry<Vestiging, Boolean> vclEntry:vcl.entrySet()) {
//				assertFalse(vclEntry.getValue());
//			}
//			
//			// Controleer dat de klant geen huidige vestigingen heeft
//			assertEquals(testKlantEntry.getValue().size(), 0);
//			
//			// Open een vestiging waar de klant initieel bij zat
//			vestigingTeOpenen = testKlantEntry.getKey().iterator().next();
//			bs.openVestiging(vestigingTeOpenen.getPlaats());
//			
//			// Controleer dat de vestiging de originele klanten weer heeft
//			Vestiging vOrigineel = Vestiging.select(vestigingTeOpenen.getPlaats(), Bedrijf.getVestigingenSnapshot());
//			assertEquals(vestigingTeOpenen.getKlanten().size(), vOrigineel.getKlanten().size() );
//			for(Klant k: vOrigineel.getKlanten()) {
//				assertTrue(vestigingTeOpenen.getKlanten().contains(k));
//			}
//			
//			// Controleer de checklists:
//			assertEquals(testKlantEntry.getValue().size(), 1);
//
//			// Vestiging checklist
//			for(Entry<Vestiging, Boolean> vclEntry:vcl.entrySet()) {
//				if(vclEntry.getKey().equals(vestigingTeOpenen)) {
//					// alleen geopende vestiging is true
//					assertTrue(vclEntry.getValue());
//					assertEquals(vclEntry.getKey(), vestigingTeOpenen);
//				} else {
//					assertFalse(vclEntry.getValue());
//				}
//			}
//			
//			// Klant checklist
//			for(Entry<Klant, Entry<Collection<Vestiging>, Collection<Vestiging>>> kclEntry:kcl.entrySet()) {
//				if(vOrigineel.getKlanten().contains(kclEntry.getKey())) {
//					// originele klant
//					assertTrue(kclEntry.getValue().getKey().contains(vestigingTeOpenen)); 	//  originele vestigingen bevat geopende vestiging
//					assertTrue(kclEntry.getValue().getValue().contains(vestigingTeOpenen));	// huidige vestiging bevat geopende vestiging
//				} else {
//					// niet originele klant
//					assertFalse(kclEntry.getValue().getKey().contains(vestigingTeOpenen)); 	// originele vestigingen bevat niet geopende vestiging
//					assertFalse(kclEntry.getValue().getValue().contains(vestigingTeOpenen));// huidige vestiging bevat niet geopende vestiging
//				}
//			}
//			
//		} catch (PoiException e) {
//			e.printStackTrace();
//		}
//	}

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
	
	/**
	 * Test het diep kopieren van vestigingen
	 * @throws PoiException 
	 */
	@Test
	public void TestCopy() throws PoiException {
	  PostcodeInfo postcode = new PostcodeInfo("8701GH", "Bolsward", 53.0673994187339, 5.5274963648489);
	  PostcodeInfo postcode2 = new PostcodeInfo("8771GH", "Anders", 53.067399418, 5.52749545489);
	  Vestiging vestiging = new Vestiging("Bolsward", postcode, new ArrayList<Klant>());
	  Klant k1 = new Klant(1,postcode);
	  Klant k2 = new Klant(1,postcode2);
	  vestiging.addKlant(k1);
	  vestiging.addKlant(k2);
	  Vestiging kopie = Vestiging.copy(vestiging);
	  
	  //we hebben twee klanten in de lijst
	  assertEquals(2,kopie.getKlanten().size());
	  
	  //geen referentie
	  assertNotSame(vestiging,kopie);
	  assertNotSame(vestiging.getPostcodeInfo(),kopie.getPostcodeInfo());
	  
	  //TODO: Aangepast
	  ArrayList<Klant> vklanten = (ArrayList<Klant>) vestiging.getKlanten();
	  ArrayList<Klant> cklanten = (ArrayList<Klant>) kopie.getKlanten();
	  for(int i=0; i<2; i++) {
	     assertNotSame(vklanten.get(i), cklanten.get(i));
	  }
	  
	  //zelfde inhoud
	  assertEquals(vestiging.getPostcodeInfo().getPostcode(), kopie.getPostcodeInfo().getPostcode());
	  assertEquals(vestiging.getPlaats(), kopie.getPlaats());
	  for(int i=0; i<2; i++) {
        assertEquals(vklanten.get(i).getKlantnr(),cklanten.get(i).getKlantnr());
        assertEquals(vklanten.get(i).getPostcodeInfo().getPostcode(),cklanten.get(i).getPostcodeInfo().getPostcode());
     }
	  
	  
	}

}
