package com.yoga.bus.models;

import java.util.Set;

import javax.persistence.*;

@Entity
@Table(name = "trip")
public class Trip {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	private int fare;
	
	private int journeyTime;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "source_stop_id")
	private Stop sourceStop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "dest_stop_id")
	private Stop destStop;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "bus_id")
	private Bus bus;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "agency_id")
	private Agency agency;

	@OneToMany(mappedBy = "tripDetail", cascade = CascadeType.ALL)
	private Set<TripSchedule> tripSchedules;

	public Trip() {
	}

	public Trip(int fare, int journeyTime, Stop sourceStop, Stop destStop, Bus bus, Agency agency) {
		this.fare = fare;
		this.journeyTime = journeyTime;
		this.sourceStop = sourceStop;
		this.destStop = destStop;
		this.bus = bus;
		this.agency = agency;
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
	
	public void setSourceStop(Stop sourceStop) {
		this.sourceStop = sourceStop;
	}

	public void setDestStop(Stop destStop) {
		this.destStop = destStop;
	}
	
	public void setBus(Bus bus) {
		this.bus = bus;
	}

	public void setAgency(Agency agency) {
		this.agency = agency;
	}

	public void setTripSchedules(Set<TripSchedule> tripSchedules) {
		this.tripSchedules = tripSchedules;
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
	
	public Stop getSourceStop() {
		return sourceStop;
	}

	public Stop getDestStop() {
		return destStop;
	}
	
	public Bus getBus() {
		return bus;
	}
	
	public Agency getAgency() {
		return agency;
	}
	
	public Set<TripSchedule> getTripSchedules() {
		return tripSchedules;
	}
}
