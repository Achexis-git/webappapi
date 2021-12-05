package com.openclassrooms.webappapi.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class SingleElementModelTests {
	
	@Test
	void PersonGetterSetterTest() {
		Person person = new Person();
		
		person.setId(0);
		person.setFirstName("Jhon");
		person.setLastName("Doe");
		person.setAddress("Rue de l'église");
		person.setCity("Paris");
		person.setZip("1000");
		person.setPhone("123456");
		person.setEmail("jhon@doe.com");
		
		assertEquals(person.getId(), 0);
		assertEquals(person.getFirstName(), "Jhon");
		assertEquals(person.getLastName(), "Doe");
		assertEquals(person.getAddress(), "Rue de l'église");
		assertEquals(person.getCity(), "Paris");
		assertEquals(person.getZip(), "1000");
		assertEquals(person.getPhone(), "123456");
		assertEquals(person.getEmail(), "jhon@doe.com");
	}
	
	@Test
	void FireStationGetterSetterTest() {
		FireStation fs = new FireStation();
		
		fs.setAddress("Rue de l'église");
		fs.setStation(1);
		
		assertEquals(fs.getAddress(), "Rue de l'église");
		assertEquals(fs.getStation(), 1);
	}
	
	@Test
	void MedicalRecordGetterSetterTest() {
		MedicalRecord mr = new MedicalRecord();
		
		mr.setFirstName("Jhon");
		mr.setLastName("Doe");
		mr.setBirthdate("01/01/1900");
		
		assertEquals(mr.getFirstName(), "Jhon");
		assertEquals(mr.getLastName(), "Doe");
		assertEquals(mr.getBirthdate(), "01/01/1900");
	}
}







