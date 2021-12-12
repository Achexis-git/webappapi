package com.openclassrooms.webappapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.JsonRepository;

@SpringBootTest
public class PersonServiceTest {

	@Autowired
	private PersonService personService;

	@MockBean
	private JsonRepository jsonRepository;

	@BeforeEach
	public void init() {
		// Paramétrise le mock
		Person mockPerson = new Person(0, "Winston", "Churchill", "Rue de la Loi, 16", "Culver", "92156", "953-158-432",
				"wc@email.com");
		Persons mockPersons = new Persons();
		mockPersons.addPerson(mockPerson);
		Mockito.when(jsonRepository.getAllPersons()).thenReturn(mockPersons);
	}

	@Test
	public void personPostTest() {
		// Station to be added
		Person postPerson = new Person(0, "John", "Boyd", "Rue de la Loi, 16", "Culver", "92156", "953-158-432",
				"jb@email.com");

		Person person = personService.postPerson(postPerson);

		// Vérifie que la person ajoutée est la bonne
		assertThat(person.getFirstName()).isEqualTo("John");
		assertThat(person.getLastName()).isEqualTo("Boyd");
	}

	@Test
	public void personGetTest() {
		Persons persons = personService.readPersons();

		assertThat(persons.getPersonList().get(0).getFirstName()).isEqualTo("Winston");
		assertThat(persons.getPersonList().get(0).getLastName()).isEqualTo("Churchill");
	}

	@Test
	public void personPutTest() {
		Person putPerson = new Person(0, "Winston", "Churchill", "Rue de la Loi, 16", "Londres", "92156", "953-158-432",
				"jb@email.com");

		Person person = personService.putPerson(putPerson);

		assertThat(person.getCity()).isEqualTo("Londres");
		assertThat(person.getEmail()).isEqualTo("jb@email.com");
	}

	@Test
	public void personDeleteTest() {
		Person mockPerson = new Person(0, "Winston", "Churchill", "Rue de la Loi, 16", "Culver", "92156", "953-158-432",
				"wc@email.com");

		Person person = personService.deletePerson(mockPerson.getFirstName(), mockPerson.getLastName());

		assertThat(person.getFirstName()).isEqualTo("Winston");
		assertThat(person.getLastName()).isEqualTo("Churchill");
	}
}
