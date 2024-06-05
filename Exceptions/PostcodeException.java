package Exceptions;

/**
 * Ongeldige situaties:
 * 	1. Ongeldige lengte
 * 	2. Ongeldige velden
 */
public class PostcodeException extends Exception {
	private static final long serialVersionUID = 1L;
	PostcodeExceptionCode pec;
	
	 PostcodeException() {
		super();
	};
	
	public PostcodeExceptionCode getErrCode() {
		return this.pec;
	}
	
	public PostcodeException(PostcodeExceptionCode pec){
		super(pec.pec_message);
		this.pec = pec;
	}
}
