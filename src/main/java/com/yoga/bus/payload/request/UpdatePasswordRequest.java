package com.yoga.bus.payload.request;

import io.swagger.annotations.ApiModelProperty;

public class UpdatePasswordRequest {

	@ApiModelProperty(hidden = true)
	private Long id;

	private String password;

	public UpdatePasswordRequest() {
	}

	public UpdatePasswordRequest(String password) {
		this.password = password;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Long getId() {
		return id;
	}

	public String getPassword() {
		return password;
	}
}