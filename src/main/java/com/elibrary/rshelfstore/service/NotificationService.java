package com.elibrary.rshelfstore.service;

import java.util.List;

import com.elibrary.rshelfstore.model.Notification;

public interface NotificationService {
	
	public void executeJob() throws Exception;
	public List<Notification> getActiveNotifications() throws Exception;
	public Notification saveNotification(Notification notification);

}
