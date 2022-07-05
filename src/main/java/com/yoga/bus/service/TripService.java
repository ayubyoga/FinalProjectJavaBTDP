package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.Trip;

public interface TripService {
	List<Trip> getAllTrip();

	List<Trip> getAllTripByAgencyId(Long agencyId);
	
	List<Trip> getAllTripByBusId(Long busId);
	
	List<Trip> getAllTripBySourceStopId(Long sourceStopId);
	
	List<Trip> getAllTripByDestStopId(Long destStopId);
	
	Trip getTripById(Long id);
	
	Trip saveTrip(Trip trip);
	
	void deleteTrip(Long id);
}