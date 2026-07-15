package views;

import utilities.InputReader;
import model.User;

public class UserView {
	
	public String loginMenu() {
		System.out.print("Insert your username: ");
		String username = InputReader.getInstance().readLine();
		return username;
	}
	
	public String newUserMenu() {
		System.out.print("Username not found. Do you want to create a new user? (y/n) ");
		String choice = InputReader.getInstance().readLine();
		if (choice.equalsIgnoreCase("y")) {
			return newUserMenu_NewUsername();
		} else {
			System.out.println("Returning to main menu.");
			return null;
		}
	}
	
	public String newUserMenu_NewUsername() {
		System.out.print("Insert new user's name: ");
		return InputReader.getInstance().readLine();
	}
}
