package main.java.model;

import java.time.LocalDate;

public class Treatment {
	private long id;
	private long userId;
	private long clientId;
	private LocalDate date;
	private String description;
	
	public Treatment(long id, long userId, long clientId, LocalDate date, String description) {
		this.id = id;
		this.userId = userId;
		this.clientId = clientId;
		this.date = date;
		this.description = description;
	}
	
	public Treatment(long userId, long clientId, LocalDate date, String description) {
		this.userId = userId;
		this.clientId = clientId;
		this.date = date;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public long getUserId() {
		return userId;
	}
	
	public long getClientId() {
		return clientId;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
}
