package Data;

import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;

import org.firebirdsql.jdbc.FBResultSet;

import Domein.Klant;
import Domein.PostcodeInfo;
import Domein.Vestiging;
import Exceptions.MapperException;
import Exceptions.MapperExceptionCode;
import Exceptions.PostcodeException;

/**
 * Mapper
 * @author carlo
 *
 */
public class Mapper {

	private class DBConst {
		private static final String DRIVERNAAM = "org.firebirdsql.jdbc.FBDriver"; // jaybird-5.0.4.java11
		private static final String URL = "jdbc:firebird://localhost:3050/C:/POI_DB/Prik2Go_res_v3.fdb";
		private static final String GEBRUIKERSNAAM = "SYSDBA";
		private static final String WACHTWOORD = "masterkey";
	}

	private PreparedStatement getVestigingen = null;
	private PreparedStatement getKlantenV = null;
	private Connection con = null; // verbinding met gegevensbank

	public Mapper() throws MapperException {
		connect();
		initPreparedStatements();
	}

	/**
	 * Geeft de DB connectie
	 * @return de connectie
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * Maakt verbinding met de database. Eerst wordt de JDBC-driver geregistreerd
	 * door het laden van de juiste implementatie van Driver;
	 * 
	 * @throws MapperException als de driver niet geladen kan worden of het
	 *                         verbinding maken mislukt (bv. door een fout in de
	 *                         padnaam).
	 */
	public void connect() throws MapperException {
		try {
			Class.forName(DBConst.DRIVERNAAM); // 1. zoek naar de te laden driver klasse
			DriverManager.setLogWriter(new PrintWriter(System.out)); // 2. print driver output naar console
			con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD); // 3. start de
																										// verbinding
		} catch (ClassNotFoundException cnfe) {// <- 1
			throw new MapperException(MapperExceptionCode.JAYBIRD_JDBC_NOT_FOUND, cnfe.getMessage());
		} catch (SecurityException se) {// <- 2
			throw new MapperException(MapperExceptionCode.LOGWRITER_PERMISSION_DENIED, se.getMessage());
		} catch (SQLException e) { // <- 3
			throw new MapperException(MapperExceptionCode.CONNECTION_ESTABLISH_ERR, e.getMessage());
		}
	}
 
	/**
	 * Sluit de verbinding met de database.
	 * @throws MapperException
	 */
	public void disconnect() throws MapperException {
		if (con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new MapperException(MapperExceptionCode.CONNECTION_SHUTDOWN_ERR, e.getMessage());
			}
		}
	}

	/**
	 * Initialiseer prepared statements
	 * @throws MapperException sql-query syntax bevat fouten of compilatie is mislukt
	 */
	private void initPreparedStatements() throws MapperException {
		try {
			getVestigingen = con.prepareStatement(Queries.GET_VESTIGINGEN);
			getKlantenV = con.prepareStatement(Queries.GET_KLANTEN);
		} catch (SQLException sqle) {
			throw new MapperException(MapperExceptionCode.SQL_QUERY_PREP_STAT_ERROR, sqle.getMessage());
		}
	}

	/**
	 * TODO: Contract bijwerken
	 * Leest alle vestigingen en alle onderliggende associaties uit en maakt
	 * domeinobjecten
	 * 
	 * @return lijst met vestigingen inclusief alle onderliggende associaties
	 * @throws MapperException
	 * @throws PostcodeException
	 * @contract happy {
	 * @requires con != null
	 * @requires pselectvestigingen != null
	 * @ensures \result is een lijst met vestigingen }
	 * @contract SQLException {
	 * @requires SQLException
	 * @signals DBException("Fout bij het inlezen van de Vestigingen") }
	 * @contract Ongeldige invoer {
	 * @requires postcode bevat ongeldige parameters
	 * @requires klant bevat ongeldige parameters
	 * @signals DBException("Fout bij het maken van domeinobjecten") }
	 */
	public Collection<Vestiging> getVestigingen() throws MapperException {
	  Collection<Vestiging> vestigingen = new ArrayList<>();
	  try {
		Vestiging vestigingCache;
		Collection<Klant> klantCache;
		PostcodeInfo pciCache;
		FBResultSet result;
		result = (FBResultSet) getVestigingen.executeQuery();
		while (result.next()) {
			pciCache = new PostcodeInfo(
					result.getString("POSTCODE"), 
					result.getString("PLAATS"),
					result.getDouble("LAT"), 
					result.getDouble("LNG"));
			vestigingen.add(new Vestiging(
					result.getString("PLAATS"), 
					pciCache, 
					new HashSet<>()
			));
		}

		result = (FBResultSet) getKlantenV.executeQuery();
		while (result.next()) {
			vestigingCache = selectVestiging(vestigingen, result.getString("VESTIGINGPLAATS"));
			pciCache = new PostcodeInfo(
					result.getString("KLANTPOSTCODE"), 
					result.getString("KLANTPLAATS"),
					result.getDouble("KLANTLAT"), 
					result.getDouble("KLANTLNG"));
			klantCache = vestigingCache.getKlanten();
			klantCache.add(new Klant(result.getInt("KLANTNR"), pciCache));
		}
		
	  } catch(SQLException e) {
	    throw new MapperException(MapperExceptionCode.MAPPER_DATA_BUILD_ERR,e.getMessage());
	  } catch(PostcodeException e) {
	    throw new MapperException(MapperExceptionCode.MAPPER_DATA_BUILD_ERR,e.getMessage());
      }
		return vestigingen;
	}

	/**
	 * Zoekt vestiging op met plaatsnaam
	 * @param vestigingen 	Verzameling waarin gezocht wordt
	 * @param vSelect		selectiecriteria
	 * @return gevonden vestiging
	 * @throws MapperException Als de vestiging niet in de verzameling zit
	 */
	private Vestiging selectVestiging(Collection<Vestiging> vestigingen, String vSelect) throws MapperException {
		for (Vestiging vi : vestigingen) {
			if (vi.getPlaats().equalsIgnoreCase(vSelect)) {
				return vi;
			}
		}
		throw new MapperException(MapperExceptionCode.MAPPER_DATA_BUILD_ERR, "Vestiging niet gevonden");
	}
	
	
	
	
}
