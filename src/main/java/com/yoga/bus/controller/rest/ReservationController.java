package com.yoga.bus.controller.rest;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Ticket;
import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.ReservationRequest;
import com.yoga.bus.payload.response.AvailableSeatNumberResponse;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.payload.response.TicketResponse;
import com.yoga.bus.service.impl.TicketServiceImpl;
import com.yoga.bus.service.impl.TripScheduleServiceImpl;
import com.yoga.bus.service.impl.UserServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/reservation")
public class ReservationController {

	TicketServiceImpl ticketServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	TripScheduleServiceImpl tripScheduleServiceImpl;

	@PostMapping("")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> bookTicket(@Valid @RequestBody ReservationRequest reservationRequest) {

		int seatNumber = reservationRequest.getSeatNumber();
		Long tripScheduleId = reservationRequest.getTripScheduleId();

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userServiceImpl.getUserByUsername(username);
		Long passengerId = user.getId();

		if (seatNumber == 0 || passengerId == null || tripScheduleId == null || passengerId == 0 || tripScheduleId == 0
				|| reservationRequest.getCancellable() == null) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		boolean cancellable = reservationRequest.getCancellable();

		TripSchedule schedule = tripScheduleServiceImpl.getTripScheduleById(tripScheduleId);

		if (schedule.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data trip schedule tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		String journeyDate = schedule.getTripDate();

		User passenger = userServiceImpl.getUserById(passengerId);

		LocalDate today = LocalDate.now();

		String tripDateString = tripScheduleServiceImpl.getTripScheduleById(tripScheduleId).getTripDate();
		LocalDate tripDate = LocalDate.parse(tripDateString);

		if (tripDate.isBefore(today)) {
			Response errorResponse = new Response("400", "Bad Request", "Trip schedule sudah lewat, silahkan pilih trip schedule yang lain");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if (passenger.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data penumpang tidak ditemukan");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		int availableSeats = schedule.getAvailableSeats();
		if (availableSeats == 0) {
			Response errorResponse = new Response("400", "Bad Request", "Mohon maaf, tiket sudah habis");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		int bookedSeatsByPassenger = ticketServiceImpl.getNumberOfBookedSeatsByPassengerId(passengerId, tripScheduleId);

		if (bookedSeatsByPassenger > 0) {
			String errorDetails = "Mohon maaf, anda tidak dapat memesan tiket lagi untuk trip schedule ini";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		int capacity = schedule.getTripDetail().getBus().getCapacity();

		if (seatNumber > capacity) {
			String errorDetails = "Kursi tidak valid. Kapasitas bus hanya " + capacity + " kursi";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		List<Integer> numberBooked = tripScheduleServiceImpl.getAllSeatNumberBooked(tripScheduleId);

		if (numberBooked.contains(seatNumber)) {
			String errorDetails = "Kursi sudah dipesan, silahkan pilih kursi lain";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Ticket ticket = new Ticket(seatNumber, cancellable, journeyDate, schedule, passenger);

		ticketServiceImpl.saveTicket(ticket);

		schedule.setAvailableSeats(availableSeats - 1);
		tripScheduleServiceImpl.saveTripSchedule(schedule);

		Response successResponse = new Response("200", "OK", "Tiket berhasil dipesan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@GetMapping("/passenger")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAllOwnedTicketByLoggedInUser() {

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userServiceImpl.getUserByUsername(username);
		Long passengerId = user.getId();

		List<Ticket> tickets = ticketServiceImpl.getAllTicketByPassengerId(passengerId);

		if (tickets.size() != 0) {
			List<TicketResponse> ticketResponses = new ArrayList<>();

			for (Ticket ticket : tickets) {
				ticketResponses.add(new TicketResponse(ticket.getId(), ticket.getSeatNumber(), ticket.getCancellable(),
						ticket.getJourneyDate(), ticket.getPassenger().getId(), ticket.getTripSchedule().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(ticketResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Anda belum memiliki tiket yang tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	// ticket
	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getTicketById(@PathVariable Long id) {

		Ticket ticket = ticketServiceImpl.getTicketById(id);

		if (ticket.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data ticket tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userServiceImpl.getUserByUsername(username);
		Long passengerId = user.getId();

		if (ticket.getPassenger().getId() != passengerId) {
			Response errorResponse = new Response("400", "Bad Request", "Anda tidak dapat melihat ticket milik penumpang lain");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		TicketResponse ticketResponse = new TicketResponse(ticket.getId(), ticket.getSeatNumber(),
				ticket.getCancellable(), ticket.getJourneyDate(), ticket.getPassenger().getId(),
				ticket.getTripSchedule().getId());

		return ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
	}

	@GetMapping("/available-seat-number/{tripScheduleId}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> getAvailableSeatNumberByTripScheduleId(@PathVariable Long tripScheduleId) {

		TripSchedule schedule = tripScheduleServiceImpl.getTripScheduleById(tripScheduleId);

		if (schedule.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data trip schedule tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Integer> bookedSeatNumber = tripScheduleServiceImpl.getAllSeatNumberBooked(tripScheduleId);

		int capacity = schedule.getTripDetail().getBus().getCapacity();

		List<Integer> availableSeatNumber = new ArrayList<>();

		for (int i = 1; i <= capacity; i++) {
			if (!bookedSeatNumber.contains(i)) {
				availableSeatNumber.add(i);
			}
		}

		AvailableSeatNumberResponse availableSeatNumberResponse = new AvailableSeatNumberResponse(tripScheduleId,
				availableSeatNumber);
		return ResponseEntity.status(HttpStatus.OK).body(availableSeatNumberResponse);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('USER')")
	public ResponseEntity<?> cancelTicket(@PathVariable Long id) {

		Ticket ticket = ticketServiceImpl.getTicketById(id);

		if (ticket.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data ticket tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		String username = userDetails.getUsername();
		User user = userServiceImpl.getUserByUsername(username);
		Long passengerId = user.getId();

		if (passengerId != ticket.getPassenger().getId()) {
			Response errorResponse = new Response("400", "Bad Request",
					"Ticket hanya dapat dibatalkan oleh pemilik ticket");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if (ticket.getCancellable() == false) {
			Response errorResponse = new Response("400", "Bad Request", "Mohon maaf, ticket tidak dapat dibatalkan");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		TripSchedule schedule = ticket.getTripSchedule();

		ticketServiceImpl.deleteTicket(id);

		schedule.setAvailableSeats(schedule.getAvailableSeats() + 1);
		tripScheduleServiceImpl.saveTripSchedule(schedule);

		Response successResponse = new Response("200", "OK", "Ticket berhasil dibatalkan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}
}
