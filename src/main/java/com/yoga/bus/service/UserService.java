package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.User;

public interface UserService {
	List<User> getAllUser();
	
	User saveUser(User user);
	
	User getUserById(Long id);
	
	User getUserByUsername(String username);
	
	void deleteUser(Long id);
}