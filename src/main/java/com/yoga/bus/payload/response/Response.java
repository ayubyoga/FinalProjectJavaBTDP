package com.yoga.bus.payload.response;

public class Response {

	private String code;
	
	private String status;
	
	private String details;

	public Response() {	
	}

	public Response(String code, String status, String details) {
		this.code = code;
		
		this.status = status;
		
		this.details = details;
	}

	public void setCode(String code) {
		this.code = code;
	}
	
	public void setStatus(String status) {
		this.status = status;
	}
	
	public void setDetails(String details) {
		this.details = details;
	}

	public String getCode() {
		return code;
	}
	
	public String getStatus() {
		return status;
	}
	
	public String getDetails() {
		return details;
	}
}

