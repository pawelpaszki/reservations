package system;

import java.util.ArrayList;

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
}
