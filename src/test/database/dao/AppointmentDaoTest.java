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
import database.dao.AppointmentDao;
import model.Appointment;

public class AppointmentDaoTest {
	
	private static Connection connection;
	private static AppointmentDao appointmentDao;
	
	@BeforeAll
	static void setUp() throws SQLException{
		DBConnection.alternatePath("jdbc:sqlite:test.db");
		connection=DBConnection.getConnection();
		appointmentDao = new AppointmentDao(connection);
		clearTable();
	}
	
	@AfterEach
	void clearTableAfter() throws SQLException{
		clearTable();
	}
	
	@AfterAll
	static void teardown() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DROP TABLE appointments")){
			statement.executeUpdate();
		}
		DBConnection.resetPath();
	}
	
	private static void clearTable() throws SQLException {
		try(PreparedStatement statement = connection.prepareStatement("DELETE FROM appointments");
			PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'appointments'")){
			statement.executeUpdate();
			statement1.executeUpdate();
		}
	}
	
	@Test
	void testCreateAndAutoincrementAndGetByIds(){
		LocalDate testDate = LocalDate.parse("2020-01-01");
		LocalTime testTime = LocalTime.parse("09:00");
		LocalDate testDate1 = LocalDate.parse("2021-01-01");
		LocalTime testTime1 = LocalTime.parse("10:00");
		Appointment test = new Appointment(1, 1, testDate, testTime);
		Appointment test1 = new Appointment(1, 1, testDate1, testTime1);
		appointmentDao.addAppointment(test);
		appointmentDao.addAppointment(test1);
		
		List<Appointment> result = appointmentDao.getAppointmentsByClientId((long) 1);
		assertNotNull(result);
		assertEquals(2, result.size());
		List<Appointment> result1 = appointmentDao.getAppointmentsByUserId((long) 1);
		assertNotNull(result1);
		assertEquals(2, result1.size());
		
		Appointment resultAppointment = result.getFirst();
		assertEquals(1, resultAppointment.getId());
		assertEquals(1, resultAppointment.getUserId());
		assertEquals(1, resultAppointment.getClientId());
		assertEquals(testDate, resultAppointment.getDate());
		assertEquals(testTime, resultAppointment.getStartTime());
		
		resultAppointment = result.getLast();
		assertEquals(2, resultAppointment.getId());
	}
}
