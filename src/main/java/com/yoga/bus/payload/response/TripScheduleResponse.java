package com.yoga.bus.payload.response;

public class TripScheduleResponse {

	private Long id;
	
	private String tripDate;
	
	private int availableSeats;
	
	private Long tripDetailId;

	public TripScheduleResponse() {
	}

	public TripScheduleResponse(Long id, String tripDate, int availableSeats, Long tripDetailId) {
		this.id = id;
		
		this.tripDate = tripDate;
		
		this.availableSeats = availableSeats;
		
		this.tripDetailId = tripDetailId;
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

	public int getAvailableSeats() {
		return availableSeats;
	}

	public void setTripDetailid(Long tripDetailId) {
		this.tripDetailId = tripDetailId;
	}

	public Long getId() {
		return id;
	}

	public String getTripDate() {
		return tripDate;
	}

	public Long getTripDetailId() {
		return tripDetailId;
	}
}
