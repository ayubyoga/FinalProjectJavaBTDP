package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.Stop;

public interface StopService {
	List<Stop> getAllStop();
	
	Stop saveStop(Stop stop);

	Stop getStopById(Long id);
	
	void deleteStop(Long id);
}