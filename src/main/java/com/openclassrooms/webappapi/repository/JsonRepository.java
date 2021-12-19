package com.openclassrooms.webappapi.repository;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.output.JsonStream;
import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.File;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.model.Persons;

/**
 * Allows to read and write in files and get the informations
 *  
 * @author alexis
 * @version 1.0
 *
 */
@Repository
public class JsonRepository {

	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Filepath of the file to read
	 */
	private static String jsonFilepathRead = System.getProperty("user.dir") + "/src/main/resources/data.json";
	/**
	 * Filepath of the file in which write
	 */
	private static String jsonFilepathWrite = System.getProperty("user.dir") + "/src/main/resources/dataTestWrite.json";

	/**
	 * Persons in the file
	 */
	private static Persons persons = new Persons();
	/**
	 * Firestations in the file
	 */
	private static FireStations fireStations = new FireStations();
	/**
	 * Medical records in the file
	 */
	private static MedicalRecords medicalRecords = new MedicalRecords();

	// If modify the file & attributes not update => true (need to reread the file)
	// Modify the file => turn true
	// Read the file => turn false
	// Prevent multiple reading, the file is only read one time by execution
	private static boolean fileChanged = true;

	private static boolean fileUnSaved = false;

	public JsonRepository() {

	}

	public Persons getAllPersons() {
		Persons p = new Persons();
		p.setPersonList(persons.getPersonList());
		return p;
	}

	public FireStations getAllFireStations() {
		FireStations fs = new FireStations();
		fs.setFsList(fireStations.getFsList());
		return fs;
	}

	public MedicalRecords getAllMedicalRecords() {
		MedicalRecords mr = new MedicalRecords();
		mr.setMrList(medicalRecords.getMrList());
		return mr;
	}

	public void setPersons(Persons persons) {
		Persons p = new Persons();
		p.setPersonList(persons.getPersonList());
		JsonRepository.persons = p;

		fileUnSaved = true;
	}

	public void setFirestations(FireStations firestations) {
		FireStations fs = new FireStations();
		fs.setFsList(firestations.getFsList());
		JsonRepository.fireStations = fs;

		fileUnSaved = true;
	}

	public void setMedicalRecords(MedicalRecords medicalRecords) {
		MedicalRecords mr = new MedicalRecords();
		mr.setMrList(medicalRecords.getMrList());
		JsonRepository.medicalRecords = mr;

		fileUnSaved = true;
	}

	/**
	 * Verify if the file is already saved, if not call the method to write in the file
	 * 
	 * @see JsonRepository#writeJson() 
	 */
	public void save() {
		if (fileUnSaved) {
			writeJson();
			fileUnSaved = false;
			logger.info("File saved");
		} else {
			logger.info("File already saved");
		}
	}

	/**
	 * Verify if the file is already loaded, if not call a method to read the file
	 * 
	 * @see JsonRepository#readJson()
	 */
	public void load() {
		if (fileChanged) {
			readJson();
			fileChanged = false;
			logger.info("File loaded");
		} else {
			logger.info("File already loaded");
		}
		if (fileUnSaved) {
			logger.warn("Overwrite unsaved changes");
		}
	}

	/**
	 * Read the file
	 * 
	 * @see JsonRepository#jsonFilepathRead
	 */
	private void readJson() {
		// 1) Open the file
		try (BufferedReader reader = new BufferedReader(new FileReader(jsonFilepathRead))) {
			logger.info("Open data.json file");

			// 2) Copy the content of the file into one string
			String stringFile = "";
			String line = reader.readLine();
			while (line != null) {
				stringFile += line;
				logger.trace(line);
				line = reader.readLine();
			}

			// 3) Deserialize the string into an object File
			File file = JsonIterator.deserialize(stringFile, File.class);
			logger.debug("Deserialization succeeded");

			// 4) Set the static objects
			persons.setPersonList(file.getPersons());
			fireStations.setFsList(file.getFirestations());
			medicalRecords.setMrList(file.getMedicalrecords());

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("All correctly loaded");
	}

	/**
	 * Write the file
	 * 
	 * @see JsonRepository#jsonFilepathWrite
	 */
	private void writeJson() {
		// 1) Create the object that will be serialized
		File file = new File();
		// 2) Set the attributes
		file.setPersons(persons.getPersonList());
		file.setFirestations(fireStations.getFsList());
		file.setMedicalrecords(medicalRecords.getMrList());

		// 3) Serialize the object to get a string
		String stringToSave = JsonStream.serialize(file);

		// 4) Open JSON file
		try (FileWriter writer = new FileWriter(jsonFilepathWrite)) {
			// 5) Write the string into the file
			writer.write(stringToSave);
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("File correctly saved");
	}
}
