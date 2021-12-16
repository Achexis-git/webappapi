package com.openclassrooms.webappapi.IT;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.response.HomeChildren;
import com.openclassrooms.webappapi.service.FirestationService;
import com.openclassrooms.webappapi.service.MedicalRecordService;
import com.openclassrooms.webappapi.service.PersonService;
import com.openclassrooms.webappapi.service.URLsService;

@SpringBootTest
public class WholeRequestIT {

	@Autowired
	URLsService urlsService;

	@Autowired
	PersonService pService;

	@Autowired
	FirestationService fsService;

	@Autowired
	MedicalRecordService mrService;

	@Test
	public void getChildAddressIT() {
		// GIVEN
		String address = "1509 Culver St";

		// WHEN
		HomeChildren hc = urlsService.getChildAddress(address);

		// THEN
		assertThat(hc.getChildList().get(0).getFirstName()).isEqualTo("Tenley");
		assertThat(hc.getChildList().get(0).getLastName()).isEqualTo("Boyd");
		assertThat(hc.getAdultList().get(0).getFirstName()).isEqualTo("John");
		assertThat(hc.getAdultList().get(0).getLastName()).isEqualTo("Boyd");
	}

	@Test
	public void postPersonIT() {
		// GIVEN
		Person person = new Person(0, "Winston", "Churchill", "Rue de la Loi, 16", "Culver", "92156", "953-158-432",
				"wc@email.com");

		// WHEN
		Person postedPerson = pService.createPerson(person);

		// THEN
		assertThat(postedPerson.getFirstName()).isEqualTo("Winston");
		assertThat(postedPerson.getLastName()).isEqualTo("Churchill");
	}
}
