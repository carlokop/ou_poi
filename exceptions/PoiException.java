package exceptions;

/**
 * Exceptie voor fouten in de mapper en domeinlaag
 * Geeft aangepagste foutmelding
 * @see PoiExceptionCode
 */
public class PoiException extends Exception{
	private static final long serialVersionUID = 1L;

	PoiExceptionCode pec;
	String details;

	/**
	 * Maakt de exceptie en stelt attributen in
	 * @param pec  PoiExceptionCode
	 * @param details  details over de exception
	 */
	public PoiException(PoiExceptionCode pec, String details) {
		super(pec.getErrMessage());
		this.pec = pec;
		this.details = details;
	}

	/**
	 * Geeft de errorcode
	 * @return  de PoiExceptionCode 
	 */
	public PoiExceptionCode getErrCode() {
		return this.pec;
	}

	/**
	 * Geeft de detals van de exception
	 * @return een foutdetails
	 */
	public String getErrDetails() {
		return this.details;
	}

	/**
	 * Geeft een string reprsentatie van de foutdetails
	 * @return de foutmelding als string
	 */
	@Override
	public String toString() {
		return super.toString() + " | ec_details:" + this.details;
	}
}
