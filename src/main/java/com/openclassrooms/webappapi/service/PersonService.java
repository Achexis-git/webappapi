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

@Service
public class PersonService {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private JsonRepository jsonRepository;

	public Person createPerson(Person newPerson) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();

		persons.addPerson(newPerson);
		logger.info("Posted person : {} {}", newPerson.getFirstName(), newPerson.getLastName());

		jsonRepository.setPersons(persons);
		jsonRepository.save();

		return persons.getPersonList().get(persons.getPersonList().size() - 1);
	}

	public Persons readPersons() {
		jsonRepository.load();
		return jsonRepository.getAllPersons();
	}

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
					& p.getLastName().compareTo(newPerson.getLastName()) == 0) {
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

	public Person deletePerson(String firstName, String lastName) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();
		List<Person> pList = persons.getPersonList();

		Person deletedPerson = new Person();

		// 1) Browse person list
		for (int i = 0; i < pList.size(); i++) {
			Person p = pList.get(i);
			// 2) If same first name, last name
			if (p.getFirstName().compareTo(firstName) == 0 & p.getLastName().compareTo(lastName) == 0) {
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
