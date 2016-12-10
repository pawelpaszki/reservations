package system;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a class representing Payment for Reservations
 */
public class Payment {

	private String method;
	private double amount;
	private String status;
	private int reservationID;
	
	/**
	 * Default Constructor
	 */
	public Payment() {
		
	}
	
	/**
	 * Parameterized Constructor
	 * @param method of the Payment
	 * @param amount to be paid
	 * @param status of the payment
	 * @param reservationID 
	 */
	public Payment (String method, double amount, String status, int reservationID) {
		this.method = method;
		this.amount = amount;
		this.status = status;
		this.reservationID = reservationID;
	}
	/**
	 * @return the method
	 */
	public String getMethod() {
		return method;
	}
	/**
	 * @param method the method to set
	 */
	public void setMethod(String method) {
		this.method = method;
	}
	/**
	 * @return the amount
	 */
	public double getAmount() {
		return amount;
	}
	/**
	 * @param amount the amount to set
	 */
	public void setAmount(double amount) {
		this.amount = amount;
	}
	/**
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the reservationID
	 */
	public int getReservationID() {
		return reservationID;
	}
	/**
	 * @param reservationID the reservationID to set
	 */
	public void setReservationID(int reservationID) {
		this.reservationID = reservationID;
	}
	
	
}
