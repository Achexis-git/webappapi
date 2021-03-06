package com.openclassrooms.webappapi.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.model.response.Home;
import com.openclassrooms.webappapi.model.response.HomeChildren;
import com.openclassrooms.webappapi.model.response.InhabitantFire;
import com.openclassrooms.webappapi.model.response.PersonInfo;
import com.openclassrooms.webappapi.model.response.PersonsAndCountdown;
import com.openclassrooms.webappapi.repository.JsonRepository;

// TODO: Renommer les tests

@SpringBootTest
public class URLsServiceTest {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private URLsService urlsService;

	@MockBean
	private JsonRepository jsonRepository;

	@BeforeEach
	public void init() {
		// GIVEN
		// Configure mock person
		Person mockPerson = new Person(0, "Winston", "Churchill", "Rue de la Loi, 16", "Culver", "92156", "953-158-432",
				"wc@email.com");
		Persons mockPersons = new Persons();
		mockPersons.addPerson(mockPerson);
		Mockito.when(jsonRepository.getAllPersons()).thenReturn(mockPersons);

		// Configure mock firestation
		FireStation mockFs = new FireStation();
		mockFs.setAddress("Rue de la Loi, 16");
		mockFs.setStation(1);
		FireStations mockFirestations = new FireStations();
		mockFirestations.addFireStation(mockFs);
		Mockito.when(jsonRepository.getAllFireStations()).thenReturn(mockFirestations);

		// Configure mock medical record
		List<String> medications = new ArrayList<String>();
		medications.add("tetracyclaz:650mg");
		List<String> allergies = new ArrayList<String>();
		allergies.add("xilliathal");
		MedicalRecord mockMedicalRecord = new MedicalRecord("Winston", "Churchill", "10/06/1896", medications,
				allergies);
		MedicalRecords mockMr = new MedicalRecords();
		mockMr.addMedicalRecord(mockMedicalRecord);
		Mockito.when(jsonRepository.getAllMedicalRecords()).thenReturn(mockMr);
	}

	@Test
	public void getPeopleCloseToFirestationTest() {
		// WHEN
		PersonsAndCountdown pacd = urlsService.getPeoplesCloseToFireStation(1);

		// THEN
		assertThat(pacd.getAdultCountdown()).isEqualTo(1);
		assertThat(pacd.getChildrenCountdown()).isZero();
		verify(jsonRepository).getAllFireStations();
		verify(jsonRepository).getAllMedicalRecords();
		verify(jsonRepository).getAllPersons();
	}

	@Test
	public void getChildAddressTest() {
		// GIVEN
		// Add a child to the mock
		// Configure mock person
		Person mockPerson = new Person(0, "John", "Churchill", "Rue de la Loi, 16", "Culver", "92156", "953-158-432",
				"wc@email.com");
		Persons mockPersons = new Persons();
		mockPersons.addPerson(mockPerson);
		Mockito.when(jsonRepository.getAllPersons()).thenReturn(mockPersons);

		// Configure mock medical record
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord mockMedicalRecord = new MedicalRecord("John", "Churchill", "10/06/2010", medications, allergies);
		MedicalRecords mockMr = new MedicalRecords();
		mockMr.addMedicalRecord(mockMedicalRecord);
		Mockito.when(jsonRepository.getAllMedicalRecords()).thenReturn(mockMr);

		// WHEN
		HomeChildren hc = urlsService.getChildAddress("Rue de la Loi, 16");

		// THEN
		assertThat(hc.getChildList().get(0).getFirstName()).isEqualTo("John");
		assertThat(hc.getChildList().get(0).getLastName()).isEqualTo("Churchill");
		assertThat(hc.getAdultList().size()).isZero();
		verify(jsonRepository).getAllPersons();
		verify(jsonRepository).getAllMedicalRecords();
	}

	@Test
	public void getPhoneCloseToFirestationTest() {
		// WHEN
		List<String> phones = urlsService.getPhoneCloseToFirestation(1);

		// THEN
		assertThat(phones.get(0)).isEqualTo("953-158-432");
		verify(jsonRepository).getAllFireStations();
		verify(jsonRepository).getAllPersons();
	}

	@Test
	public void getAllEmailCityTest() {
		// WHEN
		List<String> emails = urlsService.getAllEmailCity("Culver");

		// THEN
		assertThat(emails.get(0)).isEqualTo("wc@email.com");
		verify(jsonRepository).getAllPersons();
	}

	@Test
	public void getPosologieCloseToFirestationTest() {
		// WHEN
		InhabitantFire iFire = urlsService.getPosologieCloseToFirestation("Rue de la Loi, 16");

		// THEN
		assertThat(iFire.getStationNumber()).isEqualTo(1);
		assertThat(iFire.getHome().getHomeInhabitantList().get(0).getFirstName()).isEqualTo("Winston");
		verify(jsonRepository).getAllFireStations();
		verify(jsonRepository).getAllMedicalRecords();
		verify(jsonRepository).getAllPersons();
	}

	@Test
	public void getHomesCloseToStationsTest() {
		// GIVEN
		List<Integer> stations = new ArrayList<>();
		stations.add(1);

		// WHEN
		List<Home> homes = urlsService.getHomesCloseToStations(stations);

		// THEN
		assertThat(homes.size()).isNotZero();
		assertThat(homes.get(0).getAddress()).isEqualTo("Rue de la Loi, 16");
		assertThat(homes.get(0).getHomeInhabitantList().get(0).getFirstName()).isEqualTo("Winston");
		verify(jsonRepository).getAllFireStations();
		verify(jsonRepository).getAllMedicalRecords();
		verify(jsonRepository).getAllPersons();
	}

	@Test
	public void getPersonInfoTest() {
		// WHEN
		List<PersonInfo> pi = urlsService.getPersonInfo("Winston", "Churchill");

		// THEN
		assertThat(pi.get(0).getEmail()).isEqualTo("wc@email.com");
		verify(jsonRepository).getAllPersons();
	}
}
