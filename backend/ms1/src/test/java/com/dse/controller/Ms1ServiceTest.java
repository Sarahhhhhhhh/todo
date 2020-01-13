package com.dse.controller;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;

import com.dse.User;

class Ms1ServiceTest {
	
	private Ms1Service service;
	
	public Ms1ServiceTest() {
		this.service = new Ms1Service();
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
	void addUserSuccesstest() {
		assertEquals(HttpStatus.OK, this.service.addUser(new User ("newUser", "newUser","newUser",  "newUser", new ArrayList<String> (Arrays.asList("88888")))));
	}
	
	@Test
	void addUserFailtest() {
		assertEquals(HttpStatus.NO_CONTENT, this.service.addUser(new User ("Jakub", "Gawrylkowicz","jakub@gmail.com",  "Jakub123", new ArrayList<String> (Arrays.asList("88888")))));
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
		assertEquals(HttpStatus.NO_CONTENT, this.service.verifyUser("jakub@gmail.com", "Jakub1234"));
	}
	
	@Test
	void updateUsertest() {
		User user = this.service.getUser("jakub@gmail.com");
		user.setPassword("123");
		this.service.updateUser("jakub@gmail.com", user);
		assertEquals(HttpStatus.OK, this.service.verifyUser("jakub@gmail.com", "123"));
	}
}
