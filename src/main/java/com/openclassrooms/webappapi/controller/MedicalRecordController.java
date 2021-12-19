package com.openclassrooms.webappapi.controller;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.openclassrooms.webappapi.WebappapiApplication;
import com.openclassrooms.webappapi.model.MedicalRecord;
import com.openclassrooms.webappapi.model.MedicalRecords;
import com.openclassrooms.webappapi.service.MedicalRecordService;

/**
 * CRUD controller for medical records
 * 
 * @author alexis
 * @version 1.0
 * @see com.openclassrooms.webappapi.service.MedicalRecordService
 *
 */
@RestController
public class MedicalRecordController {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Required to process requests
	 */
	@Autowired
	MedicalRecordService mrService;

	@PostMapping(path = "/medicalrecord", consumes = "application/json", produces = "application/json")
	public MedicalRecord postMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return mrService.createMedicalRecord(medicalRecord);
	}

	@GetMapping(path = "/medicalrecord", produces = "application/json")
	public MedicalRecords getMedicalRecords() {
		logger.info("Getting medical records");
		return mrService.readMedicalRecords();
	}

	@PutMapping(path = "/medicalrecord", consumes = "application/json", produces = "application/json")
	public MedicalRecord putMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return mrService.updateMedicalRecord(medicalRecord);
	}

	@DeleteMapping(path = "/medicalrecord", consumes = "application/json", produces = "application/json")
	public MedicalRecord deleteMedicalRecord(@RequestBody MedicalRecord medicalRecord) {
		return mrService.deleteMedicalRecord(medicalRecord);
	}
}
