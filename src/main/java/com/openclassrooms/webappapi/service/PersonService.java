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
	
	public Person postPerson (Person person) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();
		List<Person> pList = persons.getPersonList();
		
		pList.add(person);
		persons.setPersonList(pList);
		
		jsonRepository.setPersons(persons);
		jsonRepository.save();
		
		return person;
	}
	
	public Person putPerson(Person person) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();
		List<Person> pList = persons.getPersonList();
		
		Person updatedPerson = new Person();
		int j = 0;
		
		// 1) Parcours la liste
		for (int i = 0; i <pList.size(); i++) {
			Person p = pList.get(i);
			// 2) Si même nom prénom
			if (p.getFirstName().compareTo(person.getFirstName()) == 0 & p.getLastName().compareTo(person.getLastName()) == 0) {
				// 3) Take list index
				j = i;
				break;
			}
		}
		// 4) Replace the person
		pList.set(j, person);
		updatedPerson = pList.get(j);
		
		// 6) Set l'objet persons
		persons.setPersonList(pList);
		// 7) Set le jsonRepository
		jsonRepository.setPersons(persons);
		// 8) Save les changements dans le fichier
		jsonRepository.save();
		
		return updatedPerson;
	}
	
	public Person deletePerson(String firstName, String lastName) {
		jsonRepository.load();
		Persons persons = jsonRepository.getAllPersons();
		List<Person> pList = persons.getPersonList();
		
		Person deletedPerson = new Person();
		
		// 1) Parcours la liste
		for (Person p : pList) {
			// 2) Si même nom prénom
			if (p.getFirstName().compareTo(firstName) == 0 & p.getLastName().compareTo(lastName) == 0) {
				// 3) Delete la personne
				pList.remove(p);
				deletedPerson = p;
				break;
			}
		}
		
		// 4) Set l'object persons
		persons.setPersonList(pList);
		// 5) Set le jsonRepository
		jsonRepository.setPersons(persons);
		// 6) Save les changements dans le fichier
		jsonRepository.save();
		
		return deletedPerson;
	}
}
