package com.openclassrooms.webappapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.webappapi.service.MedicalRecordService;

@WebMvcTest(controllers = MedicalRecordController.class)
public class MedicalRecordControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private MedicalRecordService mrService;

	@Test
	public void postMedicalRecordTest() throws Exception {
		String body = "{ \"firstName\":\"Winston\", \"lastName\":\"Churchill\", \"birthdate\":\"03/06/1984\", "
				+ "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";
		mockMvc.perform(post("/medicalrecord").contentType("application/json").content(body))
				.andExpect(status().isOk());
	}

	@Test
	public void getMedicalRecordTest() throws Exception {
		mockMvc.perform(get("/medicalrecord")).andExpect(status().isOk());
	}

	@Test
	public void putMedicalRecord() throws Exception {
		String body = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", "
				+ "\"medications\":[], \"allergies\":[] }";
		mockMvc.perform(put("/medicalrecord").contentType("application/json").content(body)).andExpect(status().isOk());
	}

	@Test
	public void deleteMedicalRecordTest() throws Exception {
		String body = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", \"birthdate\":\"03/06/1984\", "
				+ "\"medications\":[\"aznol:350mg\", \"hydrapermazol:100mg\"], \"allergies\":[\"nillacilan\"] }";
		mockMvc.perform(delete("/medicalrecord").contentType("application/json").content(body))
				.andExpect(status().isOk());
	}
}
