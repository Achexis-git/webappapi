package com.openclassrooms.webappapi.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MultipleElementsModelTests {
	
	@Test
	void personsCreation() {
		Persons persons = new Persons();
		
		assertThat(persons.getPersonList()).isEmpty();
	}
	
	@Test 
	void personsManipulation() {
		Persons persons = new Persons();
		List<Person> list = new ArrayList<Person>();
		list.add(new Person());
		
		persons.setPersonList(list);
		persons.addPerson(new Person());
		
		list.add(new Person());
		assertEquals(persons.getPersonList(), list);
	}
	
	@Test
	void fireStationsCreation() {
		FireStations fireStations = new FireStations();
		
		assertThat(fireStations.getFsList()).isEmpty();
	}
	
	@Test 
	void fireStationsManipulation() {
		FireStations fireStations = new FireStations();
		List<FireStation> list = new ArrayList<FireStation>();
		list.add(new FireStation());
		
		fireStations.setFsList(list);
		fireStations.addFireStation(new FireStation());
		
		list.add(new FireStation());
		assertEquals(fireStations.getFsList(), list);
	}
	
	@Test
	void medicalRecordsCreation() {
		MedicalRecords medicalRecords = new MedicalRecords();
		
		assertThat(medicalRecords.getMrList()).isEmpty();
	}
	
	@Test 
	void medicalRecordsManipulation() {
		MedicalRecords medicalRecords = new MedicalRecords();
		List<MedicalRecord> list = new ArrayList<MedicalRecord>();
		list.add(new MedicalRecord());
		
		medicalRecords.setMrList(list);
		medicalRecords.addMedicalRecord(new MedicalRecord());
		
		list.add(new MedicalRecord());
		assertEquals(medicalRecords.getMrList(), list);
	}
}








