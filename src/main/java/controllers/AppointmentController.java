package main.java.controllers;

import java.util.List;

import main.java.utilities.AppSession; 
import main.java.model.Appointment;
import main.java.model.Client;
import main.java.model.User;
import main.java.services.AppointmentService;
import main.java.views.AppointmentView;
import main.java.views.CommonView;

public class AppointmentController {
	AppointmentView appointmentView = new AppointmentView();
	AppointmentService appointmentService = new AppointmentService();
	CommonView commonView = new CommonView();
	
	public AppointmentController() {}

	public void manageAppointments() {
		while (true) {
			int choice = appointmentView.showAppointmentMenu();
			switch (choice) {
				case 1:
					viewClientAppointments();
					continue;
				case 2:
					if(addAppointment()) {
						commonView.displayMessage("Appointment added successfully!");
					}
					continue;
				case 3:
					return;
				default:
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}
	
	
	public void viewUserAppointments(){
		User currentUser = AppSession.getInstance().getCurrentUser();
		List<Appointment> appointments = appointmentService.getByUserId(currentUser.getId());
		if (appointments.isEmpty()) {
			commonView.displayMessage("No appointments found for user: " + currentUser.getUsername());
		} else {
			appointmentView.displayUserAppointments(appointments);
		}
	}
	
	public void viewClientAppointments() {
		Client currentClient = AppSession.getInstance().getCurrentClient();
		List<Appointment> appointments = appointmentService.getByClientId(currentClient.getId());
		if (appointments.isEmpty()) {
			commonView.displayMessage("No appointments found for client: " + currentClient.getName());
		} else {
			appointmentView.displayClientAppointments(appointments);
		}
	}
	
	public boolean addAppointment() {
		String date = appointmentView.askForDate();
		String time = appointmentView.askForTime();
		try {
			appointmentService.create(AppSession.getInstance().getCurrentUser().getId(), 
					AppSession.getInstance().getCurrentClient().getId(), 
					date, time);
		} catch (IllegalArgumentException e) {
			commonView.displayMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	//Constructor used in testing
	public AppointmentController(AppointmentView appointmentView, AppointmentService appointmentService, CommonView commonView) {
		this.appointmentView = appointmentView;
		this.appointmentService = appointmentService;
		this.commonView = commonView;
	}
}
	
	

