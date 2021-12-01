package com.openclassrooms.model;

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
}
