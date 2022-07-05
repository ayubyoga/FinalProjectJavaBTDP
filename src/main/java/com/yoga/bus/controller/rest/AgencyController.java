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
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.AgencyRequest;
import com.yoga.bus.payload.response.AgencyResponse;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.service.impl.AgencyServiceImpl;
import com.yoga.bus.service.impl.UserServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {

	@Autowired
	AgencyServiceImpl agencyServiceImpl;

	@Autowired
	UserServiceImpl userServiceImpl;

	@PostMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createAgency(@Valid @RequestBody AgencyRequest agencyRequest) {

		String code = agencyRequest.getCode();
		String name = agencyRequest.getName();
		String details = agencyRequest.getDetails();
		Long ownerId = agencyRequest.getOwnerId();

		if(code == null || name == null || details == null || ownerId == null
				|| code.equals("") || name.equals("") || details.equals("") || ownerId == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		User owner = userServiceImpl.getUserById(ownerId);

		if(owner.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data owner tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Agency agency = new Agency(code, name, details, owner);

		agencyServiceImpl.saveAgency(agency);

		Response successResponse = new Response("200", "OK", "Agency berhasil disimpan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@GetMapping("")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAllAgency() {

		List<Agency> agencies = agencyServiceImpl.getAllAgency();

		if(agencies.size() != 0) {
			List<AgencyResponse> agencyResponses = new ArrayList<>();
			
			for (Agency agency : agencies) {
				agencyResponses.add(new AgencyResponse(agency.getId(), agency.getCode(), agency.getName(),
						agency.getDetails(), agency.getOwner().getId()));
			}
			
			return ResponseEntity.status(HttpStatus.OK).body(agencyResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada Agency yang tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAgencyById(@PathVariable Long id) {

		Agency agency = agencyServiceImpl.getAgencyById(id);

		if(agency.getId() != null) {
			AgencyResponse agencyResponse = new AgencyResponse(agency.getId(), agency.getCode(),
					agency.getName(), agency.getDetails(), agency.getOwner().getId());
		
			return ResponseEntity.status(HttpStatus.OK).body(agencyResponse);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Data agency tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
	
	@GetMapping("/owner/{ownerId}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAgencyByOwnerId(@PathVariable(value = "ownerId") Long ownerId) {

		if(userServiceImpl.getUserById(ownerId).getId() != ownerId) {
			Response errorResponse = new Response("404", "Not Found", "Data owner tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		List<Agency> agencies = agencyServiceImpl.getAllAgencyByOwnerId(ownerId);
		
		if(agencies.size() != 0) {
			List<AgencyResponse> agencyResponses = new ArrayList<>();
			
			for(Agency agency: agencies) {
				agencyResponses.add(new AgencyResponse(agency.getId(), agency.getCode(),
						agency.getName(), agency.getDetails(), agency.getOwner().getId()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(agencyResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "User tidak memiliki Agency");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateAgency(@Valid @RequestBody AgencyRequest agencyRequest, @PathVariable Long id) {

		String code = agencyRequest.getCode();
		String name = agencyRequest.getName();
		String details = agencyRequest.getDetails();
		Long ownerId = agencyRequest.getOwnerId();
		
		Agency agency = agencyServiceImpl.getAgencyById(id);

		if(agency.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data agency tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		if(code == null || name == null || details == null || ownerId == null
				|| code.equals("") || name.equals("") || details.equals("") || ownerId == 0) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		User owner = userServiceImpl.getUserById(ownerId);

		if(owner.getId() == null) {
			Response errorResponse = new Response("400", "Bad Request", "Data owner tidak ada");
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		agency.setCode(code);
		agency.setName(name);
		agency.setDetails(details);
		agency.setOwner(owner);

		agencyServiceImpl.saveAgency(agency);

		Response successResponse = new Response("200", "OK", "Agency berhasil diubah");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = {@Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteAgency(@PathVariable Long id) {
	
		try {
			agencyServiceImpl.deleteAgency(id);
			
	
			Response successResponse = new Response("200", "OK", "Agency berhasil dihapus");
			return ResponseEntity.status(HttpStatus.OK).body(successResponse);
		} catch (EmptyResultDataAccessException e) {
			
			Response errorResponse = new Response("404", "Not Found", "Data agency tidak ditemukan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}