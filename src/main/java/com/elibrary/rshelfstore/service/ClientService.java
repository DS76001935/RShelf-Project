package com.elibrary.rshelfstore.service;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.servlet.http.HttpServletRequest;

import com.elibrary.rshelfstore.model.Client;

public interface ClientService {

	List<Client> getAllClients() throws Exception;

	List<Client> getById(Long clientId) throws Exception;

	Client updateClient(Client client) throws Exception;

	Client deleteClient(Long clientId) throws Exception;

	List<Client> searchClient(String keyword) throws Exception;

	Client saveClient(Client client);

	void saveVerficationTokenForClient(String token, Client client);

	String validateVerificationToken(String token);

	void saveVerificationTokenForUser(String token, Client client);

	Client findClientByEmail(String email);

	Optional<Client> getUserByPasswordResetToken(String token);

	boolean checkIfValidOldPassword(Client client, String oldPassword);

	void changePassword(Client client, String newPassword);

	String validatePasswordResetToken(String token);

	void generateNewVerificationToken(Map<String, Object> requestParams, HttpServletRequest request);

	void createPasswordResetTokenForUser(Client client, String token);
	
	Client checkUsername(String username);
}
