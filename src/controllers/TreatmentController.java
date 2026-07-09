package controllers;

import java.time.LocalDate;
import java.util.List;

import views.TreatmentView;
import views.CommonView;
import model.Client;
import model.Treatment;
import utilities.AppSession;
import database.dao.TreatmentDao;


public class TreatmentController {
	TreatmentView treatmentView = new TreatmentView();
	TreatmentDao treatmentDao = new TreatmentDao();
	CommonView commonView = new CommonView();
	
	public void manageTreatments() {
		while (true) {
			
			int choice = treatmentView.showTreatmentMenu();
			
			switch (choice) {
				case 1:
					addTreatment();
					continue;
				case 2:
					viewTreatments();
					continue;
				case 3:
					return;
				default:
					System.out.println("Invalid choice. Please try again.");
			}
		}
	}
	
	public void addTreatment() {
		String treatmentDescription = treatmentView.askForDescription();
		String date = treatmentView.askForDate();
		Treatment newTreatment = new Treatment(AppSession.getInstance().getCurrentUser().getId(),
				AppSession.getInstance().getCurrentClient().getId(),
				LocalDate.parse(date),
				treatmentDescription);
		treatmentDao.addTreatment(newTreatment);
		commonView.displayMessage("Treatment added successfully.");
	}
	
	public void viewTreatments() {
		Client currentClient = AppSession.getInstance().getCurrentClient();
		List<Treatment> treatments = treatmentDao.getTreatmentsByClientId(currentClient);
		if (treatments.isEmpty()) {
			commonView.displayMessage("No treatments found for client: " + currentClient.getName());
		} else {
			treatmentView.displayTreatments(treatments);
		}
		
	}

}
