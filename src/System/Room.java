package System;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;

public class Room {

	private int capacity;
	private int number;
	private HashMap<String, Reservation> reservations = new HashMap<String, Reservation>();
	private ArrayList<Reservation> reservationList = new ArrayList<Reservation>();

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
		HashSet<Integer> monthWith31 = new HashSet<>(Arrays.asList(new Integer[] { 1, 3, 5, 7, 8, 10, 12 }));
		HashSet<Integer> monthWith30 = new HashSet<>(Arrays.asList(new Integer[] { 4, 6, 9, 11 }));
		int counter = 0;
		for (int month = startDate.getMonth(); month <= endDate.getMonth();) {
			for (int day = startDate.getDay();; day++) {
				if (month >= endDate.getMonth() && day > endDate.getDay() || (month > endDate.getMonth())) {
					if (counter == 0) {
						return false;
					} else {
						return true;
					}
				}
				if ((monthWith31.contains(Integer.valueOf(month)) && day > 31)
						|| (monthWith30.contains(Integer.valueOf(month)) && day > 30) || (day > 28 && month == 2)) {
					month++;
					day = 1;
				}
				counter++;
				String tempDate = day + "/" + month + "/" + 2017;
				System.out.println(tempDate);
				if (reservations.containsKey(tempDate)) {
					return false;
				}
			}
		}
		if (counter == 0) {
			return false;
		}
		return true;
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

	public void addReservation(Reservation reservation) {
		reservationList.add(reservation);
		Date date = reservation.getDate();
		String stringDate = date.getDay() + "/" + date.getMonth() + "/" + date.getYear();
		reservations.put(stringDate, reservation);
	}
}
