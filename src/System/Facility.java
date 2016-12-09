package system;

import java.util.ArrayList;
import java.util.HashMap;

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
		for(Room room: rooms){
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
	 * @param rooms the rooms to set
	 */
	public void setRooms(ArrayList<Room> rooms) {
		this.rooms = rooms;
	}
	
	public String getReservationDetails(String emailAddress, int bookingID) {
		String roomInfo = "No reservations for: " + emailAddress;
		HashMap<Integer, Integer> bookingIDs = getBookingIdsForGuest(emailAddress);
		if (bookingIDs.size() > 0 && bookingIDs.containsKey(bookingID)) {
			roomInfo = getRoom(bookingIDs.get(bookingID)).getReservationsDetails(bookingID);
		}
		return roomInfo;
	}
	
	public String removeReservation(String emailAddress, int bookingID) {
		String cancellationInfo = "No reservations for: " + emailAddress;
		
		HashMap<Integer, Integer> ids = getBookingIdsForGuest(emailAddress);
		if(ids.containsKey(bookingID))
			cancellationInfo = getRoom(ids.get(bookingID)).removeReservationDetails(bookingID);
		return cancellationInfo;
	}

	public HashMap<Integer, Integer> getBookingIdsForGuest(String emailAddress) {
		HashMap<Integer, Integer> bookingIDs = new HashMap<Integer, Integer>();
		for (Room room : rooms) {
			for (Reservation reservation : room.getReservations()) {
				if (reservation.getGuest().getEmailAddress().equals(emailAddress)) {
					bookingIDs.put(reservation.getBookingId(), room.getNumber());
				}
			}
		}
		return bookingIDs;
	}
}
