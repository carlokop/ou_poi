package Data;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Mapper {
  
//  private PreparedStatement pselectvestigingen = null;
//  private PreparedStatement pselectklanten = null;
  private Connection con = null; // verbinding met gegevensbank 
  
  Mapper() throws DBException {
    maakVerbinding();
    initialiseerPrepStatements();
  }
  
  
  /**
   * Maakt verbinding met de database. 
   * Eerst wordt de JDBC-driver geregistreerd door het laden van
   * de juiste implementatie van Driver; de naam van deze
   * klasse is als constante voorhanden in DBConst.
   * Er kan daardoor van driver gewisseld worden zonder deze
   * klasse aan te passen.
   * @throws DBException als de driver niet geladen kan worden
   *         of het verbinding maken mislukt (bv. door een fout in
   *         de padnaam).
   */
  private void maakVerbinding() throws DBException {
    try {
      //DriverManager.setLogWriter(new PrintWriter(System.out));
      Class.forName(DBConst.DRIVERNAAM);
      con = DriverManager.getConnection(DBConst.URL, DBConst.GEBRUIKERSNAAM, DBConst.WACHTWOORD);
    }
    catch (ClassNotFoundException e) {
      throw new DBException("Driver niet geladen.");
    }
    catch (SQLException e) {
      throw new DBException("Verbinding maken is mislukt.");      
    }
  }
  
  /**
   * Sluit de verbinding met de database.
   */
  public void sluitVerbinding() {
    if (con != null) {
      try {
        con.close();
        //System.out.println("Verbinding gesloten.");
      }
      catch (SQLException e) {      
      }
    }
  }
  
  
  /**
   * Initialiseert twee PreparedStatements voor de
   * SQL-opdrachten om
   * - alle cd's in te lezen uit tabel CD
   * - alle tracks in te lezen bij een gegeven CD
   * @throws DBException als de SQL-opdracht een fout bevat
   *         of niet gecompileerd kan worden.
   */
  private void initialiseerPrepStatements() throws DBException {
//    try {
//      pselectvestigingen = con.prepareStatement("SELECT * FROM vestiging");
//      pselectkklanten = con.prepareStatement("SELECT * FROM Klant WHERE cd = ?");
//    }
//    catch (SQLException e) {
//      //als er nu een fout optreedt, moet de verbinding eerst gesloten worden!
//      sluitVerbinding();
//      throw new DBException("Fout bij het formuleren van een sql-opdracht.");
//    }
  }
  
  
  //lees vestigingen
  
  
  //lees klanten?
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
  
    
}
