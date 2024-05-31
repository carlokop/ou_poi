package Data;

public class DBConst {

  // maak pad relatief

  // Vul in PADNAAM nog de juiste padnaam in naar de map met uitwerkingen
  public static final String PADNAAM = "D:/Prik2Go_res";   //we moeten de database denk beide even op dezelfde locatie opslaan
  public static final String DRIVERNAAM = "org.firebirdsql.jdbc.FBDriver";
  public static final String URL = "jdbc:firebirdsql://localhost/" + PADNAAM;
  public static final String GEBRUIKERSNAAM = "SYSDBA";
  public static final String WACHTWOORD = "masterkey";
}
