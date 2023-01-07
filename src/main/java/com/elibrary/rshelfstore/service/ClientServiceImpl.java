package com.elibrary.rshelfstore.service;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.event.RegistrationCompleteEvent;
import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.model.PasswordResetToken;
import com.elibrary.rshelfstore.model.VerificationToken;
import com.elibrary.rshelfstore.repository.ClientRepository;
import com.elibrary.rshelfstore.repository.PasswordResetTokenRepository;
import com.elibrary.rshelfstore.repository.VerificationRepository;

@Service
public class ClientServiceImpl implements ClientService {

	@Autowired
	private ClientRepository clientRepo;

	@Autowired
	private PasswordEncoder passwordEncoder;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;

	@Autowired
	private VerificationRepository verificationRepository;

	@Override
	public List<Client> getAllClients() throws Exception {
		return clientRepo.findAll().stream().filter(data -> data.getIsDelete() == 0).collect(Collectors.toList());
	}

	@Override
	public List<Client> getById(Long clientId) throws Exception {
		return clientRepo.findById(clientId).stream()
				.filter(data -> data.getIsDelete() == 0 && data.getClientId() == clientId).collect(Collectors.toList());
	}

	@Override
	public Client updateClient(Client client) throws Exception {
		Client c = null;
		if (client.getClientId() != null) {
			Client myClient = clientRepo.getById(client.getClientId());
			client.setCreatedOn(myClient.getCreatedOn());
			client.setUsername(myClient.getUsername());
			client.setPassword(myClient.getPassword());
			client.setLastModifiedOn(new Date());
			c = clientRepo.save(client);
		}
		return c;
	}

	public Client deleteClient(Long clientId) throws Exception {
		Client client = null;
		if (clientId != null) {
			client = clientRepo.getById(clientId);
			client.setIsActive(0);
			client.setIsDelete(1);
			clientRepo.save(client);
		}
		return client;
	}

	public List<Client> searchClient(String keyword) throws Exception {
		return clientRepo.findClientByName(keyword.toLowerCase());
	}

	@Bean
	public PasswordEncoder getPasswordEncoder() {
		return new BCryptPasswordEncoder(11);
	}

	@Override
	public Client saveClient(Client client) {
		client.setPassword(passwordEncoder.encode(client.getPassword()));
//		client.setCreatedOn(new Date());
//		client.setLastModifiedOn(new Date());
		Client c = clientRepo.save(client);
		return c;
	}

	@Override
	public void saveVerficationTokenForClient(String token, Client client) {
		VerificationToken verificationToken = new VerificationToken(client, token);
		verificationRepository.save(verificationToken);
	}

	@Override
	public void saveVerificationTokenForUser(String token, Client client) {
		VerificationToken verificationToken = new VerificationToken(client, token);

		verificationRepository.save(verificationToken);
	}

	@Override
	public String validateVerificationToken(String token) {
		VerificationToken verificationToken = verificationRepository.findVerificationTokenByToken(token);

		if (verificationToken == null)
			return "Invalid";

		Client client = verificationToken.getClient();
		Calendar cal = Calendar.getInstance();

		if ((verificationToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
			verificationRepository.delete(verificationToken);
			return "Expired";
		}
		client.setIsActive(1);
		clientRepo.save(client);
		return "Verified";
	}

	@Override
	public String validatePasswordResetToken(String token) {
		PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByToken(token);

		if (passwordResetToken == null) {
			return "invalid";
		}
		Calendar cal = Calendar.getInstance();

		if ((passwordResetToken.getExpirationTime().getTime() - cal.getTime().getTime()) <= 0) {
			passwordResetTokenRepository.delete(passwordResetToken);
			return "expired";
		}

		return "Verified";
	}

	@Override
	public Optional<Client> getUserByPasswordResetToken(String token) {
		return Optional.ofNullable(passwordResetTokenRepository.findByToken(token).getClient());
	}

	@Override
	public void changePassword(Client client, String newPassword) {
		client.setPassword(passwordEncoder.encode(newPassword));
		clientRepo.save(client);
	}

	@Override
	public boolean checkIfValidOldPassword(Client client, String oldPassword) {
		return passwordEncoder.matches(oldPassword, client.getPassword());
	}

	@Override
	public void generateNewVerificationToken(Map<String, Object> requestParams, HttpServletRequest request) {
		if (requestParams.get("oldToken") != null) {
			VerificationToken verificationToken = verificationRepository
					.findVerificationTokenByToken(String.valueOf(requestParams.get("oldToken")));
			if (verificationToken != null) {
				Client c = verificationToken.getClient();
				verificationRepository.delete(verificationToken);
				applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(c, applicationUrl(request)));
			}
		}
		if (requestParams.get("clientUsername") != null) {
			Client cObj = clientRepo.findClientByUsername(String.valueOf(requestParams.get("clientUsername")));
			if (cObj != null) {
				List<VerificationToken> verificationToken = verificationRepository
						.findAllVerificationTokenByClient(cObj);
				verificationRepository.deleteAll(verificationToken);
				applicationEventPublisher.publishEvent(new RegistrationCompleteEvent(cObj, applicationUrl(request)));
			}
		}
	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@Override
	public void createPasswordResetTokenForUser(Client client, String token) {
		PasswordResetToken passwordResetToken = new PasswordResetToken(client, token);
		passwordResetTokenRepository.save(passwordResetToken);
	}

	@Override
	public Client findClientByEmail(String email) {
		return clientRepo.findClientByEmail(email);
	}

	@Override
	public Client checkUsername(String username) {
		Client clientObj = clientRepo.findClientByUsername(username);
		System.out.println(clientObj);
		return clientObj = (clientObj != null ? clientObj : null);
	}

}
