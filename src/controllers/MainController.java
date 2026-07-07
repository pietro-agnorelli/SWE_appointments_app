package controllers;

import model.User;
import utilities.AppSession;
import views.MainView;
import views.CommonView;

public class MainController {
	MainView mainView = new MainView();
	CommonView commonView = new CommonView();
	UserController userController = new UserController();
	ClientController clientController = new ClientController();
	
	public void start() {
		
		while (true) {
			User currentUser = AppSession.getInstance().getCurrentUser();
			if (currentUser == null) {
				currentUser = userController.userLogin();
				if (currentUser != null) {
					AppSession.getInstance().setCurrentUser(currentUser);
					commonView.displayMessage("Welcome, " + currentUser.getUsername() + "!");
				} else {
					break; // Exit the application if user login fails or user chooses not to create a new user
				}
			} else {
				int choice = mainView.showMainMenu();
				switch (choice) {
					case 1:
						clientController.manageClients();
					case 2:
						// Handle appointment management
					case 3:
						// Handle treatment management
					case 4:
						commonView.displayMessage("Exiting the application. Goodbye!");
						AppSession.getInstance().clearCurrentUser();
						continue;
					default:
						commonView.displayMessage("Invalid choice. Please try again.");
				}
			}
		}
		
	}
}
