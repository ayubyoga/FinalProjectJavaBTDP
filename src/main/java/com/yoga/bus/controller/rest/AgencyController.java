package com.yoga.bus.controller.rest;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.yoga.bus.models.Agency;
import com.yoga.bus.models.User;
import com.yoga.bus.payload.request.AgencyRequest;
import com.yoga.bus.payload.response.MessageResponse;
import com.yoga.bus.repository.AgencyRepository;
import com.yoga.bus.repository.BusRepository;
import com.yoga.bus.repository.UserRepository;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/agency")
public class AgencyController {

	@Autowired
	AgencyRepository agencyRepository;

	@Autowired
	UserRepository userRepository;

	@Autowired
	BusRepository busRepository;

	@GetMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAll() {
		List<AgencyRequest> dataArrResult = new ArrayList<>();
		for (Agency dataArr : agencyRepository.findAll()) {
			dataArrResult.add(new AgencyRequest(dataArr.getId(), dataArr.getCode(), dataArr.getName(),
					dataArr.getDetails(), dataArr.getOwner().getId()));
		}
		return ResponseEntity.ok(new MessageResponse<AgencyRequest>(true, "Berhasil", dataArrResult));
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getAgencyById(@PathVariable(value = "id") Long id) {
		Agency agency = agencyRepository.findById(id).get();
		if (agency == null) {
			return ResponseEntity.notFound().build();
		} else {
			AgencyRequest dataResult = new AgencyRequest(agency.getId(), agency.getCode(), agency.getName(),
					agency.getDetails(), agency.getOwner().getId());
			return ResponseEntity.ok(new MessageResponse<AgencyRequest>(true, "Berhasil", dataResult));
		}
	}

	@PostMapping("/")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> addAgency(@Valid @RequestBody AgencyRequest agencyRequest) {
		User user = userRepository.findById(agencyRequest.getOwner()).get();
		Agency agency = new Agency(agencyRequest.getCode(), agencyRequest.getDetails(), agencyRequest.getName(), user);
		return ResponseEntity
				.ok(new MessageResponse<Agency>(true, "Berhasil menambahkan data", agencyRepository.save(agency)));
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateAgency(@PathVariable(value = "id") Long id,
			@Valid @RequestBody AgencyRequest agencyDetail) {
		Agency agency = agencyRepository.findById(id).get();
		User user = userRepository.findById(agencyDetail.getOwner()).get();
		if (agency == null) {
			return ResponseEntity.notFound().build();
		}
		agency.setCode(agencyDetail.getCode());
		agency.setDetails(agencyDetail.getDetails());
		agency.setName(agencyDetail.getName());
		agency.setOwner(user);

		Agency updatedAgency = agencyRepository.save(agency);

		return ResponseEntity.ok(new MessageResponse<Agency>(true, "Success Updating Data", updatedAgency));
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteAgency(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			agencyRepository.findById(id).get();

			result = "Berhasil menghapus data dengan ID: " + id;
			agencyRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<Agency>(true, result));
		} catch (Exception e) {
			result = "Data dengan ID: " + id + " Tidak Ditemukan, silahkan masukkan ID yang valid";
			return ResponseEntity.ok(new MessageResponse<Agency>(false, result));
		}
	}

}
