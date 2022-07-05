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
import com.yoga.bus.models.Stop;
import com.yoga.bus.models.Trip;
import com.yoga.bus.payload.request.TripRequest;
import com.yoga.bus.payload.response.TripResponse;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.service.impl.AgencyServiceImpl;
import com.yoga.bus.service.impl.BusServiceImpl;
import com.yoga.bus.service.impl.StopServiceImpl;
import com.yoga.bus.service.impl.TripServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/trip")
public class TripController {

	@Autowired
	TripServiceImpl tripServiceImpl;

	@Autowired
	StopServiceImpl stopServiceImpl;

	@Autowired
	BusServiceImpl busServiceImpl;

	@Autowired
	AgencyServiceImpl agencyServiceImpl;

	@PostMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createTrip(@Valid @RequestBody TripRequest tripRequest) {

		int fare = tripRequest.getFare();
		int journeyTime = tripRequest.getJourneyTime();
		Long sourceStopId = tripRequest.getSourceStopId();
		Long destStopId = tripRequest.getDestStopId();
		Long busId = tripRequest.getBusId();

		if(fare == 0 || journeyTime == 0 || sourceStopId == null || destStopId == null
				|| busId == null || sourceStopId == 0 || destStopId == 0 || busId == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Bus bus = busServiceImpl.getBusById(busId);

		if(bus.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data bus tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Agency agency = bus.getAgency();

		if(agency.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data agency tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Stop destStop = stopServiceImpl.getStopById(destStopId);

		if(destStop.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data destination stop tidak ditemukan");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Stop sourceStop = stopServiceImpl.getStopById(sourceStopId);

		if(sourceStop.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data source stop tidak ditemukan");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if(sourceStop.getId() == destStop.getId()) {
			Response errorResponse = new Response("400", "Bad Request", "Field sourceStopId dan destStopId tidak boleh sama");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Trip trip = new Trip(fare, journeyTime, sourceStop, destStop, bus, agency);

		tripServiceImpl.saveTrip(trip);

		Response successResponse = new Response("200", "OK", "Trip berhasil disimpan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@GetMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAllTrip() {

		List<Trip> trips = tripServiceImpl.getAllTrip();

		if(trips.size() != 0) {
			List<TripResponse> tripResponses = new ArrayList<>();
			
			for (Trip trip : trips) {
				tripResponses.add(new TripResponse(trip.getId(), trip.getFare(), trip.getJourneyTime(),
						trip.getSourceStop().getId(), trip.getDestStop().getId(),
						trip.getBus().getId(), trip.getAgency().getId()));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(tripResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada trip yang tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripById(@PathVariable Long id) {

		Trip trip = tripServiceImpl.getTripById(id);

		if(trip.getId() != null) {
			TripResponse tripResponse = new TripResponse(trip.getId(), trip.getFare(), trip.getJourneyTime(),
					trip.getSourceStop().getId(), trip.getDestStop().getId(),
					trip.getBus().getId(), trip.getAgency().getId());
		
			return ResponseEntity.status(HttpStatus.OK).body(tripResponse);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Data trip tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/agency/{agencyId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripByAgencyId(@PathVariable(value = "agencyId") Long agencyId) {

		if(agencyServiceImpl.getAgencyById(agencyId).getId() != agencyId) {
			Response errorResponse = new Response("404", "Not Found", "Data agency tidakada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Trip> trips = tripServiceImpl.getAllTripByAgencyId(agencyId);

		if(trips.size() != 0) {
			List<TripResponse> tripResponses = new ArrayList<>();
			
			for(Trip trip: trips) {
				tripResponses.add(new TripResponse(trip.getId(), trip.getFare(), trip.getJourneyTime(),
						trip.getSourceStop().getId(), trip.getDestStop().getId(),
						trip.getBus().getId(), trip.getAgency().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(tripResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Agency tidak memiliki trip");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/bus/{busId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripByBusId(@PathVariable(value = "busId") Long busId) {

		if(busServiceImpl.getBusById(busId).getId() != busId) {
			Response errorResponse = new Response("404", "Not Found", "Data bus tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Trip> trips = tripServiceImpl.getAllTripByBusId(busId);

		if(trips.size() != 0) {
			List<TripResponse> tripResponses = new ArrayList<>();
			
			for(Trip trip: trips) {
				tripResponses.add(new TripResponse(trip.getId(), trip.getFare(), trip.getJourneyTime(),
						trip.getSourceStop().getId(), trip.getDestStop().getId(),
						trip.getBus().getId(), trip.getAgency().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(tripResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Bus tidak memiliki trip");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/source-stop/{sourceStopId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripBySourceStopId(@PathVariable(value = "sourceStopId") Long sourceStopId) {

		if(stopServiceImpl.getStopById(sourceStopId).getId() != sourceStopId) {
			Response errorResponse = new Response("404", "Not Found", "Data stop tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Trip> trips = tripServiceImpl.getAllTripBySourceStopId(sourceStopId);

		if(trips.size() != 0) {
			List<TripResponse> tripResponses = new ArrayList<>();
			
			for(Trip trip: trips) {
				tripResponses.add(new TripResponse(trip.getId(), trip.getFare(), trip.getJourneyTime(),
						trip.getSourceStop().getId(), trip.getDestStop().getId(),
						trip.getBus().getId(), trip.getAgency().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(tripResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada trip yang berangkat"
					+ " dari stop tersebut");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/dest-stop/{destStopId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getTripByDestStopId(@PathVariable(value = "destStopId") Long destStopId) {

		if(stopServiceImpl.getStopById(destStopId).getId() != destStopId) {
			Response errorResponse = new Response("404", "Not Found", "Data stop tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Trip> trips = tripServiceImpl.getAllTripByDestStopId(destStopId);

		if(trips.size() != 0) {
			List<TripResponse> tripResponses = new ArrayList<>();
			
			for(Trip trip: trips) {
				tripResponses.add(new TripResponse(trip.getId(), trip.getFare(), trip.getJourneyTime(),
						trip.getSourceStop().getId(), trip.getDestStop().getId(),
						trip.getBus().getId(), trip.getAgency().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(tripResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada trip menuju stop tersebut");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateTrip(@Valid @RequestBody TripRequest tripRequest, @PathVariable Long id) {

		int fare = tripRequest.getFare();
		int journeyTime = tripRequest.getJourneyTime();
		Long sourceStopId = tripRequest.getSourceStopId();
		Long destStopId = tripRequest.getDestStopId();
		Long busId = tripRequest.getBusId();

		Trip trip = tripServiceImpl.getTripById(id);

		if(trip.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data trip tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		if(fare == 0 || journeyTime == 0 || sourceStopId == null || destStopId == null
				|| busId == null || sourceStopId == 0 || destStopId == 0 || busId == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Bus bus = busServiceImpl.getBusById(busId);

		if(bus.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data bus tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Agency agency = bus.getAgency();

		Stop destStop = stopServiceImpl.getStopById(destStopId);

		if(destStop.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data destination stop tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Stop sourceStop = stopServiceImpl.getStopById(sourceStopId);

		if(sourceStop.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data source stop tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		if(sourceStop.getId() == destStop.getId()) {
			Response errorResponse = new Response("400", "Bad Request", "Field sourceStopId dan destStopId"
					+ " tidak boleh sama");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		trip.setFare(fare);
		trip.setJourneyTime(journeyTime);
		trip.setSourceStop(sourceStop);
		trip.setDestStop(destStop);
		trip.setBus(bus);
		trip.setAgency(agency);

		tripServiceImpl.saveTrip(trip);

		Response successResponse = new Response("200", "OK", "Trip berhasil diubah");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteTrip(@PathVariable Long id) {
		try {
			tripServiceImpl.deleteTrip(id);

			Response successResponse = new Response("200", "OK", "Trip berhasil dihapus");
			return ResponseEntity.status(HttpStatus.OK).body(successResponse);
		} catch (EmptyResultDataAccessException e) {
			Response errorResponse = new Response("404", "Not Found", "Data trip tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}
