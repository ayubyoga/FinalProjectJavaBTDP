package com.yoga.bus.payload.response;

public class TicketResponse {

	private Long id;
	
	private int seatNumber;
	
	private Boolean cancellable;
	
	private String journeyDate;
	
	private Long passengerId;
	
	private Long tripScheduleId;

	public TicketResponse() {
	}

	public TicketResponse(Long id, int seatNumber, Boolean cancellable, String journeyDate,
			Long passengerId, Long tripScheduleId) {
		this.id = id;
		
		this.seatNumber = seatNumber;
		
		this.cancellable = cancellable;
		
		this.journeyDate = journeyDate;
		
		this.passengerId = passengerId;
		
		this.tripScheduleId = tripScheduleId;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setSeatNumber(int seatNumber) {
		this.seatNumber = seatNumber;
	}
	
	public void setCancellable(Boolean cancellable) {
		this.cancellable = cancellable;
	}
	
	public void setJourneyDate(String journeyDate) {
		this.journeyDate = journeyDate;;
	}
	
	public void setPassengerId(Long passengerId) {
		this.passengerId = passengerId;
	}
	
	public void setTripScheduleId(Long tripScheduleId) {
		this.tripScheduleId = tripScheduleId;
	}

	public Long getId() {
		return id;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public String getJourneyDate() {
		return journeyDate;
	}

	public Boolean getCancellable() {
		return cancellable;
	}
	
	public Long getPassengerId() {
		return passengerId;
	}

	public Long getTripScheduleId() {
		return tripScheduleId;
	}
}