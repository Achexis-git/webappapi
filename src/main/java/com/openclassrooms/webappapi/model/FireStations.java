package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.webappapi.WebappapiApplication;

/**
 * Handling of a list of firestations
 * 
 * @author alexis
 * @version 1.0
 *
 */
public class FireStations {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

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

	/**
	 * Add a firestation at the end of the list
	 * 
	 * @param fs Firestation to add
	 */
	public void addFireStation(FireStation fs) {
		this.fsList.add(fs);
	}

	/**
	 * Get the number of firestations
	 * 
	 * @return Size of the list
	 */
	public int numberOfFireStations() {
		return fsList.size();
	}

	/**
	 * Modify the firestation at the given index
	 * 
	 * @param i Index
	 * @param f New firestation
	 */
	public void setFsIndex(int i, FireStation f) {
		if (i >= 0 && i < fsList.size()) {
			fsList.set(i, f);
		} else {
			logger.error("Want to set out of index firestations access");
		}
	}

	/**
	 * Remove the firestation at the given index
	 * 
	 * @param i Index
	 */
	public void removeFsIndex(int i) {
		if (i >= 0 && i < fsList.size()) {
			fsList.remove(i);
		} else {
			logger.error("Want to delete out of index firestations access");
		}
	}
}
