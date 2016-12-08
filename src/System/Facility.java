package System;
import java.util.ArrayList;

public class Facility {
	
	private ArrayList<Room> rooms = new ArrayList<Room>();
	
	public ArrayList<Room> checkAvailability(Date startDate, Date endDate) {
		ArrayList<Room> availableRooms = new ArrayList<Room>();
		for (int i = 0; i < rooms.size(); i++) {
			if(rooms.get(i).isAvailable(startDate,endDate)) {
				availableRooms.add(rooms.get(i));
			}
		}
		return availableRooms;
	}
	
	public void addRoom(Room room) {
		rooms.add(room);
	}
}
