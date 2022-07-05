package com.yoga.bus.models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="agency")
public class Agency {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	private String name;
	
	private String details;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "owner_user_id")
	private User owner;

	@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
	private Set<Bus> buses;

	@OneToMany(mappedBy = "agency", cascade = CascadeType.ALL)
	private Set<Trip> trips;
	
	public Agency() {
	}
	
	public Agency(String code, String name, String details, User owner) {
		this.code = code;
		this.name = name;
		this.details = details;
		this.owner = owner;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public void setCode(String code) {
		this.code = code;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}

	public void setOwner(User owner) {
		this.owner = owner;
	}
	
	public void setTrips(Set<Trip> trips) {
		this.trips = trips;
	}
	
	public Long getId() {
		return id;
	}

	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDetails() {
		return details;
	}
	
	public User getOwner() {
		return owner;
	}

	public Set<Bus> getBuses() {
		return buses;
	}
	
	public Set<Trip> getTrips() {
		return trips;
	}
}
