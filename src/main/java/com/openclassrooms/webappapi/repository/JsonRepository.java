package com.openclassrooms.webappapi.repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.springframework.stereotype.Repository;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.FireStations;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;

@Repository
public class JsonRepository {

	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	private static String jsonFilepath = System.getProperty("user.dir") + "/src/main/resources/data.json";

	private static Persons persons = new Persons();
	private static FireStations fireStations = new FireStations();
	private static MedicalRecords medicalRecords = new MedicalRecords();

	// If modify the file & attributes not update => true (need to reread the file)
	// Modify the file => turn true
	// Read the file => turn false
	// Prevent multiple reading
	private static boolean fileChanged = true;
	
	private static boolean fileUnSaved = true;

	public JsonRepository() {

	}

	public Persons getAllPersons() {
		// read json & update persons & fireStations & medicalRecords
		if (fileChanged) {
			readJson();
			fileChanged = false;
		}
		Persons p = new Persons();
		p.setPersonList(persons.getPersonList());
		return p;
	}

	public FireStations getAllFireStations() {
		// read json & update persons & fireStations & medicalRecords
		if (fileChanged) {
			readJson();
			fileChanged = false;
		}
		FireStations fs = new FireStations();
		fs.setFsList(fireStations.getFsList());
		return fs;
	}

	public MedicalRecords getAllMedicalRecords() {
		// read json & update persons & fireStations & medicalRecords
		if (fileChanged) {
			readJson();
			fileChanged = false;
		}
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
	
	public void save() {
		if (fileUnSaved) {
			writeJson();
			fileUnSaved = false;
			logger.info("File saved");
		} 
		else {
			logger.info("File already saved");
		}
	}

	// @SuppressWarnings("unchecked")
	private void readJson() {
		JSONParser jsonParser = new JSONParser();

		try (FileReader reader = new FileReader(jsonFilepath)) {
			// Read data.json file
			logger.info("Open data.json file");

			// Parse content of the file into an object
			Object obj = jsonParser.parse(reader);
			logger.info("File parsed into obj");

			// Cast the Object into JSONObject
			JSONObject objJson = (JSONObject) obj;

			// Get "persons" of the JSONObject and put it into an array
			JSONArray personList = (JSONArray) objJson.get("persons");
			// Get "persons" of the JSONObject and put it into an array
			JSONArray fireStationList = (JSONArray) objJson.get("firestations");
			// Get "persons" of the JSONObject and put it into an array
			JSONArray medicalRecordList = (JSONArray) objJson.get("medicalrecords");

			logger.info("Arrayed the json");
			logger.debug("First element of the list : " + personList.get(0).toString());

			// TODO: Dois y avoir moyen de faire ça de façon plus smart

			// Browse personList and add each person to persons
			persons = new Persons(); // Reset the attribute
			Person pers;
			for (int i = 0; i < personList.size(); i++) {
				objJson = (JSONObject) personList.get(i);
				logger.trace("Get the person " + i);
				pers = parsePersonObject(objJson, i);
				logger.trace("Cast the person");
				persons.addPerson(pers);
				logger.trace("Add the person");
				// listPerson.addPerson(parsePersonObject((JSONObject) personList.get(i))); // à
				// décomposer
			}

			// Browse fireStationList and add each fire station to fireStaions
			fireStations = new FireStations(); // Reset the attribute
			FireStation fs;
			for (int i = 0; i < fireStationList.size(); i++) {
				objJson = (JSONObject) fireStationList.get(i);
				logger.trace("Get the fire station " + i);
				fs = parseFireStationObject(objJson);
				logger.trace("Cast the fire station");
				fireStations.addFireStation(fs);
				logger.trace("Add the fire station");
			}

			// Browse personList and add each person to the object persons Person pers;
			medicalRecords = new MedicalRecords(); // Reset the attribute
			MedicalRecord mr;
			for (int i = 0; i < medicalRecordList.size(); i++) {
				objJson = (JSONObject) medicalRecordList.get(i);
				logger.trace("Get the medical record " + i);
				mr = parseMedicalRecordObject(objJson);
				logger.trace("Cast the medical record");
				medicalRecords.addMedicalRecord(mr);
				logger.trace("Add the medical record"); //
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("All correctly loaded");
	}

	private static Person parsePersonObject(JSONObject person, int id) {
		// Read the JSONObject and return a Person with the right attributes

		String firstName = (String) person.get("firstName");
		String lastName = (String) person.get("lastName");
		String address = (String) person.get("address");
		String city = (String) person.get("city");
		String zip = (String) person.get("zip");
		String phone = (String) person.get("phone");
		String email = (String) person.get("email");

		return new Person(id, firstName, lastName, address, city, zip, phone, email);
	}

	private static FireStation parseFireStationObject(JSONObject fireStation) {
		// Read the JSONObject and return a FireStation with the right attributes

		String address = (String) fireStation.get("address");
		String station = (String) fireStation.get("station");

		return new FireStation(address, Integer.parseInt(station));
	}

	private static MedicalRecord parseMedicalRecordObject(JSONObject medicalRecord) {
		// Read the JSONObject and return a MedicalRecord with the right attributes

		String firstName = (String) medicalRecord.get("firstName");
		String lastName = (String) medicalRecord.get("lastName");
		String birthdate = (String) medicalRecord.get("birthdate");

		Object mObj = medicalRecord.get("medications");
		logger.trace("Get the medication : " + mObj);
		List<String> medication = toList((JSONArray) mObj);
		logger.trace("Medication correctly mapped : " + medication);

		Object aObj = medicalRecord.get("allergies");
		List<String> allergies = toList((JSONArray) aObj);

		return new MedicalRecord(firstName, lastName, birthdate, medication, allergies);
	}

	// Convert JSONArray into a list
	private static List<String> toList(JSONArray jsonArr) {
		List<String> list = new ArrayList<String>();
		for (int i = 0; i < jsonArr.size(); i++) {
			list.add((String) jsonArr.get(i));
			logger.trace("Ajoute élément : " + (String) jsonArr.get(i));
		}

		return list;
	}

	@SuppressWarnings("unchecked")
	private void writeJson() {

		// 1) Crée l'objet que je vais enregistrer
		JSONObject jsonObj = new JSONObject();

		// 2.1) Crée son attribut "persons"
		JSONArray personJsonArr = createPersonJsonArr();
		// 2.2) Crée son attribut "firestations"
		JSONArray firestationJsonArr = createFirestationJsonArr();
		// 2.3) Crée son attribut "medicalrecords"
		JSONArray medicalrecordJsonArr = createMedicalRecordJsonArr();

		jsonObj.put("persons", personJsonArr);
		jsonObj.put("firestations", firestationJsonArr);
		jsonObj.put("medicalrecords", medicalrecordJsonArr);

		// Write JSON file
		try (FileWriter file = new FileWriter(
				System.getProperty("user.dir") + "/src/main/resources/dataTestWrite.json")) {
			// We can write any JSONArray or JSONObject instance to the file
			logger.info("Writing file");
			file.write(jsonObj.toJSONString().replace("\\", "")); // Remove the backslashes (appears in birthdate slashes)
			file.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@SuppressWarnings("unchecked")
	private JSONArray createPersonJsonArr() {
		List<Person> pList = persons.getPersonList();

		JSONArray personJsonArr = new JSONArray();

		// 1) Parcours la liste de person
		for (Person p : pList) {
			// 2) Crée un JSONObject
			JSONObject pJson = new JSONObject();
			// 3) Mets chacun des élément de la person dans le JSONObject
			pJson.put("firstName", p.getFirstName());
			pJson.put("lastName", p.getLastName());
			pJson.put("address", p.getAddress());
			pJson.put("city", p.getCity());
			pJson.put("zip", p.getZip());
			pJson.put("phone", p.getPhone());
			pJson.put("email", p.getEmail());
			// 4) Ajoute le JSONObject à la liste
			personJsonArr.add(pJson);
		}

		return personJsonArr;
	}

	@SuppressWarnings("unchecked")
	private JSONArray createFirestationJsonArr() {
		List<FireStation> fsList = fireStations.getFsList();

		JSONArray firestationJsonArr = new JSONArray();

		// 1) Parcours la liste de firestation
		for (FireStation fs : fsList) {
			// 2) Crée un JSONObject
			JSONObject fsJson = new JSONObject();
			// 3) Mets chacun des élément de la firestation dans le JSONObject
			fsJson.put("address", fs.getAddress());
			fsJson.put("station", Integer.toString(fs.getStation()));
			// 4) Ajoute le JSONObject à la liste
			firestationJsonArr.add(fsJson);
		}

		return firestationJsonArr;
	}

	@SuppressWarnings("unchecked")
	private JSONArray createMedicalRecordJsonArr() {
		JSONParser jsonParser = new JSONParser();
		List<MedicalRecord> mrList = medicalRecords.getMrList();
		
		JSONArray medicalRecordJsonArr = new JSONArray();
		
		// 1) Parcours les medical record
		for (MedicalRecord mr : mrList) {
			// 2) Crée un JSONObject
			JSONObject mrJson = new JSONObject();
			// 3.1) Mets chacun des élément du medical record dans le JSONObject
			mrJson.put("firstName", mr.getFirstName());
			mrJson.put("lastName", mr.getLastName());
			mrJson.put("birthdate", mr.getBirthdate());
			logger.trace("Birthdate : " + mrJson.get("birthdate"));
			// 3.2) Parcours les medications
			JSONArray med = new JSONArray();
			for (String m : mr.getMedication()) {
				med.add(m);
			}
			// 3.3) Parcours les allergies
			JSONArray al = new JSONArray();
			for (String a : mr.getAllergies()) {
				al.add(a);
			}
			mrJson.put("medications", med);
			mrJson.put("allergies", al);
			
			medicalRecordJsonArr.add(mrJson);
		}
		
		return medicalRecordJsonArr;
	}
}
