package test.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import model.User;

class UserTest {

	@Test
	void testConstructorsAndGetters() {
		User user = new User("test");
		User user1 = new User(1, "test1");
		String name = user.getUsername();
		String name1 = user1.getUsername();
		long id = user.getId();
		long id1 = user1.getId();
		
		
		assertEquals(name, "test");
		assertEquals(name1, "test1");
		assertEquals(id, 0);
		assertEquals(id1, 1);
	}
}
