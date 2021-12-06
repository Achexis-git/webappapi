package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class HomeChildren {
	private String address;
	private List<Child> childList;
	private List<Adult> adultList;

	public void setAddress(String address) {
		this.address = address;
	}

	public void setChildList(List<Child> childList) {
		this.childList = new ArrayList<Child>(childList);
	}

	public void setAdultList(List<Adult> adultList) {
		this.adultList = new ArrayList<Adult>(adultList);
	}

	public String getAddress() {
		return address;
	}

	public List<Child> getChildList() {
		return new ArrayList<Child>(childList);
	}

	public List<Adult> getAdultList() {
		return new ArrayList<Adult>(adultList);
	}
}
