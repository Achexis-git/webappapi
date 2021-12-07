package com.openclassrooms.webappapi.model;

public class PersonsAndCountdown {
	private Persons persons;
	private int adultsCountdown;
	private int childrenCountdown;

	public void setPersons(Persons persons) {
		Persons p = new Persons();
		p.setPersonList(persons.getPersonList());
		this.persons = p;
	}

	public void setAdultCountdown(int adultCountdown) {
		this.adultsCountdown = adultCountdown;
	}

	public void setChildrenCountdown(int childrenCountdown) {
		this.childrenCountdown = childrenCountdown;
	}

	public Persons getPersons() {
		Persons p = new Persons();
		p.setPersonList(persons.getPersonList());
		return p;
	}

	public int getAdultCountdown() {
		return adultsCountdown;
	}

	public int getChildrenCountdown() {
		return childrenCountdown;
	}
}
