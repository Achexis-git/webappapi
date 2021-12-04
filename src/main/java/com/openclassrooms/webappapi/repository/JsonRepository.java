package com.openclassrooms.webappapi.repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

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
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;

@Repository
public class JsonRepository {

	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	private static String jsonFilepath = System.getProperty("user.dir") + "/src/main/resources/data.json";

	private static Persons persons = new Persons();
	private static FireStations fireStations = new FireStations();

	public JsonRepository() {

	}

	public Persons getAllPersons() {
		// read json & update persons & fireStations
		readJsonPerson();
		return persons;
	}

	public FireStations getAllFireStations() {
		// read json & update persons & fireStations
		readJsonPerson();
		return fireStations;
	}

	// @SuppressWarnings("unchecked")
	private void readJsonPerson() {
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
			JSONArray medicalRecordList = (JSONArray) objJson.get("mediaclrecords");

			logger.info("Arrayed the json");
			logger.debug("First element of the list : " + personList.get(0).toString());

			// Browse personList and add each person to the object persons
			Person pers;
			for (int i = 0; i < personList.size(); i++) {
				objJson = (JSONObject) personList.get(i);
				logger.trace("Get the person " + i);
				pers = parsePersonObject(objJson);
				logger.trace("Cast the person");
				persons.addPerson(pers);
				logger.trace("Add the person");
				// listPerson.addPerson(parsePersonObject((JSONObject) personList.get(i))); // à
				// décomposer
			}

			// Browse personList and add each person to the object persons
			FireStation fs;
			for (int i = 0; i < fireStationList.size(); i++) {
				objJson = (JSONObject) fireStationList.get(i);
				logger.trace("Get the fire station " + i);
				fs = parseFireStationObject(objJson);
				logger.trace("Cast the fire station");
				fireStations.addFireStation(fs);
				logger.trace("Add the fire station");
				// listPerson.addPerson(parsePersonObject((JSONObject) personList.get(i))); // à
				// décomposer
			}
			/*
			 * // Browse personList and add each person to the object persons Person pers;
			 * for (int i = 0; i < personList.size(); i++) { objJson = (JSONObject)
			 * personList.get(i); logger.trace("Get the person " + i); pers =
			 * parsePersonObject(objJson); logger.trace("Cast the person");
			 * persons.addPerson(pers); logger.trace("Add the person"); //
			 * listPerson.addPerson(parsePersonObject((JSONObject) personList.get(i))); // à
			 * // décomposer }
			 */

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("All correctly loaded");
	}

	private static Person parsePersonObject(JSONObject person) {
		// Read the JSONObject and return a Person with the right attributes

		String firstName = (String) person.get("firstName");
		String lastName = (String) person.get("lastName");
		String address = (String) person.get("address");
		String city = (String) person.get("city");
		String zip = (String) person.get("zip");
		String phone = (String) person.get("phone");
		String email = (String) person.get("email");

		return new Person(0, firstName, lastName, address, city, zip, phone, email);
	}

	private static FireStation parseFireStationObject(JSONObject fireStation) {
		// Read the JSONObject and return a FireStation with the right attributes

		String address = (String) fireStation.get("address");
		String station = (String) fireStation.get("station");

		return new FireStation(address, station);
	}
}