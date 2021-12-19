package com.openclassrooms.webappapi.model;

import com.jsoniter.annotation.JsonProperty;
import com.jsoniter.fuzzy.MaybeStringIntDecoder;

import lombok.Data;

/**
 * Firestation container
 * 
 * @author alexis
 * @version 1.0
 *
 */
@Data
public class FireStation {

	private String address;
	@JsonProperty(decoder = MaybeStringIntDecoder.class)
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
