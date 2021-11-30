package com.openclassrooms.webappapi;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappapiApplication implements CommandLineRunner{

	public static void main(String[] args) {
		SpringApplication.run(WebappapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World !");
		
	}

}
