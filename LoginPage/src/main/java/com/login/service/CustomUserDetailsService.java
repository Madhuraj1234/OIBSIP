package com.login.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.login.entities.User;
import com.login.repository.FormRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	@Autowired
	private FormRepository formRepository;
	
	@Override
	public UserDetails loadUserByUsername(String username) 
			throws UsernameNotFoundException
	{
		User user = this.formRepository.getUserByUsername(username);
		
		if(user==null)
		{
			throw new UsernameNotFoundException("User not found");
		}
		return new CustomUserDetails(user);
	}

}
