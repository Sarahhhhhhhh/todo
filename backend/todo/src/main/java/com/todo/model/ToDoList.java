package com.todo.model;

import java.util.ArrayList;
import java.util.UUID;

public class ToDoList {

	// member variables
	private String id;
	private String user;
	private String title;
	private ArrayList<ToDoTask> todos = new ArrayList<ToDoTask>();
	private String description;

	public ToDoList() {
	}

	public ToDoList(String user, String title, ArrayList<ToDoTask> todos, String description) {
		this.id = UUID.randomUUID().toString();
		this.user = user;
		this.title = title;
		this.todos = todos;
		this.description = description;
	}

	// getters, setters
	public String getUser() {
		return user;
	}

	public void setUser(String user) {
		this.user = user;
	}

	public ArrayList<ToDoTask> getTodos() {
		return todos;
	}

	public void settodos(ArrayList<ToDoTask> todos) {
		this.todos = todos;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}
}