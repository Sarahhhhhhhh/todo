package com.dse.service;

import java.util.ArrayList;
import java.util.Collection;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.dse.dao.ToDoListDAO;
import com.dse.list.ToDoList;
import com.dse.task.ToDoTask;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class ToDoListService {
	ToDoListDAO dao = new ToDoListDAO();
	// Mapper is used to convert the java object to JSON object
	ObjectMapper mapper = new ObjectMapper();
	HttpHeaders headers;
	HttpEntity<String> entity;
	ResponseEntity<String> response;
	@Autowired
	private RestTemplate restTemplate;

	public Collection<ToDoList> getAllToDoLists() {
		return dao.getAllToDoLists();
	}

	public ToDoList getListById(int id) throws JsonProcessingException {
		return dao.getListById(id);
	}

	// works but is not in use. Here for possible needs in the future
	public ArrayList<ToDoTask> getAllListtodos(int id) {
		return dao.getListById(id).getTodos();
	}

	// works but is not in use. Here for possible needs in the future
	public ToDoTask getListTaskById(int id, int taskID) {
		return dao.getListById(id).getTodos().get(taskID);
	}

	// works but is not in use. Here for possible needs in the future
	public void addTask(int listID, ToDoTask task) throws JsonProcessingException {
		dao.addTask(listID, task);
	}

	public Collection<ToDoList> getListsWithSameUser(String user) {
		return dao.getListsWithSameUser(user);
	}

	public void deleteListById(int id) throws JsonProcessingException {
		notifyMs4(id);
		notifyMs1(id);
		dao.deleteListById(id);
	}

	public void addList(ToDoList todolist) throws JsonProcessingException {
		ToDoList list = dao.addList(todolist);
		notifyMs1(list);
		notifyMs4(list);
	}

	// works but is not in use. Here for possible needs in the future
	public void deleteTask(int listID, int taskID) throws JsonProcessingException {
		dao.deleteTask(listID, taskID);
	}

	public void updateList(int listID, ToDoList todolist) throws JsonProcessingException {
		dao.updateList(listID, todolist);
		notifyMs4(listID, todolist);
	}

	// works but is not in use. Here for possible needs in the future
	public void updateTaskText(int listID, int taskID) throws JsonProcessingException {
		dao.updateTaskText(listID, taskID, dao.getListById(listID).getTodos().get(taskID).getText());
	}

	// Used to inform ms4 when the lists are changed, added or updated. Sends the
	// changed data as JSON
	public void notifyMs4(int listID, ToDoList todolist) throws JsonProcessingException {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject message = new JSONObject();
		message.put("message", "list updated");
		message.put("listID", dao.getListById(listID).getId());
		message.put("user", dao.getListById(listID).getUser());
		entity = new HttpEntity<String>(message.toString(), headers);
		try {
			response = restTemplate.exchange("http://localhost:8080/notify", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.err.println("MS4 is not available!!!");
		}
	}

	// Used to inform ms4 when the lists are changed, added or updated. Sends the
	// changed data as JSON
	public void notifyMs4(ToDoList todolist) throws JsonProcessingException {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject message = new JSONObject();
		message.put("message", "list added");
		message.put("listID", todolist.getId());
		message.put("user", todolist.getUser());
		entity = new HttpEntity<String>(message.toString(), headers);
		try {
			response = restTemplate.exchange("http://localhost:8080/notify", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.err.println("MS4 is not available!!!");
		}
	}

	// Used to inform ms4 when the lists are changed, added or updated. Sends the
	// changed data as JSON
	public void notifyMs4(int id) throws JsonProcessingException {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject message = new JSONObject();
		message.put("message", "List deleted");
		message.put("listID", dao.getListById(id).getId());
		message.put("user", dao.getListById(id).getUser());
		entity = new HttpEntity<String>(message.toString(), headers);
		try {
			response = restTemplate.exchange("http://localhost:8080/notify", HttpMethod.POST, entity, String.class);
		} catch (Exception e) {
			System.err.println("MS4 is not available!!!");
		}
	}

	// Used to inform ms1 when the lists are changed, added or updated. Sends the
	// changed data as JSON
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

	// Used to inform ms1 when the lists are changed, added or updated. Sends the
	// changed data as JSON
	public String notifyMs1(int id) throws JsonProcessingException {
		headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		JSONObject message = new JSONObject();
		// message.put("email", dao.getListById(id).getUser());
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