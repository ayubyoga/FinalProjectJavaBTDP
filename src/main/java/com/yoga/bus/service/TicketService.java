package com.yoga.bus.service;

import java.text.ParseException;

import com.yoga.bus.models.Ticket;
import com.yoga.bus.payload.request.TicketRequest;

public interface TicketService {
	Ticket bookingTicket(TicketRequest ticketRequest) throws ParseException;

	Ticket updatingTicket(Long id, TicketRequest ticketRequest) throws ParseException;
}