package com.openclassrooms.webappapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.repository.JsonRepository;

/**
 * Execute the instructions send by the associated controller
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.controller.FirestationController
 *
 */
@Service
public class FirestationService {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Allows interact with the file
	 */
	@Autowired
	private JsonRepository jsonRepository;

	/**
	 * Load the firestations, add the new firestation, and save the new list
	 * 
	 * @param newFirestation Firestation to add
	 * @return Added firestation
	 */
	public FireStation createFirestation(FireStation newFirestation) {
		jsonRepository.load();
		FireStations firestations = jsonRepository.getAllFireStations();

		firestations.addFireStation(newFirestation);
		logger.info("Posted firestation : {} at address {}", newFirestation.getStation(), newFirestation.getAddress());

		JsonRepository.setFirestations(firestations);
		jsonRepository.save();

		return firestations.getFsList().get(firestations.getFsList().size() - 1);
	}

	/**
	 * Load the firestations and get them all
	 * @return An object containing the firestations
	 */
	public FireStations readFirestations() {
		jsonRepository.load();
		return jsonRepository.getAllFireStations();
	}

	/**
	 * Load the firestations, update the firestation at the given address and save the new list
	 * @param newFirestation Firestation to update
	 * @return Updated firestation
	 */
	public FireStation updateFirestation(FireStation newFirestation) {
		jsonRepository.load();
		FireStations firestations = jsonRepository.getAllFireStations();
		List<FireStation> fsList = firestations.getFsList();

		FireStation updatedFirestation = new FireStation();

		// 1) Browse firestations
		for (int i = 0; i < fsList.size(); i++) {
			FireStation fs = fsList.get(i);
			// 2) If same addresses
			if (fs.getAddress().compareTo(newFirestation.getAddress()) == 0) {
				// 3) Update firestation
				firestations.setFsIndex(i, newFirestation);
				updatedFirestation = firestations.getFsList().get(i);
				logger.info("Updated firestation : {} at address {}", updatedFirestation.getStation(),
						updatedFirestation.getAddress());
				break;
			}
		}

		JsonRepository.setFirestations(firestations);
		jsonRepository.save();

		return updatedFirestation;
	}

	/**
	 * Load the firestations, delete the firestation with the right address and save the new list
	 * 
	 * @param delFirestation Firestation to delete
	 * @return Deleted firestation
	 */
	public FireStation deleteFirestation(FireStation delFirestation) {
		jsonRepository.load();
		FireStations firestations = jsonRepository.getAllFireStations();
		List<FireStation> fsList = firestations.getFsList();

		FireStation deletedFirestation = new FireStation();

		// 1) Browse firestations
		for (int i = 0; i < fsList.size(); i++) {
			FireStation fs = fsList.get(i);
			// 2) If same addresses and station number
			if (fs.getAddress().compareTo(delFirestation.getAddress()) == 0
					&& delFirestation.getStation() == fs.getStation()) {
				// 3) Delete firestation
				firestations.removeFsIndex(i);
				logger.info("Deleted firestation : {} at address {}", delFirestation.getStation(),
						delFirestation.getAddress());
				deletedFirestation = fs;
				break;
			}
		}

		JsonRepository.setFirestations(firestations);
		jsonRepository.save();

		return deletedFirestation;
	}

}
