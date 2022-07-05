package com.yoga.bus.controller.rest;

import java.util.HashSet;
import java.util.Set;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import io.swagger.annotations.*;
import org.springframework.security.access.prepost.PreAuthorize;

import com.yoga.bus.models.*;
import com.yoga.bus.payload.request.*;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.repository.*;
import com.yoga.bus.security.jwt.JwtUtils;
import com.yoga.bus.service.impl.UserServiceImpl;

@CrossOrigin(origins = "*", maxAge = 3600, methods = { RequestMethod.PUT, RequestMethod.POST, RequestMethod.GET })
@RestController
@RequestMapping("/api/v1/user")
public class UserController {

	@Autowired
	AuthenticationManager authenticationManager;

	@Autowired
	UserRepository userRepository;

	@Autowired
	RoleRepository roleRepository;

	@Autowired
	PasswordEncoder encoder;

	@Autowired
	AgencyRepository agencyRepository;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	JwtUtils jwtUtils;

	@PostMapping("/signup")
	public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {

		String email = signupRequest.getEmail();
		String username = signupRequest.getUsername();
		String firstName = signupRequest.getFirstName();
		String lastName = signupRequest.getLastName();
		String mobileNumber = signupRequest.getMobileNumber();
		String password = signupRequest.getPassword();
		Set<String> strRoles = signupRequest.getRole();

		if (email == null || username == null || firstName == null || lastName == null || mobileNumber == null
				|| password == null || strRoles == null || username.equals("") || email.equals("")
				|| firstName.equals("") || lastName.equals("") || strRoles.size() == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if (userRepository.existsByUsername(signupRequest.getUsername())) {
			Response errorResponse = new Response("400", "Bad Request",
					"Username sudah digunakan, silahkan gunakan username lain");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if (userRepository.existsByEmail(signupRequest.getEmail())) {
			Response errorResponse = new Response("400", "Bad Request",
					"Email sudah digunakan, silahkan gunakan email lain");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		User user = new User(signupRequest.getFirstName(), signupRequest.getLastName(), signupRequest.getMobileNumber(),
				signupRequest.getUsername(), signupRequest.getEmail(), encoder.encode(signupRequest.getPassword()));

		Set<Role> roles = new HashSet<>();

		for (String item : strRoles) {
			if (item.equalsIgnoreCase("user") || item.equalsIgnoreCase("role_user")) {
				Role userRole = roleRepository.findById(1);
				roles.add(userRole);
			} else if (item.equalsIgnoreCase("admin") || item.equalsIgnoreCase("admin")) {
				Role adminRole = roleRepository.findById(2);
				roles.add(adminRole);
			} else {
				Response errorResponse = new Response("400", "Bad Request", "Role tidak ada");
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
			}
		}

		user.setRoles(roles);

		userRepository.save(user);

		Response successResponse = new Response("200", "OK", "User berhasil didaftarkan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@PutMapping("/update")
	@ApiOperation(value = "update", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
	public ResponseEntity<?> updateUser(@Valid @RequestBody UpdateUserRequest updateUserRequest) {

		String firstName = updateUserRequest.getFirstName();
		String lastName = updateUserRequest.getLastName();
		String mobileNumber = updateUserRequest.getMobileNumber();

		if (firstName == null || lastName == null || mobileNumber == null || firstName.equals("") || lastName.equals("")
				|| mobileNumber.equals("")) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();

		User user = userServiceImpl.getUserByUsername(username);

		if (user == null) {
			Response errorResponse = new Response("404", "Not Found", "Data tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		user.setFirstName(firstName);
		user.setLastName(lastName);
		user.setMobileNumber(mobileNumber);

		userRepository.save(user);

		Response successResponse = new Response("200", "OK", "Data berhasil diupdate");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}
}
