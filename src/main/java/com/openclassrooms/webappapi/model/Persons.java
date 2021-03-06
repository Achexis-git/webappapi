package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.webappapi.WebappapiApplication;

/**
 * Handling of a list of persons
 * 
 * @author alexis
 * @version 1.0
 *
 */
public class Persons {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	private List<Person> personList;

	public Persons() {
		this.personList = new ArrayList<>();
	}

	public void resetList() {
		personList = new ArrayList<>();
	}
	
	public List<Person> getPersonList() {
		if (personList == null) {
			personList = new ArrayList<>();
		}
		return new ArrayList<Person>(personList);
	}

	public void setPersonList(List<Person> personList) {
		this.personList = new ArrayList<Person>(personList);
	}

	/**
	 * Add a new person at the end of the list
	 * 
	 * @param person Person to add
	 */
	public void addPerson(Person person) {
		this.personList.add(person);
	}

	/**
	 * Modify person at the given index
	 * 
	 * @param i Index
	 * @param p New person
	 */
	public void setPersonIndex(int i, Person p) {
		if (i >= 0 && i < personList.size()) {
			personList.set(i, p);
		} else {
			logger.error("Want to set out of index personss access");
		}
	}

	/**
	 * Remove person at the given index
	 * 
	 * @param i Index
	 */
	public void removePersonIndex(int i) {
		if (i >= 0 && i < personList.size()) {
			personList.remove(i);
		} else {
			logger.error("Want to delete out of index persons access");
		}
	}
}
