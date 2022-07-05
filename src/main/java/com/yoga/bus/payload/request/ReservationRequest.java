package com.yoga.bus.payload.request;

import io.swagger.annotations.ApiModelProperty;

public class ReservationRequest {

	@ApiModelProperty(hidden = true)
	private Long id;
	
	private int seatNumber;
	private Boolean cancellable;
	private Long tripScheduleId;

	public ReservationRequest() {
	}

	public ReservationRequest(int seatNumber, Boolean cancellable, Long tripScheduleId) {
		this.seatNumber = seatNumber;
		this.cancellable = cancellable;
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
	
	public void setTripScheduleId(Long tripScheduleId) {
		this.tripScheduleId = tripScheduleId;
	}

	public Long getId() {
		return id;
	}
	
	public int getSeatNumber() {
		return seatNumber;
	}
	
	public Boolean getCancellable() {
		return cancellable;
	}

	public Long getTripScheduleId() {
		return tripScheduleId;
	}
}