package controllers;

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
	
	public TreatmentController() {}
	
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
					commonView.displayMessage("Invalid choice. Please try again.");
			}
		}
	}
	
	public boolean addTreatment() {
		String description = treatmentView.askForDescription();
		String date = treatmentView.askForDate();
		try {
			treatmentService.create(AppSession.getInstance().getCurrentUser().getId(),
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
	
	public TreatmentController(TreatmentView treatmentView, TreatmentService treatmentService, CommonView commonView) {
		this.treatmentView = treatmentView;
		this.treatmentService = treatmentService;
		this.commonView = commonView;
	}
}
