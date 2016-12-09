package tests;


import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import system.*;

public class FacilityTest {

	private Facility facility;
	private Room room1;
	private Room room2;
	private Guest guest;
	private Payment payment;
	private HashSet<SpecialRequest> specialRequests;
	
	@Before
	public void setUp() throws Exception {
		facility = new Facility();
		room1 = new Room(2, 1);
		room2 = new Room(2, 2);
		facility.addRoom(room1);
		facility.addRoom(room2);
		guest = new Guest("Joe Bloggs", "jbloggs@gmail.com", "051-123456");
		payment = new Payment();
		specialRequests = new HashSet<SpecialRequest>();
		specialRequests.add(SpecialRequest.COT);
		
		room1.makeReservation(guest, new Date (1,1,2017), new Date (2,2,2017), payment, specialRequests);
		room2.makeReservation(guest, new Date (3,3,2017), new Date (4,4,2017), payment, specialRequests);
	}

	/*
	 * This test tests add method without index. before adding an element
	 * size of the ArrayList is checked and after adding one element 
	 * this check is performed again to make sure that the list's size
	 * has increased by one element 
	 */
	@Test
	public void testCheckAvailability() {		
		ArrayList<Room> roomsA = facility.checkAvailability(new Date(2,1,2017), new Date(30,1,2017));
		ArrayList<Room> roomsB = facility.checkAvailability(new Date(2,1,2017), new Date(28,2,2017));
		ArrayList<Room> roomsC = facility.checkAvailability(new Date(1,1,2017), new Date(1,1,2017));
		ArrayList<Room> roomsD = facility.checkAvailability(new Date(2,1,2017), new Date(1,1,2017));
		
		assertEquals(roomsA.size(),1);
		assertTrue(roomsB.size() == 1);
		assertTrue(roomsC.size() == 1);
		assertTrue(roomsD.size() == 0);
		
		assertEquals(roomsA.get(0).getNumber(),2);
		assertEquals(roomsB.get(0).getNumber(),2);
		assertEquals(roomsC.get(0).getNumber(),2);
	}
}
