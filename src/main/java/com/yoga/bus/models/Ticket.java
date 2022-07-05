package com.yoga.bus.models;

import javax.persistence.*;

@Entity
@Table(name = "ticket")
public class Ticket {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int seatNumber;
	
	private Boolean cancellable;
	
	private String journeyDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "trip_schedule_id")
	private TripSchedule tripSchedule;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "passenger_id")
	private User passenger;

	public Ticket() {
	}

	public Ticket(int seatNumber, boolean cancellable, String journeyDate,
			TripSchedule tripSchedule, User passenger) {
		this.seatNumber = seatNumber;
		this.cancellable = cancellable;
		this.journeyDate = journeyDate;
		this.tripSchedule = tripSchedule;
		this.passenger = passenger;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}

	public void setCancellable(boolean cancellable) {
		this.cancellable = cancellable;
	}

	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;
	}
	
	public void setTripSchedule(TripSchedule tripSchedule) {
		this.tripSchedule = tripSchedule;
	}

	public void setPassenger(User passenger) {
		this.passenger = passenger;
	}

	public Long getId() {
		return id;
	}

	public int getSeatNumber() {
		return seatNumber;
	}

	public boolean getCancellable() {
		return cancellable;
	}
	
	public String getJourneyDate() {
		return journeyDate;
	}
	
	public TripSchedule getTripSchedule() {
		return tripSchedule;
	}

	public User getPassenger() {
		return passenger;
	}
}
