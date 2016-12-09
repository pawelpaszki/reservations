package util;

public class InputValidator {

	/**
	 * 
	 * @param email
	 *            is passed
	 * @return true, if the parameter is a valid address, false otherwise
	 */
	public static boolean isValidEmailAddress(String email) {
		String ePattern = "^[a-zA-Z0-9.!#$%&'*+/=?^_`{|}~-]+@((\\[[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\.[0-9]{1,3}\\])|(([a-zA-Z\\-0-9]+\\.)+[a-zA-Z]{2,}))$";
		java.util.regex.Pattern p = java.util.regex.Pattern.compile(ePattern);
		java.util.regex.Matcher m = p.matcher(email);
		return m.matches();
	}
	
	/**
	 * 
	 * @param phoneNumber
	 *            is passed
	 * @return true, if the parameter is a valid phone number, false otherwise
	 */
	public static boolean isValidPhoneNumber (String phoneNumber) {
		String[] tokens = phoneNumber.split("-");
		int noOfTokens = tokens.length;
		int counter = 0; 
		for(String token: tokens) {
			for(int i = 0; i < token.length(); i++) {
				counter++;
				Character tempChar = token.charAt(i);
				if (!Character.isDigit(tempChar)) {
		            return false;
		        }
			}
		}
		return counter > 7 && counter < 11 && noOfTokens == 2;
	}
}
