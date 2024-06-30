package Kladblok;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Collection;

import org.firebirdsql.jdbc.FBResultSet;

import controller.Controller;
import data.Mapper;
import data.Queries;
import domein.Bedrijf;
import domein.Vestiging;
import exceptions.PoiException;
import gui.Main.Gui;
import gui.simulatie.Visualizer;

/**
 * Deze klasse is geen onderdeel van het op te leveren systeem en dient slechts voor het testen van output
 */
public class Kladblok {
	private Mapper m;
	private Connection con;
	private PreparedStatement ps0, ps1;
	private FBResultSet result;

	/**
     * Verbindt de database
     */
	public void connect() {
	  try {
        m = new Mapper();
        con = m.getConnection();
      } catch (PoiException e) {
          e.printStackTrace();
      }
	}

	/**
     * Test de query met een aantal prints naar het console
     */
	public void blad() {
		try {
		    ps0 = con.prepareStatement(Queries.GET_VESTIGINGEN);
			result = (FBResultSet) ps0.executeQuery();
			System.out.println("-");
			System.out.println(result);
			System.out.println(result.next());
			System.out.println(result.getString("PLAATS"));
			System.out.println(result.getString("POSTCODE"));
			System.out.println(result.getString("LAT"));
			System.out.println(result.getString("LNG"));
			System.out.println("-");

			ps1 = con.prepareStatement(Queries.GET_KLANTEN);
			result = (FBResultSet) ps1.executeQuery();
			System.out.println("-");
			System.out.println(result);
			System.out.println(result.next());
			System.out.println(result.getString("KLANTNR"));
			System.out.println(result.getString("KLANTPOSTCODE"));
			System.out.println(result.getString("KLANTLAT"));
			System.out.println(result.getString("KLANTLNG"));
			System.out.println(result.getString("VESTIGINGPLAATS"));
			System.out.println("-");

			m.disconnect();
			System.out.println(m.getConnection().isClosed());

		} catch (PoiException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
     * Prints de vestigingen naar het console
     */
	public void printVestigingen() {
	  try {
	    Collection<Vestiging> vestigingen = m.getVestigingen();

	    for(Vestiging v: vestigingen) {
	      System.out.println(v.toString());
	    }

	  } catch(Exception e) {
	    System.out.println(e.getMessage());
	  }

	}
	
	/**
	 * test voor carlo
	 */
	public void taak5carlo() {
	  Gui gui = null;
	  try {
	    
	    Bedrijf b = new Bedrijf(); // bedrijf propageert PoiException naar boven naar main
        Controller facade = new Controller(b);
        gui = new Gui(facade);
//        b.attach(gui);
      } catch(Exception e) {
        gui = new Gui(e.getMessage());
        System.out.println("Ging iets fout: "+e.getMessage());
      }
	  
	}

	/**
     * Initialiseert het kladblok
     * @param args argumenten
     */
	public static void main(String[] args) {
		Kladblok k = new Kladblok();
		//k.connect();
		//k.blad();
		k.taak5carlo();
		//k.printVestigingen();
	  }
}
