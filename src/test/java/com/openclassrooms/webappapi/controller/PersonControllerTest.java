package com.openclassrooms.webappapi.controller;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.openclassrooms.webappapi.service.PersonService;

@WebMvcTest(controllers = PersonController.class)
public class PersonControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private PersonService pService;

	@Test
	public void postFirestationTest() throws Exception {
		String body = "{ \"firstName\":\"Winston\", \"lastName\":\"Churchill\", "
				+ "\"address\":\"951 LoneTree Rd\", \"city\":\"Culver\", \"zip\":\"97451\","
				+ "\"phone\":\"841-874-7458\", \"email\":\"wc@email.com\" }";
		mockMvc.perform(post("/person").contentType("application/json").content(body)).andExpect(status().isOk());
	}

	@Test
	public void getFirestationTest() throws Exception {
		mockMvc.perform(get("/person")).andExpect(status().isOk());
	}

	@Test
	public void putFirestationTest() throws Exception {
		String body = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\", "
				+ "\"address\":\"951 LoneTree Rd\", \"city\":\"Culver\", \"zip\":\"97451\","
				+ "\"phone\":\"841-874-7458\", \"email\":\"wc@email.com\" }";
		mockMvc.perform(put("/person").contentType("application/json").content(body)).andExpect(status().isOk());
	}

	@Test
	public void deleteFirestationTest() throws Exception {
		String body = "{ \"firstName\":\"John\", \"lastName\":\"Boyd\" }";
		mockMvc.perform(delete("/person").contentType("application/json").content(body)).andExpect(status().isOk());
	}
}
