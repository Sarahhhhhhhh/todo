package com.um.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.um.model.User;
import com.um.service.SessionService;
import com.um.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

	@Autowired
	private UserService umService;
	@Autowired
	private SessionService sessionService;

	@RequestMapping("/users/{id}")
	public User getUser(@PathVariable String id) {
		return umService.getUser(id);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		return new ResponseEntity<>(umService.addUser(user));
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public void updateUser(@RequestBody User user, @PathVariable String id) {
		umService.updateUser(id, user);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyUser(@RequestBody User user) {
		return new ResponseEntity<>(umService.verifyUser(user.getEmail(), user.getPassword()));
	}

	@RequestMapping(value = "/logout", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void logout(@RequestHeader("x-app-token") String token) {
		this.sessionService.removeUser(token);
	}
}
