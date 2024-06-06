package Data;

import java.io.PrintWriter;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import Exceptions.MapperException;
import Exceptions.MapperExceptionCode;

public class Mapper {
  
	private class DBConst {
    	private static final String DRIVERNAAM = "org.firebirdsql.jdbc.FBDriver"; //jaybird-5.0.4.java11
    	private static final String URL = "jdbc:firebird://localhost:3050/C:/POI_DB/Prik2Go_res_v3.fdb";
    	private static final String GEBRUIKERSNAAM = "SYSDBA";
    	private static final String WACHTWOORD = "masterkey";
	}
	
    private PreparedStatement pselectvestigingen = null;
	private Connection con = null; // verbinding met gegevensbank

	public Mapper() throws MapperException {
		connect();
		initialiseerPrepStatements();	
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
	 * @throws MapperException als de driver niet geladen kan worden of het verbinding
	 *                     maken mislukt (bv. door een fout in de padnaam).
	 */
	public void connect() throws MapperException {
		try {
			Class.forName(DBConst.DRIVERNAAM); // 1. zoek naar de te laden driver klasse
			DriverManager.setLogWriter(new PrintWriter(System.out)); // 2. print driver output naar console
			con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD); // 3. start de verbinding
		} catch (ClassNotFoundException cnfe) {// <- 1
			throw new MapperException(MapperExceptionCode.JAYBIRD_JDBC_NOT_FOUND, cnfe.getMessage());
		} catch (SecurityException se) {// <- 2
			throw new MapperException(MapperExceptionCode.LOGWRITER_PERMISSION_DENIED, se.getMessage());
		} catch (SQLException e) { // <- 3
			throw new MapperException(MapperExceptionCode.CONNECTION_ESTABLISH_ERR,e.getMessage());
		}
	}

	/**
	 * Sluit de verbinding met de database.
	 * @throws MapperException 
	 */
	public void disconnect() throws MapperException {
		if(con != null) {
			try {
				con.close();
			} catch (SQLException e) {
				throw new MapperException(MapperExceptionCode.CONNECTION_SHUTDOWN_ERR, e.getMessage());
			}
		}
	}

	/* - 	-	-	-	-	-	-	-	-	-	-	-	- TODO hieronder -	-	-	-	-	-	-	-	-	-	-	-	-	-	-*/
	
	/**
	 * Initialiseert twee PreparedStatements voor de SQL-opdrachten om - alle cd's
	 * in te lezen uit tabel CD - alle tracks in te lezen bij een gegeven CD
	 * 
	 * @throws MapperException als de SQL-opdracht een fout bevat of niet gecompileerd
	 */
	private void initialiseerPrepStatements() throws MapperException {
      try {
        pselectvestigingen = con.prepareStatement( // <-- ?? Carlo navragen
            "WITH vest_postcode AS (\r\n"
            + "  SELECT v.plaats, v.postcode AS vest_postcode, p.lat AS vest_lat, p.lng AS vest_lng FROM vestiging v\r\n"
            + "  JOIN postcodeinfo p ON v.postcode = p.postcode\r\n"
            + "),\r\n"
            + "klant_postcode AS (\r\n"
            + "  SELECT k.nr AS klantnr, k.postcode, p.plaats AS klant_plaats, p.lat AS klant_lat, p.lng AS klant_lng FROM klant k\r\n"
            + "  JOIN postcodeinfo p ON k.postcode = p.postcode\r\n"
            + ")\r\n"
            + "SELECT v.plaats, vest_postcode, vest_lat, vest_lng, klantnr, klant_plaats, k.postcode AS klant_postcode, klant_lat, klant_lng \r\n"
            + "FROM bezoek b\r\n"
            + "JOIN vest_postcode v ON b.vestiging = v.plaats\r\n"
            + "JOIN klant_postcode k ON b.klant = klantnr\r\n"
            + "ORDER BY vestiging;\r\n");
        
      }
      catch (SQLException e) {
        //als er nu een fout optreedt, moet de verbinding eerst gesloten worden! TODO: Waarom?
    	  disconnect();
//        throw new MapperException();
      }
	}

	/**
	 * Leest alle vestigingen en alle onderliggende associaties uit en maakt domeinobjecten
	 * @return lijst met vestigingen inclusief alle onderliggende associaties
	 * @throws MapperException
	 * @contract happy {
	 *   @requires con != null
	 *   @requires pselectvestigingen != null
	 *   @ensures \result is een lijst met vestigingen
	 * }
	 * @contract SQLException {
	 *   @requires SQLException
	 *   @signals DBException("Fout bij het inlezen van de Vestigingen")
	 * }
	 * @contract Ongeldige invoer {
     *   @requires postcode bevat ongeldige parameters
     *   @requires klant bevat ongeldige parameters
     *   @signals DBException("Fout bij het maken van domeinobjecten")
     * }
	 */
	public Collection<Vestiging> getVestigingen() throws MapperException {
	  
	    Collection<Vestiging> vestigingenlijst = new ArrayList<>();
	    //Collection<Klant> klanten = new ArrayList<>();
	    
	    Map<Integer, Klant> klantenMap = new HashMap<>();
        Map<String, Vestiging> vestigingenMap = new HashMap<>();
	    
	    
	    try {
	      ResultSet res = pselectvestigingen.executeQuery();
	      while (res.next()) {
	        String vestiging_plaats = res.getString(1);
	        String vestiging_postcode = res.getString(2);
	        double vestiging_lat = res.getDouble(3);
	        double vestiging_lng = res.getDouble(4);
	        
	        int klantnr = res.getInt(5);
	        String klant_plaats = res.getString(6);
	        String klant_postcode = res.getString(7);
	        double klant_lat = res.getDouble(8);
            double klant_lng = res.getDouble(9);
                        
            /*
             * Op advies van John Chen heb ik me ingelezen in de complexieit van deze code
             * ipv nested loops om een lookup te doen met O(n2) heb ik de lookup vervangen mapfunctie 
             * teneinde de complexiteit te verlagen naar O(n)
             * gezien dit nog niet uitgelegd in eerdere cursussen is en dit de èerst keer is dat ik dat doe 
             * heb ik dit stukje code met hulp van ChatGPT refactored
             */
            Klant klant = klantenMap.computeIfAbsent(klantnr, k -> {
              PostcodeInfo pKlant = new PostcodeInfo(klant_postcode, klant_plaats, klant_lat, klant_lng);
              return new Klant(klantnr, pKlant);
            });
            
            Vestiging vestiging = vestigingenMap.computeIfAbsent(vestiging_plaats, v -> {
              PostcodeInfo pVestiging = new PostcodeInfo(vestiging_postcode, vestiging_plaats, vestiging_lat, vestiging_lng);
              Vestiging newVestiging = new Vestiging(vestiging_plaats, pVestiging, new ArrayList<>());
              vestigingenlijst.add(newVestiging);
              return newVestiging;
            });
   
            //voeg unieke klanten toe
//            for(Klant k: klanten) {
//              if(k.getKlantnr() == klantnr) {
//                klant = k;
//                break;
//              } 
//            }
            
           //vestiging bestaat nog niet, maak nieuwe
//            if(klant == null) {
//              Postcode pKlant = new Postcode(klant_postcode,klant_plaats, klant_lat, klant_lng);
//              klant = new Klant(klantnr, pKlant);
//              klanten.add(klant);
//            }
            
            /*
             * Maak vestiging
             * In eerste instantie maken we een instantie en voeg die toe aan vestigingen 
             * als de vestiging al bestaat geef die terug
             * vul vervolgens de vestigingen met de klanten
             */
//            Postcode pvestiging = new Postcode(vestiging_postcode,vestiging_plaats, vestiging_lat, vestiging_lng); 
//            Collection<Klant> klantenVestiging = new ArrayList<>();
//            Vestiging vestiging = null;
//            
//            //als de vestiging reeds gemaakt is, geef die vestiging
//            for(Vestiging v: vestigingen) {
//              if(v.getPlaats().equals(vestiging_plaats)) {
//                vestiging = v;
//                break;
//              } 
//            }
//            
//            //vestiging bestaat nog niet, maak nieuwe
//            if(vestiging == null) {
//              vestiging = new Vestiging(vestiging_plaats, pvestiging, klantenVestiging);
//              vestigingen.add(vestiging);
//            }
            
            vestiging.voegKlantToe(klant);
            
	      }

	    }
	    catch (SQLException e) {
	      throw new MapperException("Fout bij het inlezen van de Vestigingen");
	      
	    } catch(IllegalArgumentException e) {
	      //we maken hier een dbexceptie van
	      throw new MapperException("Fout bij het maken van domeinobjecten");
	    }
	    
	    return vestigingenlijst;
	    
	    
	  }
}
