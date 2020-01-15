package com.dse.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.dse.Notification;
import com.dse.ResponseNotify;
import com.dse.service.NotificationService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@RestController
public class NotificationController {
	
	ObjectMapper mapper = new ObjectMapper();
	RestTemplate restTemplate;
	HttpHeaders headers;
	HttpEntity<String> entity;
	ResponseEntity<String> response;
	
	@Autowired
	private NotificationService service;
	
	@RequestMapping(value = "/notifications", method = RequestMethod.GET)
	public Collection<Notification> getAllNotification() {
		return service.getAllNotifications();
	}
	
	@RequestMapping(value = "/notifications/{user}", method = RequestMethod.GET)
	public Collection<Notification> getListsWithSameUser(@PathVariable("user") String user) {
		return service.getNotificationsFromReceiver(user);
	}
	
	@RequestMapping(value = "/notifications/add/{user}/{notificationid}", method = RequestMethod.GET)
	public void addReceiverToNotification(@PathVariable("user") String user, @PathVariable("notificationid") int id) {
		service.addReceiverToNotification (id, user);
	}
	
	@RequestMapping(value = "/notification/by/{id}", method = RequestMethod.GET)
	public Notification getNotificationByID(@PathVariable("id") int id) {
		
		return service.getNotificationByID(id);
	}
	
	@RequestMapping(value = "/new/noti/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void newNotification(@RequestBody Notification noti) {	
		service.newNotification(noti);
	}
	
	/*
	@RequestMapping(value = "/ha", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public String ha(@RequestBody String s) throws JsonProcessingException{
		return s;
	}
	@RequestMapping(value = "/", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void home(@RequestBody String id) throws JsonProcessingException{
		service.askM1ForUser(id);
	}
	@RequestMapping(value = "/uemails", method = RequestMethod.POST , consumes = MediaType.APPLICATION_JSON_VALUE)
	public ArrayList<String> uemails(@RequestBody ArrayList<String> s) throws JsonProcessingException{
		System.out.println(s);
		return s;
	}
	*/
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home() {
		
		return "home";
	}
	
	
	//Will be activated by MS2 at any Todolist changes and send Notification as Email
	@RequestMapping(value = "/notify", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public void notify(@RequestBody final ResponseNotify s) throws JsonProcessingException{	
		ArrayList<String> ar = service.askM1ForUser(s.getListID());
		if (ar != null && !ar.isEmpty()) {
			Notification n = new Notification(s.getMessage(), ar);
			service.newNotification(n);
			try {
				service.sendNotification(s.getListID(), s.getMessage(), ar);
				System.err.println("Alle Emails vesendet!!!");
			} catch (MailException me) {
				System.err.println("An email could not be sent: " + me.getMessage());
			}
		}else {
			System.err.println("Array is null");
		}
       
	}
	
	
	//For Testing Todolist changes and send Notification as Email
		@RequestMapping(value = "/notifytest", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
		public void notifyTest(@RequestBody final ResponseNotify s) throws JsonProcessingException{	
			ArrayList<String> ar = new ArrayList<>(Arrays.asList("trifko94@hotmail.de"));
			if (ar != null && !ar.isEmpty()) {
				Notification n = new Notification(s.getMessage(), ar);
				service.newNotification(n);
				try {
					service.sendNotification(s.getListID(), s.getMessage(), ar);
					System.err.println("Alle Emails vesendet!!!");
				} catch (MailException me) {
					System.err.println("An email could not be sent: " + me.getMessage());
				}
			}else {
				System.err.println("Array is null");
			}
	       
		}

	
	
	@RequestMapping(value = "/test", method = RequestMethod.POST)
	public String test() {
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("hello", headers);
        response = restTemplate.exchange("http://10.102.107.13:8080/test", HttpMethod.POST, entity, String.class);
        System.out.println(response.getBody() + " ajdeee breeee");
        return response.getBody()+ "napokom";
	}


}
