package com.challenge.demo;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

/**
 * Test suite definition for RESTful tests.
 * @author steve
 *
 */
@RunWith(Suite.class)
@SuiteClasses({
	SiteTest.class,
	QuestionTest.class
})
public class RESTfulTests {

}
