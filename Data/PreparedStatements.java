package Data;

/**
 * Queries voor de database
 */
public enum PreparedStatements {
	GET_VESTIGINGEN("SELECT * FROM vestiging"); // Vul aan zonodig.
	
	PreparedStatements(String statement){
		this.statement = statement;
	};
	
	private String statement;
	
	String toQuery(){
		return this.statement;
	}
}
