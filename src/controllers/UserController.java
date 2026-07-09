package controllers;


import views.CommonView;
import views.UserView;
import database.dao.UserDao;
import model.User;

public class UserController {
	UserView userView = new UserView();
	CommonView commonView = new CommonView();
	UserDao userDao = new UserDao();
	
	public User handleUserLogin() {
		commonView.clearScreen();
		String username = userView.loginMenu();
		User user = userDao.getUserByUsername(username);
		if (user == null) {
			user=userView.newUserMenu();
			if(user == null) {
				return null;
			} else {
				userDao.addUser(user);
			}
		}
		return user;
	}
}
