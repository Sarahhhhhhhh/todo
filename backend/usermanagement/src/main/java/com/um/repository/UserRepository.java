package com.um.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.um.model.User;

@Repository
public interface UserRepository extends MongoRepository<User, String> {
	public User findByEmail(String email);
}
