package test.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.*;

import database.DBConnection;
import model.Appointment;
import services.AppointmentService;

public class AppointmentServiceTest {
	
	AppointmentService appointmentService;
	
	@BeforeAll
	static void alternativeDatabase() throws SQLException{
		DBConnection.closeConnection();
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp() throws SQLException{
		appointmentService= new AppointmentService();
	}
	
	@AfterEach
	void teardown() throws SQLException{
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS appointments");
		PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'appointments'");
		statement.executeUpdate();
		statement1.executeUpdate();
	}
	
	@AfterAll
	static void resetDatabase() {
		DBConnection.closeConnection();
		DBConnection.resetPath();
	}
	
	@Test
	void testCreateAppointment(){
		appointmentService.create(1, 1, LocalDate.now().plusDays(2).toString(), "09:00");
		Appointment appointment = appointmentService.getByUserId(1).getFirst();
		assertNotNull(appointment);
		assertEquals(1, appointment.getId());
		assertEquals(1, appointment.getUserId());
		assertEquals(1, appointment.getClientId());
		assertEquals( LocalDate.now().plusDays(2), appointment.getDate());
		assertEquals(LocalTime.parse("09:00"), appointment.getStartTime());
	}
	
	@Test
	void testCreateAppointment_EmptyDate(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1, "", "09:00");
		});
		assertEquals("Date cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateAppointment_EmptyTime(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  LocalDate.now().plusDays(2).toString(), "");
		});
		assertEquals("Starting time cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateAppointment_WrongFormatDate(){
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "20200101", "09:00");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020/01/01", "09:00");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "01-01-2020", "09:00");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-13-01", "09:00");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-32", "09:00");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-02-30", "09:00");
		});
		assertEquals("Date format is wrong", exception.getMessage());
	}
	
	@Test
	void testCreateAppointment_WrongFormatTime(){
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-01", "09-00");
		});
		assertEquals("Time format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-01", "9:00");
		});
		assertEquals("Time format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-01", "09:0");
		});
		assertEquals("Time format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-01", "9");
		});
		assertEquals("Time format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-01", "25:00");
		});
		assertEquals("Time format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1,  "2020-01-01", "09:61");
		});
		assertEquals("Time format is wrong", exception.getMessage());
	}
	
	@Test
	void testCreateAppointment_PastDate(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			appointmentService.create(1, 1, LocalDate.now().minusDays(2).toString(), "09:00");
		});
		assertEquals("Cannot add appointments to the past", exception.getMessage());
	}
}
