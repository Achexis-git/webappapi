package com.openclassrooms.webappapi.repository;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.io.File;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Repository;

import com.jsoniter.JsonIterator;
import com.jsoniter.any.Any;
import com.jsoniter.output.JsonStream;
import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.FileWrite;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.model.Person;
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
	/**
	 * Logger
	 */
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

	public static void setPersons(Persons persons) {
		Persons p = new Persons();
		p.setPersonList(persons.getPersonList());
		JsonRepository.persons = p;

		JsonRepository.fileUnSaved = true;
	}

	public static void setFirestations(FireStations firestations) {
		FireStations fs = new FireStations();
		fs.setFsList(firestations.getFsList());
		JsonRepository.fireStations = fs;

		JsonRepository.fileUnSaved = true;
	}

	public static void setMedicalRecords(MedicalRecords medicalRecords) {
		MedicalRecords mr = new MedicalRecords();
		mr.setMrList(medicalRecords.getMrList());
		JsonRepository.medicalRecords = mr;

		JsonRepository.fileUnSaved = true;
	}

	/**
	 * Verify if the file is already saved, if not call the method to write in the
	 * file
	 * 
	 * @see JsonRepository#writeJson()
	 */
	public void save() {
		if (fileUnSaved) {
			writeJson();
			JsonRepository.fileUnSaved = false;
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
			JsonRepository.fileChanged = false;
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
		try {
			byte[] bytesFile = Files.readAllBytes(new File(jsonFilepathRead).toPath());
			logger.info("Open data.json file");

			// 2) Parse the file
			JsonIterator iter = JsonIterator.parse(bytesFile);

			// 3) Extract each list
			Any any = iter.readAny();
			Any personAny = any.get("persons");
			Any fsAny = any.get("firestations");
			Any mrAny = any.get("medicalrecords");

			// 4) Put the persons into the attribute
			persons.resetList();

			personAny.forEach(a -> persons.addPerson(new Person(0, a.get("firstName").toString(),
					a.get("lastName").toString(), a.get("address").toString(), a.get("city").toString(),
					a.get("zip").toString(), a.get("phone").toString(), a.get("email").toString())));

			// 5) Put the firestations into the attributes
			fireStations.resetList();

			fsAny.forEach(a -> fireStations
					.addFireStation(new FireStation(a.get("address").toString(), a.get("station").toInt())));

			// 6) Put the medical records into the attributes
			medicalRecords.resetList();

			mrAny.forEach(a -> medicalRecords
					.addMedicalRecord(new MedicalRecord(a.get("firstName").toString(), a.get("lastName").toString(),
							a.get("birthdate").toString(), new ArrayList<>(), new ArrayList<>())));

			// 6.2) Extract and put the medications and allergies into the attributes
			logger.debug("Medical records loaded");
			for (int i = 0; i < medicalRecords.numberOfMedicalRecords(); i++) {
				MedicalRecord mr = medicalRecords.getMrList().get(i);

				List<String> medications = new ArrayList<>();
				mrAny.get(i).get("medications").forEach(m -> medications.add(m.toString()));

				List<String> allergies = new ArrayList<>();
				mrAny.get(i).get("allergies").forEach(a -> allergies.add(a.toString()));

				mr.setMedication(medications);
				mr.setAllergies(allergies);
				medicalRecords.setMrIndex(i, mr);
			}
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
		FileWrite file = new FileWrite();
		// 2) Set the attributes
		file.setPersons(persons.getPersonList());
		file.setFirestations(fireStations.getFsList());
		file.setMedicalrecords(medicalRecords.getMrList());

		// 3) Open JSON file
		try (Writer writer = new OutputStreamWriter(new FileOutputStream(new File(jsonFilepathWrite)))) {
			// 4) Write the string into the file
			writer.write(JsonStream.serialize(file));
		} catch (IOException e) {
			e.printStackTrace();
		}
		logger.debug("File correctly saved");

	}
}
