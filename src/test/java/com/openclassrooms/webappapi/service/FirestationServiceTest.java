package com.openclassrooms.webappapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.repository.JsonRepository;

@SpringBootTest
public class FirestationServiceTest {

	@Autowired
	private FirestationService fsService;

	@MockBean
	private JsonRepository jsonRepository;

	@BeforeEach
	public void init() {
		// Configure the mock
		FireStation mockFs = new FireStation();
		mockFs.setAddress("Rue de la loi");
		mockFs.setStation(1);
		FireStations mockFirestations = new FireStations();
		mockFirestations.addFireStation(mockFs);
		Mockito.when(jsonRepository.getAllFireStations()).thenReturn(mockFirestations);
	}

	@Test
	public void firestationPostTest() {
		// GIVEN
		// Station to be added
		FireStation postFs = new FireStation();
		postFs.setAddress("Rue de l'église");
		postFs.setStation(2);

		// WHEN
		FireStation fs = fsService.createFirestation(postFs);

		// THEN
		// Verify that the station is correctly added
		assertThat(fs.getAddress()).isEqualTo("Rue de l'église");
		assertThat(fs.getStation()).isEqualTo(2);
	}

	@Test
	public void firestationGetTest() {
		// WHEN
		FireStations firestations = fsService.readFirestations();

		// THEN
		assertThat(firestations.getFsList().get(0).getAddress()).isEqualTo("Rue de la loi");
		assertThat(firestations.getFsList().get(0).getStation()).isEqualTo(1);
	}

	@Test
	public void firestationPutTest() {
		// GIVEN
		// Station to be updated
		FireStation putFs = new FireStation();
		putFs.setAddress("Rue de la loi");
		putFs.setStation(2);

		// WHEN
		FireStation fs = fsService.updateFirestation(putFs);

		// THEN
		assertThat(fs.getAddress()).isEqualTo("Rue de la loi");
		assertThat(fs.getStation()).isEqualTo(2);
	}

	@Test
	public void firestationDeleteTest() {
		// GIVEN
		FireStation deleteFs = new FireStation();
		deleteFs.setAddress("Rue de la loi");
		deleteFs.setStation(1);

		// WHEN
		FireStation fs = fsService.deleteFirestation(deleteFs);

		// THEN
		assertThat(fs.getAddress()).isEqualTo("Rue de la loi");
		assertThat(fs.getStation()).isEqualTo(1);
	}
}
