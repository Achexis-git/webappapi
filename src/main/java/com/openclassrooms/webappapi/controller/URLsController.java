package com.openclassrooms.webappapi.controller;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.response.Home;
import com.openclassrooms.webappapi.model.response.HomeChildren;
import com.openclassrooms.webappapi.model.response.InhabitantFire;
import com.openclassrooms.webappapi.model.response.PersonInfo;
import com.openclassrooms.webappapi.model.response.PersonsAndCountdown;
import com.openclassrooms.webappapi.service.URLsService;

@RestController
public class URLsController {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private URLsService urlsService;

	// http://localhost:8080/firestation?stationNumber=<station_number>
	@GetMapping("/firestation")
	public PersonsAndCountdown getFirestationStationNumber(@RequestParam(name = "stationNumber") int sn) {
		logger.info("Request 1 received");
		return urlsService.getPeoplesCloseToFireStation(sn);
	}

	// http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	public HomeChildren getEnfantAddress(@RequestParam String address) {
		logger.info("Request 2 received");
		return urlsService.getChildAddress(address);
	}

	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@GetMapping("/phoneAlert")
	public List<String> getPhoneAlertFirestation(@RequestParam(name = "firestation") int stationNumber) {
		logger.info("Request 3 received");
		return urlsService.getPhoneCloseToFirestation(stationNumber);
	}

	// http://localhost:8080/fire?address=<address>
	@GetMapping("/fire")
	public InhabitantFire getFireAddress(@RequestParam String address) {
		logger.info("Request 4 received");
		return urlsService.getPosologieCloseToFirestation(address);
	}

	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// ex : http://localhost:8080/flood/stations?stations=1&stations=2
	@GetMapping("/flood/stations")
	public List<Home> getFloodStationsStations(@RequestParam List<Integer> stations) {
		logger.info("Request 5 received");
		return urlsService.getHomesCloseToStations(stations);
	}

	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping("/personInfo")
	public List<PersonInfo> getpersonInfoName(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("Request 6 received");
		// List<PersonInfo> pi = getService.getPersonInfo(firstName, lastName);
		return urlsService.getPersonInfo(firstName, lastName);
	}

	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmailCity(@RequestParam String city) {
		logger.info("Request 7 received");
		return urlsService.getAllEmailCity(city);
	}

}