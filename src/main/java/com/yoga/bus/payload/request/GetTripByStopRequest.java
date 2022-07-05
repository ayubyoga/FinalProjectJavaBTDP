package com.yoga.bus.payload.request;

public class GetTripByStopRequest {

	private Long sourceStopId;
	private Long destStopId;

	public GetTripByStopRequest() {
	}

	public GetTripByStopRequest(Long sourceStopId, Long destStopId) {
		this.sourceStopId = sourceStopId;
		this.destStopId = destStopId;
	}

	public void setSourceStop(Long sourceStopId) {
		this.sourceStopId = sourceStopId;
	}
	
	public void setDestStop(Long destStopId) {
		this.destStopId = destStopId;
	}

	public Long getSourceStop() {
		return sourceStopId;
	}
	
	public Long getDestStop() {
		return destStopId;
	}
}