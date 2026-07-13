package test.database.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.*;

import database.DBConnection;
import database.dao.UserDao;
import model.User;

public class UserDaoTest {
	
	private static Connection connection;
	private static UserDao userDao;
	
	@BeforeAll
	static void setUp() throws SQLException{
		DBConnection.alternatePath("jdbc:sqlite:test.db");
		connection=DBConnection.getConnection();
		userDao = new UserDao(connection);
		clearTable();
	}
	
	@AfterEach
	void clearTableAfter() throws SQLException{
		clearTable();
	}
	
	@AfterAll
	static void teardown() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DROP TABLE users")){
			statement.executeUpdate();
		}
		DBConnection.resetPath();
	}
	
	private static void clearTable() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DELETE FROM users");
			PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'users'")){
			statement.executeUpdate();
			statement1.executeUpdate();
		}
	}
	
	@Test
	void testCreateAndGetByUsername() {
		User test = new User("test");
		userDao.addUser(test);
		User result = userDao.getUserByUsername("test");
		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals(test.getUsername(), result.getUsername());
	}
	

}
