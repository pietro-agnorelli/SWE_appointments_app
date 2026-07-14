package services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import database.DBConnection;
import database.dao.TreatmentDao;
import model.Treatment;

public class TreatmentService {
	private final TreatmentDao treatmentDao = new TreatmentDao(DBConnection.getConnection());
	
	public void create(long userId, long clientId, String description, String date) throws IllegalArgumentException {
		if(date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		if(description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		LocalDate parsedDate;
		try {
			parsedDate = LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Date format is wrong");
		}
		if(parsedDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Date cannot be from the future");
		}
		treatmentDao.addTreatment(new Treatment(userId, clientId, parsedDate, description));
	}
	
	public List<Treatment> getByClientId(long id){
		return treatmentDao.getTreatmentsByClientId(id);
	}
}

