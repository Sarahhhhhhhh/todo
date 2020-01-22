package com.um.service;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.um.model.User;
import com.um.repository.UserRepository;

@Service
public class UserService {

	@Autowired
	private SessionService sessionService;
	@Autowired
	private UserRepository userRepository;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public User getUser(String id) {
		Optional<User> user = this.userRepository.findAll().stream().filter(t -> t.getEmail().equals(id)).findFirst();
		if (user.isPresent()) {
			return user.get();
		}
		return null;
	}

	public HttpStatus addUser(User user) {
		User userDb = this.userRepository.findByEmail(user.getEmail());
		if (userDb != null) {
			return HttpStatus.NO_CONTENT;
		}
		this.userRepository.save(user);
		return HttpStatus.OK;

	}

	public void updateUser(String id, User user) {
		user.setId(id);
		this.userRepository.save(user);
	}

	public void deleteUser(String id) {
		this.userRepository.delete(id);
	}

	public HttpStatus verifyUser(String email, String password) {
		User user = this.userRepository.findByEmail(email);
		if (user == null || !user.getPassword().contentEquals(password)) {
			logger.error("Credentials error");
			return HttpStatus.INTERNAL_SERVER_ERROR;
		}
		logger.info("User verified, ToDoLists ID's user is authorised to acces: ");
		this.sessionService.addUser(user);
		return HttpStatus.OK;

	}

}