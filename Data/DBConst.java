package Data;

public class DBConst {

  // maak pad relatief

  // Vul in PADNAAM nog de juiste padnaam in naar de map met uitwerkingen
  public static final String PADNAAM = 
		  "D:/Prik2Go_res"
//		  "../fdb/Prik2Go_res_v3.fdb"
//		  "../fdb/Prik2Go_res.fdb"
		  ; 
  public static final String DRIVERNAAM = "org.firebirdsql.jdbc.FBDriver";
  public static final String URL = "jdbc:firebirdsql://localhost/" + PADNAAM;
  
  // moeten private zijn, zie ook feedback op discussieforum, dit zal leiden tot wat globale aanpassingen
  private static final String GEBRUIKERSNAAM = "SYSDBA";
  private static final String WACHTWOORD = "masterkey";
}
