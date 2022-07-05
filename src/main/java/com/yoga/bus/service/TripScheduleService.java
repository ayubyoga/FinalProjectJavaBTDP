package com.yoga.bus.service;

import java.text.ParseException;

import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.payload.request.TripScheduleRequest;

public interface TripScheduleService {
	TripSchedule addNewTrip(TripScheduleRequest tripScheduleRequest) throws ParseException;

	TripSchedule updatingTrip(Long id, TripScheduleRequest tripScheduleRequest) throws ParseException;
}
