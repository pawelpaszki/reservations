package tests;

import static org.junit.Assert.*;

import java.util.InputMismatchException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is very simple test for Date class checking if correct type 
 * of Exception is thrown 
 */

public class DateTest {

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@AfterClass
	public static void tearDownAfterClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testCreateInvalidDate() {
		try {
			
		}
		catch (Exception e) {
			assertTrue(e instanceof InputMismatchException);
		}
	}

}
