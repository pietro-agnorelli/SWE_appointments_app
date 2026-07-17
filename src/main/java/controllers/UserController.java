package main.java.controllers;


import main.java.views.CommonView;
import main.java.views.UserView;
import main.java.services.UserService;
import main.java.model.User;

public class UserController {
	UserView userView = new UserView();
	CommonView commonView = new CommonView();
	UserService userService = new UserService();
	
	public UserController() {}
	
	public User handleUserLogin() {
		commonView.clearScreen();
		String username = userView.loginMenu();
		User user = userService.getByUsername(username);
		if(user == null) {
			try {
				username = userView.newUserMenu();
				user = userService.create(username);
			} catch(IllegalArgumentException e) {
				commonView.displayMessage(e.getMessage());
			}
			while(user==null) {
				try {
					username=userView.newUserMenu_NewUsername();
					user=userService.create(username);
				} catch(IllegalArgumentException e) {
					commonView.displayMessage(e.getMessage());
				}
			}
		}
		return user;
	}
	
	//Constructor used in testing
	public UserController(UserView userView, CommonView commonView, UserService userService) { 
		this.userView = userView; 
		this.commonView = commonView; 
		this.userService = userService;
	}
}
