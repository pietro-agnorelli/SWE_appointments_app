package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import database.DBConnection;
import database.dao.AppointmentDao;
import model.Appointment;
import model.Client;
import model.User;

public class AppointmentService {
	private final AppointmentDao appointmentDao = new AppointmentDao(DBConnection.getConnection());
		
	public void create(long userId, long clientId, String date, String time) throws IllegalArgumentException {
		if(date.trim().isEmpty()) {
			throw new IllegalArgumentException("Date cannot be empty");
		}
		if(time.trim().isEmpty()) {
			throw new IllegalArgumentException("Starting time cannot be empty");
		}
		LocalDate parsedDate;
		try {
			parsedDate = LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Date format is wrong");
		}
		LocalTime parsedTime;
		try {
			parsedTime = LocalTime.parse(time);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Time format is wrong");
		}
		if(parsedDate.isBefore(LocalDate.now())) {
			throw new IllegalArgumentException("Cannot add appointments to the past");
		}
		appointmentDao.addAppointment(new Appointment(userId, clientId, parsedDate, parsedTime));
	}
	
	public List<Appointment> getByUserId (long id){
		return appointmentDao.getAppointmentsByUserId(id);
	}
	
	public List<Appointment> getByClientId(long id){
		return appointmentDao.getAppointmentsByClientId(id);
	}
}
