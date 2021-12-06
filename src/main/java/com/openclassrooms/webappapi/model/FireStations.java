package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class FireStations {
	private List<FireStation> fsList;
	
	public FireStations() {
		this.fsList = new ArrayList<>();
	}
	
	public List<FireStation> getFsList() {
		return new ArrayList<FireStation>(fsList);
	}
	
	public void setFsList(List<FireStation> fsList) {
		this.fsList = new ArrayList<FireStation>(fsList);
	}
	
	public void addFireStation(FireStation fs) {
		this.fsList.add(fs);
	}
	
	public int numberOfFireStations() {
		return fsList.size();
	}
}
