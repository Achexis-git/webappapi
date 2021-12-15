package com.openclassrooms.webappapi.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.repository.JsonRepository;

@SpringBootTest
public class MedicalRecordTest {

	@Autowired
	MedicalRecordService mrService;

	@MockBean
	JsonRepository jsonRepository;

	@BeforeEach
	public void init() {
		// Configure the mock
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord mockMedicalRecord = new MedicalRecord("Winston", "Churchill", "10/06/1896", medications,
				allergies);
		MedicalRecords mockMr = new MedicalRecords();
		mockMr.addMedicalRecord(mockMedicalRecord);
		Mockito.when(jsonRepository.getAllMedicalRecords()).thenReturn(mockMr);
	}

	@Test
	public void medicalRecordPostTest() {
		// GIVEN
		// Medical Record to be added
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord postMedicalRecord = new MedicalRecord("John", "Churchill", "10/06/1896", medications, allergies);

		// WHEN
		MedicalRecord mr = mrService.createMedicalRecord(postMedicalRecord);

		// THEN
		// Verify the medical record is correctly added
		assertThat(mr.getFirstName()).isEqualTo("John");
		assertThat(mr.getLastName()).isEqualTo("Churchill");
	}

	@Test
	public void medicalRecordGetTest() {
		// WHEN
		MedicalRecords mr = mrService.readMedicalRecords();

		// THEN
		assertThat(mr.getMrList().get(0).getFirstName()).isEqualTo("Winston");
		assertThat(mr.getMrList().get(0).getLastName()).isEqualTo("Churchill");
	}

	@Test
	public void medicalRecordPutTest() {
		// GIVEN
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord putMedicalRecord = new MedicalRecord("Winston", "Churchill", "10/25/1896", medications,
				allergies);

		// WHEN
		MedicalRecord mr = mrService.updateMedicalRecord(putMedicalRecord);

		// THEN
		assertThat(mr.getBirthdate()).isEqualTo("10/25/1896");
		assertThat(mr.getFirstName()).isEqualTo("Winston");
	}

	@Test
	public void medicalRecordDeleteTest() {
		// GIVEN
		List<String> medications = new ArrayList<String>();
		List<String> allergies = new ArrayList<String>();
		MedicalRecord deleteMedicalRecord = new MedicalRecord("Winston", "Churchill", "10/06/1896", medications,
				allergies);

		// WHEN
		MedicalRecord mr = mrService.deleteMedicalRecord(deleteMedicalRecord);

		// THEN
		assertThat(mr.getFirstName()).isEqualTo("Winston");
		assertThat(mr.getLastName()).isEqualTo("Churchill");
	}
}
