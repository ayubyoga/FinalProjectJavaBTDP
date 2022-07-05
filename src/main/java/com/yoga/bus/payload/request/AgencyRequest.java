package com.yoga.bus.payload.request;

import java.util.Objects;

import io.swagger.annotations.ApiModelProperty;

public class AgencyRequest {
	@ApiModelProperty(hidden = true)
	private Long id;

	private String code;

	private String name;

	private String details;

	private Long owner;
	
	

	public AgencyRequest() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AgencyRequest(Long id, String code, String name, String details, Long owner) {
		super();
		this.id = id;
		this.code = code;
		this.name = name;
		this.details = details;
		this.owner = owner;
	}

	@Override
	public String toString() {
		return "AgencyRequest [id=" + id + ", code=" + code + ", name=" + name + ", details=" + details + ", owner="
				+ owner + ", hashCode()=" + hashCode() + ", getId()=" + getId() + ", getCode()=" + getCode()
				+ ", getName()=" + getName() + ", getDetails()=" + getDetails() + ", getOwner()=" + getOwner()
				+ ", getClass()=" + getClass() + ", toString()=" + super.toString() + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(code, details, id, name, owner);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		AgencyRequest other = (AgencyRequest) obj;
		return Objects.equals(code, other.code) && Objects.equals(details, other.details)
				&& Objects.equals(id, other.id) && Objects.equals(name, other.name)
				&& Objects.equals(owner, other.owner);
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDetails() {
		return details;
	}

	public void setDetails(String details) {
		this.details = details;
	}

	public Long getOwner() {
		return owner;
	}

	public void setOwner(Long owner) {
		this.owner = owner;
	}

}