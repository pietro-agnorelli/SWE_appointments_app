package controllers;


import views.CommonView;
import views.UserView;
import services.UserService;
import model.User;

public class UserController {
	UserView userView = new UserView();
	CommonView commonView = new CommonView();
	UserService userService = new UserService();
	
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
		}
		return user;
	}
}
