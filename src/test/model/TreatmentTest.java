package test.model;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;

import org.junit.jupiter.api.Test;

import model.Treatment;

class TreatmentTest {

	@Test
	void testConstructorsAndGetters() {
		LocalDate date = LocalDate.parse("2020-01-01");
		LocalDate date1 = LocalDate.parse("2020-01-02");
		Treatment treatment = new Treatment(1, 1, date, "test");
		Treatment treatment1 = new Treatment(1, 2, 2, date1, "test1");
		long id = treatment.getId();
		long id1 = treatment1.getId();
		long userId = treatment.getUserId();
		long userId1 = treatment1.getUserId();
		long clientId = treatment.getClientId();
		long clientId1 = treatment1.getClientId();
		LocalDate dateTest = treatment.getDate();
		LocalDate dateTest1 = treatment1.getDate();
		String description = treatment.getDescription();
		String description1 = treatment1.getDescription();
		
		assertEquals(id, 0);
		assertEquals(id1, 1);
		assertEquals(userId, 1);
		assertEquals(userId1, 2);
		assertEquals(clientId, 1);
		assertEquals(clientId1, 2);
		assertEquals(dateTest, date);
		assertEquals(dateTest1, date1);
		assertEquals(description, "test");
		assertEquals(description1, "test1");
	}

}
