package test.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.SQLException;

import org.junit.jupiter.api.Test;

import database.DBConnection;

public class DBConnectionTest {
	
	@Test
	void test() {
		try {
			Connection connection = DBConnection.getConnection();
			assertNotNull(connection);
		} catch (SQLException e) {
			fail(e.getMessage());
		}
	}
}
