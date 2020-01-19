package com.dse.dao;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import org.springframework.stereotype.Repository;
import com.dse.list.ToDoList;
import com.dse.task.ToDoTask;

@Repository		
public class ToDoListDAO {	
	private static ArrayList<ToDoList> todolists = new ArrayList<ToDoList>();
	
	public ArrayList<ToDoList> getAllToDoLists() {		
		return todolists;
	}
	
	public ToDoList getListById(int id) {
		for(Iterator<ToDoList> iter = this.getAllToDoLists().iterator(); iter.hasNext();) {
			ToDoList list = iter.next();
			if(list.getId() == id) return list;
		}
		throw new IllegalArgumentException("The list with this id doesn't exist");
	}
	
	//Returns all the lists that a specific user has
	public Collection<ToDoList> getListsWithSameUser(String user) {
		ArrayList<ToDoList> sameUser = new ArrayList<ToDoList>();
		for(ToDoList list : todolists) {
			if(list.getUser().equals(user))
				sameUser.add(list);
		}	
		return sameUser;
	}

	public void deleteListById(int id) {
		for(Iterator<ToDoList> iter = this.getAllToDoLists().iterator(); iter.hasNext();) {
			ToDoList list = iter.next();
			if(list.getId() == id) iter.remove();
		}
	}

	public ToDoList addList(ToDoList todolist) {
		//Used this constructor to avoid problems with iterator
		ToDoList list = new ToDoList(todolist.getUser(), todolist.getTitle(), todolist.getTodos(), todolist.getDescription());
		todolists.add(list);
		return list;
	}
	
	public void updateList(int listID, ToDoList todolist) {
		todolist.setId(listID);
		todolists.set(listID, todolist);
	}
	
	public void addTask(int listID, ToDoTask task) {
		this.getListById(listID).getTodos().add(task);
	}
	
	public void deleteTask(int listID, int taskID) {
		for(Iterator<ToDoTask> iter = this.getListById(listID).getTodos().iterator(); iter.hasNext();) {
			ToDoTask task = iter.next();
			if(task.getTaskID() == taskID) iter.remove();
		}
	}
	
	public void updateTaskText(int listID, int taskID, String text) {
		for(int i = 0; i < this.getListById(listID).getTodos().size(); i++)
			if(this.getListById(listID).getTodos().get(i).getTaskID() == taskID)
				this.getListById(listID).getTodos().get(i).setText(text);
	}
	
	//works but is not in use. Here for possible needs in the future
	public void completeTask(int listID, int taskID, boolean done) {
		for(int i = 0; i < this.getListById(listID).getTodos().size(); i++)
			if(this.getListById(listID).getTodos().get(i).getTaskID() == taskID)
				this.getListById(listID).getTodos().get(i).setDone(done);
	}
}