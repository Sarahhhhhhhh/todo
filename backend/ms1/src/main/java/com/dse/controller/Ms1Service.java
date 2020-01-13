package com.dse.controller;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.MediaType;
import com.dse.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class Ms1Service {
	
	RestTemplate restTemplate;
	HttpHeaders headers;
	HttpEntity<String> entity;
	ResponseEntity<String> response;
	ObjectMapper mapper = new ObjectMapper();
	
	private List<User> users = new ArrayList<>(Arrays.asList(
			new User ("Stefan", "Trifunovic","st.trifunovic@gmail.com" , "Trifko123", new ArrayList<String> (Arrays.asList("999"))),
			new User ("Kristijan", "Matic", "trifko94@hotmail.de", "Kiki123", new ArrayList<String> (Arrays.asList("2222" , "11111"))),
			new User ("Jakub", "Gawrylkowicz","jakub@gmail.com",  "Jakub123", new ArrayList<String> (Arrays.asList("88888"))		
			)));
	
	public List <User> getAllUsers(){
		return users;
	}
	
	public User getUser(String id) {
		Optional<User> user = users.stream().filter(t -> t.getEmail().equals(id)).findFirst();
		if(user.isPresent()) {
			return user.get();
		}
		return null;
	
	}
	
	public HttpStatus addUser(User user) {
		int obj = 5;
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.getEmail().equals(user.getEmail())) {
				System.out.println("User allready exists!");
				obj = 10;
				break;
			}else {
				if(i+1 == users.size() && !u.getEmail().equals(user.getEmail())) {
				users.add(user);
				System.out.println("New user added!");
				break;
				}
			}
		}if(obj == 10) {
			return HttpStatus.NO_CONTENT;
		}else {
			return HttpStatus.OK;
			}
	}
	
	public void updateUser(String id, User user) {
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.getEmail().equals(id)) {
				users.set(i, user);
				return;
			}
		}
	}
	
	public void updateTodoID(String email, String id) {
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.getEmail().equals(email)) {
				if(u.getTodolistsId().size() == 0) {
					System.out.println("todo Id added");
					u.addTodoIDs(id);
					return;
				}
				for(int j = 0; j < u.getTodolistsId().size(); j++) {
					if(!u.getTodolistsId().get(j).equals(id)) {
						System.out.println("todo Id added");
						u.addTodoIDs(id);
						return;
					}
				}	
			}
		}
	}
	
	//checks all users- pull email of all users with authorisation for given ToDo List id
	public ArrayList<String> todoIdCheck(String id) throws JsonProcessingException {
		ArrayList<String> emails = new ArrayList<>();
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
			if(u.getTodolistsId().size() == 0) {
			}else {
				for(int j = 0; j < u.getTodolistsId().size(); j++) {
					if(u.getTodolistsId().get(j).equals(id)) {
						emails.add(u.getEmail());
					}
				}
			}
		}System.out.println(emails);
			return emails;
	}
	//not needed 
	/*//Used to inform ms4 email of all users with authorisation for given ToDo List id
		public void notifyMs4(ArrayList<String> emails) throws JsonProcessingException {
			restTemplate = new RestTemplate();
			headers = new HttpHeaders();
	        headers.setContentType(MediaType.APPLICATION_JSON);
	        entity = new HttpEntity<String>(mapper.writeValueAsString(emails), headers);
	        response = restTemplate.exchange("10.102.107.17:8080/uemails" , HttpMethod.POST, entity, String.class);
	        System.out.println(response.getBody());
		}*/
	        
	public void deleteUser(String id) {
		users.removeIf(t -> t.getEmail().equals(id));
	}
	//verify user- in case that credentials correspond prints given users to do lists id's
	public HttpStatus verifyUser(String email, String password) {
		int verify = 5;
		for (int i = 0; i < users.size(); i++) {
			User u = users.get(i);
				if(u.getEmail().equals(email) && u.getPassword().equals(password)) {
					verify = 10;
					System.out.println("User verified, ToDoLists ID's user is authorised to acces: " + getUser(email).getTodolistsId());
					return HttpStatus.OK;
				}
		}
		if(verify == 5) {
			System.out.println("Credentials error");
		return HttpStatus.NO_CONTENT;
		}else {
			return null;
		}
	}
	public void askM1ForUser(int id) throws JsonProcessingException {
		  RestTemplate restTemplate = new RestTemplate();
		  headers = new HttpHeaders();
		        headers.setContentType(MediaType.APPLICATION_JSON);
		        entity = new HttpEntity<String>("hola", headers);
		        response = restTemplate.exchange("http://10.102.107.17:8080/ha", HttpMethod.POST, entity, String.class);
		        System.out.println(response.getBody());
		 }

	public void deleteTodoID(String email, String id) {
		int temp = -1;
		for(User u: users) {
			if(u.getEmail().equals(email)){
				for (int i=0; i < u.getTodolistsId().size(); i++) {
					if(id.equals(u.getTodolistsId().get(i))) {
						temp = i;
					}
				}
				if (temp != -1) {
					u.getTodolistsId().remove(temp); //jebem mu mater!
				}
			}
		}
	}
}