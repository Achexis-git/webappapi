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

@Service
public class MedicalRecordService {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	@Autowired
	JsonRepository jsonRepository;

	public MedicalRecord createMedicalRecord(MedicalRecord newMedicalRecord) {
		jsonRepository.load();
		MedicalRecords medicalRecords = jsonRepository.getAllMedicalRecords();

		medicalRecords.addMedicalRecord(newMedicalRecord);
		logger.info("Posted medical record : {} {}", newMedicalRecord.getFirstName(), newMedicalRecord.getLastName());

		jsonRepository.setMedicalRecords(medicalRecords);
		jsonRepository.save();

		return newMedicalRecord;
	}

	public MedicalRecords readMedicalRecords() {
		jsonRepository.load();
		return jsonRepository.getAllMedicalRecords();
	}

	public MedicalRecord updateMedicalRecord(MedicalRecord newMedicalRecord) {
		jsonRepository.load();
		MedicalRecords medicalRecords = jsonRepository.getAllMedicalRecords();
		List<MedicalRecord> mrList = medicalRecords.getMrList();

		// Browse medical records
		for (int i = 0; i < mrList.size(); i++) {
			MedicalRecord mr = mrList.get(i);
			// If same first name last name
			if (mr.getFirstName().compareTo(newMedicalRecord.getFirstName()) == 0
					&& mr.getLastName().compareTo(newMedicalRecord.getLastName()) == 0) {
				// Update medical record
				medicalRecords.setMrIndex(i, newMedicalRecord);
				logger.info("Updated medical record : {} {}", newMedicalRecord.getFirstName(),
						newMedicalRecord.getLastName());
				break;
			}
		}

		jsonRepository.setMedicalRecords(medicalRecords);
		jsonRepository.save();

		return newMedicalRecord;
	}

	public MedicalRecord deleteMedicalRecord(MedicalRecord delMedicalRecord) {
		jsonRepository.load();
		MedicalRecords medicalRecords = jsonRepository.getAllMedicalRecords();
		List<MedicalRecord> mrList = medicalRecords.getMrList();

		// 1) Browse medical records
		for (int i = 0; i < mrList.size(); i++) {
			MedicalRecord mr = mrList.get(i);
			// 2) If same first name last name
			if (mr.getFirstName().compareTo(delMedicalRecord.getFirstName()) == 0
					&& mr.getLastName().compareTo(delMedicalRecord.getLastName()) == 0) {
				// 3) Delete firestation
				medicalRecords.removeMrIndex(i);
				logger.info("Deleted medical record : {} {}", delMedicalRecord.getFirstName(),
						delMedicalRecord.getLastName());
				break;
			}
		}

		jsonRepository.setMedicalRecords(medicalRecords);
		jsonRepository.save();

		return delMedicalRecord;
	}
}