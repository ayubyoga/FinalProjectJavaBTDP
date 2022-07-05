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

import com.yoga.bus.models.Stop;
import com.yoga.bus.payload.request.StopRequest;
import com.yoga.bus.payload.response.StopResponse;
import com.yoga.bus.payload.response.Response;
import com.yoga.bus.service.impl.StopServiceImpl;

import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.Authorization;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/v1/stop")
public class StopController {

	@Autowired
	StopServiceImpl stopServiceImpl;

	@PostMapping("")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> createStop(@Valid @RequestBody StopRequest stopRequest) {

		String code = stopRequest.getCode();
		String name = stopRequest.getName();
		String detail = stopRequest.getDetail();

		if (code == null || name == null || detail == null || code.equals("") || name.equals("") || detail.equals("")) {

			String errorDetails = "Pastikan field tidak kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		Stop stop = new Stop(code, name, detail);

		stopServiceImpl.saveStop(stop);

		Response successResponse = new Response("200", "OK", "Stop berhasil disimpan");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@GetMapping("")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getAllStop() {

		List<Stop> stops = stopServiceImpl.getAllStop();

		if (stops.size() != 0) {
			List<StopResponse> stopResponses = new ArrayList<>();

			for (Stop stop : stops) {
				stopResponses.add(new StopResponse(stop.getId(), stop.getCode(), stop.getName(), stop.getDetail()));
			}

			return ResponseEntity.status(HttpStatus.OK).body(stopResponses);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Tidak ada stop yang tersimpan");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@GetMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN') or hasRole('USER')")
	public ResponseEntity<?> getStopById(@PathVariable Long id) {

		Stop stop = stopServiceImpl.getStopById(id);

		if (stop.getId() != null) {
			StopResponse stopResponse = new StopResponse(stop.getId(), stop.getCode(), stop.getName(),
					stop.getDetail());

			return ResponseEntity.status(HttpStatus.OK).body(stopResponse);
		} else {
			Response errorResponse = new Response("404", "Not Found", "Data stop tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}

	@PutMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> updateStop(@Valid @RequestBody StopRequest stopRequest, @PathVariable Long id) {

		String code = stopRequest.getCode();
		String name = stopRequest.getName();
		String detail = stopRequest.getDetail();

		Stop stop = stopServiceImpl.getStopById(id);

		if (stop.getId() == null) {
			Response errorResponse = new Response("404", "Not Found", "Data stop tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}

		if (code == null || name == null || detail == null || code.equals("") || name.equals("") || detail.equals("")) {

			String errorDetails = "Pastikan field tidak kosong dan tidak bernilai 0 atau null atau string kosong";
			Response errorResponse = new Response("400", "Bad Request", errorDetails);
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
		}

		stop.setCode(code);
		stop.setName(name);
		stop.setDetail(detail);

		stopServiceImpl.saveStop(stop);

		Response successResponse = new Response("200", "OK", "Stop berhasil diubah");
		return ResponseEntity.status(HttpStatus.OK).body(successResponse);
	}

	@DeleteMapping("/{id}")
	@ApiOperation(value = "", authorizations = { @Authorization(value = "apiKey") })
	@PreAuthorize("hasRole('ADMIN')")
	public ResponseEntity<?> deleteStop(@PathVariable Long id) {
		try {
			stopServiceImpl.deleteStop(id);

			Response successResponse = new Response("200", "OK", "Stop berhasil dihapus");
			return ResponseEntity.status(HttpStatus.OK).body(successResponse);
		} catch (EmptyResultDataAccessException e) {
			Response errorResponse = new Response("404", "Not Found", "Data stop tidak ada");
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
		}
	}
}