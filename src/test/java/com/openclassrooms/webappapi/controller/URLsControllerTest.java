package com.openclassrooms.webappapi.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.openclassrooms.webappapi.service.URLsService;

@WebMvcTest(controllers = URLsController.class)
public class URLsControllerTest {
	@Autowired
	private MockMvc mockMvc;

	@MockBean
	private URLsService urlsService;

	@Test
	public void getFirestationStationNumber() throws Exception {
		mockMvc.perform(get("/firestation").queryParam("stationNumber", "2")).andExpect(status().isOk());
	}

	@Test
	public void getChildAlert() throws Exception {
		mockMvc.perform(get("/childAlert").queryParam("address", "1509 Culver St")).andExpect(status().isOk());
	}

	@Test
	public void getPhoneAlert() throws Exception {
		mockMvc.perform(get("/phoneAlert").queryParam("firestation", "2")).andExpect(status().isOk());
	}

	@Test
	public void getFire() throws Exception {
		mockMvc.perform(get("/fire").queryParam("address", "1509 Culver St")).andExpect(status().isOk());
	}

	@Test
	public void getFloodStations() throws Exception {
		mockMvc.perform(get("/flood/stations").queryParam("stations", "1").queryParam("stations", "2"))
				.andExpect(status().isOk());
	}

	@Test
	public void getPersonInfo() throws Exception {
		mockMvc.perform(get("/personInfo").queryParam("firstName", "John").queryParam("lastName", "Boyd"))
				.andExpect(status().isOk());
	}

	@Test
	public void getCommunityEmail() throws Exception {
		mockMvc.perform(get("/communityEmail").queryParam("city", "Culver")).andExpect(status().isOk());
	}
}
