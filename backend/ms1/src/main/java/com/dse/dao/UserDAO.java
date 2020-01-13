package com.dse.dao;

import java.util.ArrayList;

import com.dse.User;

public interface UserDAO {
	
	public ArrayList<User> getAllUsers();
	public User getUserByUserID(String id);
	public void saveUser(User user);
	public void verifyUser(User user);
	public void deleteAllUsers();

}
