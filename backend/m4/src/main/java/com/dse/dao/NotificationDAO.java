package com.dse.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import com.dse.Notification;


@SuppressWarnings("serial")
public class NotificationDAO {
	private static Map<Integer,Notification> notifications;
	
	static {
		notifications = new HashMap<Integer, Notification>() {
			{
				put(1, new Notification("addTask", new ArrayList<>(Arrays.asList("xxx@gmail.com")) ));
			}
		};
	}
	
	public Notification getNotificationByID(int id) {
		return notifications.get(id);
	}
	
	public void addNewNotification(Notification n) {
		notifications.put(n.getNotificationID(), n);
	}
	
	public void deleteNotificationByID(int id) {
		notifications.remove(id);
	}
	
	public void addReceiverToNotification(int id, String receiver) {
		getNotificationByID(id).addReceiver(receiver);
	}
	
	public void addReceiverToNotification(int id, Collection<String> receiver) {
		getNotificationByID(id).addReceiver(receiver);
	}
	
	public void deleteReceiver(int id, String receiver) {
		for(Iterator<String> iter = getNotificationByID(id).getReceiver().iterator(); iter.hasNext();) {
			String tempReceiver = iter.next();
			if(receiver.equals(tempReceiver)) iter.remove();
		}
		if(!checkNotificationHasReceiver(id)){
			deleteNotificationByID(id);
		}
	}
	
	public boolean checkNotificationHasReceiver(int id) {
		if(getNotificationByID(id).getReceiver().isEmpty()) {
			return false;
		}else {
			return true;
		}
	}
	
	public Collection<Notification> getNotificationsFromReceiver(String user) {
		Map<Integer, Notification> sameReceiver = new HashMap<Integer, Notification>();
		for(Map.Entry<Integer, Notification> entry : notifications.entrySet()) {
			for(String s: entry.getValue().getReceiver()) {
				String[] temp =s.split("\\.");
				if(temp[0].equals(user))
					sameReceiver.put(entry.getKey(), entry.getValue());
			}
		}
		return sameReceiver.values();
	}
	
	public Collection<Notification> getAllNotifications() {
		return notifications.values();
	}

}
