package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.webappapi.WebappapiApplication;

public class Persons {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	private List<Person> personList;

	public Persons() {
		this.personList = new ArrayList<>();
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

	public void addPerson(Person person) {
		this.personList.add(person);
	}

	public void setPersonIndex(int i, Person p) {
		if (i >= 0 && i < personList.size()) {
			personList.set(i, p);
		} else {
			logger.error("Want to set out of index personss access");
		}
	}

	public void removePersonIndex(int i) {
		if (i >= 0 && i < personList.size()) {
			personList.remove(i);
		} else {
			logger.error("Want to delete out of index persons access");
		}
	}
}
