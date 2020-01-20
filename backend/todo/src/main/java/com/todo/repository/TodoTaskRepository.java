package com.todo.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.todo.model.ToDoTask;

@Repository
public interface TodoTaskRepository extends MongoRepository<ToDoTask, String> {

}
