package com.cuponservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.cuponservice.mode.User;
import com.cuponservice.repository.UserRepo;

@Service
public class UserDetailServiceImp implements UserDetailsService{
    
	@Autowired
	UserRepo  userRepo;
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = userRepo.findByEmail(username);
		if(user == null)
		{
			throw new UsernameNotFoundException("User not for this Email" + user.getEmail());
		}
		return new org.springframework.security.core.userdetails.User(user.getEmail(),user.getPassword(), user.getRoles()) ;
	}

}
