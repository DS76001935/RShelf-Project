package com.elibrary.rshelfstore.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.elibrary.rshelfstore.model.Notification;
import com.elibrary.rshelfstore.pojos.ResponseEntity;
import com.elibrary.rshelfstore.service.NotificationService;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/rshelfstore/notification")
@CrossOrigin(origins = "*")
@Slf4j
public class NotificationController {

	@Autowired
	private NotificationService notificationService;
	
	@PostMapping("/"
			+ ""
			+ "")
	public ResponseEntity<Notification> saveNotification(@RequestBody Notification notification) throws Exception{
		ResponseEntity<Notification> res = new ResponseEntity<Notification>();
		
		notificationService.saveNotification(notification);
		
		return res;
	}

	@GetMapping("/getNotifications")
	public ResponseEntity<List<Notification>> getAllActiveNotifications(HttpServletRequest request) throws Exception {
		ResponseEntity<List<Notification>> res = new ResponseEntity<List<Notification>>();

		List<Notification> notifications = notificationService.getActiveNotifications();

		if (notifications != null && notifications.size() > 0) {
			res.setData(notifications);
			res.setMessage("Such Active Notifications Retrieved.");
			res.setStatus(200);
			log.info("Active Notifications Retrieved." + notifications);
		} else {
			res.setData(null);
			res.setMessage("No Active Notifications Found!!");
			res.setStatus(-1);
			log.info("No Notifications Found!!");
		}
		return res;
	}
	
	

}
