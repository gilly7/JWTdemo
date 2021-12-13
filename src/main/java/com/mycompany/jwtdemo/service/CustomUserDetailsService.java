package com.mycompany.jwtdemo.service;

import java.util.ArrayList;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service

public class CustomUserDetailsService implements UserDetailsService {

	//this method does validation of user existence
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		// TODO Auto-generated method stub
		
		if(userName.equals("John")) { //here you can make a call with the help of a repository and do the validation
			return new User("John","secret", new ArrayList<>());
			
		} else {
			throw new UsernameNotFoundException("User does not exist");
		}
		
	}

}
