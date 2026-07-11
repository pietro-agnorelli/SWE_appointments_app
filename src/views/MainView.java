package views;

import utilities.InputReader;

public class MainView {
	
	public int showMainMenu() {
		System.out.println("Welcome to the Appointment Management System");
		System.out.println("Please select an option:");
		System.out.println("1. Manage Clients");
		System.out.println("2. See your Appointments");
		System.out.println("3. Logout");
		System.out.print("Enter your choice: ");
		String choice = InputReader.getInstance().readLine();
		if(choice == null || choice.trim().isEmpty()) {
			return -1;
		}
		return Integer.parseInt(choice);
	}
}
