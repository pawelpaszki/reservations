package system;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a class representing Hotel Room
 */

public class Room {

	private int capacity;
	private int number;
	// The HashMap below is used to quickly check if there is a booking
	// key for the map is String representation of the booking Date
	private HashMap<String, Reservation> reservations = new HashMap<String, Reservation>();
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();
	private double cost = 50.0;

	/**
	 * Constructor for Room instances
	 * @param capacity of the room
	 * @param number - Room number
	 */
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

	/**
	 * 
	 * @param startDate of period to be checked, if available
	 * @param endDate of period to be checked, if available
	 * @return true if Room is available for specified period, false otherwise
	 */
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
	
	/**
	 * This method makes a reservation for the Room
	 * @param guest
	 * @param startDate
	 * @param endDate
	 * @param payment
	 * @param specialRequests
	 */
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

	/**
	 * 
	 * @param bookingID 
	 * @return String representation of reservation details with booking 
	 * number specified as a parameter
	 */
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

	/**
	 * 
	 * @param bookingID
	 * @return details of removed reservation for specified booking number
	 */
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
	 * This method updates Guest details for specified booking number (for single booking) 
	 * @param bookingID
	 * @param guest
	 */
	public void updateGuest(int bookingID, Guest guest) {
		for (Reservation reservation: reservationList) {
			if (reservation.getBookingId() == bookingID) {
				reservation.setGuest(guest);
			}
		}
	}
	
	/**
	 * This method updates special requests for reservation with specified booking number
	 * @param bookingID
	 * @param specialRequests
	 */
	public void updateSpecialRequests(int bookingID, HashSet<SpecialRequest> specialRequests) {
		for (Reservation reservation: reservationList) {
			if (reservation.getBookingId() == bookingID) {
				reservation.setSpecialRequests(specialRequests);
			}
		}
	}
	
	/**
	 * 
	 * @param bookingID
	 * @return special requests for reservation with given booking number
	 */
	public HashSet<SpecialRequest> getSpecialRequests(int bookingID) {
		for (Reservation reservation: reservationList) {
			if (reservation.getBookingId() == bookingID) {
				return reservation.getSpecialRequests();
			}
		}
		return null;
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
