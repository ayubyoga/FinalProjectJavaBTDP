package com.yoga.bus.service;

import java.util.List;

import com.yoga.bus.models.Ticket;

public interface TicketService {
	Ticket saveTicket(Ticket ticket);

	Ticket getTicketById(Long id);

	List<Ticket> getAllTicket();

	List<Ticket> getAllTicketByPassengerId(Long passengerId);
	
	List<Ticket> getAllTicketByTripScheduleId(Long tripScheduleId);
	
	int getNumberOfBookedSeatsByPassengerId(Long passengerId, Long tripScheduleId);
	
	void deleteTicket(Long id);
}