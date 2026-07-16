package views;

import java.util.List;

import utilities.InputReader;
import model.Appointment;

public class AppointmentView {
	
	public int showAppointmentMenu() {
		System.out.println("Appointment Menu:");
		System.out.println("1. View Appointments");
		System.out.println("2. Add Appointment");
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
	
	public void displayUserAppointments(List<Appointment> appointments) {
		System.out.println("Your Appointments:");
		for (Appointment appointment : appointments) {
			System.out.println( "Client ID: " + appointment.getClientId() + 
					" |  Date: " + appointment.getDate() + 
					" |  Time: " + appointment.getStartTime());
		}
	}
	
	public void displayClientAppointments(List<Appointment> appointments) {
		System.out.println("Client Appointments:");
		for (Appointment appointment : appointments) {
			System.out.println("User ID: " + appointment.getUserId() + 
					" |  Date: " + appointment.getDate() + 
					" |  Time: " + appointment.getStartTime());
		}
	}
	
	public String askForDate() {
		System.out.print("Enter appointment date (YYYY-MM-DD): ");
		return InputReader.getInstance().readLine();
	}
	
	public String askForTime() {
		System.out.print("Enter appointment time (HH:MM): ");
		return InputReader.getInstance().readLine();
	}
}
