package test.java.database;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;

import org.junit.jupiter.api.Test;

import main.java.database.DBConnection;

public class DBConnectionTest {
	
	@Test
	void test() {
			Connection connection = DBConnection.getConnection();
			assertNotNull(connection);
	}
}
