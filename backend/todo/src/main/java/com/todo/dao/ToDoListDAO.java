package com.todo.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.springframework.stereotype.Repository;

import com.todo.model.ToDoList;
import com.todo.model.ToDoTask;

@Repository
public class ToDoListDAO {

	private Map<String, ToDoList> todolists = new HashMap<String, ToDoList>();

	public ToDoList getListById(String id) {
		return this.todolists.get(id);
	}

	public Collection<ToDoList> getListsWithSameUser(String user) {
		ArrayList<ToDoList> sameUser = new ArrayList<ToDoList>();
		for (ToDoList list : todolists.values()) {
			if (list.getUser().equals(user))
				sameUser.add(list);
		}
		return sameUser;
	}

	public void deleteListById(String id) {
		this.todolists.remove(id);
	}

	public ToDoList addList(ToDoList todolist) {
		ToDoList list = new ToDoList(todolist.getUser(), todolist.getTitle(), todolist.getTodos(),
				todolist.getDescription());
		this.todolists.put(list.getId(), list);
		return list;
	}

	public void updateList(String listID, ToDoList todolist) {
		todolist.setId(listID);
		this.todolists.put(listID, todolist);
	}

	public void addTask(String listID, ToDoTask task) {
		this.getListById(listID).getTodos().add(task);
	}

	public void deleteTask(String listID, int taskID) {
		for (Iterator<ToDoTask> iter = this.getListById(listID).getTodos().iterator(); iter.hasNext();) {
			ToDoTask task = iter.next();
			if (task.getTaskID() == taskID)
				iter.remove();
		}
	}

	public void updateTaskText(String listID, int taskID, String text) {
		for (int i = 0; i < this.getListById(listID).getTodos().size(); i++)
			if (this.getListById(listID).getTodos().get(i).getTaskID() == taskID)
				this.getListById(listID).getTodos().get(i).setText(text);
	}

	public void completeTask(String listID, int taskID, boolean done) {
		for (int i = 0; i < this.getListById(listID).getTodos().size(); i++)
			if (this.getListById(listID).getTodos().get(i).getTaskID() == taskID)
				this.getListById(listID).getTodos().get(i).setDone(done);
	}

	public Map<String, ToDoList> getTodolists() {
		return todolists;
	}

	public void setTodolists(Map<String, ToDoList> todolists) {
		this.todolists = todolists;
	}

}