package views;

import java.util.List;

import model.Client;
import utilities.InputReader;

public class ClientView {
	
	public int showClientMenu() {
		System.out.println("Client Menu:");
		System.out.println("1. View Clients");
		System.out.println("2. Add Client");
		System.out.println("3. Select Client (use Id)");
		System.out.println("4. Back to Main Menu");
		System.out.print("Enter your choice: ");
		String choice = InputReader.getInstance().readLine();
		return Integer.parseInt(choice);
	}
	
	public int showSelectedClientMenu() {
		System.out.println("Selected Client Menu:");
		System.out.println("1. Manage Treatments");
		System.out.println("2. Manage Appointments");
		System.out.println("3. Back to Client Menu");
		System.out.print("Enter your choice: ");
		String choice = InputReader.getInstance().readLine();
		return Integer.parseInt(choice);
	}
	
	public void displayCurrentClient(Client client) {
		System.out.print("Current Client:");
		System.out.println("ID: " + client.getId() + 
				", Name: " + client.getName() + ", Email: " + client.getEmail());
	}
	
	public void displayClients(List<Client> clients) {
		System.out.println("List of Clients:");
		for (Client client : clients) {
			System.out.println("ID: " + client.getId() + 
					", Name: " + client.getName() + ", Email: " + client.getEmail());
		}
	}
	
	public String askForName() {
		System.out.print("Enter client name: ");
		return InputReader.getInstance().readLine();
	}
	
	public String askForEmail() {
		System.out.print("Enter client email: ");
		return InputReader.getInstance().readLine();
	}
	
	public long selectClient() {
		System.out.print("Enter client ID to select: ");
		String id = InputReader.getInstance().readLine();
		return Long.parseLong(id);
	}

}
