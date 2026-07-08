package controllers;

import java.util.List;

import utilities.AppSession;
import views.CommonView;
import views.ClientView;
import database.dao.ClientDao;
import model.Client;

public class ClientController {
	CommonView commonView = new CommonView();
	ClientView clientView = new ClientView();
	TreatmentController treatmentController = new TreatmentController();
	AppointmentController appointmentController = new AppointmentController();
	ClientDao clientDao = new ClientDao();
	
	public void manageClients() {
		
		while (true) {
			
			if (AppSession.getInstance().getCurrentClient() != null) {
				clientView.displayCurrentClient(AppSession.getInstance().getCurrentClient());
			}
			int choice = clientView.showClientMenu();
			
			switch (choice) {
				case 1:
					viewClients();
					continue;
				case 2:
					addClient();
					continue;
				case 3:
					selectClient();
					manageSelectedClient();
					continue;
				case 4:
					return; // Exit to main menu
				default:
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}
	
	public void viewClients() {
		List<Client> clients = clientDao.getAllClients();
		if (clients.isEmpty()) {
			commonView.displayMessage("No clients found.");
		} else {
			clientView.displayClients(clients);
		}
	}
	
	public void addClient() {
		String name = clientView.askForName();
		String email = clientView.askForEmail();
		Client newClient = new Client(name, email);
		clientDao.addClient(newClient);
		commonView.displayMessage("Client added successfully.");
	}
	
	public void selectClient() {
		long selectedClientId = clientView.selectClient();
		Client selectedClient = clientDao.getClientById(selectedClientId);
		if (selectedClient != null) {
			clientView.displayCurrentClient(selectedClient);
			AppSession.getInstance().setCurrentClient(selectedClient);
		} else {
			commonView.displayMessage("Client not found.");
		}
	}
	
	public void manageSelectedClient() {
		while (true) {
			int choice = clientView.showSelectedClientMenu();
			switch (choice) {
				case 1:
					treatmentController.manageTreatments();
					continue;
				case 2:
					appointmentController.manageAppointments();
					continue;
				case 3:
					return; // Exit to client menu
				default:
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}

}
