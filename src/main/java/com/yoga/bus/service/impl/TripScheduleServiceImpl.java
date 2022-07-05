package com.yoga.bus.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.repository.TripScheduleRepository;
import com.yoga.bus.service.TripScheduleService;

@Service
@Transactional
public class TripScheduleServiceImpl implements TripScheduleService {
	
	@Autowired
	private TripScheduleRepository tripScheduleRepository;

	public TripSchedule saveTripSchedule(TripSchedule tripSchedule) {
		tripScheduleRepository.save(tripSchedule);
		return tripSchedule;
	}

	public List<TripSchedule> getAllTripSchedule() {
		return tripScheduleRepository.findAll();
	}

	public TripSchedule getTripScheduleById(Long id) {
		return tripScheduleRepository.findById(id).orElse(new TripSchedule());
	}

	public List<TripSchedule> getAllTripScheduleByTripId(Long tripId) {
		return tripScheduleRepository.findByTripId(tripId);
	}
	
	public List<Integer> getAllSeatNumberBooked(Long tripScheduleId) {
		return tripScheduleRepository.findSeatNumberByTripScheduleId(tripScheduleId);
	}
	
	public void deleteTripSchedule(Long id) {
		tripScheduleRepository.deleteById(id);
	}
}
