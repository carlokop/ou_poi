package exceptions;

public class PoiException extends Exception{
	private static final long serialVersionUID = 1L;

	PoiExceptionCode pec;
	String details;

	public PoiException(PoiExceptionCode pec, String details) {
		super(pec.getErrMessage());
		this.pec = pec;
		this.details = details;
	}

	public PoiExceptionCode getErrCode() {
		return this.pec;
	}

	public String getErrDetails() {
		return this.details;
	}

	@Override
	public String toString() {
		return super.toString() + " | ec_details:" + this.details;
	}
}
