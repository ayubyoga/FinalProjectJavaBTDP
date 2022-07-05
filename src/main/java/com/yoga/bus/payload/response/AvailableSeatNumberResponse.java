package com.yoga.bus.payload.response;

import java.util.List;

public class AvailableSeatNumberResponse {
	private Long tripScheduleId;
	
	private List<Integer> availableSeatNumber;

	public AvailableSeatNumberResponse() {
	}

	public void setAvailableSeatNumber(List<Integer> availableSeatNumber) {
		this.availableSeatNumber = availableSeatNumber;
	}

	public AvailableSeatNumberResponse(Long tripScheduleId, List<Integer> availableSeatNumber) {
		this.tripScheduleId = tripScheduleId;
		
		this.availableSeatNumber = availableSeatNumber;
	}

	public void setTripScheduleId(Long tripScheduleId) {
		this.tripScheduleId = tripScheduleId;
	}

	public List<Integer> getAvailableSeatNumber() {
		return availableSeatNumber;
	}

	public Long getTripScheduleId() {
		return tripScheduleId;
	}

}
