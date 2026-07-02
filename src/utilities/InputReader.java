package utilities;

import java.util.Scanner;

public class InputReader {
	private static InputReader instance;
	private Scanner scanner;
	
	private InputReader() {}
	
	public static InputReader getInstance() {
		if (instance == null) {
			instance = new InputReader();
			instance.scanner = new Scanner(System.in);
		}
		return instance;
	}
	
	public String readLine() {
		return scanner.nextLine();
	}
	
	public String readLine(String prompt) {
		System.out.print(prompt);
		return scanner.nextLine();
	}
}
