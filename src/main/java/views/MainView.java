package main.java.views;

import main.java.utilities.InputReader;

public class MainView {
	
	public int showMainMenu() {
		System.out.println("Welcome to the Appointment Management System");
		System.out.println("Please select an option:");
		System.out.println("1. Manage Clients");
		System.out.println("2. See your Appointments");
		System.out.println("3. Logout");
		System.out.print("Enter your choice: ");
		String choice = InputReader.getInstance().readLine();
		int output = 0;
		try {
			output = Integer.parseInt(choice);
		} catch (NumberFormatException e) {
			output = 0;
		}
		return output;
	}
}
