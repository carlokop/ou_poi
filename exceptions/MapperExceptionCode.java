package exceptions;

public enum MapperExceptionCode {
	JAYBIRD_JDBC_NOT_FOUND("Java kan de klasse voor de driver niet vinden, controleer de classpath op de juiste instellingen"),
	SQL_QUERY_PREP_STAT_ERROR("Fout bij het formuleren/compileren van een sql-opdracht"),
	LOGWRITER_PERMISSION_DENIED("Logwriter heeft geen beschikking over de juiste rechten"),
	CONNECTION_ESTABLISH_ERR("Database verbindings niet geslaagd"),
	CONNECTION_SHUTDOWN_ERR("Het sluiten van de database verbinding is niet geslaagd"),
	MAPPER_DATA_BUILD_ERR("Er heeft een fout plaatsgevonden bij het opbouwen van de data voor de modellen")
	;

	private String mec_message;
	MapperExceptionCode(String mec_message){
		this.mec_message = mec_message;
	}
	
	public String getErrMessage(){
		return this.mec_message;
	}
}
