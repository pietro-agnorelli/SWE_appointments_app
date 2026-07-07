package views;

import utilities.InputReader;

public class MainView {
	
	public int showMainMenu() {
		System.out.println("Welcome to the Appointment Management System");
		System.out.println("Please select an option:");
		System.out.println("1. Manage Clients");
		System.out.println("2. Manage Appointments");
		System.out.println("3. Manage Treatments");
		System.out.println("4. Exit");
		System.out.print("Enter your choice: ");
		String choice = InputReader.getInstance().readLine();
		return Integer.parseInt(choice);
	}
	
}
