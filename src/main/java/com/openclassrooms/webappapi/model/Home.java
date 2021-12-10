package com.openclassrooms.webappapi.model;

import java.util.ArrayList;
import java.util.List;

public class Home {
	private String address;
	private List<HomeInhabitant> homeInhabitantList;

	public Home() {
		this.address = "";
		this.homeInhabitantList = new ArrayList<HomeInhabitant>();
	}

	public void setHomeInhabitantList(List<HomeInhabitant> homeInhabitantList) {
		this.homeInhabitantList = new ArrayList<HomeInhabitant>(homeInhabitantList);
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public List<HomeInhabitant> getHomeInhabitantList() {
		return new ArrayList<HomeInhabitant>(homeInhabitantList);
	}

	public String getAddress() {
		return address;
	}

	public void addHomeInhabitant(HomeInhabitant hi) {
		homeInhabitantList.add(hi);
	}
}
