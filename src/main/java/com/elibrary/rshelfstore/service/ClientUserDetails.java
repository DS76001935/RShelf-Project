package com.elibrary.rshelfstore.service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.elibrary.rshelfstore.model.Client;

@Component
public class ClientUserDetails implements UserDetails {

	private static final long serialVersionUID = 1070787195170009370L;

	public ClientUserDetails() {
	}

	Client client = new Client();

	public ClientUserDetails(Client client) {
		this.client = client;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		String role = client.getRole();
		List<SimpleGrantedAuthority> authorities = new ArrayList<>();
		Set<String> roles = new HashSet<String>();
		roles.add(role);

		roles.forEach((item) -> authorities.add(new SimpleGrantedAuthority(item)));
		return authorities;
	}

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return client.getPassword();
	}

	@Override
	public String getUsername() {
		// TODO Auto-generated method stub
		return client.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return client.getIsActive() == 1 ? true : false;
	}

}
