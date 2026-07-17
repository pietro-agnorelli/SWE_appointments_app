package test.java.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.*;

import main.java.controllers.UserController;
import main.java.database.DBConnection;
import main.java.model.User;
import main.java.views.UserView;
import main.java.services.UserService;

public class UserControllerTest {
	UserService userService;
	FakeCommonView commonView;
	
	@BeforeAll
	static void setUpTestDB()  throws SQLException{
		DBConnection.closeConnection();
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp() throws SQLException{
		cleanDB();
		userService = new UserService();
		commonView = new FakeCommonView();
	}
	
	@AfterAll
	static void teardownTestDB() throws SQLException{
		cleanDB();
		DBConnection.closeConnection();
		DBConnection.resetPath();
	}
	
	static void cleanDB() throws SQLException{
		PreparedStatement statement = DBConnection.getConnection().prepareStatement(
				"DROP TABLE IF EXISTS users");
		statement.executeUpdate();
	}
	
	@Test
	void testUserLogin() {
		User test=userService.create("test");
		FakeUserView userView = new FakeUserView("test", false, null, null);
		UserController userController = new UserController(userView, commonView, userService);
		
		User result = userController.handleUserLogin();
		
		assertNotNull(result);
		assertEquals(test.getId(), result.getId());
		assertEquals(test.getUsername(), result.getUsername());
	}
	
	@Test
	void testUserLogin_NewUserCreation() {
		FakeUserView userView = new FakeUserView("", true, "test", null);
		UserController userController = new UserController(userView, commonView, userService);
		
		User result = userController.handleUserLogin();
		
		assertNotNull(result);
		assertEquals("test", result.getUsername());
	}
	
	@Test
	void testUserLogin_NewUserCreation_ExistingUsername() {
		userService.create("test");
		FakeUserView userView = new FakeUserView("", true, "test", "test1");
		UserController userController = new UserController(userView, commonView, userService);
		
		User result = userController.handleUserLogin();
		
		assertNotNull(result);
		assertEquals("test1", result.getUsername());
		assertEquals("Username already exists", commonView.lastMessage);
	}
	
	@Test
	void testUserLogin_NewUserCreation_EmptyUsername() {
		userService.create("test");
		FakeUserView userView = new FakeUserView("", true, "", "test1");
		UserController userController = new UserController(userView, commonView, userService);
		
		User result = userController.handleUserLogin();
		
		assertNotNull(result);
		assertEquals("test1", result.getUsername());
		assertEquals("Username cannot be empty", commonView.lastMessage);
	}
	
	@Test
	void testUserLogin_UserDeclinesToCreateNew() {
		FakeUserView userView = new FakeUserView("", false, null, null);
		UserController userController = new UserController(userView, commonView, userService);
		
		User result = userController.handleUserLogin();
		
		assertNotNull(result);
		assertNull(result.getUsername());
		assertEquals(-1, result.getId());
	}
	
	
	//Stand-In class that simulates UserView expected behavior
	class FakeUserView extends UserView{
		private String loginInput;
		private boolean newUserCreation;
		private String newUserInput;
		private String newUserInputSecondAttempt;
		
		FakeUserView(String loginInput, boolean newUserCreation, String newUserInput, String newUserInputSecondAttempt) {
	        this.loginInput = loginInput;
	        this.newUserCreation = newUserCreation;
	        this.newUserInput = newUserInput;
	        this.newUserInputSecondAttempt = newUserInputSecondAttempt;
	    }
		
		@Override
		public String loginMenu() {
			return loginInput;
		}
		
		@Override
		public String newUserMenu() {
			if(newUserCreation) {
				return newUserInput;
			}
			return null;
		}
		
		@Override
		public String newUserMenu_NewUsername() {
			return newUserInputSecondAttempt;
		}
	}
}


