package com.todo.service;

import java.util.Collection;
import java.util.List;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.dao.ToDoListDAO;
import com.todo.model.ToDoList;
import com.todo.model.ToDoTask;

@Service
public class ToDoListService {
	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private ToDoListDAO dao;
	private ObjectMapper mapper = new ObjectMapper();
	private HttpHeaders headers;
	private HttpEntity<String> entity;
	private ResponseEntity<String> response;

	public Collection<ToDoList> getAllToDoLists() {
		return dao.getTodolists().values();
	}

	public ToDoList getListById(String id) throws JsonProcessingException {
		return dao.getListById(id);
	}

	public List<ToDoTask> getAllListtodos(String id) {
		return dao.getListById(id).getTodos();
	}

	public ToDoTask getListTaskById(String id, int taskID) {
		return dao.getListById(id).getTodos().get(taskID);
	}

	public void addTask(String listID, ToDoTask task) throws JsonProcessingException {
		dao.addTask(listID, task);
	}

	public Collection<ToDoList> getListsWithSameUser(String user) {
		return dao.getListsWithSameUser(user);
	}

	public void deleteListById(String id) throws JsonProcessingException {
		notifyMs1(id);
		dao.deleteListById(id);
	}

	public void addList(ToDoList todolist) throws JsonProcessingException {
		ToDoList list = dao.addList(todolist);
		notifyMs1(list);
	}

	public void deleteTask(String listID, int taskID) throws JsonProcessingException {
		dao.deleteTask(listID, taskID);
	}

	public void updateList(String listID, ToDoList todolist) throws JsonProcessingException {
		dao.updateList(listID, todolist);
	}

	public void updateTaskText(String listID, int taskID) throws JsonProcessingException {
		dao.updateTaskText(listID, taskID, dao.getListById(listID).getTodos().get(taskID).getText());
	}

	public String notifyMs1(ToDoList todolist) throws JsonProcessingException {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject message = new JSONObject();
		message.put("email", todolist.getUser());
		entity = new HttpEntity<String>(message.toString(), headers);
		try {
			response = restTemplate.exchange("http://localhost:8080/todo/" + todolist.getId(), HttpMethod.PUT, entity,
					String.class);
			return response.getBody();
		} catch (Exception e) {
			System.err.println("MS1 is not available!!!");
		}
		return "FAILED";
	}

	public String notifyMs1(String id) throws JsonProcessingException {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject message = new JSONObject();
		entity = new HttpEntity<String>(message.toString(), headers);
		try {
			response = restTemplate.exchange("http://localhost:8080/todo/" + id, HttpMethod.DELETE, entity,
					String.class);
			return response.getBody();
		} catch (Exception e) {
			System.err.println("MS1 is not available!!!");
		}
		return "FAILED";
	}
}