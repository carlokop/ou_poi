package exceptions;

public enum PostcodeExceptionCode {
	POSTCODE_NULL("Postcode is null"),
	POSTCODE_LENGTE("Ongeldige postcode lengte"), 
	POSTCODE_VELDEN("Ongeldige Postcode velden"),
	POSTCODE_NUL_START("Postcode begint met 0"),

	PLAATSNAAM_NULL("Plaatsnaam is null"),
	PLAATSNAAM_LEEG("Plaatsnaam is leeg"),
	PLAATSNAAM_ALLEEN_SPATIES("Plaatsnaam bevat alleen spaties"),
	PLAATSNAAM_ILLEGAL_CHAR("Karakter in plaatsnaam niet toegestaan"),
	PLAATSNAAM_TE_GROOT("Plaatsnaam maximale lengte overschreden"),
	
	GGCOORDS_LAT_OVERSCHRIJDING("GGCoord lat overschrijdt grenswaarde"),
	GGCOORDS_LNG_OVERSCHRIJDING("GGCoord lng overschrijdt grenswaarde"),
	;

	private String pec_message;
	PostcodeExceptionCode(String pec_message){
		this.pec_message = pec_message;
	}
	
	String getErrMessage(){
		return this.pec_message;
	}
};