package com.openclassrooms.webappapi.controller;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.Home;
import com.openclassrooms.webappapi.model.HomeChildren;
import com.openclassrooms.webappapi.model.PersonInfo;
import com.openclassrooms.webappapi.model.PersonPosologie;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.service.GetService;

@RestController
public class URLsController {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);
	
	@Autowired
	private GetService getService;
	
	// http://localhost:8080/firestation?stationNumber=<station_number>
	@GetMapping("/firestation")
	public Persons getPeoplesCloseToFireStation(@RequestParam(name = "stationNumber") int sn) {
		logger.info("Request 1 received");
		return getService.getFirestationStationNumber(sn);
	}
	
	// http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	public HomeChildren getEnfantAddress(@RequestParam String address){
		logger.info("Request 2 received");
		return getService.getEnfantAddress(address);
	}
	
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@GetMapping("/phoneAlert")
	public List<String> getPhoneAlertFirestation(@RequestParam(name = "firestation") int stationNumber) {
		logger.info("Request 3 received");
		return getService.getPhoneCloseToFirestation(stationNumber);
	}
	
	// http://localhost:8080/fire?address=<address>
	@GetMapping("/fire")
	public List<PersonPosologie> getFireAddress(@RequestParam String address) {
		logger.info("Request 4 received");
		return getService.getPosologieCloseToFirestation(address);
		//TODO: Compléter la méthode au dessus & fonctionne pas
	}
	
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// ex : http://localhost:8080/flood/stations?stations=1&stations=2
	@GetMapping("/flood/stations")
	public List<Home> getFloodStationsStations(@RequestParam List<Integer> stations) {
		logger.info("Request 5 received");
		return getService.getHomesCloseToStations(stations);
	}
	
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping("/personInfo")
	public List<PersonInfo> getpersonInfoName(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("Request 6 received");
		// List<PersonInfo> pi = getService.getPersonInfo(firstName, lastName); 
		return getService.getPersonInfo(firstName, lastName);
	}
		
	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmailCity(@RequestParam String city) {
		logger.info("Request 7 received");
		return getService.getAllEmailCity(city);
	}

}
