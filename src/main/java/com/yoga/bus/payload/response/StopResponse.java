package com.yoga.bus.payload.response;

public class StopResponse {

	private Long id;
	
	private String code;
	
	private String name;
	
	private String detail;

	public StopResponse() {
	}

	public StopResponse(Long id, String code, String name, String detail) {
		this.id = id;
		
		this.code = code;
		
		this.name = name;
		
		this.detail = detail;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setcode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDetail(String detail) {
		this.detail = detail;
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

	public String getDetail() {
		return detail;
	}

}