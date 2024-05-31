package test;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


public class MapperTest {

	Connection c;
	DriverManager dm;
	
	@BeforeEach
	public void init() {
		//TODO Begin connectie voor de database
    	c = null;
    	
	}
	
	/**
	 * Connectie met fdb database is succesvol
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
     * Connectie met fdb database succesvol afgesloten
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
