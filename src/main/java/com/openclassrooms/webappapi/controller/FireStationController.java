package com.openclassrooms.webappapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.JsonRepository;

@RestController
@RequestMapping(path = "/firestation")
public class FireStationController {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);
	
	@Autowired
	private JsonRepository jsonRepository;
	
	@GetMapping(path="/", produces="application/json")
	public FireStations getFireStations() {
		logger.info("Hello");
		return jsonRepository.getAllFireStations();
	}
}
