
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Exceptions.MapperException;
import Exceptions.MapperExceptionCode;

// Test stukjes code hier

public class Kladblok {
	private Connection con = null;
	private class DBConst {
    	private static final String DRIVERNAAM = "org.firebirdsql.jdbc.FBDriver";
    	private static final String URL = "jdbc:firebird://localhost:3050/C:/POI_DB/Prik2Go_res_v3.fdb";
    	private static final String GEBRUIKERSNAAM = "SYSDBA";
    	private static final String WACHTWOORD = "masterkey";
	}
	
	public void blad() {
		try {
			maakVerbinding();
		} catch (MapperException e) {
			e.printStackTrace();
		}
	}
	
	private void maakVerbinding() throws MapperException {
		try {
			DriverManager.setLogWriter(new PrintWriter(System.out));
			Class.forName(DBConst.DRIVERNAAM);
			con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD);
		} catch (ClassNotFoundException cnfe) {// <- 1
			throw new MapperException(MapperExceptionCode.JAYBIRD_JDBC_NOT_FOUND, cnfe.getMessage());
		} catch (SecurityException se) {// <- 2
			throw new MapperException(MapperExceptionCode.LOGWRITER_PERMISSION_DENIED, se.getMessage());
		} catch (SQLException e) { // <- 3
			throw new MapperException(MapperExceptionCode.CONNECTION_ESTABLISH_ERR,e.getMessage());
		}
	}
	
	public static void main(String[] args) {
		new Kladblok().blad();
	  }
}
