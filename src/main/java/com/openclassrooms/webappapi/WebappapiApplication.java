package com.openclassrooms.webappapi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Base class
 * 
 * @author alexis
 * @version 1.0
 *
 */
@SpringBootApplication
public class WebappapiApplication{
	/**
	 * Logger
	 */
	private static final Logger logger = LogManager.getLogger(WebappapiApplication.class);

	/**
	 * Launch the applications
	 * 
	 * @param args Parameters
	 */
	public static void main(String[] args) {
		logger.debug("Test log4j before start");
		SpringApplication.run(WebappapiApplication.class, args);
		logger.info("Application launched");
	}
}
