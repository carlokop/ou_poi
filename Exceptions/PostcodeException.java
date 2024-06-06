package Exceptions;

public class PostcodeException extends Exception {
	private static final long serialVersionUID = 1L;
	PostcodeExceptionCode pec;
	String pec_details;
	
	public PostcodeException(PostcodeExceptionCode pec, String pec_details){
		super(pec.getErrMessage());
		this.pec = pec;
		this.pec_details = pec_details;
	}	
	
	public PostcodeExceptionCode getErrCode() {
		return this.pec;
	}
	
	public String getErrDetails() {
		return this.pec_details;
	};
	
	@Override
	public String toString() {
		return super.toString() + " | pec_details:" + this.pec_details;
	}
}
