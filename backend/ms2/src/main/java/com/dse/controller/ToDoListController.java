package com.dse.controller;

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
import com.dse.list.ToDoList;
import com.dse.service.ToDoListService;
import com.dse.task.ToDoTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@CrossOrigin(origins = "*")
@RestController
public class ToDoListController {
	ObjectMapper mapper = new ObjectMapper();
	@Autowired
	private ToDoListService service;
	
	//Sends all the lists to the service which requested the lists
	@RequestMapping(value = "/lists", method = RequestMethod.GET)
	public Collection<ToDoList> getAllToDoLists() {
		return service.getAllToDoLists();
	}
	
	//Sends a specific list to the service which requested it
	@RequestMapping(value = "/lists/{id}", method = RequestMethod.GET)
	public ResponseEntity<String> getListById(@PathVariable("id") int id) throws JsonProcessingException {
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListById(id)), null, HttpStatus.OK);
	}
	
	//Sends all the tasks from a specific list to the service which requested it
	@RequestMapping(value = "/lists/{id}/tasks", method = RequestMethod.GET)
	public ResponseEntity<String> getAllListTasks(@PathVariable("id") int id) throws JsonProcessingException {
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListById(id).getTodos()), null, HttpStatus.OK);
	}

	
	//Adds the task to the specific list when requested from some other service
	@RequestMapping(value = "/lists/{id}/tasks", method = RequestMethod.POST)
	public void addTask(@PathVariable("id") int listID, @RequestBody ToDoTask task) throws JsonProcessingException {
		service.addTask(listID, task);
	}
	
	//Deletes the task from a specific list when requested from some other service
	@RequestMapping(value = "/lists/{id}/tasks/{taskID}", method = RequestMethod.DELETE)
	public void deleteTask(@PathVariable("id") int listID, @PathVariable("taskID") int taskID) throws JsonProcessingException {
		service.deleteTask(listID, taskID);
	}
	
	//Updates the task from a specific list when requested from some other service
	@RequestMapping(value = "/lists/{id}/tasks/{taskID}", method = RequestMethod.PUT)
	public void updateTask(@PathVariable("id") int listID, @PathVariable("taskID") int taskID) throws JsonProcessingException {
		service.updateTaskText(listID, taskID);
	}
	
	//Sends all the lists from a specific user to the service which requested the lists
	@RequestMapping(value = "/{user}", method = RequestMethod.GET)
	public Collection<ToDoList> getListsWithSameUser(@PathVariable("user") String user) {
		return service.getListsWithSameUser(user);
	}
	
	@RequestMapping(value = "/user", method = RequestMethod.POST)
		public Collection<ToDoList> getListsWithSamBeUserName(@RequestBody String user) {
			return service.getListsWithSameUser(user);
		}
	
	//Deletes a specific list when requested from some other service and sends all the lists from
	//a specific user as a response to the service which requested the deletion
	@RequestMapping(value = "/lists/{id}", method = RequestMethod.DELETE)
	public ResponseEntity<String> deleteListById(@PathVariable("id") int id) throws JsonProcessingException {
		ToDoList list = service.getListById(id);
		service.deleteListById(id);
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListsWithSameUser(list.getUser())), null, HttpStatus.OK);
	}
	
	//Adds the list when requested from some other service and sends all the lists from a specific user
	//as a response to the service which requested the addition 
	@RequestMapping(value = "/lists", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> addList(@RequestBody ToDoList todolist) throws JsonProcessingException {	
		service.addList(todolist);
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListsWithSameUser(todolist.getUser())), null, HttpStatus.CREATED);
	}
	
	//Updates the list when requested from some other service and sends all the lists from a specific user
	//as a response to the service which requested the update 
	@RequestMapping(value = "/lists/{id}", method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<String> updateList(@RequestBody ToDoList todolist, @PathVariable("id") int id) throws JsonProcessingException {
		service.updateList(id, todolist);
		return new ResponseEntity<String>(mapper.writeValueAsString(service.getListById(id)), null, HttpStatus.OK);
	}
}