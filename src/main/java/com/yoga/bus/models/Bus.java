package com.yoga.bus.models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name="bus")
public class Bus {

	public Bus(Long id, String code, int capacity, String make, Agency agency, Set<Trip> trips) {
		super();
		
		this.id = id;
		
		this.code = code;
		
		this.capacity = capacity;
		
		this.make = make;
		
		this.agency = agency;
		
		this.trips = trips;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	private int capacity;
	
	private String make;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@OneToMany(mappedBy = "bus", cascade = CascadeType.ALL)
	private Set<Trip> trips;

	public Bus() {
	}

	public Bus(String code, int capacity, String make, Agency agency) {
		this.code = code;
		this.capacity = capacity;
		this.make = make;
		this.agency = agency;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	public void setMake(String make) {
		this.make = make;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
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

	public int getCapacity() {
		return capacity;
	}
	
	public String getMake() {
		return make;
	}
	
	public Agency getAgency() {
		return agency;
	}

	public Set<Trip> getTrips() {
		return trips;
	}
}