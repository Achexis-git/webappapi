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

import com.openclassrooms.webappapi.service.FirestationService;

@WebMvcTest(controllers = FirestationController.class)
public class FirestationControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private FirestationService fsService;

	@Test
	public void testPostFirestation() throws Exception {
		String body = "{ \"address\":\"37 12th St\", \"station\":\"2\" }";
		mockMvc.perform(post("/firestation").contentType("application/json").content(body)).andExpect(status().isOk());
	}

	@Test
	public void testGetFirestation() throws Exception {
		mockMvc.perform(get("/firestation")).andExpect(status().isOk());
	}

	@Test
	public void testPutFirestation() throws Exception {
		String body = "{ \"address\":\"1509 Culver St\", \"station\":\"1\" }";
		mockMvc.perform(put("/firestation").contentType("application/json").content(body)).andExpect(status().isOk());
	}

	@Test
	public void testDeleteFirestation() throws Exception {
		String body = "{ \"address\":\"1509 Culver St\", \"station\":\"3\" }";
		mockMvc.perform(delete("/firestation").contentType("application/json").content(body))
				.andExpect(status().isOk());
	}
}
