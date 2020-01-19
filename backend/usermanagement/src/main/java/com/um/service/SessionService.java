package com.um.service;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.um.model.User;

@Service
public class SessionService {
	private Map<String, User> authenticatedUsers = new HashMap<String, User>();

	public void addUser(User user) {
		this.authenticatedUsers.put(UUID.randomUUID().toString(), user);
	}

	public void removeUser(String token) {
		this.authenticatedUsers.remove(token);
	}

	public Map<String, User> getAuthenticatedUsers() {
		return authenticatedUsers;
	}

	public void setAuthenticatedUsers(Map<String, User> authenticatedUsers) {
		this.authenticatedUsers = authenticatedUsers;
	}

}
