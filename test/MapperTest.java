package test;

import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Exceptions.MapperException;
import Data.Mapper;
import Domein.Klant;
import Domein.Vestiging;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Collection;

/**
 * Test de Mapper classe
 * @author John Chen
 *
 */
public class MapperTest {

	private Connection c;
	//private DriverManager dm;
	private Mapper m;
	MapperException me;
	
	MapperTest() throws MapperException {
	  m = new Mapper();
	}
	
	
	
	@BeforeEach
	public void init() {
		//TODO Begin connectie voor de database
    	c = m.getConnection();
    	
	}
	
	/**
	 * Test connectie met de fdb database
	 */
    @Test
    public void connectionTest(){ 
    	try {
			assertTrue(c.isValid(1000));
		} catch (SQLException e) {
			fail("connectionfout:" + e.getErrorCode());
			e.printStackTrace();
		}
    }
    
    /**
     * Test correct ophalen van de vestigingen
     * @throws MapperException 
     * @throws IllegalArgumentException 
     * @author carlo
     */
    /*
     @	@contract ophalenVestigingen {
     @		@requires fdb database met geen andere wijzigingen anders dan in taak 3 aangegeven.
     @		@ensures  lijst met vestigingen opgehaald en in benodigd formaat
     @	}
     */
    @Test
    public void getVestigingen() throws MapperException {
    	//TODO
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
    	assertEquals(1,i);
    	
    	//klant 794 zit in zowel vestiging groningen als zuidhorn
    	//test of dit dezelfde instantie is
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
    	assertEquals(kGron,kZuidh);
    	
    	
    	//hoe testen we de DBException?
    	
    }
    
    /**
     * Test vereist tabel bezoek
     */
//    public void getVestigingenKlanten() {
//    	//TODO
//    	Collection<Vestiging> vestigingen = null;
//    	Collection<Klant> klanten = null;
//    	
//    	assertEquals(vestigingen.size(), 12); 
//    	for(Vestiging vk:vestigingen) {
//    		klanten = vk.getKlanten();
//    		// test op klant heeft bezoek en vestiging klopt, vereist tabel bezoek
//    	}
//    }
    
    /**
     * Test sluiten van connectie met de fdb database
     */
    /*
    @	@contract sluitenConnectie {
    @ 		@requires Connectie is geldig
    @ 		@ensures  Verbinding met db is verbroken
    @	}
    */
    @Test
    public void connectionClose() {
    	try {
			c.close();
			assertTrue(c.isClosed());
		} catch (SQLException e) {
			fail("connectionfout:" + e.getErrorCode());
			e.printStackTrace();
		}
    }
}
