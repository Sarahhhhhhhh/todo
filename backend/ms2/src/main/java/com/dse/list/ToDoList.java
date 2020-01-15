package com.dse.list;

import java.util.ArrayList;
import com.dse.ToDoListManagement;
import com.dse.task.ToDoTask;

public class ToDoList {
	private int id;
	private String user;
	private String title;
	private ArrayList<ToDoTask> todos = new ArrayList<ToDoTask>();
	private String description;
	
	private static ToDoListManagement management = ToDoListManagement.getInstance();
	
	public ToDoList() {}
	
	public ToDoList(String user, String title, ArrayList<ToDoTask> todos, String description) {
		this.id = management.nextListID();
		this.user = user;
		this.title = title;
		this.todos = todos;
		this.description = description;
	}

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

	public int getId() {
		return id;
	}
	
	public void setId(int id) {
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