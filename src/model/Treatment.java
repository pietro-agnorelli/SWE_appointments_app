package model;

import java.time.LocalDate;

public class Treatment {
	private long id;
	private LocalDate date;
	private String description;
	
	public Treatment(long id, LocalDate date, String description) {
		this.id = id;
		this.date = date;
		this.description = description;
	}
	
	public Treatment(LocalDate date, String description) {
		this.date = date;
		this.description = description;
	}
	
	public long getId() {
		return id;
	}
	
	public LocalDate getDate() {
		return date;
	}
	
	public String getDescription() {
		return description;
	}
}
