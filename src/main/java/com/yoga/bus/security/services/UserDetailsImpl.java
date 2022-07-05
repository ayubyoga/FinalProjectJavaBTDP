package com.yoga.bus.security.services;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.yoga.bus.models.User;

public class UserDetailsImpl implements UserDetails {

	private static final long serialVersionUID = 1L;

	private Long id;

	private String email;
	
	private String username;

	private String firstName;

	private String lastName;

	private String mobileNumber;

	@JsonIgnore
	private String password;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl() {
	}

	public UserDetailsImpl(Long id, String username, String password, String email, String firstName, String lastName,
			String mobileNumber, Collection<? extends GrantedAuthority> authorities) {
		this.id = id;

		this.username = username;

		this.password = password;

		this.email = email;

		this.firstName = firstName;

		this.lastName = lastName;

		this.mobileNumber = mobileNumber;

		this.authorities = authorities;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public void setAuthorities(Collection<? extends GrantedAuthority> authorities) {
		this.authorities = authorities;
	}

	public Long getId() {
		return id;
	}

	public String getUsername() {
		return username;
	}

	public String getEmail() {
		return email;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getMobileNumber() {
		return mobileNumber;
	}

	public String getPassword() {
		return password;
	}

	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name())).collect(Collectors.toList());

		return new UserDetailsImpl(user.getId(), user.getUsername(), user.getEmail(), user.getPassword(),
				user.getFirstName(), user.getLastName(), user.getMobileNumber(), authorities);
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
		return true;
	}
}
