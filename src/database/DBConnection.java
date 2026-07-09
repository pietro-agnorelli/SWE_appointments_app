package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection;
	private static String path = "jdbc:sqlite:database.db";
	
	private DBConnection() { }
	
	public static Connection getConnection() throws SQLException {
		if (connection == null || connection.isClosed()) {
			try {
				Class.forName("org.sqlite.JDBC");
				connection = DriverManager.getConnection(path);
			} catch (ClassNotFoundException e) {
				throw new SQLException("SQLite JDBC driver not found", e);
			}
		}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}
}
