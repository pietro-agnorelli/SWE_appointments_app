package test.java.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import org.junit.jupiter.api.Test;

import main.java.model.Appointment;

class AppointmentTest {

	@Test
	void testConstructorsAndGetters() {
		LocalDate date = LocalDate.parse("2020-01-01");
		LocalDate date1 = LocalDate.parse("2020-01-02");
		LocalTime time = LocalTime.parse("09:00");
		LocalTime time1 = LocalTime.parse("10:00");
		Appointment appointment = new Appointment(1, 1, date, time);
		Appointment appointment1 = new Appointment(1, 2, 2, date1, time1);
		long id = appointment.getId();
		long id1 = appointment1.getId();
		long userId = appointment.getUserId();
		long userId1 = appointment1.getUserId();
		long clientId = appointment.getClientId();
		long clientId1 = appointment1.getClientId();
		LocalDate dateTest = appointment.getDate();
		LocalDate dateTest1 = appointment1.getDate();
		LocalTime timeTest = appointment.getStartTime();
		LocalTime timeTest1 = appointment1.getStartTime();
		
		assertEquals(id, 0);
		assertEquals(id1, 1);
		assertEquals(userId, 1);
		assertEquals(userId1, 2);
		assertEquals(clientId, 1);
		assertEquals(clientId1, 2);
		assertEquals(dateTest, date);
		assertEquals(dateTest1, date1);
		assertEquals(timeTest, time);
		assertEquals(timeTest1, time1);
	}

}