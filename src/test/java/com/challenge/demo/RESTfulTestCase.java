package com.challenge.demo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import org.junit.After;
import org.junit.Before;
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.annotation.DirtiesContext;

import junit.framework.TestCase;

/**
 * The base class for RESTful test cases.
 * It just starts and stop the RESTful service for testing, and
 * wrap RESTful calls.
 * 
 * Add annotation @DirtiesContext to prevent a running test keep the Spring Boot 
 * listening port even the test is completed.
 * @author steve
 *
 */
@DirtiesContext
public abstract class RESTfulTestCase extends TestCase {

	private ConfigurableApplicationContext context = null;
	
	// Helper functions to print HTTP response to stdout.
	private boolean printResponseToOutput = false;
	
	protected void enablePrintResponseToOutput() {
		printResponseToOutput = true;
	}
	
	protected void disablePrintResponseToOutput() {
		printResponseToOutput = false;
	}
	
	@Before
	public void setUp() {
		String[] args = TestRunner.INTIIAL_ARGS;
		if (args == null) {
			args = new String[0];
		}
		
		context = SpringApplication.run(DemoApplication.class, args);
	}
	
	@After
	public void tearDown() {
		SpringApplication.exit(context, new ExitCodeGenerator() {
            @Override
            public int getExitCode() {
                return 0;
            }
        });
	}
	
	private static final String SERVER_HOST = "http://localhost:8080"; 
	
	private int sendHttpRequest(String relativeURL, String method, String payload, StringBuffer response) {
		// First clear the response buffer.
		if (response != null) {
			response.delete(0, response.length());
		}
		
		int responseCode = 800; // This is not a valid HTTP status.
		HttpURLConnection conn = null;
		
		try {
			URL url =   new URL(SERVER_HOST + relativeURL); 
			conn = (HttpURLConnection) url.openConnection(); 
			
			// set the request method and properties. 
			conn.setRequestMethod(method); 
			conn.setRequestProperty("Content-Type", "application/json"); 
			conn.setRequestProperty("Accept", "application/json");
			
			// Send request body if required.
			if (payload != null) {
				conn.setDoOutput(true);
				OutputStream out = conn.getOutputStream();
				out.write(payload.getBytes());
			}
			
			// Get response
			responseCode = conn.getResponseCode();
			
			// Get the response stream
			InputStream is = null;
			if (responseCode >= 300) {
				// Something wrong
				is = conn.getErrorStream();
			} else {
				is = conn.getInputStream();
			}
			
			// Read the buffer.
			if (is != null && response != null) {
				BufferedReader br = new BufferedReader(new InputStreamReader(is));
                String responseLine = null; 
                while ((responseLine = br.readLine()) != null)  
                { 
                    response.append(responseLine).append("\n"); 
                }
            }
		} catch (IOException ex) {
			ex.printStackTrace();
		} catch (Exception ex2) {
			ex2.printStackTrace();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}
		}
		
		if (response != null && response.length() > 0) {
			// Remove the last \n which is not required.
			response.deleteCharAt(response.length() - 1);
		}
		
		printResponse(responseCode, response);
		
		return responseCode;
	}
	
	protected int createEntity(String relativeURL, String payload, StringBuffer response) {
		return sendHttpRequest(relativeURL, "POST", payload, response);
	}
	
	protected int updateEntity(String relativeURL, String payload, StringBuffer response) {
		return sendHttpRequest(relativeURL, "PUT", payload, response);
	}
	
	protected int retrieveEntity(String relativeURL, StringBuffer response) {
		return sendHttpRequest(relativeURL, "GET", null, response);
	}
	
	protected int deleteEntity(String relativeURL, StringBuffer response) {
		return sendHttpRequest(relativeURL, "DELETE", null, response);
	}
	
	protected void printResponse(int httpStatus, StringBuffer response) {
		if (printResponseToOutput) {
			System.out.println(httpStatus);
			
			if (response != null) {
				System.out.println(response);
			}
		}
	}
}
