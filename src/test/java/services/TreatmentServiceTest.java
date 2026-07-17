package test.java.services;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;

import org.junit.jupiter.api.*;

import main.java.database.DBConnection;
import main.java.model.Treatment;
import main.java.services.TreatmentService;

public class TreatmentServiceTest {
	
	TreatmentService treatmentService;
	
	@BeforeAll
	static void alternativeDatabase() throws SQLException{
		DBConnection.closeConnection();
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp() throws SQLException{
		treatmentService= new TreatmentService();
	}
	
	@AfterEach
	void teardown() throws SQLException{
		Connection connection = DBConnection.getConnection();
		PreparedStatement statement = connection.prepareStatement("DROP TABLE IF EXISTS treatments");
		PreparedStatement statement1 = connection.prepareStatement("UPDATE sqlite_sequence SET seq=0 WHERE name= 'treatments'");
		statement.executeUpdate();
		statement1.executeUpdate();
	}
	
	@AfterAll
	static void resetDatabase() {
		DBConnection.closeConnection();
		DBConnection.resetPath();
	}
	
	@Test
	void testCreateTreatment(){
		treatmentService.create(1, 1, LocalDate.now().minusDays(2).toString(), "test");
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
			treatmentService.create(1, 1, "", "test");
		});
		assertEquals("Date cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateTreatment_EmptyDescription(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, LocalDate.now().minusDays(2).toString(), "");
		});
		assertEquals("Description cannot be empty", exception.getMessage());
	}
	
	@Test
	void testCreateTreatment_WrongFormatDate(){
		
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "20200101", "test");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "2020/01/01", "test");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "01-01-2020", "test");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "2020-13-01", "test");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "2020-01-32", "test");
		});
		assertEquals("Date format is wrong", exception.getMessage());
		
		exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, "2020-02-30", "test");
		});
		assertEquals("Date format is wrong", exception.getMessage());
	}
	
	@Test
	void testCreateTreatment_FutureDate(){
		Exception exception = assertThrows(IllegalArgumentException.class, () ->{
			treatmentService.create(1, 1, LocalDate.now().plusDays(2).toString(), "test");
		});
		assertEquals("Cannot add treatments to the future", exception.getMessage());
	}
}
