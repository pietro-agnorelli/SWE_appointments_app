package test.database.dao;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import org.junit.jupiter.api.*;

import database.DBConnection;
import database.dao.TreatmentDao;
import model.Treatment;

public class TreatmentDaoTest {
	
	private static Connection connection;
	private static TreatmentDao treatmentDao;
	
	@BeforeAll
	static void setUp() throws SQLException{
		DBConnection.alternatePath("jdbc:sqlite:test.db");
		connection=DBConnection.getConnection();
		treatmentDao = new TreatmentDao(connection);
		clearTable();
	}
	
	@AfterEach
	void clearTableAfter() throws SQLException{
		clearTable();
	}
	
	@AfterAll
	static void teardown() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DROP TABLE treatments")){
			statement.executeUpdate();
		}
		DBConnection.resetPath();
	}
	
	private static void clearTable() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DELETE FROM treatments");
			PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'treatments'")){
			statement.executeUpdate();
			statement1.executeUpdate();
		}
	}
	
	@Test
	void testCreateAndAutoincrementAndGetByClientId(){
		LocalDate testDate = LocalDate.parse("2020-01-01");
		LocalDate testDate1 = LocalDate.parse("2021-01-01");
		Treatment test = new Treatment(1, 1, testDate, "test");
		Treatment test1 = new Treatment(1, 1, testDate1, "test1");
		treatmentDao.addTreatment(test);
		treatmentDao.addTreatment(test1);
		
		List<Treatment> result = treatmentDao.getTreatmentsByClientId((long) 1);
		assertNotNull(result);
		assertEquals(2, result.size());

		Treatment resultAppointment = result.getFirst();
		assertEquals(1, resultAppointment.getId());
		assertEquals(1, resultAppointment.getUserId());
		assertEquals(1, resultAppointment.getClientId());
		assertEquals(testDate, resultAppointment.getDate());
		assertEquals("test", resultAppointment.getDescription());
		
		resultAppointment = result.getLast();
		assertEquals(2, resultAppointment.getId());
	}

}
