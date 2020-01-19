package com.todo.controller;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.model.ToDoList;
import com.todo.model.ToDoTask;
import com.todo.service.ToDoListService;

@CrossOrigin(origins = "*")
@RestController
public class ToDoListController {

	@Autowired
	private ToDoListService service;
	private ObjectMapper mapper = new ObjectMapper();

	@RequestMapping(value = "/lists", method = RequestMethod.GET)
	public Collection<ToDoList> getAllToDoLists() {
		return service.getAllToDoLists();
	}

	@RequestMapping(value = "/lists/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getListById(@PathVariable("id") String id) throws JsonProcessingException {
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListById(id)), null, HttpStatus.OK);
	}

	@RequestMapping(value = "/lists/{id}/tasks", method = RequestMethod.GET)
	public ResponseEntity<String> getAllListTasks(@PathVariable("id") String id) throws JsonProcessingException {
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListById(id).getTodos()), null,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/lists/{id}/tasks", method = RequestMethod.POST)
	public void addTask(@PathVariable("id") String listID, @RequestBody ToDoTask task) throws JsonProcessingException {
		service.addTask(listID, task);
	}

	@RequestMapping(value = "/lists/{id}/tasks/{taskID}", method = RequestMethod.DELETE)
	public void deleteTask(@PathVariable("id") String listID, @PathVariable("taskID") int taskID)
			throws JsonProcessingException {
		service.deleteTask(listID, taskID);
	}

	@RequestMapping(value = "/lists/{id}/tasks/{taskID}", method = RequestMethod.GET)
	public ResponseEntity<String> getTaskById(@PathVariable("id") String listID, @PathVariable("taskID") int taskID)
			throws JsonProcessingException {
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListTaskById(listID, taskID)), null,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/lists/{id}/tasks/{taskID}", method = RequestMethod.PUT)
	public void updateTask(@PathVariable("id") String listID, @PathVariable("taskID") int taskID)
			throws JsonProcessingException {
		service.updateTaskText(listID, taskID);
	}

	@RequestMapping(value = "/{user}", method = RequestMethod.GET)
	public Collection<ToDoList> getListsWithSameUser(@PathVariable("user") String user) {
		return service.getListsWithSameUser(user);
	}

	@RequestMapping(value = "/lists/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteListById(@PathVariable("id") String id) throws JsonProcessingException {
		ToDoList list = service.getListById(id);
		service.deleteListById(id);
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListsWithSameUser(list.getUser())), null,
				HttpStatus.OK);
	}

	@RequestMapping(value = "/lists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addList(@RequestBody ToDoList todolist) throws JsonProcessingException {
		service.addList(todolist);
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListsWithSameUser(todolist.getUser())),
				null, HttpStatus.CREATED);
	}

	@RequestMapping(value = "/lists/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateList(@RequestBody ToDoList todolist, @PathVariable("id") String id)
			throws JsonProcessingException {
		service.updateList(id, todolist);
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListById(id)), null, HttpStatus.OK);
	}
}