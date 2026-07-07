package views;

import java.util.List;

import model.Client;
import utilities.InputReader;

public class ClientView {
	
	public int showClientMenu() {
		System.out.println("Client Menu:");
		System.out.println("1. View Clients");
		System.out.println("2. Add Client");
		System.out.println("3. Update Client");
		System.out.println("4. Delete Client");
		System.out.println("5. Back to Main Menu");
		System.out.print("Enter your choice: ");
		String choice = InputReader.getInstance().readLine();
		return Integer.parseInt(choice);
	}
	
	public void displayClients(List<Client> clients) {
		System.out.println("List of Clients:");
		for (Client client : clients) {
			System.out.println("ID: " + client.getId() + 
					", Name: " + client.getName() + ", Email: " + client.getEmail());
		}
	}

}
