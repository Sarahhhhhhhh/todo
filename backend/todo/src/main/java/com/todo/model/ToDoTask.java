package com.todo.model;

import org.springframework.data.annotation.Id;

public class ToDoTask {
	@Id
	private String id;
	private int taskID;
	private String text;
	private String date;
	private boolean done;

	public ToDoTask() {
	}

	public ToDoTask(String text, int taskID) {
		this.taskID = taskID;
		this.text = text;
		this.done = false;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public int getTaskID() {
		return this.taskID;
	}

	public void setTaskID(int taskID) {
		this.taskID = taskID;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
}
