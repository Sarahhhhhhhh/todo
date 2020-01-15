package com.dse.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import org.springframework.web.client.RestTemplate;

import com.dse.Notification;
import com.dse.dao.NotificationDAO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;



@Service
public class NotificationService {
	
	JavaMailSender javaMailSender;
	
	@Autowired
	public NotificationService(JavaMailSender jms) {
		this.javaMailSender = jms;
	}
	ObjectMapper mapper = new ObjectMapper();
	
	NotificationDAO dao = new NotificationDAO();
	RestTemplate restTemplate;
	HttpHeaders headers;
	HttpEntity<String> entity;
	ResponseEntity<String> response;
	
	
	public Collection<Notification> getNotificationsFromReceiver(String user){
		return dao.getNotificationsFromReceiver(user);
	}
	
	public Collection<Notification> getAllNotifications(){
		return dao.getAllNotifications();
	}
	
	public Notification getNotificationByID(int id) {
		return dao.getNotificationByID(id);
	}
	
	public void newNotification(Notification n) {
		dao.addNewNotification(n);
	}
	public void addReceiverToNotification(int id, String receiver) {
		dao.addReceiverToNotification(id, receiver);
	}
	//Get all USER-Emails which have access to a list
	public ArrayList<String> askM1ForUser(String id) throws JsonProcessingException {
		ArrayList<String> ret = null;
		
		restTemplate = new RestTemplate();
		headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        entity = new HttpEntity<String>("hello", headers);
        try {
        	response = restTemplate.exchange("http://10.102.107.13:8080/uemails/"+id, HttpMethod.POST, entity, String.class);
        	ret =  new ArrayList<String> (Arrays.asList((response.getBody().split(","))));
      
        }catch(Exception e) {
        	System.err.println("MS1 is not available!!!");
        }
        
        return ret; 
	}
	// Send Notification as EMAIL
	public void sendNotification(String listID, String message, ArrayList<String> ar) throws MailException{
		for(String s: ar) {
			SimpleMailMessage mail = new SimpleMailMessage();
			mail.setTo(s);
			mail.setSubject("DSE Notification!!!");
			mail.setFrom("st.trifunovic@gmail.com");
			mail.setText("Notification!!!\n\n"
					+ "An ihrer Todo-Liste mit der ID " + listID + " wurde\n"
							+ "folgende Änderung durchgeführt: ### "+ message+ " ###");
			javaMailSender.send(mail);
			System.out.println("Mail sent to "+ s);
		}
	}

}
