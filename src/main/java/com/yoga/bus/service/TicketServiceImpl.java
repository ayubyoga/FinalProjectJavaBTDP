package com.yoga.bus.service;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.yoga.bus.models.Ticket;
import com.yoga.bus.models.TripSchedule;
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.TicketRequest;
import com.yoga.bus.repository.TicketRepository;
import com.yoga.bus.repository.TripScheduleRepository;
import com.yoga.bus.repository.UserRepository;

import lombok.AllArgsConstructor;

@AllArgsConstructor
@Component
public class TicketServiceImpl implements TicketService {

	@Autowired
	private TicketRepository ticketRepository;

	@Autowired
	private UserRepository userRepository;

	@Autowired
	private TripScheduleRepository tripScheduleRepository;

	public Optional<User> checkIfUserPresent(String currentUser) {

		Optional<User> user = userRepository.findByUsername(currentUser);

		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
		}
		return user;
	}

	public Optional<TripSchedule> checkIfTripScheduleAvailable(TicketRequest ticketRequest) throws ParseException {

		// find trip schedule by id
		Optional<TripSchedule> tripSchedule = tripScheduleRepository.findById(ticketRequest.getTripScheduleId());

		String journeyDate = ticketRequest.getJourneyDate();
		String requestedDate = tripSchedule.get().getTripDate();

		SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
		Date myDate = sdf.parse(requestedDate);
		Date tripDate = sdf.parse(journeyDate);

		if (!tripSchedule.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Trip schedule not found");
		}
		if (!myDate.equals(tripDate)) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No trip found at date " + journeyDate);
		}
		if (tripSchedule.get().getAvailableSeats() == 0) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Ticked sold out");
		}
		return tripSchedule;
	}

	@Override
	public Ticket updatingTicket(Long id, TicketRequest ticketRequest) throws ParseException {

		Optional<Ticket> ticket = ticketRepository.findById(id);
		
		if (!ticket.isPresent()) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Ticket not found");
		}

		Optional<TripSchedule> tripSchedule = checkIfTripScheduleAvailable(ticketRequest);
		
		ticket.get().setJourneyDate(ticketRequest.getJourneyDate());
		ticket.get().setTripSchedule(tripSchedule.get());

		Ticket savedTicket = ticketRepository.save(ticket.get());

		return savedTicket;
	}

	@Override
	public Ticket bookingTicket(TicketRequest ticketRequest) throws ParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
