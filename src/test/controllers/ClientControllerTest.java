package test.controllers;

import static org.junit.jupiter.api.Assertions.*;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;

import org.junit.jupiter.api.*;

import controllers.AppointmentController;
import controllers.ClientController;
import controllers.TreatmentController;
import database.DBConnection;
import model.Client;
import model.User;
import services.ClientService;
import utilities.AppSession;
import views.ClientView;

public class ClientControllerTest {
	private ClientService clientService;
	private FakeCommonView commonView;
	private AppSession appSession;
	private FakeTreatmentController treatmentController;
	private FakeAppointmentController appointmentController;
	
	@BeforeAll
	static void setUpTestDB()  throws SQLException{
		DBConnection.closeConnection();
		DBConnection.alternatePath("jdbc:sqlite:test.db");
	}
	
	@BeforeEach
	void setUp()  throws SQLException{
		appSession = AppSession.getInstance();
		clientService = new ClientService();
		commonView = new FakeCommonView();
		treatmentController = new FakeTreatmentController();
		appointmentController = new FakeAppointmentController();
		appSession.setCurrentUser(new User(1, "test"));
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
				"DROP TABLE IF EXISTS clients");
		statement.executeUpdate();
	}
	
	@Test
	void testAddClient() {
		FakeClientView clientView = new FakeClientView("tester", "test@test.it", 0);
		ClientController clientController = new ClientController(commonView, clientView, treatmentController, appointmentController, clientService);
		
		boolean result = clientController.addClient();
		assertTrue(result);
	}
	
	@Test
	void testViewClients() {
		FakeClientView clientView = new FakeClientView("", "", 0);
		ClientController clientController = new ClientController(commonView, clientView, treatmentController, appointmentController, clientService);
		
		clientController.viewClients();
		assertEquals("No clients found.", commonView.lastMessage);
		
		clientService.create("tester", "test@test.it");
		clientService.create("tester1", "test1@test.it");
		clientController.viewClients();
		assertEquals(2, clientView.getDisplayedClientsCount());
	}
	
	@Test
	void testSelecClient_NoClientFound() {
		FakeClientView clientView = new FakeClientView("", "", 0);
		ClientController clientController = new ClientController(commonView, clientView, treatmentController, appointmentController, clientService);
		
		clientController.selectClient();
		assertNull(appSession.getCurrentClient());
		assertEquals("Client not found.", commonView.lastMessage);
	}
	
	@Test
	void testSelectedClient() {
		clientService.create("tester", "test@test.it");
		long id = clientService.getAll().getFirst().getId();
		FakeClientView clientView = new FakeClientView("", "", id);
		ClientController clientController = new ClientController(commonView, clientView, treatmentController, appointmentController, clientService);
		
		clientController.selectClient();
		Client selected = appSession.getCurrentClient();
		assertNotNull(selected);
		assertEquals(id, selected.getId());
	}
	
	@Test
	void testManageSelectedClient() {
		clientService.create("tester", "test@test.it");
		Client client =  clientService.getAll().getFirst();
		AppSession.getInstance().setCurrentClient(client);
		FakeClientView clientView = new FakeClientView("", "", 0);

		ClientController clientController = new ClientController(commonView, clientView, treatmentController, appointmentController, clientService);
		
		clientController.manageSelectedClient();
		
		assertEquals(1, treatmentController.manageTreatmentsCount);
		assertEquals(1, appointmentController.manageAppointmentsCount);
		assertEquals("Invalid choice. Please try again.", commonView.lastMessage);
	}
	
	
	
	class FakeClientView extends ClientView{
		private String name;
		private String email;
		private long selectId;
		private int displayedClientsCount = 0;
		
		private int[] selectedMenuSequence = {0, 1, 2, 3};
		private int index = 0;
		
		FakeClientView(String name, String email, long selectId){
			this.name = name;
			this.email = email;
			this.selectId = selectId;
		}
		
		@Override
		public int showClientMenu() {
			return 0;
		}
		
		@Override
		public int showSelectedClientMenu() {
			if(index >= 4) {
				return 0;
			}
			return selectedMenuSequence[index++];
		}
		
		@Override
		public void displayCurrentClient(Client client) {}
		
		@Override
		public void displayClients(List<Client> clients) {
			displayedClientsCount = clients.size();
		}
		
		@Override
		public String askForName() {
			return name;
		}
		
		@Override
		public String askForEmail() {
			return email;
		}
		
		@Override
		public long selectClient() {
			return selectId;
		}
		
		public int getDisplayedClientsCount() {
			return displayedClientsCount;
		}	
	}


	class FakeTreatmentController extends TreatmentController {
	    int manageTreatmentsCount = 0;

	    @Override
	    public void manageTreatments() {
	        manageTreatmentsCount++;
	    }
	}

	class FakeAppointmentController extends AppointmentController {
	    int manageAppointmentsCount = 0;

	    @Override
	    public void manageAppointments() {
	        manageAppointmentsCount++;
	    }
	}
}





