package com.yoga.bus.service;

import com.yoga.bus.models.Bus;
import com.yoga.bus.payload.request.BusRequest;

public interface BusService {

	Bus addNewBus(BusRequest busRequest);

	Bus updatingBus(Long id, BusRequest busRequest);
}