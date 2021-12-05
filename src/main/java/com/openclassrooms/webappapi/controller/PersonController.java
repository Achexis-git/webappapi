package com.openclassrooms.webappapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.JsonRepository;

@RestController
@RequestMapping(path = "/person")
public class PersonController {
	
	@Autowired
	private JsonRepository jsonRepository;
	
	@GetMapping(path="/", produces="application/json")
	public Persons getPersons() {
		return jsonRepository.getAllPersons();
	}
	
	
}
