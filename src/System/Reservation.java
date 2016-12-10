package system;

import java.util.HashSet;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a class representing Payment for Reservations
 */

public class Reservation {

	private static int idCounter = 0;
	
	private int bookingId;
	private Date date;
	private Payment payment;
	private Guest guest;
	private HashSet<SpecialRequest> specialRequests;
	
	/**
	 * Constructor for Reservation instances
	 * @param date
	 * @param guest
	 * @param bookingID
	 * @param payment
	 * @param specialRequests
	 */
	public Reservation (Date date, Guest guest, int bookingID, Payment payment, HashSet<SpecialRequest> specialRequests) {
		this.bookingId = bookingID;
		this.date = date;
		this.setGuest(guest);
		this.payment = payment;
		this.specialRequests = specialRequests;
	}
	
	/**
	 * 
	 * @return ID, which is unique for each booking (not necessarily unique for each Reservation)
	 */
	public static int nextReservationID(){
		return ++idCounter;
	}
	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}
	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}
	/**
	 * @return the payment
	 */
	public Payment getPayment() {
		return payment;
	}
	/**
	 * @param payment the payment to set
	 */
	public void setPayment(Payment payment) {
		this.payment = payment;
	}
	/**
	 * @return the guest
	 */
	public Guest getGuest() {
		return guest;
	}
	/**
	 * @param guest the guest to set
	 */
	public void setGuest(Guest guest) {
		this.guest = guest;
	}
	/**
	 * @return the bookingId
	 */
	public int getBookingId() {
		return bookingId;
	}
	/**
	 * @return the idCounter
	 */
	public static int getIdCounter() {
		return idCounter;
	}
	/**
	 * @param idCounter the idCounter to set
	 */
	public static void setIdCounter(int idCounter) {
		Reservation.idCounter = idCounter;
	}
	/**
	 * String representation of Reservation
	 */
	public String toString(){
		return "bookingID: " + bookingId + ", date: " + date + ", payment: " + payment + ", guest: " + guest;
	}
	/**
	 * @return the specialRequests
	 */
	public HashSet<SpecialRequest> getSpecialRequests() {
		return specialRequests;
	}
	/**
	 * @param specialRequests the specialRequests to set
	 */
	public void setSpecialRequests(HashSet<SpecialRequest> specialRequests) {
		this.specialRequests = specialRequests;
	}
}
