package com.yoga.bus.controller.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Trip;
import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.payload.request.TripScheduleRequest;
import com.yoga.bus.payload.response.TripScheduleResponse;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.service.impl.TripScheduleServiceImpl;
import com.yoga.bus.service.impl.TripServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/trip-schedule")
public class TripScheduleController {

	@Autowired
	TripScheduleServiceImpl tripScheduleServiceImpl;

	@Autowired
	TripServiceImpl tripServiceImpl;

	@PostMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createTripSchedule(@Valid @RequestBody TripScheduleRequest tripScheduleRequest) {

		Long tripDetailId = tripScheduleRequest.getTripDetailId();

		if(tripScheduleRequest.getTripDate() == null || tripDetailId == null || tripDetailId == 0) {
			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		LocalDate tripDate = tripScheduleRequest.getTripDate();

		Trip tripDetail = tripServiceImpl.getTripById(tripDetailId);

		if(tripDetail.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data trip detail tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		LocalDate today = LocalDate.now();

		if(tripDate.isBefore(today)) {
			Response errorResponse = new Response("400", "Bad Request", "Trip date sudah lewat, silahkan masukkan trip date yang valid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		int availableSeats = tripDetail.getBus().getCapacity();

		TripSchedule tripSchedule = new TripSchedule(tripDate.toString(), availableSeats, tripDetail);

		tripScheduleServiceImpl.saveTripSchedule(tripSchedule);

		Response successResponse = new Response("200", "OK", "Trip schedule berhasil disimpan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@GetMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAllTripSchedule() {

		List<TripSchedule> tripSchedules = tripScheduleServiceImpl.getAllTripSchedule();

		if(tripSchedules.size() != 0) {
			List<TripScheduleResponse> tripScheduleResponses = new ArrayList<>();
			
			for (TripSchedule tripSchedule : tripSchedules) {
				tripScheduleResponses.add(new TripScheduleResponse(tripSchedule.getId(),
						tripSchedule.getTripDate(), tripSchedule.getAvailableSeats(),
						tripSchedule.getTripDetail().getId()));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(tripScheduleResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada trip schedule tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripScheduleById(@PathVariable Long id) {

		TripSchedule tripSchedule = tripScheduleServiceImpl.getTripScheduleById(id);

		if(tripSchedule.getId() != null) {
			TripScheduleResponse tripScheduleResponse = new TripScheduleResponse(tripSchedule.getId(),
					tripSchedule.getTripDate(), tripSchedule.getAvailableSeats(),
					tripSchedule.getTripDetail().getId());
			
			return ResponseEntity.status(HttpStatus.OK).body(tripScheduleResponse);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Data trip schedule tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/trip/{tripId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripScheduleByTripId(@PathVariable(value = "tripId") Long tripId) {

		if(tripServiceImpl.getTripById(tripId).getId() != tripId) {
			Response errorResponse = new Response("404", "Not Found", "Data trip tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<TripSchedule> tripSchedules = tripScheduleServiceImpl.getAllTripScheduleByTripId(tripId);

		if(tripSchedules.size() != 0) {
			List<TripScheduleResponse> tripScheduleResponses = new ArrayList<>();
			
			for(TripSchedule tripSchedule: tripSchedules) {
				tripScheduleResponses.add(new TripScheduleResponse(tripSchedule.getId(),
						tripSchedule.getTripDate(), tripSchedule.getAvailableSeats(),
						tripSchedule.getTripDetail().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(tripScheduleResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Trip schedule tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTripSchedule(@Valid @RequestBody TripScheduleRequest tripScheduleRequest,
			@PathVariable Long id) {

		Long tripDetailId = tripScheduleRequest.getTripDetailId();

		TripSchedule tripSchedule = tripScheduleServiceImpl.getTripScheduleById(id);

		if(tripSchedule.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data trip schedule tidak ditemukan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		if(tripScheduleRequest.getTripDate() == null || tripDetailId == null || tripDetailId == 0) {
			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		LocalDate tripDate = tripScheduleRequest.getTripDate();

		Trip tripDetail = tripServiceImpl.getTripById(tripDetailId);

		if(tripDetail.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data trip detail tidak ditemukan");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		LocalDate today = LocalDate.now();

		if(tripDate.isBefore(today)) {
			Response errorResponse = new Response("400", "Bad Request", "Trip date sudah lewat, silahkan masukkan trip date yang valid");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		int availableSeats = tripDetail.getBus().getCapacity();

		tripSchedule.setTripDate(tripDate.toString());
		tripSchedule.setAvailableSeats(availableSeats);
		tripSchedule.setTripDetail(tripDetail);

		tripScheduleServiceImpl.saveTripSchedule(tripSchedule);

		Response successResponse = new Response("200", "OK", "Trip schedule berhasil diubah");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTripSchedule(@PathVariable Long id) {
		try {
			tripScheduleServiceImpl.deleteTripSchedule(id);
			Response successResponse = new Response("200", "OK", "Trip schedule berhasil dihapus");
			return ResponseEntity.status(HttpStatus.OK).body(successResponse);
		} catch (EmptyResultDataAccessException e) {
			Response errorResponse = new Response("404", "Not Found", "Data tidak ditemukan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}
