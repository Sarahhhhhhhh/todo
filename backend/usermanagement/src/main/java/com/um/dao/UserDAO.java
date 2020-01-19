package com.um.dao;

import java.util.ArrayList;

import com.um.model.User;

public interface UserDAO {

	public ArrayList<User> getAllUsers();

	public User getUserByUserID(String id);

	public void saveUser(User user);

	public void verifyUser(User user);

	public void deleteAllUsers();
}
