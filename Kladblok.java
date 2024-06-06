
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.firebirdsql.jdbc.FBResultSet;

import Data.Mapper;
import Data.Queries;
import Exceptions.MapperException;

// Test stukjes code hier

public class Kladblok {
	private Mapper m;
	private Connection con;
	private PreparedStatement ps0, ps1;
	private FBResultSet result;
	
	public void blad() {
		try {
			m = new Mapper();
			m.connect();
			System.out.println(m.getConnection().isClosed());
			con = m.getConnection();

			ps0 = con.prepareStatement(Queries.GET_VESTIGING);
			
			result = (FBResultSet) ps0.executeQuery();
			System.out.println("-");
			System.out.println(result);
			System.out.println(result.next());
			System.out.println(result.getString("PLAATS"));
			System.out.println(result.getString("POSTCODE"));
			System.out.println(result.getString("LAT"));
			System.out.println(result.getString("LNG"));			
			System.out.println("-");

			ps1 = con.prepareStatement(Queries.GET_KLANTEN);
			result = (FBResultSet) ps1.executeQuery();
			System.out.println("-");
			System.out.println(result);
			System.out.println(result.next());
			System.out.println(result.getString("KLANTNR"));
			System.out.println(result.getString("KLANTPOSTCODE"));
			System.out.println(result.getString("KLANTLAT"));
			System.out.println(result.getString("KLANTLNG"));
			System.out.println(result.getString("VESTIGINGPLAATS"));			
			System.out.println("-");

			m.disconnect();
			System.out.println(m.getConnection().isClosed());

		} catch (MapperException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		new Kladblok().blad();
	  }
}
