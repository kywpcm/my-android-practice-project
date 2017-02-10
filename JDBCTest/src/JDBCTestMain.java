import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class JDBCTestMain {

	public static void main(String[] args) throws SQLException {
		String driver = "com.mysql.jdbc.Driver";
		String url = "jdbc:mysql://localhost:3306/mydatabase";
		String user = "root";
		String password = "1234";
		String qry = "SELECT * FROM person";

		try {
			Class.forName(driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Connection conn = DriverManager.getConnection(url, user, password);
		Statement stmt = conn.createStatement();
		ResultSet rs = stmt.executeQuery( qry );
		while ( rs.next() ) {
			int numColumns = rs.getMetaData().getColumnCount();
			for ( int i = 1 ; i <= numColumns ; i++ ) {
				System.out.println( rs.getObject(i) );
			}
		}
		rs.close();
		stmt.close();
		conn.close();
	}

}