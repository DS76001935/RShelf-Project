package com.elibrary.rshelfstore.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.model.Notification;
import com.elibrary.rshelfstore.pojos.EmailDetails;
import com.elibrary.rshelfstore.repository.ClientRepository;
import com.elibrary.rshelfstore.repository.NotificationRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class NotificationServiceImpl implements NotificationService {

	@Autowired
	private NotificationRepository notificationRepo;

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private JavaMailSender javaMailSender;
	
	@Value("${spring.mail.username}")
	private String from;
//
//	@Autowired
//	private DomainToDtoClass domainToDtoClass;
//
//	@Autowired
//	private DtoToDomainClass dtoToDomainClass;

	@Override
	@Scheduled(cron = "${cron-job}")
	public void executeJob() throws Exception {

		List<Notification> activeNotifications = new ArrayList<Notification>();

		try {
			List<Notification> list = notificationRepo.findAll().stream()
					.filter((item) -> item.getIsNotified() == 0 && item.getIsDelete() == 0)
					.collect(Collectors.toList());

			List<Client> allRegisteredClients = clientRepo.findAll().stream()
					.filter((item) -> item.getIsActive() == 1 && item.getRole().equalsIgnoreCase("USER"))
					.collect(Collectors.toList());
			
			System.out.println(allRegisteredClients);

			if (list.size() > 0 && allRegisteredClients.size() > 0) {
				for (Notification item : list) {
					for (Client c : allRegisteredClients) {

						EmailDetails obj = new EmailDetails();
						obj.setMsgBody(item.getDescription());
						obj.setRecipient(c.getEmail());
						obj.setSubject(item.getTitle());
						this.sendSimpleMail(obj);
					}
					item.setIsNotified(1);
					notificationRepo.save(item);
					activeNotifications.add(item);
				}
				log.info("Notifications Sent at the given interval => " + new Date());
				log.info("activeNotifications { " + activeNotifications + " } ");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String sendSimpleMail(EmailDetails details) {

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom(from);
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			// Sending the mail
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully...";
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			return "Error while Sending Mail";
		}
	}

	@Override
	public Notification saveNotification(Notification notification) {

		return notificationRepo.save(notification);
	}

	@Override
	public List<Notification> getActiveNotifications() throws Exception {

		return notificationRepo.findAll().stream()
				.filter((item) -> item.getIsNotified() == 1 && item.getIsDelete() == 0).collect(Collectors.toList());
	}

}
