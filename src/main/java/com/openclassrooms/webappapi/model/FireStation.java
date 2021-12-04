package com.openclassrooms.webappapi.model;

import lombok.Data;

@Data
public class FireStation {
	
	private String address;
	private String station;
	
	public FireStation() {
		
	}
	
	public FireStation(String address, String station) {
		this.address = address;
		this.station = station;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStation(String station) {
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public String getStation() {
		return station;
	}

}
