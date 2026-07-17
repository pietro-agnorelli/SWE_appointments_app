package test.java.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import main.java.model.Client;

class ClientTest {

	@Test
	void testConstructorsAndGetters() {
		Client client = new Client("test", "test@test.it");
		Client client1 = new Client(1, "test1", "test1@test.it");
		String name = client.getName();
		String name1 = client1.getName();
		String email = client.getEmail();
		String email1 = client1.getEmail();
		long id = client.getId();
		long id1 = client1.getId();
		
		assertEquals(name, "test");
		assertEquals(name1, "test1");
		assertEquals(email, "test@test.it");
		assertEquals(email1, "test1@test.it");
		assertEquals(id, 0);
		assertEquals(id1, 1);
	}
}
