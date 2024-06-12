package exceptions;

/**
 * Dit regelt welke foutcodes met welke foutmeldingen er mogen zijn
 */
public enum PoiExceptionCode {

	JAYBIRD_JDBC_NOT_FOUND("Java kan de klasse voor de driver niet vinden, controleer uw instellingen"),
	SQL_QUERY_PREP_STAT_ERROR("Fout bij het formuleren/compileren van een sql-opdracht"),
	LOGWRITER_PERMISSION_DENIED("Logwriter heeft geen beschikking over de juiste rechten"),
	CONNECTION_ESTABLISH_ERR("Database verbindings niet geslaagd"),
	CONNECTION_SHUTDOWN_ERR("Het sluiten van de database verbinding is niet geslaagd"),
	MAPPER_DATA_BUILD_ERR("Er heeft een fout plaatsgevonden bij het opbouwen van de data voor de modellen"),

	KLANTENLIJST_NULL("Klantenlijst wijst naar null"),
	KLANTNUMMER_NIET_POSITIEF("Klantknummer moet een positief getal zijn"),
	
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

	VESTIGING_PLAATS_ONGELDIG("Vestiging is onbekend of null"),
	;
	private String ec_message;

	/*
	 * Maakt de error message
	 */
	PoiExceptionCode(String ec_message) {
		this.ec_message = ec_message;
	}

	/**
	 * Geeft de error message
	 * @return de error message
	 */
	public String getErrMessage() {
		return this.ec_message;
	}

}
