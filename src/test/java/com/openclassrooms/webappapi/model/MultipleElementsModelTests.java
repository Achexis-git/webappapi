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
	void personsCreationTest() {
		Persons persons = new Persons();
		
		assertThat(persons.getPersonList()).isEmpty();
	}
	
	@Test 
	void personsManipulationTest() {
		Persons persons = new Persons();
		List<Person> list = new ArrayList<Person>();
		list.add(new Person());
		
		persons.setPersonList(list);
		persons.addPerson(new Person());
		
		list.add(new Person());
		assertEquals(persons.getPersonList(), list);
	}
}
