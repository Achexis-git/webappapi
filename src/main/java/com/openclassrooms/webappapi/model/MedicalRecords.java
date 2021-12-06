package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecords {
	
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
	
	public void addMedicalRecord(MedicalRecord mr) {
		this.mrList.add(mr);
	}
}
