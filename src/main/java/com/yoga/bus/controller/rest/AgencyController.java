package com.yoga.bus.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import com.yoga.bus.models.Agency;
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.AgencyRequest;
import com.yoga.bus.payload.response.MessageResponse;
import com.yoga.bus.payload.response.ResponseHandler;
import com.yoga.bus.repository.AgencyRepository;
import com.yoga.bus.repository.BusRepository;
import com.yoga.bus.repository.UserRepository;
import com.yoga.bus.service.AgencyService;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {

	@Autowired
	AgencyRepository agencyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BusRepository busRepository;

	@Autowired
	AgencyService agencyService;

	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAgency(@Valid @RequestBody AgencyRequest agencyRequest) {
		User user = userRepository.findById(agencyRequest.getOwner()).get();
		Agency agency = new Agency(
				agencyRequest.getCode(), 
				agencyRequest.getDetails(), 
				agencyRequest.getName(), 
				user);
		return ResponseEntity
				.ok(new MessageResponse<Agency>(true, "Success Adding Data", agencyRepository.save(agency)));
	}
	
	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getAll() {
		List<AgencyRequest> dataArrResult = new ArrayList<>();
		for (Agency dataArr : agencyRepository.findAll()) {
			dataArrResult.add(new AgencyRequest(dataArr.getId(), dataArr.getCode(), dataArr.getName(),
					dataArr.getDetails(), dataArr.getOwner().getId()));
		}
		return ResponseEntity.ok(new MessageResponse<AgencyRequest>(true, "Success Retrieving Data", dataArrResult));
	}


	@GetMapping("/{id}")
	@ApiOperation(value = "get agency", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> getAgency(@PathVariable(value = "id") Long id) {

		try {
			Agency agency = agencyRepository.findById(id).get();
			return ResponseHandler.generateResponse("success", HttpStatus.OK, agency);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, e.getMessage(), e.getCause());
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "update agency", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> updateAgency(@PathVariable(value = "id") Long id,
			@Valid @RequestBody AgencyRequest agencyRequest) {

		Agency agency = agencyService.updatingAgency(id, agencyRequest);
		return new ResponseEntity<>(agency, HttpStatus.OK);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "delete agency", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ROLE_ADMIN')")
	public ResponseEntity<?> deleteAgency(@PathVariable(value = "id") Long id) {

		try {
			agencyRepository.deleteById(id);
			String result = "Success Delete Agency with Id: " + id;
			return new ResponseEntity<>(result, HttpStatus.OK);

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}
}
