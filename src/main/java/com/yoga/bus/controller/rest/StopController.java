package com.yoga.bus.controller.rest;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.yoga.bus.models.Stop;
import com.yoga.bus.payload.response.MessageResponse;
import com.yoga.bus.repository.StopRepository;

import io.swagger.annotations.*;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/stop")
public class StopController {

	@Autowired
	StopRepository stopRepository;

	@PostMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<?> addStop(@Valid @RequestBody Stop stop) {
		return ResponseEntity.ok(stopRepository.save(stop));
	}
	
	@GetMapping("/")
	@PreAuthorize("hasRole('ADMIN')")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	public ResponseEntity<?> getAllStops() {
		return ResponseEntity.ok(stopRepository.findAll());
	}
	
	@GetMapping("/code/{code}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> getStopByCode(@PathVariable(value = "code") String code) {
		return ResponseEntity.ok(stopRepository.findByCode(code));
	}
	
	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteStop(@PathVariable(value = "id") Long id) {
		String result = "";
		try {
			stopRepository.findById(id).get();

			result = "Berhasil menghapus data dengan ID: " + id;
			stopRepository.deleteById(id);

			return ResponseEntity.ok(new MessageResponse<Stop>(true, result));
		} catch (Exception e) {
			result = "Data dengan Id: " + id + " tidak ditemukan, silahkan masukkan Id yang valid";
			return ResponseEntity.ok(new MessageResponse<Stop>(false, result));
		}
	}
	
}
