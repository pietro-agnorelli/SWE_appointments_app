package controllers;

import java.util.List;
import java.time.LocalDate;
import java.time.LocalTime;

import utilities.AppSession; 
import model.Appointment;
import model.Client;
import model.User;
import database.dao.AppointmentDao;
import views.AppointmentView;
import views.ClientView;
import views.CommonView;

public class AppointmentController {
	CommonView commonView = new CommonView();
	AppointmentView appointmentView = new AppointmentView();
	ClientView clientView = new ClientView();
	AppointmentDao appointmentDao = new AppointmentDao();
	
	public void viewUserAppointments(){
		User currentUser = AppSession.getInstance().getCurrentUser();
		List<Appointment> appointments = appointmentDao.getAppointmentsByUserId(currentUser);
		if (appointments.isEmpty()) {
			commonView.displayMessage("No appointments found for user: " + currentUser.getUsername());
		} else {
			appointmentView.displayUserAppointments(appointments);
		}
	}
	
	public void manageAppointments() {
		while (true) {
			int choice = appointmentView.showAppointmentMenu();
			switch (choice) {
				case 1:
					viewClientAppointments();
					continue;
				case 2:
					addAppointment();
					continue;
				case 3:
					return;
				default:
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}
	
	public void viewClientAppointments() {
		Client currentClient = AppSession.getInstance().getCurrentClient();
		List<Appointment> appointments = appointmentDao.getAppointmentsByClientId(currentClient);
		if (appointments.isEmpty()) {
			commonView.displayMessage("No appointments found for client: " + currentClient.getName());
		} else {
			appointmentView.displayClientAppointments(appointments);
		}
	}
	
	public void addAppointment() {
		String date = appointmentView.askForDate();
		String time = appointmentView.askForTime();
		Appointment newAppointment = new Appointment(AppSession.getInstance().getCurrentUser().getId(), 
				AppSession.getInstance().getCurrentClient().getId(), 
				LocalDate.parse(date), 
				LocalTime.parse(time));
		appointmentDao.addAppointment(newAppointment);
		commonView.displayMessage("Appointment added successfully.");
		}
		
}
	
	

