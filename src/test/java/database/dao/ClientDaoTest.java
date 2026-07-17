package test.java.database.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.*;

import main.java.database.DBConnection;
import main.java.database.dao.ClientDao;
import main.java.model.Client;

class ClientDaoTest {

	private static Connection connection;
	private static ClientDao clientDao;
	
	@BeforeAll
	static void setUp() throws SQLException{
		DBConnection.alternatePath("jdbc:sqlite:test.db");
		connection=DBConnection.getConnection();
		clientDao = new ClientDao(connection);
		clearTable();
	}
	
	@AfterEach
	void clearTableAfter() throws SQLException{
		clearTable();
	}
	
	@AfterAll
	static void teardown() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DROP TABLE clients")){
			statement.executeUpdate();
		}
		DBConnection.resetPath();
	}
	
	private static void clearTable() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DELETE FROM clients");
			PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'clients'")){
			statement.executeUpdate();
			statement1.executeUpdate();
		}
	}

	@Test
	void testCreateAndGetById() throws SQLException{
		Client test = new Client("test", "test@test.it");
		clientDao.addClient(test);
		Client result = clientDao.getClientById((long) 1);
		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals(test.getName(), result.getName());
		assertEquals(test.getEmail(), result.getEmail());
	}
	
	@Test
	void testGetAllAndAutoIncrement() {
		Client test = new Client("test", "test@test.it");
		Client test1 = new Client("test1", "test1@test.it");
		clientDao.addClient(test);
		clientDao.addClient(test1);
		List<Client> result = clientDao.getAllClients();
		assertNotNull(result);
		assertEquals(2, result.size());
		assertEquals(2, result.getLast().getId());
	}
	
	@Test
	void testGetByEmail() throws SQLException{
		Client test = new Client("test", "test@test.it");
		clientDao.addClient(test);
		Client result = clientDao.getClientByEmail(test.getEmail());
		assertNotNull(result);
		assertEquals(1, result.getId());
		assertEquals(test.getName(), result.getName());
		assertEquals(test.getEmail(), result.getEmail());
	}

}
