package com.yoga.bus.payload.response;

public class BusResponse {

	private Long id;
	
	private String code;
	
	private int capacity;
	
	private String make;
	
	private Long agencyId;

	public BusResponse() {
	}

	public BusResponse(Long id, String code, int capacity, String make, Long agencyId) {
		this.id = id;
		
		this.code = code;
		
		this.capacity = capacity;
		
		this.make = make;
		
		this.agencyId = agencyId;
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

	public void setAgencyId(Long agencyId) {
		this.agencyId = agencyId;
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
	
	public Long getAgencyId() {
		return agencyId;
	}
}