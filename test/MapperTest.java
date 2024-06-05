package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import Domein.Klant;
import Domein.Vestiging;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;


public class MapperTest {

	Connection c;
	DriverManager dm;
	
	@BeforeEach
	public void init() {
		//TODO Begin connectie voor de database
    	c = null;
    	
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
     */
    /*
     @	@contract ophalenVestigingen {
     @		@requires fdb database met geen andere wijzigingen anders dan in taak 3 aangegeven.
     @		@ensures  lijst met vestigingen opgehaald en in benodigd formaat
     @	}
     */
    @Test
    public void getVestigingen(){
    	//TODO
    	Collection<Vestiging> vestigingen = null;
    	assertEquals(vestigingen.size(), 12); 
    }
    
    /**
     * Test vereist tabel bezoek
     */
    public void getVestigingenKlanten() {
    	//TODO
    	Collection<Vestiging> vestigingen = null;
    	Collection<Klant> klanten = null;
    	
    	assertEquals(vestigingen.size(), 12); 
    	for(Vestiging vk:vestigingen) {
    		klanten = vk.getKlanten();
    		// test op klant heeft bezoek en vestiging klopt, vereist tabel bezoek
    	}
    }
    
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
