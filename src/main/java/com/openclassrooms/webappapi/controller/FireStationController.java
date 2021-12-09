package com.openclassrooms.webappapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.repository.JsonRepository;
import com.openclassrooms.webappapi.service.FirestationService;

@RestController
public class FireStationController {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private FirestationService fsService;

	// Body example : { "address":"37 12th St", "station":"2" }
	@PostMapping(path = "/firestation", consumes = "application/json", produces = "application/json")
	public FireStation postFirestation(@RequestBody FireStation firestation) {
		return fsService.createFirestation(firestation);
	}

	@GetMapping(path = "/firestation", produces = "application/json")
	public FireStations getFireStations() {
		logger.info("Getting firestations");
		return fsService.readFirestations();
	}

	@PutMapping(path = "/firestation", consumes = "application/json", produces = "application/json")
	public FireStation putFirestation(@RequestBody FireStation firestation) {
		return fsService.updateFirestation(firestation);
	}

	@DeleteMapping(path = "/firestation", consumes = "application/json", produces = "application/json")
	public FireStation deleteFirestation(@RequestBody FireStation firestation) {
		return fsService.deleteFirestation(firestation);
	}
}
