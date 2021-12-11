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
		// Paramétrise le mock
		FireStation mockFs = new FireStation();
		mockFs.setAddress("Rue de la loi");
		mockFs.setStation(1);
		FireStations mockFirestations = new FireStations();
		mockFirestations.addFireStation(mockFs);
		Mockito.when(jsonRepository.getAllFireStations()).thenReturn(mockFirestations);
	}
	
	@Test
	public void firestationPostTest() {
		// Station to be added
		FireStation postFs = new FireStation();
		postFs.setAddress("Rue de l'église");
		postFs.setStation(2);
		
		FireStation fs = fsService.createFirestation(postFs);
		
		// Vérifie que la station ajoutée est la bonne
		assertThat(fs.getAddress()).isEqualTo("Rue de l'église");
		assertThat(fs.getStation()).isEqualTo(2);
	}

	@Test
	public void firestationGetTest() {

		// Test la méthode
		FireStations firestations = fsService.readFirestations();

		// Vérifie le résultat
		assertThat(firestations.getFsList().get(0).getAddress()).isEqualTo("Rue de la loi");
		assertThat(firestations.getFsList().get(0).getStation()).isEqualTo(1);
	}

	// TODO: Compléter les autres tests

}








