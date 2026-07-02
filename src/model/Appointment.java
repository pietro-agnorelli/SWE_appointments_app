package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
	public long id;
	private long userId;
	private long clientId;
	private LocalDate date;
	private LocalTime startTime;
	
	public Appointment(long id, long userId, long clientId,  LocalDate date, LocalTime startTime) {
		this.id = id;
		this.userId = userId;
		this.clientId = clientId;
		this.date = date;
		this.startTime = startTime;
	}
	
	public Appointment(long userId, long clientId, LocalDate date, LocalTime startTime) {
		this.userId = userId;
		this.clientId = clientId;
		this.date = date;
		this.startTime = startTime;
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
	
	public LocalTime getStartTime() {
		return startTime;
	}
}
