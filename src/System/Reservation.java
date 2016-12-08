package system;

public class Reservation {

	private static int idCounter = 0;
	
	private int bookingId;
	private Date date;
	private Payment payment;
	private Guest guest;
	
	public Reservation (Date date, Guest guest) {
		this.bookingId = idCounter++;
		this.date = date;
		this.setGuest(guest);
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
}
