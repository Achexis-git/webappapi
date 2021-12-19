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

/**
 * Controller to get intricate informations
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.service.URLsService
 *
 */
@RestController
public class URLsController {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Required to process requests
	 */
	@Autowired
	private URLsService urlsService;

	/**
	 * Get a list of persons covered by the firestation
	 * 
	 * @param sn Firestation number
	 * @return An object containing a list of persons covered and a countdown of the
	 *         children and adults
	 */
	// http://localhost:8080/firestation?stationNumber=<station_number>
	@GetMapping("/firestation")
	public PersonsAndCountdown getFirestationStationNumber(@RequestParam(name = "stationNumber") int sn) {
		logger.info("Request 1 received");
		return urlsService.getPeoplesCloseToFireStation(sn);
	}

	/**
	 * Get the children and adults at the address
	 * 
	 * @param address Home address
	 * @return An object containing the address, a list of childs, a list of adults
	 */
	// http://localhost:8080/childAlert?address=<address>
	@GetMapping("/childAlert")
	public HomeChildren getEnfantAddress(@RequestParam String address) {
		logger.info("Request 2 received");
		return urlsService.getChildAddress(address);
	}

	/**
	 * Get phone numbers of persons served by a firestation
	 * 
	 * @param stationNumber Firestation number
	 * @return A list of strings with the phone numbers
	 */
	// http://localhost:8080/phoneAlert?firestation=<firestation_number>
	@GetMapping("/phoneAlert")
	public List<String> getPhoneAlertFirestation(@RequestParam(name = "firestation") int stationNumber) {
		logger.info("Request 3 received");
		return urlsService.getPhoneCloseToFirestation(stationNumber);
	}

	/**
	 * Get informations of the home linked to an address
	 * 
	 * @param address Address of the home
	 * @return An object containing the firestation that serve this address and a
	 *         home containing informations about the inhabitants
	 */
	// http://localhost:8080/fire?address=<address>
	@GetMapping("/fire")
	public InhabitantFire getFireAddress(@RequestParam String address) {
		logger.info("Request 4 received");
		return urlsService.getPosologieCloseToFirestation(address);
	}

	/**
	 * Get iformations about the home served by several firestations
	 * 
	 * @param stations List containing the firstation numbers
	 * @return A list of home of served by the firestations
	 */
	// http://localhost:8080/flood/stations?stations=<a list of station_numbers>
	// ex : http://localhost:8080/flood/stations?stations=1&stations=2
	@GetMapping("/flood/stations")
	public List<Home> getFloodStationsStations(@RequestParam List<Integer> stations) {
		logger.info("Request 5 received");
		return urlsService.getHomesCloseToStations(stations);
	}

	/**
	 * Get personal informations about a person
	 * 
	 * @param firstName Person first name
	 * @param lastName Person last name
	 * @return An object containing the personal informations
	 */
	// http://localhost:8080/personInfo?firstName=<firstName>&lastName=<lastName>
	@GetMapping("/personInfo")
	public List<PersonInfo> getpersonInfoName(@RequestParam String firstName, @RequestParam String lastName) {
		logger.info("Request 6 received");
		// List<PersonInfo> pi = getService.getPersonInfo(firstName, lastName);
		return urlsService.getPersonInfo(firstName, lastName);
	}

	/**
	 * Get the email addresses of a city's inhabitants
	 * 
	 * @param city City name
	 * @return List of strings of the email addresses
	 */
	// http://localhost:8080/communityEmail?city=<city>
	@GetMapping("/communityEmail")
	public List<String> getCommunityEmailCity(@RequestParam String city) {
		logger.info("Request 7 received");
		return urlsService.getAllEmailCity(city);
	}

}