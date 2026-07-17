package main.java.services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.List;

import main.java.database.DBConnection;
import main.java.database.dao.AppointmentDao;
import main.java.model.Appointment;

public class AppointmentService {
	private final AppointmentDao appointmentDao = new AppointmentDao(DBConnection.getConnection());
		
	public void create(long userId, long clientId, String date, String time) throws IllegalArgumentException {
		
		if(date.trim().isEmpty()) {
			throw new IllegalArgumentException("Date cannot be empty");
		}
		
		if(time.trim().isEmpty()) {
			throw new IllegalArgumentException("Starting time cannot be empty");
		}
		
		//LocalDate parser used to check date format is correct
		LocalDate parsedDate;
		try {
			parsedDate = LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Date format is wrong");
		}
		
		//LocalTime parser used to check time format is correct
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
