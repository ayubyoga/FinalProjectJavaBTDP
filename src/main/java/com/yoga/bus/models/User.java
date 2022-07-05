package com.yoga.bus.models;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
@Table(name = "user_info", uniqueConstraints = { @UniqueConstraint(columnNames = "username"),
		@UniqueConstraint(columnNames = "email") })
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank
	@Size(max = 50)
	private String username;

	@NotBlank
	@Size(max = 50)
	@Email
	private String email;

	@NotBlank
	@Size(max = 120)
	@JsonIgnore
	private String password;

	@NotBlank
	@Size(max = 120)
	private String firstName;

	@NotBlank
	@Size(max = 120)
	private String lastName;

	@NotBlank
	@Size(max = 120)
	private String mobileNumber;

	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(name = "user_roles", joinColumns = @JoinColumn(name = "user_id"), inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "owner", cascade = CascadeType.ALL)
	private List<Agency> agencies;

	@OneToMany(mappedBy = "passenger", cascade = CascadeType.ALL)
	private Set<Ticket> tickets;

	public User() {
	}

	public User(String firstName, String lastName, String mobileNumber, String username, String email,
			String password) {
		this.email = email;

		this.username = username;

		this.password = password;

		this.firstName = firstName;

		this.lastName = lastName;

		this.mobileNumber = mobileNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	public void setAgencies(List<Agency> agencies) {
		this.agencies = agencies;
	}

	public void setTickets(Set<Ticket> tickets) {
		this.tickets = tickets;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getUsername() {
		return username;
	}

	public String getPassword() {
		return password;
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

	public Set<Role> getRoles() {
		return roles;
	}

	public List<Agency> getAgencies() {
		return agencies;
	}

	public Set<Ticket> getTickets() {
		return tickets;
	}
}
