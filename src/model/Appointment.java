package model;

import java.time.LocalDate;
import java.time.LocalTime;

public class Appointment {
	public long id;
	LocalDate date;
	LocalTime startTime;
	
	public Appointment(long id, LocalDate date, LocalTime startTime) {
		this.id = id;
		this.date = date;
		this.startTime = startTime;
	}
	
	public Appointment(LocalDate date, LocalTime startTime) {
		this.date = date;
		this.startTime = startTime;
	}
	
	public long getId() {
		return id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public LocalTime getStartTime() {
		return startTime;
	}
}
