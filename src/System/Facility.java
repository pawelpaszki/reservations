package system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.LinkedHashMap;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a class, which looks after dealing with Rooms:
 * - reserving Room
 * - cancelling reservation for a Room
 * - viewing or updating reservation for a Room
 */

public class Facility {

	private ArrayList<Room> rooms = new ArrayList<Room>();

	/**
	 * 
	 * @param startDate - start date of availability period
	 * @param endDate - end date of availability period
	 * @return list of available rooms (may be empty)
	 */
	public ArrayList<Room> checkAvailability(Date startDate, Date endDate) {
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).isAvailable(startDate, endDate)) {
				availableRooms.add(rooms.get(i));
			}
		}
		return availableRooms;
	}

	/**
	 * This method adds room to Facility
	 * @param room - room to be added
	 */
	public void addRoom(Room room) {
		rooms.add(room);
	}

	/**
	 * 
	 * @param roomNumber - number of room
	 * @return Room with specified number
	 */
	public Room getRoom(int roomNumber) {
		for (Room room : rooms) {
			if (room.getNumber() == roomNumber) {
				return room;
			}
		}
		return null;
	}

	/**
	 * @return all rooms
	 */
	public ArrayList<Room> getRooms() {
		return rooms;
	}

	/**
	 * @param rooms
	 *            the rooms to set
	 */
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	/**
	 * This method constructs reservation query for the reservation
	 * @param query - constructed reservation query
	 * @param startMonth - start month of the stay
	 * @param startDay - start day of the stay
	 * @param endMonth - end month of the stay
	 * @param endDay - end month of the stay
	 */
	public void checkAvailability(ReservationQuery query, int startMonth, int startDay, int endMonth, int endDay) {
		ArrayList<Room> currentListOfAvailRooms = new ArrayList<>();
		Date startDate = null;
		Date endDate = null;
		if ((endDay >= startDay && endMonth == startMonth) || endMonth > startMonth) {
			try {
				startDate = new Date(startDay, startMonth, 2017);
				endDate = new Date(endDay, endMonth, 2017);
				currentListOfAvailRooms = checkAvailability(startDate, endDate);
				if (currentListOfAvailRooms.size() > 0) {
					System.out.println(currentListOfAvailRooms.size() + " rooms available");
					if (query != null) {
						query.setStartDate(startDate);
						query.setEndDate(endDate);
						query.setRoomNumber(currentListOfAvailRooms.get(0).getNumber());
					}
				}
			} catch (InputMismatchException e) {
				System.out.println("Incorrect date(s)");
			}
		} else {
			System.out.println("Incorrect dates were provided");
		}
	}
	
	/**
	 * 
	 * @param emailAddress - email of the Guest
	 * @param bookingID - booking number for the reservations 
	 * @return details for the reservation or info that there are no reservations 
	 * for given email address
	 */
	public String getReservationDetails(String emailAddress, int bookingID) {
		String roomInfo = "No reservations for: " + emailAddress;
		if (bookingID != -1) {
			LinkedHashMap<Integer, Integer> bookingIDs = getBookingIdsForGuest(emailAddress);
			if (bookingIDs.size() > 0 && bookingIDs.containsKey(bookingID)) {
				roomInfo = getRoom(bookingIDs.get(bookingID)).getReservationsDetails(bookingID);
			}
		}
		return roomInfo;
	}

	/**
	 * 
	 * @param emailAddress - Guest's email
	 * @param bookingID - booking number for the reservations
	 * @return information about removed reservation
	 */
	public String removeReservation(String emailAddress, int bookingID) {
		String cancellationInfo = "No reservations for: " + emailAddress;
		if (bookingID != -1) {
			HashMap<Integer, Integer> ids = getBookingIdsForGuest(emailAddress);
			if (ids.containsKey(bookingID))
				cancellationInfo = getRoom(ids.get(bookingID)).removeReservationDetails(bookingID);
		}
		return cancellationInfo;
	}

	/**
	 * 
	 * @param emailAddress - Guest's email
	 * @return Map of booking numbers and corresponding Room numbers
	 */
	public LinkedHashMap<Integer, Integer> getBookingIdsForGuest(String emailAddress) {
		LinkedHashMap<Integer, Integer> bookingIDs = new LinkedHashMap<Integer, Integer>();
		for (Room room : rooms) {
			for (Reservation reservation : room.getReservationList()) {
				if (reservation.getGuest().getEmailAddress().equals(emailAddress)) {
					bookingIDs.put(reservation.getBookingId(), room.getNumber());
				}
			}
		}
		return bookingIDs;
	}

	/**
	 * This method makes reservation with the following parameters:
	 * @param roomNumber - number of the room
	 * @param guest
	 * @param startDate of the stay
	 * @param endDate of the stay
	 * @param payment details
	 * @param specialRequests list (may be empty)
	 */
	public void registerReservation(int roomNumber, Guest guest, Date startDate, Date endDate, Payment payment,
			HashSet<SpecialRequest> specialRequests) {
		getRoom(roomNumber).makeReservation(guest, startDate, endDate, payment, specialRequests);
	}

	/**
	 * This method updates details of user with the specified email address and bookingID
	 * @param emailAddress
	 * @param bookingID
	 * @param updatedGuest
	 */
	public void updateGuest(String emailAddress, int bookingID, Guest updatedGuest) {
		HashMap<Integer, Integer> ids = getBookingIdsForGuest(emailAddress);
		if (ids.containsKey(bookingID)) {
			getRoom(ids.get(bookingID)).updateGuest(bookingID, updatedGuest);
		}
	}
	
	/**
	 * This method updates special requests for reservation for user with the specified email address and bookingID
	 * @param emailAddress
	 * @param bookingID
	 * @param specialRequests
	 */
	public void updateSpecialRequests(String emailAddress, int bookingID, HashSet<SpecialRequest> specialRequests) {
		HashMap<Integer, Integer> ids = getBookingIdsForGuest(emailAddress);
		if (ids.containsKey(bookingID)) {
			getRoom(ids.get(bookingID)).updateSpecialRequests(bookingID, specialRequests);
		}
	}
}
