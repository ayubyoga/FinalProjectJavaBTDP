package com.yoga.bus.controller.rest;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Ticket;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.payload.response.TicketResponse;
import com.yoga.bus.service.impl.TicketServiceImpl;
import com.yoga.bus.service.impl.TripScheduleServiceImpl;
import com.yoga.bus.service.impl.UserServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/ticket")
public class TicketController {

	@Autowired
	TicketServiceImpl ticketServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@Autowired
	TripScheduleServiceImpl tripScheduleServiceImpl;

	@GetMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllTicket() {

		List<Ticket> tickets = ticketServiceImpl.getAllTicket();

		if(tickets.size() != 0) {
			List<TicketResponse> ticketResponses = new ArrayList<>();
			
			for (Ticket ticket : tickets) {
				ticketResponses.add(new TicketResponse(ticket.getId(), ticket.getSeatNumber(),
						ticket.getCancellable(), ticket.getJourneyDate(), ticket.getPassenger().getId(),
						ticket.getTripSchedule().getId()));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(ticketResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada ticket yang tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getTicketById(@PathVariable Long id) {

		Ticket ticket = ticketServiceImpl.getTicketById(id);

		if(ticket.getId() != null) {
			TicketResponse ticketResponse = new TicketResponse(ticket.getId(), ticket.getSeatNumber(),
					ticket.getCancellable(), ticket.getJourneyDate(),
					ticket.getPassenger().getId(), ticket.getTripSchedule().getId());
		
			return ResponseEntity.status(HttpStatus.OK).body(ticketResponse);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Data ticket tidak ditemukan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/trip-schedule/{tripScheduleId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAllTicketByTripScheduleId(@PathVariable Long tripScheduleId) {

		if(tripScheduleServiceImpl.getTripScheduleById(tripScheduleId).getId() != tripScheduleId) {
			Response errorResponse = new Response("404", "Not Found", "Data trip schedule tidak ditemukan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Ticket> tickets = ticketServiceImpl.getAllTicketByTripScheduleId(tripScheduleId);

		if(tickets.size() != 0) {
			List<TicketResponse> ticketResponses = new ArrayList<>();
			
			for (Ticket ticket : tickets) {
				ticketResponses.add(new TicketResponse(ticket.getId(), ticket.getSeatNumber(), ticket.getCancellable(),
						ticket.getJourneyDate(), ticket.getPassenger().getId(),
						ticket.getTripSchedule().getId()));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(ticketResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada ticket yang dipesan"
					+ " dari trip schedule tersebut");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}
