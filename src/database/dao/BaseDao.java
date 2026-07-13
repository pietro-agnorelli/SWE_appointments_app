package database.dao;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import database.DBConnection;

public abstract class BaseDao {
	
	protected final Connection connection;
	
	
	public BaseDao(Connection connection) {
		this.connection = connection;
		try {
			ensureTable();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new RuntimeException("Failed to ensure table exists: " + e.getMessage(), e);
		}
	}
	
	abstract void ensureTable() throws SQLException;
	
	protected void ensureTableExists(String createTableSQL) throws SQLException {
		try (Statement stmt = connection.createStatement()) {
			stmt.executeUpdate(createTableSQL);
		}
	}
	
}
