package com.hart.test;

import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;
import com.hart.controller.RestExecutor;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class TestScript {
	
	private static final String baseUrl = "https://api-yellow.hart.com/v3.2";
	private static final String allergyStringUrl = "/allergy/list";
	private static final String username = "code.challenge@hart.com";
	private static final String password = "Challenge1!";
	public static String userId;
	public static int numOfAllergies = 8;
	public static String allergyName = "Soy";
	public static final Logger logger=Logger.getLogger(TestScript.class);
	static {
		PropertyConfigurator.configure("resources/log4j.properties");
	}
	
	@BeforeClass(alwaysRun = true)
	public void testLogin() {
		logger.info("Running Post Login...");
		RestExecutor executor = new RestExecutor();
		executor.postLogin(baseUrl, username, password)
			.expectCode(200);
	}
	
	@Test
	public void testGetAllergies() {
		logger.info("Running Allergies test...");
		RestExecutor executor = new RestExecutor();
		executor.get(baseUrl,allergyStringUrl)
			.expectCode(200)
			.expectListSize(numOfAllergies)
			.expectListItem(allergyName);
		
	}
	
}
