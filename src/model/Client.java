package model;

public class Client {
	private long id;
	private String name;
	private String email;

	public Client(long id, String name, String email) {
		this.id = id;
		this.name = name;
		this.email = email;
	}
	
	public Client(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public long getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public String getEmail() {
		return email;
	}
}
