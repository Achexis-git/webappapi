package com.openclassrooms.webappapi.repository;

import java.io.FileNotFoundException;
import java.io.FileReader;
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
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;

@Repository
public class PersonRepository {
	
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);
	
	private static Persons listPerson = new Persons();
	
	public PersonRepository() {
		// read json & initialize listPerson
		readJsonPerson();
	}
	
	public Persons getAllPersons() {
		return listPerson;
	}
	
	
	
	@SuppressWarnings("unchecked")
	private void readJsonPerson() {
		JSONParser jsonParser = new JSONParser();
		
		try (FileReader reader = new FileReader(System.getProperty("user.dir")+"/src/main/resources/data.json")) {
			logger.info("Open data.json file");
			
			// read json file
			Object obj = jsonParser.parse(reader);
			logger.info("File parsed into obj");
			
			JSONObject objJson = (JSONObject) obj;
			
			JSONArray personList = (JSONArray) objJson.get("persons");
			logger.info("Arrayed the json");
			logger.debug("First element of the list : " + personList.get(0).toString());
			
			Person pers;
			for(int i = 0; i < personList.size(); i++) {
				objJson = (JSONObject) personList.get(i);
				logger.trace("Get the person " + i);
				pers = parsePersonObject(objJson);
				logger.trace("Cast the person");
				listPerson.addPerson(pers);
				logger.trace("Add the person");
				//listPerson.addPerson(parsePersonObject((JSONObject) personList.get(i))); // à décomposer
			}
			
			
			//personList.forEach(pers -> parsePersonObject((JSONObject) pers));
			logger.info("End loading");
			
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
		logger.info("Persons correctly loaded");
	}
	
	private static Person parsePersonObject(JSONObject person) {
		
		// get person object within list
		// JSONObject personObject = (JSONObject) person.get("person");
		
		// logger.info("person : " + personObject.toString());
		/*
		String firstName = (String) personObject.get("firstName");
		String lastName  = (String) personObject.get("lastName");
		String address   = (String) personObject.get("address");
		String city      = (String) personObject.get("city");
		String zip       = (String) personObject.get("zip");
		String phone     = (String) personObject.get("phone");
		String email     = (String) personObject.get("email");
		*/
		
		String firstName = (String) person.get("firstName");
		String lastName  = (String) person.get("lastName");
		String address   = (String) person.get("address");
		String city      = (String) person.get("city");
		String zip       = (String) person.get("zip");
		String phone     = (String) person.get("phone");
		String email     = (String) person.get("email");
		
		return new Person(0, firstName, lastName, address, city, zip, phone, email);
	}
}













