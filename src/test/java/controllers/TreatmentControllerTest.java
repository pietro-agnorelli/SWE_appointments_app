package test.java.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;

import main.java.controllers.TreatmentController;
import main.java.database.DBConnection;
import main.java.model.Client;
import main.java.model.Treatment;
import main.java.model.User;
import main.java.services.TreatmentService;
import main.java.utilities.AppSession;
import main.java.views.TreatmentView;


public class TreatmentControllerTest {
	private TreatmentService treatmentService;
	private FakeCommonView commonView;
	private AppSession appSession;
	
	@BeforeAll
	static void setUpTestDB()  throws SQLException{
		DBConnection.closeConnection();
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp(){
		appSession = AppSession.getInstance();
		appSession.clearCurrentUser();
		appSession.clearCurrentClient();
		treatmentService = new TreatmentService();
		commonView = new FakeCommonView();
		appSession.setCurrentUser(new User(1, "test"));
		appSession.setCurrentClient(new Client(1, "tester", "test@test.it"));
	}
	
	@AfterEach
	void teardown()  throws SQLException{
		cleanDB();
		appSession.clearCurrentUser();
		appSession.clearCurrentClient();
	}
	
	@AfterAll
	static void teardownTestDB() throws SQLException{
		cleanDB();
		DBConnection.closeConnection();
		DBConnection.resetPath();
	}
	
	static void cleanDB() throws SQLException{
		PreparedStatement statement = DBConnection.getConnection().prepareStatement(
				"DROP TABLE IF EXISTS treatments");
		statement.executeUpdate();
	}
	
	@Test
	void testAddTreatment() {
		FakeTreatmentView treatmentView = new FakeTreatmentView("Test", 
				LocalDate.now().minusDays(2).toString());
		TreatmentController treatmentController = new TreatmentController(treatmentView, treatmentService, commonView);
		
		boolean result = treatmentController.addTreatment();
		assertTrue(result);
	}
	
	@Test
	void testViewTreatments() {
		FakeTreatmentView treatmentView = new FakeTreatmentView("", "");
		TreatmentController treatmentController = new TreatmentController(treatmentView, treatmentService, commonView);
		
		treatmentController.viewTreatments();
		assertEquals("No treatments found for client: tester", commonView.lastMessage);
		
		treatmentService.create(1, 1, LocalDate.now().minusDays(2).toString(),  "test");
		treatmentService.create(1, 1, LocalDate.now().minusDays(3).toString(), "test1");
		treatmentController.viewTreatments();
		assertEquals(2, treatmentView.getDisplayedTreatmentsCount());
	}
	
	
	//Stand-In class that simulates TreatmentView expected behavior
	class FakeTreatmentView extends TreatmentView{
		private String description;
		private String date;
		private int displayedTreatmentsCount = 0;
		
		FakeTreatmentView(String description, String date){
			this.description = description;
			this.date = date;
		}
		
		@Override
		public int showTreatmentMenu() {
			return 0;
		}
		
		@Override
		public String askForDescription() {
			return description;
		}
		
		@Override
		public String askForDate() {
			return date;
		}
		
		@Override
		public void displayTreatments(List<Treatment> treatments) {
			displayedTreatmentsCount = treatments.size();
		}
		
		public int getDisplayedTreatmentsCount() {
			return displayedTreatmentsCount;
		}
	}
}


