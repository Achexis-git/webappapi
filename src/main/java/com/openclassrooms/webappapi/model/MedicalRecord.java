package com.openclassrooms.webappapi.model;

import java.util.List;
import java.util.Map;

import lombok.Data;

public class MedicalRecord {
	
	private String firstName;
	private String lastName;
	private String birthdate;
	private Map<String,String> medication;
	private List<String> allergies;
	
	public MedicalRecord() {
		
	}
	
	public MedicalRecord(String firstName, String lastName, String birthdate, Map<String, String> medication, List<String> allergies) {
		this.firstName  = firstName;
		this.lastName   = lastName;
		this.birthdate  = birthdate;
		this.medication = medication;
		this.allergies  = allergies;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public void setBirthdate(String birthdate) {
		this.birthdate = birthdate;
	}

	public String getFirstName() {
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public String getBirthdate() {
		return birthdate;
	}
}
