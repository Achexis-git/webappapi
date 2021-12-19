package com.openclassrooms.webappapi.service;

import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.repository.JsonRepository;

/**
 * Execute the instructions send by the associated controller
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.controller.MedicalRecordController
 *
 */
@Service
public class MedicalRecordService {
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
	 * Load the medical records, add the new medical record, and save the new list
	 * 
	 * @param newMedicalRecord Medical record to add
	 * @return Added medical record
	 */
	public MedicalRecord createMedicalRecord(MedicalRecord newMedicalRecord) {
		jsonRepository.load();
		MedicalRecords medicalRecords = jsonRepository.getAllMedicalRecords();

		medicalRecords.addMedicalRecord(newMedicalRecord);
		logger.info("Posted medical record : {} {}", newMedicalRecord.getFirstName(), newMedicalRecord.getLastName());

		jsonRepository.setMedicalRecords(medicalRecords);
		jsonRepository.save();

		return medicalRecords.getMrList().get(medicalRecords.getMrList().size() - 1);
	}

	/**
	 * Load the medical records and get them all
	 * 
	 * @return An object containing the medical records
	 */
	public MedicalRecords readMedicalRecords() {
		jsonRepository.load();
		return jsonRepository.getAllMedicalRecords();
	}

	/**
	 * Load the medical records, update the medical record at the given address and save the new list
	 * 
	 * @param newMedicalRecord Medical record to update
	 * @return Updated medical record
	 */
	public MedicalRecord updateMedicalRecord(MedicalRecord newMedicalRecord) {
		jsonRepository.load();
		MedicalRecords medicalRecords = jsonRepository.getAllMedicalRecords();
		List<MedicalRecord> mrList = medicalRecords.getMrList();

		MedicalRecord updatedMedicalRecord = new MedicalRecord();

		// 1) Browse medical records
		for (int i = 0; i < mrList.size(); i++) {
			MedicalRecord mr = mrList.get(i);
			// 2) If same first name last name
			if (mr.getFirstName().compareTo(newMedicalRecord.getFirstName()) == 0
					&& mr.getLastName().compareTo(newMedicalRecord.getLastName()) == 0) {
				// 3) Update medical record
				medicalRecords.setMrIndex(i, newMedicalRecord);
				logger.info("Updated medical record : {} {}", newMedicalRecord.getFirstName(),
						newMedicalRecord.getLastName());
				updatedMedicalRecord = medicalRecords.getMrList().get(i);
				break;
			}
		}

		jsonRepository.setMedicalRecords(medicalRecords);
		jsonRepository.save();

		return updatedMedicalRecord;
	}

	/**
	 * Load the medical records, delete the medical record with the right address and save the new list
	 * @param delMedicalRecord Medical record to delete
	 * @return Deleted medical record
	 */
	public MedicalRecord deleteMedicalRecord(MedicalRecord delMedicalRecord) {
		jsonRepository.load();
		MedicalRecords medicalRecords = jsonRepository.getAllMedicalRecords();
		List<MedicalRecord> mrList = medicalRecords.getMrList();

		MedicalRecord deletedMedicalRecord = new MedicalRecord();

		// 1) Browse medical records
		for (int i = 0; i < mrList.size(); i++) {
			MedicalRecord mr = mrList.get(i);
			// 2) If same first name last name
			if (mr.getFirstName().compareTo(delMedicalRecord.getFirstName()) == 0
					&& mr.getLastName().compareTo(delMedicalRecord.getLastName()) == 0) {
				// 3) Delete firestation
				deletedMedicalRecord = medicalRecords.getMrList().get(i);
				medicalRecords.removeMrIndex(i);
				logger.info("Deleted medical record : {} {}", delMedicalRecord.getFirstName(),
						delMedicalRecord.getLastName());
				break;
			}
		}

		jsonRepository.setMedicalRecords(medicalRecords);
		jsonRepository.save();

		return deletedMedicalRecord;
	}
}