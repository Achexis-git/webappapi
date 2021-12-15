package com.openclassrooms.webappapi.model.response;

import java.util.ArrayList;
import java.util.List;

public class HomeInhabitant {
	private String firstName;
	private String lastName;
	private String phone;
	private int age;
	private List<String> medication;
	private List<String> allergies;

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setPhone(String phone) {
		this.phone = phone;
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

	public String getPhone() {
		return phone;
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
