package com.yoga.bus.payload.request;

import io.swagger.annotations.ApiModelProperty;

public class UpdateUserRequest {
	
	@ApiModelProperty(hidden = true)
	private Long id;

	private String firstName;
	
	private String lastName;
	
	private String mobileNumber;

	public UpdateUserRequest() {
	}

	public UpdateUserRequest(String firstName, String lastName, String mobileNumber) {
		this.firstName = firstName;
		
		this.lastName = lastName;
		
		this.mobileNumber = mobileNumber;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public Long getId() {
		return id;
	}

  public String getFirstName() {
  	return firstName;
  }
  
  public String getLastName() {
  	return lastName;
  }

  public String getMobileNumber() {
  	return mobileNumber;
  }
}