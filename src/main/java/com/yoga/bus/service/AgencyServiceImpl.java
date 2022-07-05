package com.yoga.bus.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

import com.yoga.bus.models.Agency;
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.AgencyRequest;
import com.yoga.bus.repository.AgencyRepository;
import com.yoga.bus.repository.UserRepository;

@Component
public class AgencyServiceImpl implements AgencyService {

	@Autowired
	private AgencyRepository agencyRepository;
	
	@Autowired
	private UserRepository userRepository;

	@Override
	public Agency addNewAgency(AgencyRequest agencyRequest) {
		
		Optional<User> user = userRepository.findById(agencyRequest.getOwner());

		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}

		try {
			Agency agency = new Agency(
					agencyRequest.getCode(),
					agencyRequest.getDetails(),
					agencyRequest.getName(),
					user.get());

			Agency savedAgency = agencyRepository.save(agency);
			return savedAgency;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

	@Override
	public Agency updatingAgency(Long id, AgencyRequest agencyDetail) {

		Optional<Agency> agency = agencyRepository.findById(id);
		Optional<User> user = userRepository.findById(agencyDetail.getOwner());

		if (!user.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
		}

		if (!agency.isPresent()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Agency not found");
		}

		try {
			agency.get().setCode(agencyDetail.getCode());
			agency.get().setDetails(agencyDetail.getDetails());
			agency.get().setName(agencyDetail.getName());
			agency.get().setOwner(user.get());

			Agency updatedAgency = agencyRepository.save(agency.get());
			return updatedAgency;

		} catch (Exception e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, e.getMessage(), e.getCause());
		}
	}

}