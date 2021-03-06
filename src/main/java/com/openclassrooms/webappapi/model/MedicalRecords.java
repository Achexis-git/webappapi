package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.openclassrooms.webappapi.WebappapiApplication;

/**
 * Handling of a list of medical records
 * 
 * @author alexis
 * @version 1.0
 *
 */
public class MedicalRecords {
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	private List<MedicalRecord> mrList;

	public MedicalRecords() {
		this.mrList = new ArrayList<>();
	}

	public List<MedicalRecord> getMrList() {
		return new ArrayList<MedicalRecord>(mrList);
	}

	public void setMrList(List<MedicalRecord> mrList) {
		this.mrList = new ArrayList<MedicalRecord>(mrList);
	}

	/**
	 * Add a medical record at the end of the list
	 * 
	 * @param mr Medical record to add
	 */
	public void addMedicalRecord(MedicalRecord mr) {
		this.mrList.add(mr);
	}

	/**
	 * Modify the medical record at the given index
	 * 
	 * @param i Index
	 * @param mr New medical record
	 */
	public void setMrIndex(int i, MedicalRecord mr) {
		if (i >= 0 && i < mrList.size()) {
			mrList.set(i, mr);
		} else {
			logger.error("Want to set out of index medical record");
		}
	}

	/**
	 * Remove the medical record at the given index
	 * 
	 * @param i Index
	 */
	public void removeMrIndex(int i) {
		if (i >= 0 && i < mrList.size()) {
			mrList.remove(i);
		} else {
			logger.error("Want to delete out of index medical record");
		}
	}

	/**
	 * 
	 */
	public void resetList() {
		mrList = new ArrayList<>();
		
	}
	
	public int numberOfMedicalRecords() {
		return mrList.size();
	}
}
