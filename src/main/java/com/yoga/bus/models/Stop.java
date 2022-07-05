package com.yoga.bus.models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "stop")
public class Stop {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private String code;
	
	private String name;
	
	private String detail;

	@OneToMany(mappedBy = "sourceStop", cascade = CascadeType.ALL)
	private Set<Trip> sourceTrips;

	@OneToMany(mappedBy = "destStop", cascade = CascadeType.ALL)
	private Set<Trip> destTrips;

	public Stop() {
	}

	public Stop(String code, String name, String detail) {
		this.code = code;
		this.name = name;
		this.detail = detail;
	}

	//Setter
	public void setId(Long id) {
		this.id = id;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDetail(String detail) {
		this.detail = detail;
	}

	//Getter
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
