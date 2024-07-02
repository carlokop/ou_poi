package data;

import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.firebirdsql.jdbc.FBResultSet;

import domein.Klant;
import domein.PostcodeInfo;
import domein.Vestiging;
import exceptions.PoiException;
import exceptions.PoiExceptionCode;

/**
 * Communiceert met de database en mapt gerelateerde domeinobjecten
 */
public class Mapper {

   /**
    * DB constanten om de verbinding te maken
    */
	private class DBConst {
		private static final String DRIVERNAAM = "org.firebirdsql.jdbc.FBDriver"; // jaybird-5.0.4.java11
		private static final String URL = "jdbc:firebird://localhost:3050/C:/POI_DB/Prik2Go_res_v3.fdb";
		private static final String GEBRUIKERSNAAM = "SYSDBA";
		private static final String WACHTWOORD = "masterkey";
	}

	private PreparedStatement getVestigingen = null;
	private PreparedStatement getKlantenV = null;
	private Connection con = null; // verbinding met de db

	/**
	 * Initialiseert de mapper en zet een DB verbinding op
	 * Tevens sluit de verbinding als de shutdownhook wordt aangeroepen
	 * @throws PoiException als fout is bij het maken van de verbinding
	 */
	public Mapper() throws PoiException {
		connect();
		initPreparedStatements();

		//Om koppeling te verlagen bij het sluiten van de GUI
		//Na speuren op stackoverflow
		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			try {
				System.out.println("DB shutdown initiated");
				disconnect();
			} catch (PoiException e) {
				e.printStackTrace();
			}
		}));
	}

	/**
	 * Geeft de DB connectie
	 *
	 * @return de connectie
	 */
	public Connection getConnection() {
		return con;
	}

	/**
	 * Maakt verbinding met de database. Eerst wordt de JDBC-driver geregistreerd
	 * door het laden van de juiste implementatie van Driver;
	 *
	 * @throws PoiException als de driver niet geladen kan worden of het
	 *                         verbinding maken mislukt (bv. door een fout in de
	 *                         padnaam).
	 */
	public void connect() throws PoiException {
		try {
			Class.forName(DBConst.DRIVERNAAM); // 1. zoek naar de te laden driver klasse
			DriverManager.setLogWriter(new PrintWriter(System.out)); // 2. print driver output naar console
			con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD); // 3. start de
																										// verbinding
		} catch (ClassNotFoundException cnfe) {// <- 1
			throw new PoiException(PoiExceptionCode.JAYBIRD_JDBC_NOT_FOUND, cnfe.getMessage());
		} catch (SecurityException se) {// <- 2
			throw new PoiException(PoiExceptionCode.LOGWRITER_PERMISSION_DENIED, se.getMessage());
		} catch (SQLException e) { // <- 3
			throw new PoiException(PoiExceptionCode.CONNECTION_ESTABLISH_ERR, e.getMessage());
		}
	}

	/**
	 * Sluit de verbinding met de database.
	 *
	 * @throws PoiException als er een SQL fout ontstaat
	 */
	public void disconnect() throws PoiException {
		if (con != null) {
			try {
				con.close();
				System.out.println("DB Verbinding is verbroken");
			} catch (SQLException e) {
				throw new PoiException(PoiExceptionCode.CONNECTION_SHUTDOWN_ERR, e.getMessage());
			}
		}
	}

	/**
	 * Initialiseer prepared statements
	 *
	 * @throws PoiException sql-query syntax bevat fouten of compilatie is
	 *                         mislukt
	 */
	private void initPreparedStatements() throws PoiException {
		try {
			getVestigingen = con.prepareStatement(Queries.GET_VESTIGINGEN);
			getKlantenV = con.prepareStatement(Queries.GET_KLANTEN);
		} catch (SQLException sqle) {
			throw new PoiException(PoiExceptionCode.SQL_QUERY_PREP_STAT_ERROR, sqle.getMessage());
		}
	}

	/**
     * Haalt alle vestigingen op uit de DB
     * Maakt instanties van alle vestigingen en onderliggende objecten
     * en geeft een lijst van alle vestigingen terug
     *
     * @return lijst met vestigingen inclusief alle onderliggende associaties
     * @throws PoiException als er een SQL fout of postcode exceptie ontstaat
     */
     /*@
     @ @contract happy {
     @   @requires con != null
     @   @requires pselectvestigingen != null
     @   @ensures \result is een lijst met vestigingen
     @ }
     @ @contract SQLException {
     @   @requires SQLException
     @   @signals PoiException("Fout bij het inlezen van de Vestigingen")
     @ }
     @ @contract Ongeldige invoer {
     @   @requires postcode bevat ongeldige parameters
     @   @requires klant bevat ongeldige parameters
     @   @signals PoiException("Fout bij het maken van domeinobjecten")
     @ }
     */
	public List<Vestiging> getVestigingen() throws PoiException {
		List<Vestiging> vestigingen = new ArrayList<>();
		Vestiging vestigingCache;
		List<Klant> klantCollectionCache;
		PostcodeInfo pciCache;
		FBResultSet result;
		Klant nieuweKlant;
		
		try {
			result = (FBResultSet) getVestigingen.executeQuery();
			while (result.next()) {
				pciCache = new PostcodeInfo(
						result.getString("POSTCODE"),
						result.getString("PLAATS"),
						result.getDouble("LAT"),
						result.getDouble("LNG"));
				vestigingen.add(new Vestiging(result.getString("PLAATS"),
								pciCache,
								new ArrayList<>()));  
			}

			result = (FBResultSet) getKlantenV.executeQuery();
			while (result.next()) {
				vestigingCache = selectVestiging(vestigingen, result.getString("VESTIGINGPLAATS"));
				pciCache = new PostcodeInfo(
						result.getString("KLANTPOSTCODE"),
						result.getString("KLANTPLAATS"),
						result.getDouble("KLANTLAT"),
						result.getDouble("KLANTLNG"));
				klantCollectionCache = vestigingCache.getKlanten();

				//voorkom dubbele klanten in dezelfde vestiging
				nieuweKlant = new Klant(result.getInt("KLANTNR"), pciCache);
				if (!klantCollectionCache.contains(nieuweKlant)) {
					klantCollectionCache.add(nieuweKlant);
                }
			}
		} catch (SQLException e) {
			throw new PoiException(PoiExceptionCode.MAPPER_DATA_BUILD_ERR, e.getMessage());
		} catch (PoiException e) {
			throw new PoiException(PoiExceptionCode.MAPPER_DATA_BUILD_ERR, e.getMessage());
		}
	
		return vestigingen;
	}

	/**
	 * Zoekt vestiging op met plaatsnaam
	 *
	 * @param vestigingen Verzameling waarin gezocht wordt
	 * @param vSelect     selectiecriteria
	 * @return gevonden vestiging
	 * @throws PoiException Als de vestiging niet in de verzameling zit
	 */
	private Vestiging selectVestiging(List<Vestiging> vestigingen, String vSelect) throws PoiException {
		for (Vestiging vi : vestigingen) {
			if (vi.getPlaats().equalsIgnoreCase(vSelect)) {
				return vi;
			}
		}
		throw new PoiException(PoiExceptionCode.MAPPER_DATA_BUILD_ERR, "Vestiging niet gevonden");
	}



}
