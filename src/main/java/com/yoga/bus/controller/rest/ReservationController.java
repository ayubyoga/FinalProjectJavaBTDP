package com.yoga.bus.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Ticket;
import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.TicketRequest;
import com.yoga.bus.payload.response.MessageResponse;
import com.yoga.bus.repository.TicketRepository;
import com.yoga.bus.repository.TripScheduleRepository;
import com.yoga.bus.repository.UserRepository;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/ticket")
public class ReservationController {

	@Autowired
	TicketRepository ticketRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	TripScheduleRepository tripScheduleRepository;

	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addTicket(@Valid @RequestBody TicketRequest ticketRequest) {
		User user = userRepository.findById(ticketRequest.getPassegerId()).get();
		TripSchedule tripSchedule = tripScheduleRepository.findById(ticketRequest.getTripScheduleId()).get();
		Ticket ticket = new Ticket(ticketRequest.getSeatNumber(), ticketRequest.getCancellable(),
				ticketRequest.getJourneyDate(), user, tripSchedule);
		ticketRepository.save(ticket);
		return ResponseEntity
			.ok(new MessageResponse<Ticket>(true, "Berhasil menambahkan Data", ticket));
	}
}
