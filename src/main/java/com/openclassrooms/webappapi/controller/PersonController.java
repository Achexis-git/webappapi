package com.openclassrooms.webappapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.JsonRepository;

@RestController
public class PersonController {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private JsonRepository jsonRepository;

	/*
	 * @GetMapping(path="/", produces="application/json") public Persons
	 * getPersons() { return jsonRepository.getAllPersons(); }
	 */

	// Body example : { "id":5, "firstName":"Winston", "lastName":"Churchill,
	// "address":"951 LoneTree Rd", "city":"Culver", "zip":"97451",
	// "phone":"841-874-7458", "email":"wc@email.com" }
	@PostMapping(path = "/person", consumes = "application/json", produces = "application/json")
	public String postPerson(@RequestBody Person person) {
		logger.info("Posted person : {} {} {}", person.getId(), person.getFirstName(), person.getLastName());
		return "ok";
	}
	
	@PutMapping(path = "/person", consumes = "application/json", produces = "application/json")
	public String putPerson(@RequestBody Person person) {
		logger.info("Puted person : {} {} {}", person.getId(), person.getFirstName(), person.getLastName());
		return "ok";
	}

	// Body example : { "firstName":"Winston", "lastName":"Churchill" }
	@DeleteMapping(path = "/person", consumes = "application/json", produces = "application/json")
	public String deletePerson(@RequestBody Person person) {
		logger.info("Deleted person : {} {}", person.getFirstName(), person.getLastName());
		return "ok";
	}
}






