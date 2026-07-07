package views;

import utilities.InputReader;
import model.User;

public class UserView {
	
	public String loginMenu() {
		System.out.println("Insert your username: ");
		String username = InputReader.getInstance().readLine();
		return username;
	}
	
	public User newUserMenu() {
		System.out.println("Username not found. Do you want to create a new user? (y/n)");
		String choice = InputReader.getInstance().readLine();
		if (choice.equalsIgnoreCase("y")) {
			System.out.println("Insert your username: ");
			String username = InputReader.getInstance().readLine();
			User newUser = new User(username);
			System.out.println("New user created: " + newUser.getUsername());
			return newUser;
		} else {
			System.out.println("Returning to main menu.");
			return null;
		}
	}
	
}
