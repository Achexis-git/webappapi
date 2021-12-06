package com.openclassrooms.webappapi.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.Home;
import com.openclassrooms.webappapi.model.HomeInhabitant;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.PersonPosologie;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.repository.JsonRepository;

@Service
public class GetService {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);
	
	@Autowired
	private JsonRepository jsonRepository;
	
	public Persons getFirestationStationNumber(int stationNumber) {
		List<FireStation> fireStations = jsonRepository.getAllFireStations().getFsList();
		Persons persons = jsonRepository.getAllPersons();
		
		List<String> addresses = new ArrayList<String>();
		// Parcours la liste des station
		//TODO: Dois changer la boucle
		for(int i=0; i<fireStations.size(); i++) {
			FireStation fs = fireStations.get(i);
			
			// Si la station à le bon stationNumber
			if(fs.getStation() == stationNumber) {
				// On ajoute l'adresse à la liste d'adresses
				addresses.add(fs.getAddress());
			}
		}
		
		// On parcours la liste de personnes
		List<Person> p = persons.getPersonList();
		//TODO: Dois changer la boucle
		for(int i=p.size()-1; i>=0; i--) {
			// Si une personne n'est pas dans la liste d'adresses on l'enlève
			if(!(addresses.contains(p.get(i).getAddress()))) {
				p.remove(i);
			}
		}
		persons.setPersonList(p);
		
		//TODO: Ajouter décompte nombre enfants et adultes
		
		return persons;
	}

	//TODO: Compléter celui-là
	public List<Persons> getEnfantAddress(String address) {
		List<Person> persons = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> medicalRecords = jsonRepository.getAllMedicalRecords().getMrList();
		
		Persons minors = new Persons();
		Persons majors = new Persons();
		return null;
	}
	
	public List<String> getPhoneCloseToFirestation(String addressFirestation) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		
		List<String> phoneList = new ArrayList<String>();
		
		for(Person p : pList) {
			if(p.getAddress().compareTo(addressFirestation) == 0) {
				phoneList.add(p.getPhone());
			}
		}
		
		return phoneList;
	}

	public List<String> getAllEmailCity(String city) {
		List<Person> persons = jsonRepository.getAllPersons().getPersonList();
		
		List<String> emails = new ArrayList<String>();
		
		// Parcours la liste
		//TODO: Dois changer la boucle
		for(int i=0; i<persons.size(); i++) {
			// Si la city de la personne est la même que le param on ajoute son email
			if(persons.get(i).getCity().compareTo(city) == 0) {
				emails.add(persons.get(i).getEmail());
			}
		}
		
		// Enlève les doublons en transformant la liste en set puis re en liste
		emails = new ArrayList<>(new HashSet<String>(emails));
		return emails;
	}

	public List<PersonPosologie> getPosologieCloseToFirestation(String address) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();
		
		List<PersonPosologie> ppList = new ArrayList<PersonPosologie>(); 
		
		// Parcours les personnes
		Person p;
		PersonPosologie pp;
		for(int i=0; i<pList.size(); i++) {
			// Si c'est la bonne adresse
			p = pList.get(i);
			if(p.getAddress().compareTo(address) == 0) {
				logger.info("Tour de la boucle " + i);
				// On crée un PersonPosologie qui à les attribut de la personne
				pp = new PersonPosologie();
				pp.setFirstName(p.getFirstName());
				pp.setLastName(p.getLastName());
				pp.setPhone(p.getPhone());
				
				pp.setAge(0);
				pp.setMedication(null);
				pp.setAllergies(null);
				// On ajoute ce pp à la liste
				ppList.add(pp);
				logger.info("Ajout de pp");
			}
		}
		
		//TODO: Compléter la posologie et ajouter le numéro de caserne
		// On ajoute les médications et allergies
		
		
		return ppList;
	}

	public List<Home> getHomesCloseToStations(List<Integer> stations) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();
		
		List<Home> homes = new ArrayList<Home>();
		
		logger.debug("Taille de la liste " + fsList.size());
		// fsList 3x trop grande
		// mrList 2x trop grande
		// => Bug dans jsonRepository / trouvé dois reset les attributs avant de les reremplir à la lecturedu fichier
		
		// 1) Parcours les firestations
		for(FireStation fs : fsList) {
			logger.trace("Tour de boucle fs");
			Home home = new Home();
			// 2) Si le numéro de la firestation est dans la liste des station number
			if(stations.contains(fs.getStation())) {
				logger.trace("Dans la condition");
				// 3) On crée un new Home et on initialise son adresse
				home.setAddress(fs.getAddress());
				// 4) Parcours la liste des personnes pour voir qui habite là
				for(Person p : pList) {
					// 5) Si personne à la bonne adresse
					if(p.getAddress().compareTo(home.getAddress()) == 0) {
						// 6) On crée un HomeInhabitant et on initialise ses valeurs
						HomeInhabitant hi = new HomeInhabitant();
						hi.setFirstName(p.getFirstName());
						hi.setLastName(p.getLastName());
						hi.setPhone(p.getPhone());
						// 7) Parcours les medical record pour trouver notre habitant
						for(MedicalRecord mr : mrList) {
							// 8) Si le même nom et prénom
							if(mr.getFirstName().compareTo(hi.getFirstName()) == 0 & mr.getLastName().compareTo(hi.getLastName()) == 0) {
								// 9) On ajoute le MR à hi
								hi.setMedication(mr.getMedication());
								hi.setAllergies(mr.getAllergies());
								// Calcule age
								hi.setAge(computeAge(mr.getBirthdate()));
							}
						}
						// 10) Ajoute l'habitant au foyer
						home.addHomeInhabitant(hi);
					}
				}
				// 11) On ajoute le foyer à la liste de foyers
				homes.add(home);
			}
		}
		return homes;
	}	
	
	private int computeAge(String birthday) {
		Calendar today = Calendar.getInstance();
		Calendar birthdayCal = Calendar.getInstance();
		
		logger.trace("Birthday : " + birthday);
		String[] ls = birthday.split("/");
		logger.trace("List of birthday date : " + ls[0] + "/" + ls[1] + "/" + ls[2]);
		
		try {
			// MM/DD/YYYY
			birthdayCal.set(Integer.parseInt(ls[2]), Integer.parseInt(ls[0]), Integer.parseInt(ls[1]));
		} catch (NumberFormatException e) {
			e.printStackTrace();
		}
		
		double age = (double) (today.getTimeInMillis() - birthdayCal.getTimeInMillis());
		logger.trace("L'âge est de : " + age + "ms");
		age = age / (1000.0 * 60.0 * 60.0 * 24.0 * 365.0);
		logger.trace("L'âge est de : " + age + "ans");
		
		return (int) age;
	}
}







