package com.elibrary.rshelfstore.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.elibrary.rshelfstore.model.Client;
import com.elibrary.rshelfstore.repository.ClientRepository;

@Service
public class JwtUserDetailsService implements UserDetailsService {

	@Autowired
	private ClientRepository clientRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Client client = clientRepository.findClientByUsername(username);
		
		if(client == null) throw new UsernameNotFoundException("User is not valid!");
		
		ClientUserDetails clientDetails = new ClientUserDetails(client);
		
		return clientDetails;
	}

}
