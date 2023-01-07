package com.elibrary.rshelfstore.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.elibrary.rshelfstore.event.RegistrationCompleteEvent;
import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.pojos.FilterOptions;
import com.elibrary.rshelfstore.pojos.PasswordModel;
import com.elibrary.rshelfstore.pojos.ResponseEntity;
import com.elibrary.rshelfstore.pojos.TokenResponse;
import com.elibrary.rshelfstore.pojos.UserModel;
import com.elibrary.rshelfstore.service.ClientService;
import com.elibrary.rshelfstore.service.JWTUtility;
import com.elibrary.rshelfstore.service.JwtUserDetailsService;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/rshelfstore/client")
@CrossOrigin(origins = "*")
@Slf4j
public class ClientController {

	@Autowired
	private ClientService clientService;

	@Autowired
	private ApplicationEventPublisher publisher;

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private JwtUserDetailsService jwtUserDetailsService;

	@Autowired
	private JWTUtility jwtUtility;
	
	@PostMapping("/save")
	public ResponseEntity<Client> saveClient(@RequestBody Client client, HttpServletRequest request) throws Exception {
		ResponseEntity<Client> res = new ResponseEntity<Client>();

		Client checkUsername = clientService.checkUsername(client.getUsername());

		if (checkUsername == null) {
			Client c = clientService.saveClient(client);
			publisher.publishEvent(new RegistrationCompleteEvent(c, applicationUrl(request)));

			if (c != null) {
				res.setData(c);
				res.setMessage("Client has been saved successfully in the system!");
				res.setStatus(200);
			} else {
				res.setData(null);
				res.setMessage("!! Somthing went wrong in the client registration!");
				res.setStatus(-1);
			}
		} else {
			res.setData(null);
			res.setMessage("!! Sorry, this client is already exist !!");
			res.setStatus(-1);
		}

		return res;
	}

	@GetMapping("/all")
	public ResponseEntity<List<Client>> getAllClients() throws Exception {
		ResponseEntity<List<Client>> res = new ResponseEntity<>();
		List<Client> clients = clientService.getAllClients();
		if (clients.size() > 0) {
			res.setData(clients);
			res.setMessage("Data has beed fetched successfully...");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("No Records Found!");
			res.setStatus(-1);
		}
		return res;
	}

	@GetMapping("/getById")
	public ResponseEntity<List<Client>> getClientById(@RequestParam("clientId") Long clientId) throws Exception {
		ResponseEntity<List<Client>> res = new ResponseEntity<>();
		List<Client> clientList = clientService.getById(clientId);
		if (clientList.size() > 0) {
			res.setData(clientList);
			res.setMessage("Client has been catched successfully...");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("Invalid Id!");
			res.setStatus(-1);
		}
		return res;
	}

	@PutMapping("/update")
	public ResponseEntity<Client> updateClient(@RequestBody Client c) throws Exception {
		ResponseEntity<Client> res = new ResponseEntity<>();
		Client client = clientService.updateClient(c);
		if (client != null) {
			res.setData(client);
			res.setMessage("Client has been updated successfully.");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("Invalid User!");
			res.setStatus(-1);
		}
		return res;
	}

	@DeleteMapping("/delete")
	public ResponseEntity<Client> deleteClient(@RequestParam("clientId") Long clientId) throws Exception {
		ResponseEntity<Client> res = new ResponseEntity<>();
		Client c = clientService.deleteClient(clientId);
		if (c != null) {
			res.setData(c);
			res.setMessage("Client has been deleted successfully.");
			res.setStatus(200);
		} else {
			res.setData(null);
			res.setMessage("Invalid Id!");
			res.setStatus(-1);
		}
		return res;
	}

	@PostMapping("/search")
	public ResponseEntity<List<Client>> searchClient(@RequestBody FilterOptions myFilters) throws Exception {
		ResponseEntity<List<Client>> res = new ResponseEntity<>();
		if (myFilters.getFilterValue() != null) {
			List<Client> myClients = clientService.searchClient(myFilters.getFilterValue());
			if (myClients.size() > 0) {
				res.setData(myClients);
				res.setMessage("Data has been found successfully!");
				res.setStatus(200);
			} else {
				res.setData(null);
				res.setMessage("Invalid Keyword!");
				res.setStatus(-1);
			}
		}
		return res;
	}

	@GetMapping("/verifyRegistration")
	public ResponseEntity<String> verifyRegistration(@RequestParam("token") String token) {
		ResponseEntity<String> res = new ResponseEntity<String>();
		String result = clientService.validateVerificationToken(token);
		if (result.equalsIgnoreCase("Verified")) {
			res.setMessage("User Verified Successfully");
			return res;
		}
		res.setMessage("Bad User!");
		return res;
	}

	@PostMapping("/resendVerifyToken")
	public ResponseEntity<String> resendVerificationToken(@RequestBody Map<String, Object> requestParams,
			HttpServletRequest request) {
		ResponseEntity<String> res = new ResponseEntity<String>();
		clientService.generateNewVerificationToken(requestParams, request);
		res.setMessage("Verification Link Sent");
		return res;
	}

	@PostMapping("/resetPassword")
	public ResponseEntity<String> resetPassword(@RequestBody PasswordModel passwordModel, HttpServletRequest request) {
		ResponseEntity<String> res = new ResponseEntity<String>();

		Client client = clientService.findClientByEmail(passwordModel.getEmail());
		String url = "";
		if (client != null) {
			String token = UUID.randomUUID().toString();
			clientService.createPasswordResetTokenForUser(client, token);
			url = passwordResetTokenMail(client, applicationUrl(request), token);
		}
		res.setData(url);
		return res;
	}

	@PostMapping("/savePassword")
	public String savePassword(@RequestParam("token") String token, @RequestBody PasswordModel passwordModel) {
		String result = clientService.validatePasswordResetToken(token);
		if (!result.equalsIgnoreCase("Verified")) {
			return "Invalid Token";
		}
		Optional<Client> client = clientService.getUserByPasswordResetToken(token);
		if (client.isPresent()) {
			clientService.changePassword(client.get(), passwordModel.getNewPassword());
			return "Password Reset Successfully";
		} else {
			return "Invalid Token";
		}
	}

	@PostMapping("/changePassword")
	public String changePassword(@RequestBody PasswordModel passwordModel) {
		Client client = clientService.findClientByEmail(passwordModel.getEmail());
		if (!clientService.checkIfValidOldPassword(client, passwordModel.getOldPassword())) {
			return "Invalid Old Password";
		}
		// Save New Password
		clientService.changePassword(client, passwordModel.getNewPassword());
		return "Password Changed Successfully";
	}

	private String passwordResetTokenMail(Client client, String applicationUrl, String token) {
		String url = applicationUrl + "/rshelfstore/client/savePassword?token=" + token;

		// sendVerificationEmail()
		log.info("Click the link to Reset your Password: {}", url);
		return url;
	}

//	private void resendVerificationTokenMail(Client client, String applicationUrl,
//			VerificationToken verificationToken) {
//		String url = applicationUrl + "/rshelfstore/client/verifyRegistration?token=" + verificationToken.getToken();
//
//		// sendVerificationEmail()
//		log.info("Click the link to verify your account: {}", url);
//	}

	private String applicationUrl(HttpServletRequest request) {
		return "http://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath();
	}

	@PostMapping("/authenticate")
	public ResponseEntity<TokenResponse> generateAuthenticationToken(@RequestBody UserModel userModel)
			throws Exception {
		ResponseEntity<TokenResponse> res = new ResponseEntity<TokenResponse>();
		TokenResponse tokenRes = new TokenResponse();

		authenticate(userModel.getUsername(), userModel.getOldPassword());

		final UserDetails userDetails = jwtUserDetailsService.loadUserByUsername(userModel.getUsername());
		final String token = jwtUtility.generateToken(userDetails);

		tokenRes.setToken(token);

		res.setData(tokenRes);
		res.setMessage("---Token Generated---");
		res.setStatus(200);
		return res;
	}

	private void authenticate(String username, String password) throws Exception {
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		} catch (DisabledException e) {
			throw new Exception("USER_DISABLED", e);
		} catch (BadCredentialsException e) {
			throw new Exception("INVALID_CREDENTIALS", e);
		}
	}
}
