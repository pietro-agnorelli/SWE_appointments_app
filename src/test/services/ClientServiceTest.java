package test.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import org.junit.jupiter.api.*;

import database.DBConnection;
import model.Client;
import services.ClientService;

public class ClientServiceTest {
	
private static ClientService clientService;
	
	@BeforeAll
	static void alternativeDatabase() throws SQLException{
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp() throws SQLException{
		clientService= new ClientService();
	}
	
	@AfterEach
	void teardown() throws SQLException{
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("DROP TABLE clients");
		PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'clients'");
		statement.executeUpdate();
		statement1.executeUpdate();
	}
	
	@AfterAll
	static void resetDatabase() {
		DBConnection.resetPath();
	}
	
	@Test
	void testCreateClient(){
		clientService.create("test", "test@test.it");
		Client client = clientService.getById(1);
		assertNotNull(client);
		assertEquals("test", client.getName());
		assertEquals("test@test.it", client.getEmail());
		assertEquals(1, client.getId());
	}
	
	@Test
	void testCreateClient_EmptyName(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			clientService.create("", "test@test.it");
		});
		assertEquals("Name cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateClient_EmptyEmail(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			clientService.create("test", "");
		});
		assertEquals("Email address cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateClient_EmailWrongFormat(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			clientService.create("test", "test");
		});
		assertEquals("Email address must contain @", exception.getMessage());
	}
	
	@Test
	void testCreateClient_EmailAlreadyExists(){
		clientService.create("test", "test@test.it");
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			clientService.create("test", "test@test.it");
		});
		assertEquals("Email address already used by another client", exception.getMessage());
	}
	
}
