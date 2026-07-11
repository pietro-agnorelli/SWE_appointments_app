package controllers;

import java.util.List;

import utilities.AppSession;
import views.CommonView;
import views.ClientView;
import model.Client;
import services.ClientService;

public class ClientController {
	CommonView commonView = new CommonView();
	ClientView clientView = new ClientView();
	TreatmentController treatmentController = new TreatmentController();
	AppointmentController appointmentController = new AppointmentController();
	ClientService clientService = new ClientService();
	
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
					if(addClient()) {
						commonView.displayMessage("Client addded succesfully!");
					}
					continue;
				case 3:
					selectClient();
					manageSelectedClient();
					AppSession.getInstance().clearCurrentClient();
					continue;
				case 4:
					return;
				default:
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}
	
	public void viewClients() {
		List<Client> clients = clientService.getAll();
		if (clients.isEmpty()) {
			commonView.displayMessage("No clients found.");
		} else {
			clientView.displayClients(clients);
		}
	}
	
	public boolean addClient() {
		String name = clientView.askForName();
		String email = clientView.askForEmail();
		try {
			clientService.add(name, email);
		} catch (IllegalArgumentException e) {
			commonView.displayMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	public void selectClient() {
		long selectedClientId = clientView.selectClient();
		Client selectedClient = clientService.getById(selectedClientId);
		if (selectedClient != null) {
			AppSession.getInstance().setCurrentClient(selectedClient);
		} else {
			commonView.displayMessage("Client not found.");
		}
	}
	
	public void manageSelectedClient() {
		while (true) {
			clientView.displayCurrentClient(AppSession.getInstance().getCurrentClient());
			int choice = clientView.showSelectedClientMenu();
			switch (choice) {
				case 1:
					treatmentController.manageTreatments();
					continue;
				case 2:
					appointmentController.manageAppointments();
					continue;
				case 3:
					return;
				default:
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}

}
