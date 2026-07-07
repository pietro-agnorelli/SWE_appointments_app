package controllers;

import java.util.List;

import views.CommonView;
import views.ClientView;
import database.dao.ClientDao;
import model.Client;

public class ClientController {
	CommonView commonView = new CommonView();
	ClientView clientView = new ClientView();
	ClientDao clientDao = new ClientDao();
	
	public void manageClients() {
		
		while (true) {
		
			int choice = clientView.showClientMenu();
			
			switch (choice) {
				case 1:
					viewClients();
					continue;
				case 2:
					addClient();
					continue;
				case 3:
					updateClient();
					continue;
				case 4:
					deleteClient();
					continue;
				case 5:
					return; // Exit to main menu
				default:
					System.out.println("Invalid choice. Please try again.");
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
		
	}
	
}
