package main.java.views;

import java.util.List;

import main.java.model.Treatment;
import main.java.utilities.InputReader;

public class TreatmentView {
	
	public int showTreatmentMenu() {
		System.out.println("Treatment Management Menu:");
		System.out.println("1. Add Treatment");
		System.out.println("2. View Treatments");
		System.out.println("3. Exit");
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
	
	public String askForDescription() {
		System.out.print("Enter treatment description: ");
		return InputReader.getInstance().readLine();
	}
	
	public String askForDate() {
		System.out.print("Enter treatment date (YYYY-MM-DD): ");
		return InputReader.getInstance().readLine();
	}
	
	public void displayTreatments(List<Treatment> treatments) {
		System.out.println("List of Treatments:");
		for (Treatment treatment : treatments) {
			System.out.println("Client ID: " + treatment.getClientId() + 
					" |  Date: " + treatment.getDate() + 
					" |  Description: " + treatment.getDescription());
		}
	}
}
