package com.dse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.atomic.AtomicInteger;

public class Notification {

	  private static final AtomicInteger count = new AtomicInteger(0);
	  private int notificationID;
	  private String message;
	  private ArrayList<String> receiver= new ArrayList<>();

	  public Notification (String content, ArrayList<String> receiver) {
	    this.message = content;
	    this.receiver = receiver;
	    this.notificationID = count.incrementAndGet(); 
	  }
	  

	  public String getMessage() {
	    return message;
	  }
	  
	  public ArrayList<String> getReceiver(){
		  return receiver;
	  }
	  
	  public void addReceiver(String user) {
		  receiver.add(user);
	  }
	  
	  public void addReceiver(Collection<String> user) {
		  receiver.addAll(user);
	  }
	  
	  public int getNotificationID() {
		  return notificationID;
	  }

	}