package com.openclassrooms.webappapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.JsonRepository;

/**
 * Execute the instructions send by the associated controller
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.controller.PersonController
 *
 */
@Service
public class PersonService {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Allows interact with the file
	 */
	@Autowired
	private JsonRepository jsonRepository;

	/**
	 * Load the person, add the new person, and save the new list
	 * 
	 * @param newPerson Person record to add
	 * @return Added person
	 */
	public Person createPerson(Person newPerson) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();

		persons.addPerson(newPerson);
		logger.info("Posted person : {} {}", newPerson.getFirstName(), newPerson.getLastName());

		jsonRepository.setPersons(persons);
		jsonRepository.save();

		return persons.getPersonList().get(persons.getPersonList().size() - 1);
	}

	/**
	 * Load the persons and get them all
	 * 
	 * @return An object containing the persons
	 */
	public Persons readPersons() {
		jsonRepository.load();
		return jsonRepository.getAllPersons();
	}

	/**
	 * Load the persons, update the persons and save the new list
	 * 
	 * @param newPerson Person to update
	 * @return Updated person
	 */
	public Person updatePerson(Person newPerson) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();
		List<Person> pList = persons.getPersonList();

		Person updatedPerson = new Person();

		// 1) Browse person list
		for (int i = 0; i < pList.size(); i++) {
			Person p = pList.get(i);
			// 2) If same first name, last name
			if (p.getFirstName().compareTo(newPerson.getFirstName()) == 0
					&& p.getLastName().compareTo(newPerson.getLastName()) == 0) {
				// 3) Take list index
				persons.setPersonIndex(i, newPerson);
				logger.info("Updated person : {} {}", newPerson.getFirstName(), newPerson.getLastName());
				updatedPerson = persons.getPersonList().get(i);
				break;
			}
		}

		// 4) Set the jsonRepository
		jsonRepository.setPersons(persons);
		// 5) Save changes in file
		jsonRepository.save();

		return updatedPerson;
	}

	/**
	 * Load the persons, delete the person and save the new list
	 * 
	 * @param firstName Person first name
	 * @param lastName Person last name
	 * @return Deleted person
	 */
	public Person deletePerson(String firstName, String lastName) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();
		List<Person> pList = persons.getPersonList();

		Person deletedPerson = new Person();

		// 1) Browse person list
		for (int i = 0; i < pList.size(); i++) {
			Person p = pList.get(i);
			// 2) If same first name, last name
			if (p.getFirstName().compareTo(firstName) == 0 && p.getLastName().compareTo(lastName) == 0) {
				// 3) Delete the person
				persons.removePersonIndex(i);
				logger.info("Deleted person : {} {}", firstName, lastName);
				deletedPerson = pList.get(i);
				break;
			}
		}

		// 4) Set the jsonRepository
		jsonRepository.setPersons(persons);
		// 5) Save changes in file
		jsonRepository.save();

		return deletedPerson;
	}
}
