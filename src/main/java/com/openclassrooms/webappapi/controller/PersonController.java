package com.openclassrooms.webappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.PersonRepository;

@RestController
@RequestMapping(path = "/persons")
public class PersonController {
	
	@Autowired
	private PersonRepository personRepository;
	
	@GetMapping(path="/", produces="application/json")
	public Persons getPersons() {
		return personRepository.getAllPersons();
	}
	
	
}
