package com.openclassrooms.webappapi.service;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.TimeZone;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.model.response.Adult;
import com.openclassrooms.webappapi.model.response.Child;
import com.openclassrooms.webappapi.model.response.Home;
import com.openclassrooms.webappapi.model.response.HomeChildren;
import com.openclassrooms.webappapi.model.response.HomeInhabitant;
import com.openclassrooms.webappapi.model.response.InhabitantFire;
import com.openclassrooms.webappapi.model.response.PersonInfo;
import com.openclassrooms.webappapi.model.response.PersonsAndCountdown;
import com.openclassrooms.webappapi.repository.JsonRepository;

/**
 * Execute the instructions send by the associated controller
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.controller.URLsController
 *
 */
@Service
public class URLsService {

	// TODO : Potentiel problèmes si persons et medical records pas en accord entre
	// eux
	
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
	 * Load the persons, medical records and firestations; analyze them and return
	 * the asked information
	 * 
	 * @param stationNumber Firestation number
	 * @return An object containing a list of persons covered and a countdown of the
	 *         children and adults
	 */
	public PersonsAndCountdown getPeoplesCloseToFireStation(int stationNumber) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		PersonsAndCountdown pc = new PersonsAndCountdown();
		Persons persons = new Persons();
		int childrenCountdown = 0;
		int adultsCountdown = 0;

		// 1) Browse firestations
		for (FireStation fs : fsList) {
			// 2) If number match
			if (fs.getStation() == stationNumber) {
				// 3) Browse persons
				for (int i = 0; i < pList.size(); i++) {
					Person p = pList.get(i);
					// 4) If address match
					if (p.getAddress().compareTo(fs.getAddress()) == 0) {
						// 5) Add the person to the list
						persons.addPerson(p);
						// 6) Browse medical records
						for (MedicalRecord mr : mrList) {
							// 7) If same first name, last name
							if (p.getFirstName().compareTo(mr.getFirstName()) == 0
									&& p.getLastName().compareTo(mr.getLastName()) == 0) {
								// 8) Compute age and increment the variable
								if (computeAge(mr.getBirthdate()) > 18) {
									adultsCountdown++;
								} else {
									childrenCountdown++;
								}
								// 9) Pop the mr (if 2 persons have the same name)
								mrList.remove(mr);
								// 10) Pop p ''
								pList.remove(i);
								i--;
								break;
							}
						}
					}
				}
			}
		}

		pc.setPersons(persons);
		pc.setAdultCountdown(adultsCountdown);
		pc.setChildrenCountdown(childrenCountdown);

		return pc;
	}

	/**
	 * Load the persons and medical records; analyze them and return
	 * the asked information
	 * 
	 * @param address Home address
	 * @return An object containing the address, a list of childs, a list of adults
	 */
	public HomeChildren getChildAddress(String address) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();

		HomeChildren hc = new HomeChildren();
		List<Child> childList = new ArrayList<Child>();
		List<Adult> adultList = new ArrayList<Adult>();
		hc.setAddress(address);

		// 1) Browse persons
		for (int i = 0; i < pList.size(); i++) {
			Person p = pList.get(i);
			// 2) If same address
			if (p.getAddress().compareTo(address) == 0) {
				// 3) Browse medical records
				for (MedicalRecord mr : mrList) {
					// 4) If same first name, last name
					if (mr.getFirstName().compareTo(p.getFirstName()) == 0
							&& mr.getLastName().compareTo(p.getLastName()) == 0) {
						// 5) Compute age
						int age = computeAge(mr.getBirthdate());
						// 6) Complete the list
						if (age > 18) {
							Adult a = new Adult();
							a.setFirstName(p.getFirstName());
							a.setLastName(p.getLastName());
							adultList.add(a);
						} else {
							Child c = new Child();
							c.setFirstName(p.getFirstName());
							c.setLastName(p.getLastName());
							c.setAge(age);
							childList.add(c);
						}
						// 7) Remove the mr & break the loop (2 persons same name)
						mrList.remove(mr);
						pList.remove(i);
						i--; // 8) Because remove an item
						break;
					}
				}
			}
		}

		hc.setAdultList(adultList);
		hc.setChildList(childList);

		return hc;
	}

	/**
	 * Load the persons and firestations; analyze them and return
	 * the asked information
	 * 
	 * @param stationNumber Firestation number
	 * @return A list of strings with the phone numbers
	 */
	public List<String> getPhoneCloseToFirestation(int stationNumber) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		List<String> phoneList = new ArrayList<String>();

		// 1) Browse firestations
		for (FireStation fs : fsList) {
			// 2) If same number
			if (fs.getStation() == stationNumber) {
				// 3) Browse persons
				for (Person p : pList) {
					// 4) If same address
					if (fs.getAddress().compareTo(p.getAddress()) == 0) {
						// 5) Add phone number
						phoneList.add(p.getPhone());
					}
				}
			}
		}

		// 6) Remove double
		phoneList = new ArrayList<String>(new HashSet<String>(phoneList));
		return phoneList;
	}

	/**
	 * Load the persons; analyze them and return
	 * the asked information
	 * 
	 * @param city City name
	 * @return List of strings of the email addresses
	 */
	public List<String> getAllEmailCity(String city) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();

		List<String> emails = new ArrayList<String>();

		// 1) Browse persons
		for (Person p : pList) {
			// 2) If same city
			if (p.getCity().compareTo(city) == 0) {
				emails.add(p.getEmail());
			}
		}

		// 3) Remove double
		emails = new ArrayList<String>(new HashSet<String>(emails));
		return emails;
	}

	/**
	 * Load the persons, medical records and firestations; analyze them and return
	 * the asked information
	 * 
	 * @param address Address of the firestation
	 * @return An object containing the firestation that serve this address and a
	 *         home containing informations about the inhabitants
	 */
	public InhabitantFire getPosologieCloseToFirestation(String address) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		InhabitantFire iFire = new InhabitantFire();

		// 1) Search station number
		// 1.1) Browse stations
		for (FireStation fs : fsList) {
			// 1.2) If same address
			if (fs.getAddress().compareTo(address) == 0) {
				// 1.3) Set station number
				iFire.setStationNumber(fs.getStation());
				break;
			}
		}

		// 2) Search persons living at that address
		Home home = new Home();
		home.setAddress(address);
		// 2.1) Browse persons
		for (Person p : pList) {
			// 2.2) If same address
			if (p.getAddress().compareTo(address) == 0) {
				// 2.3) Add data home inhabitant
				HomeInhabitant hi = new HomeInhabitant();
				hi.setFirstName(p.getFirstName());
				hi.setLastName(p.getLastName());
				hi.setPhone(p.getPhone());
				// 2.4) Browse medical records
				for (MedicalRecord mr : mrList) {
					// 2.5 If same first name, last name
					if (mr.getFirstName().compareTo(p.getFirstName()) == 0
							&& mr.getLastName().compareTo(p.getLastName()) == 0) {
						// 2.6) Add the mr
						hi.setMedication(mr.getMedications());
						hi.setAllergies(mr.getAllergies());
						hi.setAge(computeAge(mr.getBirthdate()));
						// 2.7) Remove mr & break
						mrList.remove(mr);
						break;
					}
				}
				// 2.8) Add home inhabitant at the home
				home.addHomeInhabitant(hi);
			}
		}

		// 2.9) Add the home at iFire
		iFire.setHome(home);

		return iFire;
	}

	/**
	 * Load the persons, medical records and firestations; analyze them and return
	 * the asked information
	 * 
	 * @param stations Firestation numbers
	 * @return A list of home of served by the firestations
	 */
	public List<Home> getHomesCloseToStations(List<Integer> stations) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		List<Home> homes = new ArrayList<Home>();

		logger.trace("List size " + fsList.size());

		// 1) Browse firestations
		for (FireStation fs : fsList) {
			logger.trace("Loop fs");
			// 2) If firestation in list
			if (stations.contains(fs.getStation())) {
				logger.trace("In condition");
				// 3) Set new home & initialize its address
				Home home = new Home();
				home.setAddress(fs.getAddress());
				// 4) Browse persons
				for (int i = 0; i < pList.size(); i++) {
					Person p = pList.get(i);
					// 5) If same address
					if (p.getAddress().compareTo(home.getAddress()) == 0) {
						// 6) Set HomeInhabitant & initialize its attributes
						HomeInhabitant hi = new HomeInhabitant();
						hi.setFirstName(p.getFirstName());
						hi.setLastName(p.getLastName());
						hi.setPhone(p.getPhone());
						// 7) Browse medical records
						for (MedicalRecord mr : mrList) {
							// 8) If same first name, last name
							if (mr.getFirstName().compareTo(hi.getFirstName()) == 0
									&& mr.getLastName().compareTo(hi.getLastName()) == 0) {
								// 9) Add mr at home inhabitant
								hi.setMedication(mr.getMedications());
								hi.setAllergies(mr.getAllergies());
								// Compute age
								hi.setAge(computeAge(mr.getBirthdate()));
								// 9.1) Pop mr & break
								mrList.remove(mr);
								pList.remove(i);
								i--;
								break;
							}
						}
						// 10) Add hi to home
						home.addHomeInhabitant(hi);
					}
				}
				// 11) Add home to home list
				homes.add(home);
			}
		}
		return homes;
	}

	/**
	 * Compute the age of the person based on its birth date
	 * 
	 * @param birthday Birth date at format "MM/DD/YYYY"
	 * @return Age in years
	 */
	private int computeAge(String birthday) {
		Calendar today = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));

		Calendar birth = Calendar.getInstance(TimeZone.getTimeZone("Europe/Paris"));
		try {
			Date date = new SimpleDateFormat("MM/dd/yyyy").parse(birthday);
			birth.setTime(date);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		int age = today.get(Calendar.YEAR) - birth.get(Calendar.YEAR);
		if (today.get(Calendar.MONTH) == today.get(Calendar.MONTH)) {
			if (today.get(Calendar.DAY_OF_MONTH) < birth.get(Calendar.DAY_OF_MONTH)) {
				age--;
			}
		} else if (today.get(Calendar.MONTH) < birth.get(Calendar.MONTH)) {
			age--;
		}

		return age;
	}

	/**
	 * Load the persons, medical records and firestations; analyze them and return
	 * the asked information
	 * 
	 * @param firstName Person first name
	 * @param lastName Person last name
	 * @return An object containing the personal informations
	 */
	public List<PersonInfo> getPersonInfo(String firstName, String lastName) {
		jsonRepository.load();
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();

		List<PersonInfo> piList = new ArrayList<PersonInfo>();

		// 1) Browse persons
		logger.debug("Ready to browse the person list");
		for (int i = 0; i < pList.size(); i++) {
			Person p = pList.get(i);
			// 2) If same first name, last name
			if (p.getFirstName().compareTo(firstName) == 0 && p.getLastName().compareTo(lastName) == 0) {
				logger.debug("Got a person with the right name");
				// 3) Create personInfo and initialize its attributes
				PersonInfo pi = new PersonInfo();
				pi.setFirstName(p.getFirstName());
				pi.setLastName(p.getLastName());
				pi.setEmail(p.getEmail());

				// 4) Browse medical records
				for (MedicalRecord mr : mrList) {
					// 5) If same first name, last name
					if (mr.getFirstName().compareTo(firstName) == 0 && mr.getLastName().compareTo(lastName) == 0) {
						logger.debug("Got a medical record with the right name");
						// 6) Add values person info
						pi.setMedication(mr.getMedications());
						pi.setAllergies(mr.getAllergies());
						pi.setAge(computeAge(mr.getBirthdate()));
						mrList.remove(mr);
						pList.remove(i);
						i--; // Adjust because pop an item
						break;
					}
				}
				// 8) Add person info to list
				piList.add(pi);
			}
		}
		return piList;
	}
}
