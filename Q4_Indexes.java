import java.sql.*;
import java.util.Scanner;

public class Q4_Indexes {

	private static final String username = "cs421g07";
	private static final String password = "sunlightchicken9115";

	public static void main(String[] args) throws SQLException {

		// setup db connection
		try {
			DriverManager.registerDriver(new com.ibm.db2.jcc.DB2Driver());
		} catch (SQLException e) {
			System.out.println("Class not found");
			e.printStackTrace();
			return;
		}

		System.out.println("Driver registered.");
		String url = "jdbc:db2://db2:50000/cs421";
		Connection conn = DriverManager.getConnection(url, username, password);
		Statement statement = conn.createStatement();
		System.out.println("Connection Established!\n");

		try {
			// drop index if it exists (assume it exists)
			statement.executeUpdate("DROP INDEX staff_firstname");
			statement.executeUpdate("DROP INDEX staff_lastname");
			statement.executeUpdate("DROP INDEX menu_price");
			statement.executeUpdate("DROP INDEX customers_firstname");
			statement.executeUpdate("DROP INDEX customers_lastname");

			String query1 = "SELECT c.firstName,c.lastName,t.NumberOfOrders "
					+ "FROM Customers c,(SELECT cid, "
					+ "COUNT(DISTINCT oid) AS NumberOfOrders "
					+ "FROM Done_by "
					+ "GROUP BY cid) t "
					+ "WHERE c.cid=t.cid";

			String query2 = "SELECT * FROM Menu WHERE price >= 10.00 AND price <= 20.00";

			String query3 = "SELECT s.firstName,s.lastName "
					+ "FROM Staff s,Customers c "
					+ "WHERE s.phone = c.phone "
					+ "AND s.firstName = c.firstName "
					+ "AND s.lastName = c.lastName";

			// execute queries without indexes and measure execution time
			long startTime1 = System.currentTimeMillis();
			statement.executeQuery(query1);
			long stopTime1 = System.currentTimeMillis();
			long elapsedTime1 = stopTime1 - startTime1;
			System.out.println("Query1(Non indexed) : \n" + query1 + "\n" + "Elapsed Time : " +  elapsedTime1 + " ms\n");

			long startTime2 = System.currentTimeMillis();
			statement.executeQuery(query2);
			long stopTime2 = System.currentTimeMillis();
			long elapsedTime2 = stopTime2 - startTime2;
			System.out.println("Query2(Non indexed) : \n" + query2 + "\n" + "Elapsed Time : " +  elapsedTime2 + " ms\n");

			long startTime3 = System.currentTimeMillis();
			statement.executeQuery(query3);
			long stopTime3 = System.currentTimeMillis();
			long elapsedTime3 = stopTime3 - startTime3;
			System.out.println("Query3(Non indexed) : \n" + query3 + "\n" + "Elapsed Time : " +  elapsedTime3 + " ms\n");

			long total_elapsedTime = elapsedTime1+elapsedTime2+elapsedTime3;

			System.out.println("Total non Indexed elapsed time : " + total_elapsedTime + "\n");


			// create indexes
			statement.executeUpdate("CREATE INDEX staff_firstname ON Staff(firstName)");
			statement.executeUpdate("CREATE INDEX staff_lastname ON Staff(lastName)");
			statement.executeUpdate("CREATE INDEX menu_price ON Menu(price)");
			statement.executeUpdate("CREATE INDEX customers_firstname ON Customers(firstName)");
			statement.executeUpdate("CREATE INDEX customers_lastname ON Customers(lastName)");

			// execute queries with indexes and measure execution time
			startTime1 = System.currentTimeMillis();
			statement.executeQuery(query1);
			stopTime1 = System.currentTimeMillis();
			elapsedTime1 = stopTime1 - startTime1;
			System.out.println("Query1(Indexed) : \n" + query1 + "\n" + "Elapsed Time : " +  elapsedTime1 + " ms\n");

			startTime2 = System.currentTimeMillis();
			statement.executeQuery(query2);
			stopTime2 = System.currentTimeMillis();
			elapsedTime2 = stopTime2 - startTime2;
			System.out.println("Query2(Indexed) : \n" + query2 + "\n" + "Elapsed Time : " +  elapsedTime2 + " ms\n");

			startTime3 = System.currentTimeMillis();
			statement.executeQuery(query3);
			stopTime3 = System.currentTimeMillis();
			elapsedTime3 = stopTime3 - startTime3;
			System.out.println("Query3(Indexed) : \n" + query3 + "\n" + "Elapsed Time : " +  elapsedTime3 + " ms\n");

			total_elapsedTime = elapsedTime1+elapsedTime2+elapsedTime3;

			System.out.println("Total Indexed elapsed time : " + total_elapsedTime + "\n");

		} catch (SQLException e) {
			System.out.println(e.getMessage());
			System.out.println("Code: " + e.getErrorCode() + " State: " + e.getSQLState());
			System.out.println("Stack trace : ");
			e.printStackTrace();
		} finally {
			try {
				// Close connection
				statement.close();
				conn.close();
				System.out.println("Connection closed successfully\n");
				System.out.println(">>>>>>>>>>>>>>DONE");
			} catch (SQLException e) {
				System.out.println(e.getMessage());
				System.out.println("Code: " + e.getErrorCode() + " State: " + e.getSQLState());
				System.out.println("Connection failed to close successfully.");
			}
		}
	}
}
