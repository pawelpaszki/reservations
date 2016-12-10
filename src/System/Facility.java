package system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;

public class Facility {

	private ArrayList<Room> rooms = new ArrayList<Room>();

	public ArrayList<Room> checkAvailability(Date startDate, Date endDate) {
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		for (int i = 0; i < rooms.size(); i++) {
			if (rooms.get(i).isAvailable(startDate, endDate)) {
				availableRooms.add(rooms.get(i));
			}
		}
		return availableRooms;
	}

	public void addRoom(Room room) {
		rooms.add(room);
	}

	public Room getRoom(int roomNumber) {
		for (Room room : rooms) {
			if (room.getNumber() == roomNumber) {
				return room;
			}
		}
		return null;
	}

	/**
	 * @return the rooms
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

	public String removeReservation(String emailAddress, int bookingID) {
		String cancellationInfo = "No reservations for: " + emailAddress;
		if (bookingID != -1) {
			HashMap<Integer, Integer> ids = getBookingIdsForGuest(emailAddress);
			if (ids.containsKey(bookingID))
				cancellationInfo = getRoom(ids.get(bookingID)).removeReservationDetails(bookingID);
		}
		return cancellationInfo;
	}

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

	public void registerReservation(int roomNumber, Guest guest, Date startDate, Date endDate, Payment payment,
			HashSet<SpecialRequest> specialRequests) {
		rooms.get(roomNumber).makeReservation(guest, startDate, endDate, payment, specialRequests);
	}

	public void updateGuest(String emailAddress, int bookingID, Guest updatedGuest) {
		HashMap<Integer, Integer> ids = getBookingIdsForGuest(emailAddress);
		if (ids.containsKey(bookingID))
			getRoom(ids.get(bookingID)).updateGuest(bookingID, updatedGuest);
	}
}
