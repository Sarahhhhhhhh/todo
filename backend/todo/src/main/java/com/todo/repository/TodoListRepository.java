package com.todo.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.todo.model.ToDoList;

@Repository
public interface TodoListRepository extends MongoRepository<ToDoList, String> {
	public List<ToDoList> findByUser(String user);
}
