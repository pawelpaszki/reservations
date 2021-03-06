package tests;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;

import util.InputValidator;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This test class tests all functions from InputValidator class
 */

public class InputValidatorTest {
	
	private String validEmailAddress;
	private String invalidEmailAddress1;
	private String invalidEmailAddress2;
	private String invalidEmailAddress3;
	private String validPhoneNumber;
	private String invalidPhoneNumber1;
	private String invalidPhoneNumber2;
	private String invalidPhoneNumber3;

	@Before
	public void setUp() throws Exception {
		validEmailAddress = "joebloggs@gmail.com";
		invalidEmailAddress1 = "joe@bloggs@gmail.com";
		invalidEmailAddress2 = "joebloggs@gmail";
		invalidEmailAddress3 = "joebloggs.gmail.com";
		validPhoneNumber = "051-123456";
		invalidPhoneNumber1 = "051-12345678";
		invalidPhoneNumber2 = "051-1234";
		invalidPhoneNumber3 = "051-12-3456";
	}

	/**
	 * one valid email address and three invalid ones are passed to check if validation works
	 */
	@Test
	public void testEmailValidation() {
		assertEquals(InputValidator.isValidEmailAddress(validEmailAddress), true);
		assertEquals(InputValidator.isValidEmailAddress(invalidEmailAddress1), false);
		assertEquals(InputValidator.isValidEmailAddress(invalidEmailAddress2), false);
		assertEquals(InputValidator.isValidEmailAddress(invalidEmailAddress3), false);
	}
	
	/**
	 * one valid phone number and three invalid ones are passed to check if validation works
	 * valid phone number can only have one hyphen and between 8-10 digits
	 */
	@Test
	public void testPhoneNumberValidation() {
		assertEquals(InputValidator.isValidPhoneNumber(validPhoneNumber), true);
		assertEquals(InputValidator.isValidPhoneNumber(invalidPhoneNumber1), false);
		assertEquals(InputValidator.isValidPhoneNumber(invalidPhoneNumber2), false);
		assertEquals(InputValidator.isValidPhoneNumber(invalidPhoneNumber3), false);
	}
	

}
