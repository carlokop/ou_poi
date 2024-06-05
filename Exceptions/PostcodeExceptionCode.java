package Exceptions;

public enum PostcodeExceptionCode {
	PEC_LENGTE("Ongeldige lengte"), PEC_VELDEN("Ongeldige velden");
	
	String pec_message;
	PostcodeExceptionCode(String pec_message){
		this.pec_message = pec_message;
	}
}