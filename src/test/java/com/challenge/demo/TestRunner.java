package com.challenge.demo;

import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

/**
 * The main entry of the JUnit test.
 * @param args
 */
public class TestRunner {
	// Keep the arguments for global use.
	public static String[] INTIIAL_ARGS = null;
	
	public static void main(String[] args) {
		INTIIAL_ARGS = args;
		
		// Run test cases.
		Result result = JUnitCore.runClasses(RESTfulTests.class);
		
		for (Failure failure : result.getFailures()) {
			System.out.println(failure.toString());
		}
		
		System.out.println(result.wasSuccessful());
	}
}
