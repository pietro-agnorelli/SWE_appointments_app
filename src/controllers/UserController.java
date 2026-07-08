package controllers;


import views.UserView;
import database.dao.UserDao;
import model.User;

public class UserController {
	UserView userView = new UserView();
	UserDao userDao = new UserDao();
	
	public User handleUserLogin() {
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
