package system;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a class representing hotel Guest
 */

public class Guest {

	private String name;
	private String emailAddress;
	private String phoneNumber;
	
	/**
	 * Constructor for Guest instances
	 * @param name of the guest
	 * @param emailAddress of the guest
	 * @param phoneNumber of the guest
	 */
	public Guest(String name, String emailAddress, String phoneNumber) {
		this.name = name;
		this.emailAddress = emailAddress;
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the emailAddress
	 */
	public String getEmailAddress() {
		return emailAddress;
	}
	/**
	 * @param emailAddress the emailAddress to set
	 */
	public void setEmailAddress(String emailAddress) {
		this.emailAddress = emailAddress;
	}
	/**
	 * @return the phoneNumber
	 */
	public String getPhoneNumber() {
		return phoneNumber;
	}
	/**
	 * @param phoneNumber the phoneNumber to set
	 */
	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}
	
	/**
	 * String representation of Guest instance
	 */
	public String toString() {
		return name + ", " + emailAddress + ", " + phoneNumber;
	}
}
