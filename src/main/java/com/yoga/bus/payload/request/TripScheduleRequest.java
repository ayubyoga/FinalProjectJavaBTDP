package com.yoga.bus.payload.request;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TripScheduleRequest {

	@NotBlank
	private String tripDate;

	@NotNull
	private int availableSeats;

	private int tripDetail;

	public int getTripDetail() {
		return tripDetail;
	}

	public void setTripDetail(int tripDetail) {
		this.tripDetail = tripDetail;
	}

	public @NotNull int getAvailableSeats() {
		// TODO Auto-generated method stub
		return 0;
	}

	public String getTripDate() {
		// TODO Auto-generated method stub
		return null;
	}
}
