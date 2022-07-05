package com.yoga.bus.controller.rest;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Trip;
import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.payload.request.GetTripScheduleRequest;
import com.yoga.bus.repository.TicketRepository;
import com.yoga.bus.repository.TripRepository;
import com.yoga.bus.repository.TripScheduleRepository;
import com.yoga.bus.payload.response.MessageResponse;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/trip_schedule")
public class TripScheduleController {
	
	@Autowired
	TripScheduleRepository tripScheduleRepository;

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	TripRepository tripRepository;

	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasAnyRole('ADMIN','USER')")
	public ResponseEntity<?> getAll() {
		List<TripSchedule> dataArr = tripScheduleRepository.findAll();
		return ResponseEntity.ok(new MessageResponse<TripSchedule>(true, "Berhasil", dataArr));
	}
	
	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTripSchedule(@Valid @RequestBody GetTripScheduleRequest tripScheduleRequest) {
		Trip trip = tripRepository.findById(tripScheduleRequest.getTrip_detail()).get();
		TripSchedule trip_schedule = new TripSchedule(tripScheduleRequest.getTripDate(),tripScheduleRequest.getAvailable_seats(), trip);
		return ResponseEntity
				.ok(new MessageResponse<TripSchedule>(true, "Berhasil menambahkan data", tripScheduleRepository.save(trip_schedule)));
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTripSchedule(@PathVariable(value = "id") Long id,
			@Valid @RequestBody GetTripScheduleRequest trip_schedule_detail) {
		TripSchedule trip_schedule = tripScheduleRepository.findById(id).get();
		Trip trip = tripRepository.findById(trip_schedule_detail.getTrip_detail()).get();
		trip_schedule.setAvailableSeats(trip_schedule_detail.getAvailable_seats());
		trip_schedule.setTripDate(trip_schedule_detail.getTripDate());
		trip_schedule.setTripDetail(trip);

		TripSchedule updatedTripSchedule = tripScheduleRepository.save(trip_schedule);

		return ResponseEntity.ok(new MessageResponse<TripSchedule>(true, "Berhasil mengupdate data", updatedTripSchedule));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTripSchedule(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			tripScheduleRepository.findById(id).get();

			result = "Berhasil menghapis data dengan ID: " + id;
			tripScheduleRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<TripSchedule>(true, result));
		} catch (Exception e) {
			result = "Data dengan ID: " + id + " Tidak Ditemukan";
			return ResponseEntity.ok(new MessageResponse<TripSchedule>(false, result));
		}
	}

}