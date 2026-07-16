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
	
	public ClientController() {}
	
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
					if(selectClient()) {
						manageSelectedClient();
						AppSession.getInstance().clearCurrentClient();
					}
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
			clientService.create(name, email);
		} catch (IllegalArgumentException e) {
			commonView.displayMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	public boolean selectClient() {
		long selectedClientId = clientView.selectClient();
		Client selectedClient = clientService.getById(selectedClientId);
		if (selectedClient == null) {
			commonView.displayMessage("Client not found.");
			return false;
		}
		AppSession.getInstance().setCurrentClient(selectedClient);
		return true;
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
	
	public ClientController(CommonView commonView, ClientView clientView, TreatmentController treatmentController, AppointmentController appointmentController, ClientService clientService) {
		this.commonView = commonView;
		this.clientView = clientView;
		this.treatmentController = treatmentController;
		this.appointmentController = appointmentController;
		this.clientService = clientService;
	}
}
