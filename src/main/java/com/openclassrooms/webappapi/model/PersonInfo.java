package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class PersonInfo {
	private String firstName;
	private String lastName;
	private String email;
	private int age;
	private List<String> medication;
	private List<String> allergies;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setMedication(List<String> medication) {
		this.medication = medication;
	}

	public void setAllergies(List<String> allergies) {
		this.allergies = allergies;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getEmail() {
		return email;
	}

	public int getAge() {
		return age;
	}

	public List<String> getMedication() {
		return new ArrayList<String>(medication);
	}

	public List<String> getAllergies() {
		return new ArrayList<String>(allergies);
	}
}
