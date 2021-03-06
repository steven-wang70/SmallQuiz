package com.challenge.demo;

import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;

public class QuestionTest extends RESTfulTestCase {

	@Before
	public void setUp() {
		super.setUp();
		createEntity("/sites", "{\"url\": \"www.bob.com\"}", null);
	}
	
	@Test
	public void testQuestion() {
		enablePrintResponseToOutput();
		
		int httpStatus;
		StringBuffer response = new StringBuffer();
		
		// Create a question.
		httpStatus = createEntity("/sites/1/questions", "{\"siteId\":1, \"questionType\": 1, \"question\": \"how many toes does a pig have?\"}", response);
		assertEquals(HttpStatus.CREATED.value(), httpStatus);
		assertTrue(response.toString().startsWith("{\"questionId\":2,\"dimInfo\":[],\"question\":\"how many toes does a pig have?\",\"siteId\":1,\"questionType\":1,\"createdAt\":"));
		
		// Get question. Should return JSON array with one element.
		httpStatus = retrieveEntity("/sites/1/questions", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertTrue(response.toString().startsWith("[{\"questionId\":2,\"dimInfo\":null,\"question\":\"how many toes does a pig have?\",\"siteId\":1,\"questionType\":1,\"createdAt\""));
		
		
	}
}
