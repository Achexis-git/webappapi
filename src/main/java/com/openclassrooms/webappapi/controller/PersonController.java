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
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.service.PersonService;

/**
 * CRUD controller for persons
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.service.PersonService
 *
 */
@RestController
public class PersonController {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Required to process requests
	 */
	@Autowired
	private PersonService personService;

	@GetMapping(path = "/person", produces = "application/json")
	public Persons getPersons() {
		logger.info("Get all persons");
		return personService.readPersons();
	}

	// Body example : { "id":0, "firstName":"Winston", "lastName":"Churchill,
	// "address":"951 LoneTree Rd", "city":"Culver", "zip":"97451",
	// "phone":"841-874-7458", "email":"wc@email.com" }
	@PostMapping(path = "/person", consumes = "application/json", produces = "application/json")
	public Person postPerson(@RequestBody Person person) {
		logger.info("Post a new person");
		return personService.createPerson(person);
	}

	// Body example : { "firstName":"John", "lastName":"Boyd", "address":"1509
	// Culver St", "city":"Culver", "zip":"91861", "phone":"841-874-6512",
	// "email":"jb@email.com" }
	@PutMapping(path = "/person", consumes = "application/json", produces = "application/json")
	public Person putPerson(@RequestBody Person person) {
		logger.info("Update a person");
		return personService.updatePerson(person);
	}

	// Body example : { "firstName":"John", "lastName":"Boyd" }
	@DeleteMapping(path = "/person", consumes = "application/json", produces = "application/json")
	public Person deletePerson(@RequestBody Person person) {
		logger.info("Delete a person");
		return personService.deletePerson(person.getFirstName(), person.getLastName());
	}
}
