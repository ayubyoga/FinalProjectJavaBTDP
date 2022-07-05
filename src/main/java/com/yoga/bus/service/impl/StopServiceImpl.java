package com.yoga.bus.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.yoga.bus.models.Stop;
import com.yoga.bus.repository.StopRepository;
import com.yoga.bus.service.StopService;

@Service
@Transactional
public class StopServiceImpl implements StopService {
	
	@Autowired
	private StopRepository stopRepository;

	public Stop saveStop(Stop stop) {
		stopRepository.save(stop);
		return stop;
	}

	public List<Stop> getAllStop() {
		return stopRepository.findAll();
	}

	public Stop getStopById(Long id) {
		return stopRepository.findById(id).orElse(new Stop());
	}

	public void deleteStop(Long id) {
		stopRepository.deleteById(id);
	}
}
