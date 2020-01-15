package com.dse;

import java.io.Serializable;
import java.util.ArrayList;

@SuppressWarnings("serial")
public class User implements Serializable{
	
	private String name, lastName, email, password;
	private ArrayList<String> todolistsId = new ArrayList<>();
	
	public User () {}
	
	public User(String name, String lastname, String email, String password, ArrayList<String> todolistsId) {
		super();
		this.setEmail(email);
		this.setName(name);
		this.setLastName(lastname);
		this.setPassword(password);
		this.setTodolistsId(todolistsId); 
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	public ArrayList<String> getTodolistsId() {
		return todolistsId;
	}
	public void setTodolistsId(ArrayList<String> todolistsId) {
		this.todolistsId = todolistsId;
	}
	public void addTodoIDs(String id) {
		this.todolistsId.add(id);
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
		
}

