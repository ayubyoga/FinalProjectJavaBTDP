package com.yoga.bus.payload.response;

public class TripResponse {

	private Long id;

	private int fare;

	private int journeyTime;

	private Long sourceStopId;
	
	private Long destStopId;
	
	private Long busId;
	
	private Long agencyId;

	public TripResponse() {
	}

	public TripResponse(Long id, int fare, int journeyTime, Long sourceStopId, Long destStopId, Long busId,
			Long agencyId) {
		this.id = id;
		
		this.fare = fare;
		
		this.journeyTime = journeyTime;
		
		this.sourceStopId = sourceStopId;
		
		this.destStopId = destStopId;
		
		this.busId = busId;
		
		this.agencyId = agencyId;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFare(int fare) {
		this.fare = fare;
	}

	public void setJourneyTime(int journeyTime) {
		this.journeyTime = journeyTime;
	}

	public void setSourceStopId(Long sourceStopId) {
		this.sourceStopId = sourceStopId;
	}

	public void setDestStopId(Long destStopId) {
		this.destStopId = destStopId;
	}

	public void setBusId(Long busId) {
		this.busId = busId;
	}

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
	}

	public Long getId() {
		return id;
	}

	public int getFare() {
		return fare;
	}

	public int getJourneyTime() {
		return journeyTime;
	}

	public Long getSourceStopId() {
		return sourceStopId;
	}

	public Long getDestStopId() {
		return destStopId;
	}

	public Long getBusId() {
		return busId;
	}

	public Long getAgencyId() {
		return agencyId;
	}
}