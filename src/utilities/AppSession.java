package utilities;

import model.Client;
import model.User;

public class AppSession {
	private static AppSession instance;
	private User currentUser;
	private Client currentClient;
	
	private AppSession() {}
	
	public static AppSession getInstance() {
		if (instance == null) {
			instance = new AppSession();
		}
		return instance;
	}
	
	public User getCurrentUser() {
		return currentUser;
	}
	
	public void setCurrentUser(User currentUser) {
		this.currentUser = currentUser;
	}
	
	public Client getCurrentClient() {
		return currentClient;
	}
	
	public void setCurrentClient(Client currentClient) {
		this.currentClient = currentClient;
	}
	
	public void clearCurrentUser() {
		this.currentUser = null;
	}
	
	public void clearCurrentClient() {
		this.currentClient = null;
	}

}
