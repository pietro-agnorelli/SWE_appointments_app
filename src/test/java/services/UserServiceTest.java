package test.java.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.*;

import main.java.database.DBConnection;
import main.java.services.UserService;
import main.java.model.User;

public class UserServiceTest {
	
	private static UserService userService;
	
	@BeforeAll
	static void alternativeDatabase() throws SQLException{
		DBConnection.closeConnection();
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp() throws SQLException{
		userService= new UserService();
	}
	
	@AfterEach
	void teardown() throws SQLException{
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS users");
		PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'users'");
		statement.executeUpdate();
		statement1.executeUpdate();
	}
	
	@AfterAll
	static void resetDatabase() {
		DBConnection.closeConnection();
		DBConnection.resetPath();
	}
	
	@Test
	void testCreateUser(){
		User user = userService.create("test");
		assertNotNull(user);
		assertEquals("test", user.getUsername());
		assertEquals(1, user.getId());
	}
	
	@Test
	void testCreateUser_NullUsername(){
		User user = userService.create(null);
		assertNotNull(user);
		assertNull(user.getUsername());
		assertEquals(-1, user.getId());
	}
	
	@Test
	void testCreateUser_UsernameAlreadyExists(){
		userService.create("test");
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			userService.create("test");
		});
		assertEquals("Username already exists", exception.getMessage());
	}
	
	@Test
	void testCreateUser_EmptyUsername(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			userService.create("");
		});
		assertEquals("Username cannot be empty", exception.getMessage());
	}
	
}
