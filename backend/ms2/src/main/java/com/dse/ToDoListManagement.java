package com.dse;

import com.dse.dao.ToDoListDAO;
import com.dse.list.ToDoList;

public class ToDoListManagement {
	
	private static int listID = 0;
	private static volatile ToDoListManagement instance;
	private static Object mutex = new Object();
	private ToDoListDAO dao = new ToDoListDAO();
	
	private ToDoListManagement() {}
	
	//Singleton pattern to be sure that the listID is always being the lastID added
	public static ToDoListManagement getInstance() {
		ToDoListManagement result = instance;
		if (result == null) {
			synchronized (mutex) {
				result = instance;
				if (result == null) {
					instance = result = new ToDoListManagement();
				}
			}
		}
		return result;
	}
	
	//Goes to the lastID in the list and increments it and then returns the listID
	public int nextListID() {
		for(ToDoList list : dao.getAllToDoLists()) {
			if(list.getId() == listID) {
				++listID;
			}
		}
		return listID;
	}
}