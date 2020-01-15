package com.dse.dao;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;

import com.dse.User;


public class SerializedUserDAO implements UserDAO{
	
	private static SerializedUserDAO instance;
	private String file;
	private ArrayList<User> users;
	
	private  SerializedUserDAO() {
		this.file = "users.dat";
		this.users = new ArrayList<User>();
		if(!new File(file).exists()) {
			readOutFile();
		}
		readFile();
	}	
	
	@Override
	public ArrayList<User> getAllUsers() {
		readFile();
		return users;
	}

	@Override
	public User getUserByUserID(String id) {
		for(User element : users) {
			if(element.getEmail() == id) {
				return element;
			}
		}
		return null;
	}

	@Autowired
	@Override
	public void saveUser(User user) {
		String id = user.getEmail();
			if(users.stream().filter(t -> t.getEmail().equals(id)) != null) {
				System.out.println("User allready exists with that ID");
			}else {
		this.users.add(user);
		readOutFile();
			}
	}

	@Override
	public void verifyUser(User user) {
		String pass = user.getPassword();
		String name = user.getEmail();
		Boolean obj = false;
		
		for(User element : users) {
			if(element.getName() == name) {
				if(element.getPassword() == pass) {
					obj = true;
					}
				}else {
					System.out.println("Wrong password");
				}
			}		
	}

	@Override
	public void deleteAllUsers() {
		this.users.clear();
		readOutFile();
		
	}
	
	@SuppressWarnings("unchecked")
	public void readFile() {
		ObjectInputStream ois = null;
		try {
			ois = new ObjectInputStream(new FileInputStream(this.file));
			this.users = (ArrayList<User>) ois.readObject();
		}
		catch (IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		catch (ClassNotFoundException e) {
			System.err.println(e);
			System.exit(1);
		}
		finally {
			try {
				ois.close();
			}
			catch(NullPointerException e) {
				e.printStackTrace();
				System.exit(1);
			}
			catch(IOException e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
	}
	
	public void readOutFile() {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(this.file));
			oos.writeObject(this.users);
		}
		catch(IOException e) {
			System.err.println(e);
			System.exit(1);
		}
		finally {
			try {
				oos.close();
			}
			catch(Exception e) {
				e.printStackTrace();
				System.exit(1);
			}
		}
		
	}
	
	public static SerializedUserDAO getInstance() {
		if(instance == null) {
			instance = new SerializedUserDAO();
		}
		return instance;
	}

}
