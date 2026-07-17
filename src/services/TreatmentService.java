package services;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import database.DBConnection;
import database.dao.TreatmentDao;
import model.Treatment;

public class TreatmentService {
	private final TreatmentDao treatmentDao = new TreatmentDao(DBConnection.getConnection());
	
	public void create(long userId, long clientId, String date, String description) throws IllegalArgumentException {
		
		if(date.trim().isEmpty()) {
			throw new IllegalArgumentException("Date cannot be empty");
		}
		
		if(description.trim().isEmpty()) {
			throw new IllegalArgumentException("Description cannot be empty");
		}
		
		//LocalDate parser used to check date format is correct
		LocalDate parsedDate;
		try {
			parsedDate = LocalDate.parse(date);
		} catch (DateTimeParseException e) {
			throw new IllegalArgumentException("Date format is wrong");
		}
		
		if(parsedDate.isAfter(LocalDate.now())) {
			throw new IllegalArgumentException("Cannot add treatments to the future");
		}
		
		treatmentDao.addTreatment(new Treatment(userId, clientId, parsedDate, description));
	}
	
	public List<Treatment> getByClientId(long id){
		return treatmentDao.getTreatmentsByClientId(id);
	}
}

