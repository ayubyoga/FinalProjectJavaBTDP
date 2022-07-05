package com.yoga.bus.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.bus.models.Trip;
import com.yoga.bus.repository.TripRepository;
import com.yoga.bus.service.TripService;

@Service
@Transactional
public class TripServiceImpl implements TripService {
	
	@Autowired
	private TripRepository tripRepository;

	public Trip saveTrip(Trip trip) {
		tripRepository.save(trip);
		return trip;
	}

	public List<Trip> getAllTrip() {
		return tripRepository.findAll();
	}

	public Trip getTripById(Long id) {
		return tripRepository.findById(id).orElse(new Trip());
	}

	public List<Trip> getAllTripByAgencyId(Long agencyId) {
		return tripRepository.findByAgencyId(agencyId);
	}

	public List<Trip> getAllTripByBusId(Long busId) {
		return tripRepository.findByBusId(busId);
	}
	
	public List<Trip> getAllTripBySourceStopId(Long sourceStopId) {
		return tripRepository.findBySourceStopId(sourceStopId);
	}
	
	public List<Trip> getAllTripByDestStopId(Long destStopId) {
		return tripRepository.findByDestStopId(destStopId);
	}

	public void deleteTrip(Long id) {
		tripRepository.deleteById(id);
	}
}
