package Exceptions;

public enum IllegalArgumentExceptionCode {
  KLANTNUMMER_NIET_POSITIEF("Klantknummer moet een positief getal zijn"),
  POSTCODE_NULL("Postcode mag niet null zijn"),
  PLAATS_NULL("Plaats mag niet null zijn"),
  PLAATS_LEEG("Plaats mag niet leeg zijn"),
  KLANTENLIJST_NULL("Klanten mag niet null zijn"),
  ;
  
  private String iae_message;
  IllegalArgumentExceptionCode(String iae_message){
      this.iae_message = iae_message;
  }
  
  public String getErrMessage(){
      return this.iae_message;
  }
}
