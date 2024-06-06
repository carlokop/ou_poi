package Exceptions;

public class MapperException extends Exception {
	private static final long serialVersionUID = 1L;

	MapperExceptionCode mec;
	String mec_details;

	public MapperException(MapperExceptionCode mec, String mec_details) {
		super(mec.getErrMessage());
		this.mec = mec;
		this.mec_details = mec_details;
	}

	public MapperExceptionCode getErrCode() {
		return this.mec;
	}

	public String getErrDetails() {
		return this.mec_details;
	};

	@Override
	public String toString() {
		return super.toString() + " | pec_details:" + this.mec_details;
	}
}
