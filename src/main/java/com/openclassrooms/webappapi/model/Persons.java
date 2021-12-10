package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class Persons {
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

	public void removePerson() {

	}
}
