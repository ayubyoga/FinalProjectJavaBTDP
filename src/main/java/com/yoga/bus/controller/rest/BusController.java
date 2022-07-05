package com.yoga.bus.controller.rest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.yoga.bus.models.Agency;
import com.yoga.bus.models.Bus;
import com.yoga.bus.payload.request.BusCustomRequest;
import com.yoga.bus.payload.request.BusRequest;
import com.yoga.bus.payload.response.MessageResponse;
import com.yoga.bus.repository.AgencyRepository;
import com.yoga.bus.repository.BusRepository;
import io.swagger.annotations.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bus")
public class BusController {

	@Autowired
	BusRepository busRepository;

	@Autowired
	AgencyRepository agencyRepository;

	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addBusByUserId(@Valid @RequestBody BusCustomRequest busCustomRequest) {
		Optional<Agency> agency = agencyRepository.findById(busCustomRequest.getAgencyId());
		Bus bus = new Bus(
				busCustomRequest.getCode(), 
				busCustomRequest.getCapacity(), 
				busCustomRequest.getMake(),
				agency.get());
		return ResponseEntity.ok(busRepository.save(bus));
	}
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<BusRequest> dataArrResult = new ArrayList<>();
		for (Bus dataArr : busRepository.findAll()) {
			dataArrResult.add(new BusRequest(
					dataArr.getId(),
					dataArr.getCode(), 
					dataArr.getCapacity(), 
					dataArr.getMake(), 
					dataArr.getAgency().
					getId()));
		}
		return ResponseEntity.ok(new MessageResponse<BusRequest>(true, "Success Retrieving Data", dataArrResult));
	}
	
	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getBusByAgencyId(@PathVariable(value = "id") Long id) {
		List<Bus> bus = busRepository.findByAgencyId(id);
		return ResponseEntity.ok(bus);
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateBus(@PathVariable(value = "id") Long id,
			@Valid @RequestBody BusRequest busDetail) {
		Bus bus = busRepository.findById(id).get();
		Agency agency = agencyRepository.findById(busDetail.getAgencyId()).get();
		if (bus == null) {
			return ResponseEntity.notFound().build();
		}
		bus.setCode(busDetail.getCode());
		bus.setCapacity(busDetail.getCapacity());
		bus.setMake(busDetail.getMake());
		bus.setAgency(agency);
		
		Bus updatedBus = busRepository.save(bus);

		return ResponseEntity.ok(new MessageResponse<Bus>(true, "Success Updating Data", updatedBus));
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "delete bus", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deleteBus(@PathVariable(value = "id") Long id) {

		try {
			busRepository.deleteById(id);
			String result = "Success Delete Bus with Id: " + id;
			return ResponseEntity.ok(result);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

}
