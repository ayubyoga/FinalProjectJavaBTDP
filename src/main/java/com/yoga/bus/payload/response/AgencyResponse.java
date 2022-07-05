package com.yoga.bus.payload.response;

public class AgencyResponse {

	private Long id;
	
	private String code;
	
	private String name;
	
	private String details;
	
	private Long owner;

	public AgencyResponse() {
	}

	public AgencyResponse(Long id, String code, String name, String details, Long owner) {
		this.id = id;
		
		this.code = code;
		
		this.name = name;
		
		this.details = details;
		
		this.owner = owner;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public void setOwnerId(Long owner) {
		this.owner = owner;
	}

	public Long getId() {
		return id;
	}
	
	public String getCode() {
		return code;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDetails() {
		return details;
	}
	
	public Long getOwnerId() {
		return owner;
	}
}