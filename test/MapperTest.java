package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;
import java.util.Iterator;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import data.Mapper;
import domein.Klant;
import domein.Vestiging;
import exceptions.PoiException;

public class MapperTest {

	private static Connection c;
	private static Mapper m;
	PoiException me;

	/**
	 * Test connectie met de fdb database
	 */
	
	@BeforeAll
	@Test
	public static void init() {
    	try {
			m = new Mapper();
    		c = m.getConnection();
    		assertTrue(c.isValid(1000));
		} catch (PoiException | SQLException e) {
			fail("Test kan niet gestart worden");
			e.printStackTrace();
		}
	}

    @Test
    public void testCorrecteSortering() throws PoiException {
    	Collection<Vestiging> vestigingen = m.getVestigingen();
    	Collection<Klant> vestigingKlanten = null;
    	Iterator<Klant> vkIt;
    	Klant prevK, crrntK;

    	for(Vestiging v: vestigingen) {
    		vestigingKlanten = v.getKlanten();
    		if(vestigingKlanten.size() > 1) {
    			vkIt = vestigingKlanten.iterator();
            	prevK = vkIt.next();
            	while(vkIt.hasNext()) {
            		crrntK = vkIt.next();
            		assertTrue(prevK.getKlantnr() < crrntK.getKlantnr());
            		prevK = crrntK;
            	}
        	}
    	}
    }

    @Test
    public void getVestigingen() throws PoiException {
    	Collection<Vestiging> vestigingen = m.getVestigingen();
    	assertEquals(12,vestigingen.size());

    	//alle vestigingen bevatten klanten
    	for(Vestiging v: vestigingen) {
    	  Collection<Klant> klanten = v.getKlanten();
    	  assertTrue(klanten.size() > 0);
    	}

    	//klant 1089 zit maar 1x in vestiging veendam te zitten ipv 2x
    	Vestiging veendam = null;
    	for(Vestiging v: vestigingen) {
          if("Veendam".equals(v.getPlaats())) {
            veendam = v;
          }
        }
    	assertNotNull(veendam);

    	int i = 0;
    	for(Klant k: veendam.getKlanten()) {
    	  if(k.getKlantnr() == 1089) {
    	    i++;
    	  }
    	}
    	assertEquals(1, i);

    	//klant 794 zit in zowel vestiging groningen als zuidhorn
    	//test op gelijkheid
    	Vestiging groningen = null;
    	Vestiging zuidhorn = null;
    	for(Vestiging v: vestigingen) {
          if("Groningen".equals(v.getPlaats())) {
            groningen = v;
          } else if("Zuidhorn".equals(v.getPlaats())) {
            zuidhorn = v;
          }
        }
    	assertNotNull(groningen);
    	assertNotNull(zuidhorn);

    	Klant kGron = null;
    	Klant kZuidh = null;
    	for(Klant k: groningen.getKlanten()) {
    	  if(k.getKlantnr() == 794) {
    	    kGron = k;
    	  }
    	}
    	
    	for(Klant k: zuidhorn.getKlanten()) {
          if(k.getKlantnr() == 794) {
            kZuidh = k;
          }
        }
    	assertNotNull(kGron);
    	assertNotNull(kZuidh);
    	assertEquals(kGron, kZuidh);
    	assertEquals(kGron.getKlantnr(), 794);
    }

	@AfterAll
	@Test
	public static void closeConnection() {
		try {
			c.close();
			assertTrue(c.isClosed());
		} catch (SQLException e) {
			e.printStackTrace();
			fail();
		}
	}
}
