package com.elibrary.rshelfstore.event.listener;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.elibrary.rshelfstore.event.RegistrationCompleteEvent;
import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.pojos.EmailDetails;
import com.elibrary.rshelfstore.service.ClientService;

import java.util.UUID;

@Component
@Slf4j
public class RegistrationCompleteEventListener implements ApplicationListener<RegistrationCompleteEvent> {

	@Autowired
	private ClientService clientService;

	@Autowired
	private JavaMailSender javaMailSender;

	@Override
	public void onApplicationEvent(RegistrationCompleteEvent event) {
		// Create the Verification Token for the User with Link
		EmailDetails emailDetails = new EmailDetails();
		Client client = event.getClient();
		String token = UUID.randomUUID().toString();
		clientService.saveVerificationTokenForUser(token, client);
		System.out.println("Saved to user_verification table...");
		// Send Mail to user
		String url = event.getApplicationUrl() + "/rshelfstore/client/verifyRegistration?token=" + token;

		// sendVerificationEmail()

		emailDetails.setMsgBody("Click the link to verify your account: { " + url + " }");
		emailDetails.setSubject("User Account Verfication Call!");
		emailDetails.setAttachment(null);
		emailDetails.setRecipient(client.getEmail());

		String res = this.sendSimpleMail(emailDetails);

		log.info(res);
	}

	public String sendSimpleMail(EmailDetails details) {

		// Try block to check for exceptions
		try {

			// Creating a simple mail message
			SimpleMailMessage mailMessage = new SimpleMailMessage();

			// Setting up necessary details
			mailMessage.setFrom("${spring.mail.username}");
			mailMessage.setTo(details.getRecipient());
			mailMessage.setText(details.getMsgBody());
			mailMessage.setSubject(details.getSubject());

			// Sending the mail
			javaMailSender.send(mailMessage);
			return "Mail Sent Successfully to the respective client's id => " + details.getRecipient();
		}

		// Catch block to handle the exceptions
		catch (Exception e) {
			return "Error while Sending Mail" + e;
		}
	}
}
