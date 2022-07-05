package com.yoga.bus.models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "trip_schedule")
public class TripSchedule {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String tripDate;
	
	private int availableSeats;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_id")
	private Trip tripDetail;

	@OneToMany(mappedBy = "tripSchedule", cascade = CascadeType.ALL)
	private Set<Ticket> ticketsSold;

	public TripSchedule() {
	}

	public TripSchedule(String tripDate, int availableSeats, Trip tripDetail) {
		this.tripDate = tripDate;
		this.availableSeats = availableSeats;
		this.tripDetail = tripDetail;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setTripDate(String tripDate) {
		this.tripDate = tripDate;
	}
	
	public void setAvailableSeats(int availableSeats) {
		this.availableSeats = availableSeats;
	}

	public void setTripDetail(Trip tripDetail) {
		this.tripDetail = tripDetail;
	}

	public void setTripDetail(Set<Ticket> ticketsSold) {
		this.ticketsSold = ticketsSold;
	}

	public Long getId() {
		return id;
	}

	public String getTripDate() {
		return tripDate;
	}
	
	public int getAvailableSeats() {
		return availableSeats;
	}
	
	public Trip getTripDetail() {
		return tripDetail;
	}
	
	public Set<Ticket> getTicketsSold() {
		return ticketsSold;
	}
}
