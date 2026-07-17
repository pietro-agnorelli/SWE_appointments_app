package test.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.*;

import controllers.AppointmentController;
import database.DBConnection;
import model.Appointment;
import model.Client;
import model.User;
import services.AppointmentService;
import utilities.AppSession;
import views.AppointmentView;

public class AppointmentControllerTest {
	private AppointmentService appointmentService;
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
		appointmentService = new AppointmentService();
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
				"DROP TABLE IF EXISTS appointments");
		statement.executeUpdate();
	}
	
	@Test
	void testAddAppointment() {
		FakeAppointmentView appointmentView = new FakeAppointmentView(LocalDate.now().plusDays(2).toString(), "09:00");
		AppointmentController appointmentController = new AppointmentController(appointmentView, appointmentService, commonView);
		
		boolean result = appointmentController.addAppointment();
		assertTrue(result);
	}
	
	@Test
	void testViewClientAppointments() {
		FakeAppointmentView appointmentView = new FakeAppointmentView("", "");
		AppointmentController appointmentController = new AppointmentController(appointmentView, appointmentService, commonView);
		
		appointmentController.viewClientAppointments();
		assertEquals("No appointments found for client: tester", commonView.lastMessage);
		
		appointmentService.create(2, 1, LocalDate.now().plusDays(2).toString(), "09:00");
		appointmentService.create(3, 1, LocalDate.now().plusDays(3).toString(), "09:00");
		appointmentController.viewClientAppointments();
		assertEquals(2, appointmentView.getDisplayedTreatmentsCount());
	}
	
	@Test
	void testViewUserAppointments() {
		FakeAppointmentView appointmentView = new FakeAppointmentView("", "");
		AppointmentController appointmentController = new AppointmentController(appointmentView, appointmentService, commonView);
		
		appointmentController.viewUserAppointments();
		assertEquals("No appointments found for user: test", commonView.lastMessage);
		
		appointmentService.create(1, 2, LocalDate.now().plusDays(2).toString(), "09:00");
		appointmentService.create(1, 3, LocalDate.now().plusDays(2).toString(), "10:00");
		appointmentController.viewUserAppointments();
		assertEquals(2, appointmentView.getDisplayedTreatmentsCount());
	}
	
	
	//Stand-In class that simulates AppointmentView expected behavior
	class FakeAppointmentView extends AppointmentView{
		private String date;
		private String time;
		private int displayedTreatmentsCount = 0;
		
		FakeAppointmentView(String date, String time){
			this.date = date;
			this.time = time;
		}
		
		@Override
		public int showAppointmentMenu() {
			return 0;
		}
		
		@Override
		public String askForTime() {
			return time;
		}
		
		@Override
		public String askForDate() {
			return date;
		}
		
		@Override
		public void displayUserAppointments(List<Appointment> appointments) {
			displayedTreatmentsCount = appointments.size();
		}
		
		@Override
		public void displayClientAppointments(List<Appointment> appointments) {
			displayedTreatmentsCount = appointments.size();
		}
		
		public int getDisplayedTreatmentsCount() {
			return displayedTreatmentsCount;
		}
	}
}

