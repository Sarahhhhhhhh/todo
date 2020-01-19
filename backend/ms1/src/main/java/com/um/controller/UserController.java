package com.um.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.um.model.User;
import com.um.service.UserService;

@CrossOrigin(origins = "*")
@RestController
public class UserController {

	@Autowired
	private UserService ms1service;

	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return ms1service.getUsers();
	}

	@RequestMapping("/users/{id}")
	public User getUser(@PathVariable String id) {
		return ms1service.getUser(id);
	}

	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		return new ResponseEntity<>(ms1service.addUser(user));
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public void updateUser(@RequestBody User user, @PathVariable String id) {
		ms1service.updateUser(id, user);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
	public void updateTodoID(@RequestParam("email") String email, @PathVariable String id) {
		ms1service.updateTodoID(email, id);
	}

	@RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
	public void deleteTodoID(@RequestParam("email") String email, @PathVariable String id) {
		ms1service.deleteTodoID(email, id);
	}

	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String id) {
		ms1service.deleteUser(id);
	}

	@RequestMapping(value = "/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyUser(@RequestBody User user) {
		return new ResponseEntity<>(ms1service.verifyUser(user.getEmail(), user.getPassword()));
	}

}
