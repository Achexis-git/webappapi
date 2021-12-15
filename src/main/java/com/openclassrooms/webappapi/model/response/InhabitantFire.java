package com.openclassrooms.webappapi.model.response;

public class InhabitantFire {
	private int stationNumber;
	private Home home;

	public void setStationNumber(int stationNumber) {
		this.stationNumber = stationNumber;
	}

	public void setHome(Home home) {
		this.home = new Home();
		this.home.setAddress(home.getAddress());
		this.home.setHomeInhabitantList(home.getHomeInhabitantList());
	}

	public int getStationNumber() {
		return stationNumber;
	}

	public Home getHome() {
		Home h = new Home();
		h.setAddress(home.getAddress());
		h.setHomeInhabitantList(home.getHomeInhabitantList());
		return h;
	}
}
