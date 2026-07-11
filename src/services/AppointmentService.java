package services;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import database.dao.AppointmentDao;
import model.Appointment;
import model.Client;
import model.User;

public class AppointmentService {
	private final AppointmentDao appointmentDao = new AppointmentDao();
	
	public void add(long userId, long clientId, String date, String time) throws IllegalArgumentException {
		if(date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		if(time == null) {
			throw new IllegalArgumentException("Starting time cannot be null");
		}
		if(!date.matches("(19|20)\\d\\d([- \\.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])")) {
			throw new IllegalArgumentException("Date format is wrong");
		}
		if(!time.matches("([0-1]?[0-9]|2[0-3]):[0-5][0-9]")) {
			throw new IllegalArgumentException("Time format is wrong");
		}
		appointmentDao.addAppointment(new Appointment(userId, clientId, LocalDate.parse(date), LocalTime.parse(time)));
	}
	
	public List<Appointment> getByUserId (long id){
		return appointmentDao.getAppointmentsByUserId(id);
	}
	
	public List<Appointment> getByClientId(long id){
		return appointmentDao.getAppointmentsByClientId(id);
	}
}
