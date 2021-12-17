package com.openclassrooms.webappapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class WebappapiApplication{
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	public static void main(String[] args) {
		logger.debug("Test log4j before start");
		SpringApplication.run(WebappapiApplication.class, args);
		logger.info("Application launched");
	}
}
