package controllers;

import java.time.LocalDate;
import java.util.List;

import views.TreatmentView;
import views.CommonView;
import model.Client;
import model.Treatment;
import services.TreatmentService;
import utilities.AppSession;


public class TreatmentController {
	TreatmentView treatmentView = new TreatmentView();
	TreatmentService treatmentService = new TreatmentService();
	CommonView commonView = new CommonView();
	
	public void manageTreatments() {
		while (true) {
			
			int choice = treatmentView.showTreatmentMenu();
			
			switch (choice) {
				case 1:
					if(addTreatment()) {
						commonView.displayMessage("Treatment added succesfully!");
					}
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
	
	public boolean addTreatment() {
		String description = treatmentView.askForDescription();
		String date = treatmentView.askForDate();
		try {
			treatmentService.add(AppSession.getInstance().getCurrentUser().getId(),
					AppSession.getInstance().getCurrentClient().getId(),
					date, description);
		} catch (IllegalArgumentException e) {
			commonView.displayMessage(e.getMessage());
			return false;
		}
		return true;
	}
	
	public void viewTreatments() {
		Client currentClient = AppSession.getInstance().getCurrentClient();
		List<Treatment> treatments = treatmentService.getByClientId(currentClient.getId());
		if (treatments.isEmpty()) {
			commonView.displayMessage("No treatments found for client: " + currentClient.getName());
		} else {
			treatmentView.displayTreatments(treatments);
		}
		
	}

}
