package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.TripSchedule;

public interface TripScheduleService {

	TripSchedule saveTripSchedule(TripSchedule tripSchedule);

	TripSchedule getTripScheduleById(Long id);

	List<Integer> getAllSeatNumberBooked(Long tripScheduleId);

	List<TripSchedule> getAllTripSchedule();

	List<TripSchedule> getAllTripScheduleByTripId(Long tripId);

	void deleteTripSchedule(Long id);
}