package com.yoga.bus.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.bus.models.User;
import com.yoga.bus.repository.UserRepository;
import com.yoga.bus.service.UserService;

@Service
@Transactional
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;

	public User saveUser(User user) {
		userRepository.save(user);
		return user;
	}

	public List<User> getAllUser() {
		return userRepository.findAll();
	}

	public User getUserById(Long id) {
		return userRepository.findById(id).orElse(new User());
	}
	
	public User getUserByUsername(String username) {
		return userRepository.findByUsername(username);
	}

	public void deleteUser(Long id) {
		userRepository.deleteById(id);
	}
}