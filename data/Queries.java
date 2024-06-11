package data;

/**
 * Queries voor de database
 */
public final class Queries {
	// Haalt klanten op en de vestiging(en) die deze klanten bezoeken.
	public final static String GET_KLANTEN =
			"SELECT k.NR Klantnr, p.POSTCODE Klantpostcode, p.PLAATS Klantplaats, p.LAT Klantlat, p.LNG Klantlng, v.PLAATS Vestigingplaats \r\n"
			+ "FROM VESTIGING v \r\n"
			+ "	 JOIN BEZOEK b ON v.PLAATS = b.VESTIGING \r\n"
			+ "	 JOIN KLANT k ON b.KLANT = k.NR\r\n"
			+ "	 JOIN POSTCODEINFO p ON k.POSTCODE = p.POSTCODE\r\n"
			+ "ORDER BY KLANT ASC;";
	
	// Haalt gegevens over de vestigingen zelf op
	public final static String GET_VESTIGINGEN =
			"SELECT v.PLAATS , p.POSTCODE , p.LAT, p.LNG \r\n"
			+ "FROM VESTIGING v \r\n"
			+ "	 JOIN POSTCODEINFO p ON v.POSTCODE = p.POSTCODE;";
}
