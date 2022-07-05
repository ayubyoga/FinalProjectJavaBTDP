package com.yoga.bus.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Agency;
import com.yoga.bus.models.Bus;
import com.yoga.bus.payload.request.BusRequest;
import com.yoga.bus.payload.response.BusResponse;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.service.impl.AgencyServiceImpl;
import com.yoga.bus.service.impl.BusServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/bus")
public class BusController {

	@Autowired
	BusServiceImpl busServiceImpl;

	@Autowired
	AgencyServiceImpl agencyServiceImpl;

	@PostMapping("")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createBus(@Valid @RequestBody BusRequest busRequest) {

		String code = busRequest.getCode();
		int capacity = busRequest.getCapacity();
		String make = busRequest.getMake();
		Long agencyId = busRequest.getAgencyId();

		if (code == null || capacity == 0 || make == null || agencyId == null || code.equals("") || make.equals("")
				|| agencyId == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Agency agency = agencyServiceImpl.getAgencyById(agencyId);

		if (agency.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data agency tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Bus bus = new Bus(code, capacity, make, agency);

		busServiceImpl.saveBus(bus);

		Response successResponse = new Response("200", "OK", "Bus berhasil disimpan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@GetMapping("")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') || hasRole('USER')")
	public ResponseEntity<?> getAllBus() {

		List<Bus> buses = busServiceImpl.getAllBus();

		if (buses.size() != 0) {

			List<BusResponse> busResponses = new ArrayList<>();

			for (Bus bus : buses) {
				busResponses.add(new BusResponse(bus.getId(), bus.getCode(), bus.getCapacity(), bus.getMake(),
						bus.getAgency().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(busResponses);
		} else {

			Response errorResponse = new Response("404", "Not Found", "Tidak ada bus yang tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getBusById(@PathVariable Long id) {

		Bus bus = busServiceImpl.getBusById(id);

		if (bus.getId() != null) {

			BusResponse busResponse = new BusResponse(bus.getId(), bus.getCode(), bus.getCapacity(), bus.getMake(),
					bus.getAgency().getId());

			return ResponseEntity.status(HttpStatus.OK).body(busResponse);
		} else {

			Response errorResponse = new Response("404", "Not Found", "Data bus tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/agency/{agencyId}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getBusByAgencyId(@PathVariable(value = "agencyId") Long agencyId) {

		if (agencyServiceImpl.getAgencyById(agencyId).getId() != agencyId) {
			Response errorResponse = new Response("404", "Not Found", "Data agency tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Bus> buses = busServiceImpl.getAllBusByAgencyId(agencyId);

		if (buses.size() != 0) {
			List<BusResponse> busResponses = new ArrayList<>();

			for (Bus bus : buses) {
				busResponses.add(new BusResponse(bus.getId(), bus.getCode(), bus.getCapacity(), bus.getMake(),
						bus.getAgency().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(busResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada bus pada Agency ini");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateBus(@Valid @RequestBody BusRequest busRequest, @PathVariable Long id) {

		String code = busRequest.getCode();
		int capacity = busRequest.getCapacity();
		String make = busRequest.getMake();
		Long agencyId = busRequest.getAgencyId();

		Bus bus = busServiceImpl.getBusById(id);

		if (bus.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data bus tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		if (code == null || capacity == 0 || make == null || agencyId == null || code.equals("") || make.equals("")
				|| agencyId == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Agency agency = agencyServiceImpl.getAgencyById(agencyId);

		if (agency.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data agency tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		bus.setCode(code);
		bus.setCapacity(capacity);
		bus.setMake(make);
		bus.setAgency(agency);

		busServiceImpl.saveBus(bus);

		Response successResponse = new Response("200", "OK", "Data bus berhasil diubah");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteBus(@PathVariable Long id) {
		try {
			busServiceImpl.deleteBus(id);

			Response successResponse = new Response("200", "OK", "Data bus berhasil dihapus");
			return ResponseEntity.status(HttpStatus.OK).body(successResponse);
		} catch (EmptyResultDataAccessException e) {

			Response errorResponse = new Response("404", "Not Found", "Data bus tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}