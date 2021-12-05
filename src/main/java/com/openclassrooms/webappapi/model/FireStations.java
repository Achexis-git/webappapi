package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class FireStations {
	private List<FireStation> fsList;
	
	public FireStations() {
		this.fsList = new ArrayList<>();
	}
	
	public List<FireStation> getFsList() {
		return fsList;
	}
	
	public void setFsList(List<FireStation> fsList) {
		this.fsList = fsList;
	}
	
	public void addFireStation(FireStation fs) {
		this.fsList.add(fs);
	}
	
	public int numberOfFireStations() {
		return fsList.size();
	}
}
