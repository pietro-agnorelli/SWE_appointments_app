package services;

import java.time.LocalDate;
import java.util.List;

import database.dao.TreatmentDao;
import model.Client;
import model.Treatment;

public class TreatmentService {
	private final TreatmentDao treatmentDao = new TreatmentDao();
	
	public void add(long userId, long clientId, String description, String date) throws IllegalArgumentException {
		if(date == null) {
			throw new IllegalArgumentException("Date cannot be null");
		}
		if(description == null) {
			throw new IllegalArgumentException("Description cannot be null");
		}
		if(!date.matches("(19|20)\\d\\d([- \\.])(0[1-9]|1[012])\\2(0[1-9]|[12][0-9]|3[01])")) {
			throw new IllegalArgumentException("Date format is wrong");
		}
		treatmentDao.addTreatment(new Treatment(userId, clientId, LocalDate.parse(date), description));
	}
	
	public List<Treatment> getByClientId(long id){
		return treatmentDao.getTreatmentsByClientId(id);
	}
}

