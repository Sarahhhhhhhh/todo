package com.dse.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.dse.ResponseMS2;
import com.dse.User;
import com.fasterxml.jackson.core.JsonProcessingException;
@CrossOrigin(origins = "*")
@RestController
public class Ms1Controller {
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	@Autowired
	private Ms1Service ms1service;
	
	@RequestMapping("/users")
	public List<User> getAllUsers() {
		return ms1service.getAllUsers();
	}
	
	@RequestMapping("/users/{id}")
	public User getUser(@PathVariable String id) {
		return ms1service.getUser(id);
	}
	
	//Add new user/registration. Upon adding new user, sends http ok status or fail if fails
	@RequestMapping(value = "/register", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addUser(@RequestBody User user) {
		return new ResponseEntity<>(ms1service.addUser(user));
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.PUT)
	public void updateUser(@RequestBody User user, @PathVariable String id) {
		ms1service.updateUser(id, user);
	}
	
	@RequestMapping(value = "/todo/{id}", method = RequestMethod.PUT)
	public void updateTodoID(@RequestBody ResponseMS2 s, @PathVariable String id) {
		ms1service.updateTodoID(s.getEmail(), id);
	}
	@RequestMapping(value = "/todo/{id}", method = RequestMethod.DELETE)
	public void deleteTodoID(@RequestBody ResponseMS2 s, @PathVariable String id) {
		ms1service.deleteTodoID(s.getEmail(), id);
	}
	
	@RequestMapping(value = "/uemails/{todoId}", method = RequestMethod.POST)
	public String todoIdCheck(@PathVariable String todoId) throws JsonProcessingException {
		String temp = "";
		ArrayList<String> al = ms1service.todoIdCheck(todoId);
		for(String s: al) {
			temp += s;
			if(!s.equals(al.get(al.size()-1))) {
				temp += ",";
			}
		}
		return temp;
	}
	
	@RequestMapping(value = "/users/{id}", method = RequestMethod.DELETE)
	public void deleteUser(@PathVariable String id) {
		ms1service.deleteUser(id);
	}
	@RequestMapping(value ="/login", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> verifyUser(@RequestBody User user) {
		return new ResponseEntity<>(ms1service.verifyUser(user.getEmail(), user.getPassword()));
	}
	
	@RequestMapping(value="/test", method= RequestMethod.POST)
	public String test() {
		return "Test!";
	}
	
	@RequestMapping(value="/route", method= RequestMethod.GET)
	public String test2() {
		return "Test2";
	}
	@RequestMapping(value = "/ha", method = RequestMethod.POST)
	 public String ha() throws JsonProcessingException{
		ms1service.askM1ForUser(0);
	  return "";
	}
}
