package com.um.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.um.model.User;
import com.um.service.UserService;

class UmServiceTest {

	private UserService service;

	public UmServiceTest() {
		this.service = new UserService();
	}

	@Test
	void getUsertest() {
		assertNotEquals(null, this.service.getUser("st.trifunovic@gmail.com"));
	}

	@Test
	void getUserNotFoundtest() {
		assertEquals(null, this.service.getUser("test"));
	}

	@Test
	void deleteUsertest() {
		this.service.deleteUser("newUser");
		assertEquals(null, this.service.getUser("newUser"));
	}

	@Test
	void verifyUserSuccesstest() {
		assertEquals(HttpStatus.OK, this.service.verifyUser("jakub@gmail.com", "Jakub123"));
	}

	@Test
	void verifyUserFailtest() {
		assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, this.service.verifyUser("jakub@gmail.com", "Jakub1234"));
	}

	@Test
	void updateUsertest() {
		User user = this.service.getUser("jakub@gmail.com");
		user.setPassword("123");
		this.service.updateUser("jakub@gmail.com", user);
		assertEquals(HttpStatus.OK, this.service.verifyUser("jakub@gmail.com", "123"));
	}
}
