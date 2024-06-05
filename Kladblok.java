
import java.sql.Connection;
import java.sql.SQLException;

import Data.Mapper;
import Exceptions.MapperException;

// Test stukjes code hier

public class Kladblok {
	private Connection con = null;
	private Mapper m;
	
	public void blad() {
		try {
			m = new Mapper();
			m.connect();
			System.out.println(m.getConnection().isClosed());
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
