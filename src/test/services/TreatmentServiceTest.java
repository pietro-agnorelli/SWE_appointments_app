package test.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

import database.DBConnection;
import model.Treatment;
import services.TreatmentService;

public class TreatmentServiceTest {
	
	TreatmentService treatmentService;
	
	@BeforeAll
	static void alternativeDatabase() throws SQLException{
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp() throws SQLException{
		treatmentService= new TreatmentService();
	}
	
	@AfterEach
	void teardown() throws SQLException{
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("DROP TABLE treatments");
		PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'treatments'");
		statement.executeUpdate();
		statement1.executeUpdate();
	}
	
	@AfterAll
	static void resetDatabase() {
		DBConnection.resetPath();
	}
	
	@Test
	void testCreateTreatment(){
		treatmentService.create(1, 1, "test", LocalDate.now().minusDays(2).toString());
		Treatment treatment = treatmentService.getByClientId(1).getFirst();
		assertNotNull(treatment);
		assertEquals(1, treatment.getId());
		assertEquals(1, treatment.getUserId());
		assertEquals(1, treatment.getClientId());
		assertEquals("test", treatment.getDescription());
		assertEquals(LocalDate.now().minusDays(2), treatment.getDate());
	}
	
	@Test
	void testCreateTreatment_EmptyDate(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "");
		});
		assertEquals("Date cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateTreatment_EmptyDescription(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "", "09:00");
		});
		assertEquals("Description cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateTreatment_WrongFormatDate(){
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "20200101");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "2020/01/01");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "01-01-2020");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "2020-13-01");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "2020-01-32");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", "2020-02-30");
		});
		assertEquals("Date format is wrong", exception.getMessage());
	}
	
	@Test
	void testCreateTreatment_FutureDate(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "test", LocalDate.now().plusDays(2).toString());
		});
		assertEquals("Cannot add treatments to the future", exception.getMessage());
	}
}
