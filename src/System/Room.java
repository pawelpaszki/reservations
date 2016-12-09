package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

public class Room {

	private int capacity;
	private int number;
	private HashMap<String, Reservation> reservations = new HashMap<String, Reservation>();
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private double cost = 50.0;

	public Room(int capacity, int number) {
		this.capacity = capacity;
		this.number = number;
	}
	
	/**
	 * @return the capacity
	 */
	public int getCapacity() {
		return capacity;
	}

	/**
	 * @param capacity
	 *            the capacity to set
	 */
	public void setCapacity(int capacity) {
		this.capacity = capacity;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number
	 *            the number to set
	 */
	public void setNumber(int number) {
		this.number = number;
	}

	/**
	 * @return the reservations
	 */
	public ArrayList<Reservation> getReservations() {
		return reservationList;
	}

	/**
	 * @param reservations
	 *            the reservations to set
	 */
	public void setReservations(ArrayList<Reservation> reservations) {
		this.reservationList = reservations;
	}

	public boolean isAvailable(Date startDate, Date endDate) {
		if (startDate.compareTo(endDate) > 0)
			return false;

		Date focusDate = startDate;
		while (focusDate != null) {
			if (reservations.containsKey(focusDate.getDay() + "/" + focusDate.getMonth() + "/" + focusDate.getYear())) {
				return false;
			}
			focusDate = Date.getNextDate(focusDate, endDate);
		}
		return true;
	}
	
	public void makeReservation(Guest guest, Date startDate, Date endDate, Payment payment, HashSet<SpecialRequest> specialRequests) {
		Date focusDate = Date.clone(startDate);
		int bookingID = Reservation.nextReservationID();
		while (focusDate != null) {
			Reservation reservation = new Reservation(Date.clone(focusDate),guest, bookingID, payment, specialRequests);
			reservationList.add(reservation);
			reservations.put(focusDate.getDay() + "/" + focusDate.getMonth() + "/" + focusDate.getYear(), reservation);
			focusDate = Date.getNextDate(focusDate, endDate);
		}
	}

	public String getReservationsDetails(int bookingID) {
		Date startDate = new Date(31, 12, 2017);
		Date endDate = new Date(31, 12, 2017);
		for (int i = 0; i < reservationList.size(); i++) {
			if (reservationList.get(i).getBookingId() == bookingID) {
				// start of the sublist whose items have the same bookID as the
				// bookingID argument
				startDate = reservationList.get(i).getDate();
				while (++i < reservationList.size() && reservationList.get(i).getBookingId() == bookingID) {
				}
				endDate = reservationList.get(--i).getDate();
				i = reservationList.size() - 1;
			}
		}
		return "Booked from: " + startDate + " to: " + endDate + "(cost per night: " + getCost() + ")";
	}

	public String removeReservationDetails(int bookingID) {
		String returnString = getReservationsDetails(bookingID).replaceAll("\\bBooked\\b", "Removed reservation booked");
		for (int i = 0; i < reservationList.size(); i++) {
			if (reservationList.get(i).getBookingId() == bookingID) {
				reservations.remove(reservationList.get(i).getDate());
				reservationList.set(i, null);
			}
		}
		reservationList.removeAll(Collections.singleton(null));
		return returnString;
	}
	/**
	 * @return the reservationList
	 */
	public ArrayList<Reservation> getReservationList() {
		return reservationList;
	}

	/**
	 * @param reservationList
	 *            the reservationList to set
	 */
	public void setReservationList(ArrayList<Reservation> reservationList) {
		this.reservationList = reservationList;
	}

	/**
	 * @return the cost
	 */
	public double getCost() {
		return cost;
	}

	/**
	 * @param cost
	 *            the cost to set
	 */
	public void setCost(double cost) {
		this.cost = cost;
	}
}
