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

@Service
public class FirestationService {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private JsonRepository jsonRepository;

	public FireStation createFirestation(FireStation newFirestation) {
		jsonRepository.load();
		FireStations firestations = jsonRepository.getAllFireStations();

		firestations.addFireStation(newFirestation);
		logger.info("Posted firestation : {} at address {}", newFirestation.getStation(), newFirestation.getAddress());

		jsonRepository.setFirestations(firestations);
		jsonRepository.save();

		return newFirestation;
	}

	public FireStations readFirestations() {
		jsonRepository.load();
		return jsonRepository.getAllFireStations();
	}

	public FireStation updateFirestation(FireStation newFirestation) {
		jsonRepository.load();
		FireStations firestations = jsonRepository.getAllFireStations();
		List<FireStation> fsList = firestations.getFsList();

		// Browse firestations
		for (int i = 0; i < fsList.size(); i++) {
			FireStation fs = fsList.get(i);
			// If same addresses
			if (fs.getAddress().compareTo(newFirestation.getAddress()) == 0) {
				// Update firestation
				firestations.setFsIndex(i, newFirestation);
				logger.info("Updated firestation : {} at address {}", newFirestation.getStation(),
						newFirestation.getAddress());
				break;
			}
		}

		jsonRepository.setFirestations(firestations);
		jsonRepository.save();

		return newFirestation;
	}

	public FireStation deleteFirestation(FireStation delFirestation) {
		jsonRepository.load();
		FireStations firestations = jsonRepository.getAllFireStations();
		List<FireStation> fsList = firestations.getFsList();

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
				break;
			}
		}

		jsonRepository.setFirestations(firestations);
		jsonRepository.save();

		return delFirestation;
	}

}
