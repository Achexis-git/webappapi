package com.openclassrooms.webappapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappapiApplication implements CommandLineRunner {
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	public static void main(String[] args) {
		logger.debug("Test log4j");
		SpringApplication.run(WebappapiApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println("Hello World !");

	}

}
