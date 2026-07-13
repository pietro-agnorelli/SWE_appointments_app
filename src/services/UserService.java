package services;

import database.DBConnection;
import database.dao.UserDao;
import model.User;

public class UserService {
	private final UserDao userDao = new UserDao(DBConnection.getConnection());
	
	public User create(String username) throws IllegalArgumentException{
		if(username == null) {
			return new User(-1, null);
		}
		if(username.trim().isEmpty()) {
			throw new IllegalArgumentException("Username cannot be empty");
		}
		if(userDao.getUserByUsername(username) != null) {
			throw new IllegalArgumentException("Username already exists");
		}
		User user = new User(username);
		userDao.addUser(user);
		return userDao.getUserByUsername(username);
	}
	
	public User getByUsername(String username) {
		return userDao.getUserByUsername(username);
	}
}
