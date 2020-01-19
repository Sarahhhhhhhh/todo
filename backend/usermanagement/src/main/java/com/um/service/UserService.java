package com.um.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.um.model.User;

@Service
public class UserService {

	@Autowired
	private SessionService sessionService;

	private List<User> users;

	private static final Logger logger = LoggerFactory.getLogger(UserService.class);

	public UserService() {
		this.users = new ArrayList<>(Arrays.asList(
				new User("Stefan", "Trifunovic", "st.trifunovic@gmail.com", "Trifko123",
						new ArrayList<String>(Arrays.asList("999"))),
				new User("Kristijan", "Matic", "trifko94@hotmail.de", "Kiki123",
						new ArrayList<String>(Arrays.asList("2222", "11111"))),
				new User("Jakub", "Gawrylkowicz", "jakub@gmail.com", "Jakub123",
						new ArrayList<String>(Arrays.asList("88888")))));
	}

	public User getUser(String id) {
		Optional<User> user = users.stream().filter(t -> t.getEmail().equals(id)).findFirst();
		if (user.isPresent()) {
			return user.get();
		}
		return null;

	}

	public HttpStatus addUser(User user) {
		int obj = 5;
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if (u.getEmail().equals(user.getEmail())) {
				logger.info("User allready exists!");
				obj = 10;
				break;
			} else {
				if (i + 1 == users.size() && !u.getEmail().equals(user.getEmail())) {
					users.add(user);
					logger.info("New user added!");
					break;
				}
			}
		}
		if (obj == 10) {
			return HttpStatus.NO_CONTENT;
		} else {
			return HttpStatus.OK;
		}
	}

	public void updateUser(String id, User user) {
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if (u.getEmail().equals(id)) {
				users.set(i, user);
				return;
			}
		}
	}

	public ArrayList<String> todoIdCheck(String id) throws JsonProcessingException {
		ArrayList<String> emails = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if (u.getTodolistsId().size() == 0) {
			} else {
				for (int j = 0; j < u.getTodolistsId().size(); j++) {
					if (u.getTodolistsId().get(j).equals(id)) {
						emails.add(u.getEmail());
					}
				}
			}
		}
		logger.info(emails.toString());
		return emails;
	}

	public void deleteUser(String id) {
		users.removeIf(t -> t.getEmail().equals(id));
	}

	public HttpStatus verifyUser(String email, String password) {
		int verify = 5;
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if (u.getEmail().equals(email) && u.getPassword().equals(password)) {
				verify = 10;
				logger.info("User verified, ToDoLists ID's user is authorised to acces: "
						+ getUser(email).getTodolistsId());
				this.sessionService.addUser(u);
				return HttpStatus.OK;
			}
		}
		if (verify == 5) {
			logger.error("Credentials error");
			return HttpStatus.INTERNAL_SERVER_ERROR;
		} else {
			return null;
		}
	}

	public void deleteTodoID(String email, String id) {
		int temp = -1;
		for (User u : users) {
			if (u.getEmail().equals(email)) {
				for (int i = 0; i < u.getTodolistsId().size(); i++) {
					if (id.equals(u.getTodolistsId().get(i))) {
						temp = i;
					}
				}
				if (temp != -1) {
					u.getTodolistsId().remove(temp);
				}
			}
		}
	}

	public List<User> getUsers() {
		return users;
	}

	public void setUsers(List<User> users) {
		this.users = users;
	}
}