package com.openclassrooms.webappapi.model;

import lombok.Data;

@Data
public class Person {

	private int id;
	private String firstName;
	private String lastName;
	private String address;
	private String city;
	private String zip;
	private String phone;
	private String email;

	public Person() {

	}

	public Person(int id, String firstName, String lastName, String address, String city, String zip, String phone,
			String email) {
		super();
		this.id        = id;
		this.firstName = firstName;
		this.lastName  = lastName;
		this.address   = address;
		this.city      = city;
		this.zip       = zip;
		this.phone     = phone;
	}
	
	// Getters & setters created by @Data
}
