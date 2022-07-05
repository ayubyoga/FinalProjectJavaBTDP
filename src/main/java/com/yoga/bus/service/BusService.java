package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.Bus;

//Interface BusService
public interface BusService {
	Bus saveBus(Bus bus);
	
	Bus getBusById(Long id);
	
	List<Bus> getAllBus();
	
	List<Bus> getAllBusByAgencyId(Long agencyId);

	void deleteBus(Long id);
}
