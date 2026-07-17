package main.java.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
	private static Connection connection;
	private static String path = "jdbc:sqlite:database.db";
	
	private DBConnection() {}

	public static Connection getConnection(){
			try {
				if (connection == null || connection.isClosed()) {
					Class.forName("org.sqlite.JDBC");
					connection = DriverManager.getConnection(path);
				}
			} catch(SQLException e){
				System.err.println("Connection error: " + e.getMessage());
			}catch (ClassNotFoundException e) {
				System.err.println("Missing SQLite driver: " + e.getMessage());
			}
		return connection;
	}
	
	public static void closeConnection() {
		if (connection != null) {
			try {
				connection.close();
				connection = null;
			} catch (SQLException e) {
				System.err.println("C: " + e.getMessage());
			}
		}
	}
	
	//Used in testing to access test database and reset path to nominal database
	public static void alternatePath(String newPath) {
		path=newPath;
	}
	
	public static void resetPath() {
		path="jdbc:sqlite:database.db";
	}
	
}
