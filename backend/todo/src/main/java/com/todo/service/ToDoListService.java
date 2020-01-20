package com.todo.service;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.todo.model.ToDoList;
import com.todo.model.ToDoTask;
import com.todo.repository.TodoListRepository;
import com.todo.repository.TodoTaskRepository;

@Service
public class ToDoListService {

	@Autowired
	private RestTemplate restTemplate;
	@Autowired
	private TodoListRepository todoListRepository;
	@Autowired
	private TodoTaskRepository todoTaskRepository;
	private ObjectMapper mapper = new ObjectMapper();
	private HttpHeaders headers;
	private HttpEntity<String> entity;
	private ResponseEntity<String> response;

	public Collection<ToDoList> getAllToDoLists() {
		return this.todoListRepository.findAll();
	}

	public ToDoList getListById(String id) {
		return this.todoListRepository.findById(id).get();
	}

	public void addList(ToDoList todolist) throws JsonProcessingException {
		this.todoListRepository.save(todolist);
	}

	public void updateList(String listID, ToDoList todolist) throws JsonProcessingException {
		todolist.setId(listID);
		this.todoListRepository.save(todolist);
	}

	public void deleteListById(String id) throws JsonProcessingException {
		this.todoListRepository.deleteById(id);
	}

	public List<ToDoTask> getAllListtodos(String id) {
		return this.todoListRepository.findById(id).get().getTodos();
	}

	public ToDoTask getListTaskById(String id, int taskID) {
		return this.todoListRepository.findById(id).get().getTodos().get(taskID);
	}

	public void addTask(String listID, ToDoTask task) throws JsonProcessingException {
		ToDoList list = this.todoListRepository.findById(listID).get();
		task.setTaskID(list.getTodos().size());
		this.todoTaskRepository.save(task);
		list.getTodos().add(task);
		this.todoListRepository.save(list);
	}

	public void deleteTask(String listID, int taskID) throws JsonProcessingException {
		ToDoList list = this.todoListRepository.findById(listID).get();
		Iterator<ToDoTask> iterator = list.getTodos().iterator();
		while (iterator.hasNext()) {
			ToDoTask task = iterator.next();
			if (task.getTaskID() == taskID) {
				iterator.remove();
			}
		}
		this.todoListRepository.save(list);
	}

	public Collection<ToDoList> getListsWithSameUser(String user) {
		return this.todoListRepository.findByUser(user);
	}

}