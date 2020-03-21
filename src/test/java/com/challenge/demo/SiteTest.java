package com.challenge.demo;

import org.junit.Test;
import org.springframework.http.HttpStatus;

public class SiteTest extends RESTfulTestCase {
	@Test
	public void testCreateSite() {
		enablePrintResponseToOutput();
		
		int httpStatus;
		StringBuffer response = new StringBuffer();
				
		// Get sites. Should return empty JSON array.
		httpStatus = retrieveEntity("/sites", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertEquals("[]", response.toString());
		
		// Negative test. Get the first site
		httpStatus = retrieveEntity("/sites/1", response);
		assertEquals(HttpStatus.NOT_FOUND.value(), httpStatus);
		
		// Negative test. Update the first site
		httpStatus = updateEntity("/sites/1", 
				"{\"siteId\":1,\"siteUUID\":\"4fd28b6a-e58b-4a06-b85d-8343e2c0999c\",\"url\":\"www.bob2.com\",\"createdAt\":\"2020-03-21T05:30:06.548+0000\",\"updatedAt\":\"2020-03-21T05:30:06.548+0000\"}", 
				response);
		assertEquals(HttpStatus.NOT_FOUND.value(), httpStatus);
		
		// Create a site
		httpStatus = createEntity("/sites", "{\"url\": \"www.bob.com\"}", response);
		assertEquals(HttpStatus.CREATED.value(), httpStatus);
		assertTrue(response.toString().startsWith("{\"siteId\":1,\"siteUUID\":\""));
		assertTrue(response.toString().contains("\"url\":\"www.bob.com\""));
		
		// Update the site
		httpStatus = updateEntity("/sites/1", 
				"{\"siteId\":1,\"siteUUID\":\"4fd28b6a-e58b-4a06-b85d-8343e2c0999c\",\"url\":\"www.bob2.com\",\"createdAt\":\"2020-03-21T05:30:06.548+0000\",\"updatedAt\":\"2020-03-21T05:30:06.548+0000\"}", 
				response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertTrue(response.toString().contains("\"url\":\"www.bob2.com\""));
		
		// Get sites. Should return JSON array with one element.
		httpStatus = retrieveEntity("/sites", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertTrue(response.toString().startsWith("[{\"siteId\":1,\"siteUUID\""));
		
		// Create another site.
		httpStatus = createEntity("/sites", "{\"url\": \"www.google.com\"}", response);
		assertEquals(HttpStatus.CREATED.value(), httpStatus);
		
		// Get sites. Should return JSON array with two element.
		httpStatus = retrieveEntity("/sites", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertTrue(response.toString().startsWith("[{\"siteId\":1,\"siteUUID\""));
		assertTrue(response.toString().contains("\"url\":\"www.bob2.com\""));
		assertTrue(response.toString().contains("\"url\":\"www.google.com\""));
		
		// Get the first site
		httpStatus = retrieveEntity("/sites/1", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertTrue(response.toString().contains("\"url\":\"www.bob2.com\""));
		
		// Delete the first site
		httpStatus = deleteEntity("/sites/1", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		// The returned payload is the deleted entity
		assertTrue(response.toString().startsWith("{\"siteId\":1,\"siteUUID\":\""));
		assertTrue(response.toString().contains("\"url\":\"www.bob2.com\""));
		
		// Negative test. Get the first site
		httpStatus = retrieveEntity("/sites/1", response);
		assertEquals(HttpStatus.NOT_FOUND.value(), httpStatus);
		
		// Negative test. Get site with wrong id.
		httpStatus = retrieveEntity("/sites/3", response);
		assertEquals(HttpStatus.NOT_FOUND.value(), httpStatus);
		
		// Get sites. Should return JSON array with one element.
		httpStatus = retrieveEntity("/sites", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertTrue(response.toString().startsWith("[{\"siteId\":2,\"siteUUID\""));
		assertFalse(response.toString().contains("\"url\":\"www.bob2.com\""));
		assertTrue(response.toString().contains("\"url\":\"www.google.com\""));
		
		// Delete the first site
		httpStatus = deleteEntity("/sites/2", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		// The returned payload is the deleted entity
		assertTrue(response.toString().startsWith("{\"siteId\":2,\"siteUUID\""));
		assertTrue(response.toString().contains("\"url\":\"www.google.com\""));
		
		// Get sites. Should return an empty JSON array.
		httpStatus = retrieveEntity("/sites", response);
		assertEquals(HttpStatus.OK.value(), httpStatus);
		assertEquals("[]", response.toString());
		
		return;
	}
}
