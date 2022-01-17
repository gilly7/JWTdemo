package com.mycompany.jwtdemo.service;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.mycompany.jwtdemo.entity.UserEntity;
import com.mycompany.jwtdemo.model.UserModel;
import com.mycompany.jwtdemo.repository.UserRepository;


@Service

public class CustomUserDetailService implements UserDetailsService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;

	//this method does validation of user existence
	
	
	public UserModel register(UserModel userModel) {
		UserEntity userEntity = new UserEntity();
		BeanUtils.copyProperties(userModel, userEntity);
		userEntity.setPassword(this.passwordEncoder.encode(userEntity.getPassword()));
		
		userEntity = userRepository.save(userEntity);
		
		BeanUtils.copyProperties(userEntity, userModel);
		return userModel;
	}
	
	
	@Override
	public UserDetails loadUserByUsername(String userName) throws UsernameNotFoundException {
		
		// TODO Auto-generated method stub
		
	UserEntity userEntity =	userRepository.findByUsername(userName);
		
		if(userEntity == null) { //here you can make a call with the help of a repository and do the validation
			//return new User("John","secret", new ArrayList<>());
			throw new UsernameNotFoundException("User does not exist");
		}	
	UserModel userModel = new UserModel();
	
	BeanUtils.copyProperties(userEntity, userModel);
	
	return userModel;
			
		
	}

}
