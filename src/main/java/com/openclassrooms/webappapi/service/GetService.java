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
import com.openclassrooms.webappapi.model.PersonInfo;
import com.openclassrooms.webappapi.model.Adult;
import com.openclassrooms.webappapi.model.Child;
import com.openclassrooms.webappapi.model.FireStation;
import com.openclassrooms.webappapi.model.Home;
import com.openclassrooms.webappapi.model.HomeChildren;
import com.openclassrooms.webappapi.model.HomeInhabitant;
import com.openclassrooms.webappapi.model.InhabitantFire;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.Person;
import com.openclassrooms.webappapi.model.Persons;
import com.openclassrooms.webappapi.model.PersonsAndCountdown;
import com.openclassrooms.webappapi.repository.JsonRepository;

@Service
public class GetService {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	private JsonRepository jsonRepository;

	public PersonsAndCountdown getPeoplesCloseToFireStation(int stationNumber) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		PersonsAndCountdown pc = new PersonsAndCountdown();
		Persons persons = new Persons();
		int childrenCountdown = 0;
		int adultsCountdown = 0;

		// 1) Parcours les firestations
		for (FireStation fs : fsList) {
			// 2) Si le station number match
			if (fs.getStation() == stationNumber) {
				// 3) Parcours les personnes
				for (int i = 0; i < pList.size(); i++) { // cette boucle car doit pop les personnes
					Person p = pList.get(i);
					// 4) Si l'adresse match
					if (p.getAddress().compareTo(fs.getAddress()) == 0) {
						// 5) Ajoute la personne à la liste
						persons.addPerson(p);
						// 6) Parcours les medical record
						for (MedicalRecord mr : mrList) {
							// 7) Si nom prénom match
							if (p.getFirstName().compareTo(mr.getFirstName()) == 0
									& p.getLastName().compareTo(mr.getLastName()) == 0) {
								// 8) Calcule l'âge et incrémente en fonction
								if (computeAge(mr.getBirthdate()) > 18) {
									adultsCountdown++;
								} else {
									childrenCountdown++;
								}
								// 9) Pop le mr en cas de même nom prénom
								mrList.remove(mr);
								// 10) Pop p en cas de même nom prénom
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

	public HomeChildren getEnfantAddress(String address) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();

		HomeChildren hc = new HomeChildren();
		List<Child> childList = new ArrayList<Child>();
		List<Adult> adultList = new ArrayList<Adult>();
		hc.setAddress(address);

		// 1) Parcours la liste de personnes
		for (int i = 0; i < pList.size(); i++) {
			Person p = pList.get(i);
			// 2) Si à la bonne adresse
			if (p.getAddress().compareTo(address) == 0) {
				// 3) On regarde le medical record
				for (MedicalRecord mr : mrList) {
					// 4) Regarde si nom et prénom match
					if (mr.getFirstName().compareTo(p.getFirstName()) == 0
							& mr.getLastName().compareTo(p.getLastName()) == 0) {
						// 5) On regarde l'age
						int age = computeAge(mr.getBirthdate());
						// 6) Complète en fonction
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
						// 7) Remove le mr et break la boucle (cas même nom prénom)
						mrList.remove(mr);
						pList.remove(i);
						i--; // 8) Pour équilibre avec le remove
						break;
					}
				}
			}
		}

		hc.setAdultList(adultList);
		hc.setChildList(childList);

		return hc;
	}

	public List<String> getPhoneCloseToFirestation(int stationNumber) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		List<String> phoneList = new ArrayList<String>();

		// 1) Parcours la liste de fire station
		for (FireStation fs : fsList) {
			// 2) Si la fs a le bon num
			if (fs.getStation() == stationNumber) {
				// 3) On parcours les personnes
				for (Person p : pList) {
					// 4) Si bonne adresse
					if (fs.getAddress().compareTo(p.getAddress()) == 0) {
						// 5) On ajoute le numéro de téléphone à la liste
						phoneList.add(p.getPhone());
					}
				}
			}
		}

		// 6) On élimine les doublons
		phoneList = new ArrayList<String>(new HashSet<String>(phoneList));
		return phoneList;
	}

	public List<String> getAllEmailCity(String city) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();

		List<String> emails = new ArrayList<String>();

		// 1) Parcours la liste
		for (Person p : pList) {
			// 2) Si la city de la personne est la même que le param on ajoute son email
			if (p.getCity().compareTo(city) == 0) {
				emails.add(p.getEmail());
			}
		}

		// 3) Enlève les doublons en transformant la liste en set puis re en liste
		emails = new ArrayList<String>(new HashSet<String>(emails));
		return emails;
	}

	public InhabitantFire getPosologieCloseToFirestation(String address) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		InhabitantFire iFire = new InhabitantFire();

		// 1) Cherche le numéro de station
		// 1.1) Parcours la liste des stations
		for (FireStation fs : fsList) {
			// 1.2) Si bonne adresse
			if (fs.getAddress().compareTo(address) == 0) {
				// 1.3) Set le numéro de station
				iFire.setStationNumber(fs.getStation());
				break;
			}
		}

		// 2) Cherche les personnes qui vivent à l'adresse
		Home home = new Home();
		home.setAddress(address);
		// 2.1) Parcours la liste de personnes
		for (Person p : pList) {
			// 2.2) Si bonne adresse
			if (p.getAddress().compareTo(address) == 0) {
				// 2.3) Ajoute les données à hi
				HomeInhabitant hi = new HomeInhabitant();
				hi.setFirstName(p.getFirstName());
				hi.setLastName(p.getLastName());
				hi.setPhone(p.getPhone());
				// 2.4) Parcours le medical record
				for (MedicalRecord mr : mrList) {
					// 2.5 Si bon nom prénom
					if (mr.getFirstName().compareTo(p.getFirstName()) == 0
							& mr.getLastName().compareTo(p.getLastName()) == 0) {
						// 2.6) Ajoute le mr
						hi.setMedication(mr.getMedication());
						hi.setAllergies(mr.getAllergies());
						hi.setAge(computeAge(mr.getBirthdate()));
						// 2.7) Remove mr dans le cas de même nom prénom et break
						mrList.remove(mr);
						break;
					}
				}
				// 2.8) Ajoute l'hi au home
				home.addHomeInhabitant(hi);
			}
		}

		// 2.9) Ajoute le home à iFire
		iFire.setHome(home);

		// TODO: Compléter la posologie et ajouter le numéro de caserne
		// On ajoute les médications et allergies

		return iFire;
	}

	public List<Home> getHomesCloseToStations(List<Integer> stations) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();
		List<FireStation> fsList = jsonRepository.getAllFireStations().getFsList();

		List<Home> homes = new ArrayList<Home>();

		logger.debug("Taille de la liste " + fsList.size());
		// fsList 3x trop grande
		// mrList 2x trop grande
		// => Bug dans jsonRepository / trouvé dois reset les attributs avant de les
		// reremplir à la lecture du fichier

		// 1) Parcours les firestations
		for (FireStation fs : fsList) {
			logger.trace("Tour de boucle fs");
			// 2) Si le numéro de la firestation est dans la liste des station number
			if (stations.contains(fs.getStation())) {
				logger.trace("Dans la condition");
				// 3) On crée un new Home et on initialise son adresse
				Home home = new Home();
				home.setAddress(fs.getAddress());
				// 4) Parcours la liste des personnes pour voir qui habite là
				for (int i = 0; i < pList.size(); i++) {
					Person p = pList.get(i);
					// 5) Si personne à la bonne adresse
					if (p.getAddress().compareTo(home.getAddress()) == 0) {
						// 6) On crée un HomeInhabitant et on initialise ses valeurs
						HomeInhabitant hi = new HomeInhabitant();
						hi.setFirstName(p.getFirstName());
						hi.setLastName(p.getLastName());
						hi.setPhone(p.getPhone());
						// 7) Parcours les medical record pour trouver notre habitant
						for (MedicalRecord mr : mrList) {
							// 8) Si le même nom et prénom
							// Potentiel problème si 2 habitants ont le même nom
							// Mais les mr donnent juste les noms prénoms pour différencier, donc pas de
							// solution
							if (mr.getFirstName().compareTo(hi.getFirstName()) == 0
									& mr.getLastName().compareTo(hi.getLastName()) == 0) {
								// 9) On ajoute le MR à hi
								hi.setMedication(mr.getMedication());
								hi.setAllergies(mr.getAllergies());
								// Calcule age
								hi.setAge(computeAge(mr.getBirthdate()));
								// 9.1) Pop le mr et break la boucle en cas de même nom prénom
								mrList.remove(mr);
								pList.remove(i);
								i--;
								break;
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

	public List<PersonInfo> getPersonInfo(String firstName, String lastName) {
		List<Person> pList = jsonRepository.getAllPersons().getPersonList();
		List<MedicalRecord> mrList = jsonRepository.getAllMedicalRecords().getMrList();

		List<PersonInfo> piList = new ArrayList<PersonInfo>();

		// 1) Parcours la liste de personnes
		logger.debug("Ready to browse the person list");
		for (int i = 0; i < pList.size(); i++) { // Peut pas utiliser l'autre boucle car remove des éléments dans la
													// boucle
			Person p = pList.get(i);
			// 2) Si le bon nom et prénom
			if (p.getFirstName().compareTo(firstName) == 0 & p.getLastName().compareTo(lastName) == 0) {
				logger.debug("Got a person with the right name");
				// 3) Crée une personInfo et remplit ses attributs
				PersonInfo pi = new PersonInfo();
				pi.setFirstName(p.getFirstName());
				pi.setLastName(p.getLastName());
				pi.setEmail(p.getEmail());

				// 4) Parcours la liste de medical record
				for (MedicalRecord mr : mrList) {
					// 5) Si le bon nom et prénom
					if (mr.getFirstName().compareTo(firstName) == 0 & mr.getLastName().compareTo(lastName) == 0) {
						logger.debug("Got a medical record with the right name");
						// 6) On ajoute les valeurs au personal info
						pi.setMedication(mr.getMedication());
						pi.setAllergies(mr.getAllergies());
						pi.setAge(computeAge(mr.getBirthdate()));
						// 7) Prendre en compte le cas où il y a plusieurs personnes avec les même nom
						// et prénom => on pop le mr et la personne et on sort des loop avec un break
						mrList.remove(mr);
						pList.remove(i);
						i--; // Dois retirer 1 à i car le remove retire 1 au éléments suivants de la liste
						break;
					}
				}
				// 8) Ajoute le pi à la liste
				piList.add(pi);
			}
		}
		return piList;
	}
}
