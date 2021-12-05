package com.openclassrooms.webappapi.model;

import lombok.Data;

@Data
public class FireStation {
	
	private String address;
	private int station;
	
	public FireStation() {
		
	}
	
	public FireStation(String address, int station) {
		this.address = address;
		this.station = station;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setStation(int station) {
		this.station = station;
	}

	public String getAddress() {
		return address;
	}

	public int getStation() {
		return station;
	}

}
