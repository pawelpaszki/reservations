package tests;


import static org.junit.Assert.*;

import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import system.*;

public class FacilityTest {

	private Facility facility;
	private Room room1;
	private Room room2;
	private Reservation reservation1;
	private Reservation reservation2;
	private Reservation reservation3;
	private Reservation reservation4;
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
		int bookingID = Reservation.getIdCounter();
		bookingID++;
		specialRequests = new HashSet<SpecialRequest>();
		specialRequests.add(SpecialRequest.COT);
		reservation1 = new Reservation(new Date (1,1,2017), guest, bookingID, payment, specialRequests);
		reservation2 = new Reservation(new Date (2,2,2017), guest, bookingID, payment, specialRequests);
		reservation3 = new Reservation(new Date (3,3,2017), guest, bookingID, payment, specialRequests);
		reservation4 = new Reservation(new Date (4,4,2017), guest, bookingID, payment, specialRequests);
		room1.addReservation(reservation1);
		room1.addReservation(reservation2);
		room2.addReservation(reservation3);
		room2.addReservation(reservation4);
		room2.addReservation(reservation1);
	}

	/*
	 * This test tests add method without index. before adding an element
	 * size of the ArrayList is checked and after adding one element 
	 * this check is performed again to make sure that the list's size
	 * has increased by one element 
	 */
	@Test
	public void testCheckAvailability() {
		//assertTrue(facility.checkAvailability(new Date(2,1,2017), new Date(30,1,2017)).size() == 2);
		System.out.println(facility.checkAvailability(new Date(2,1,2017), new Date(28,2,2017)).size());
		assertTrue(facility.checkAvailability(new Date(2,1,2017), new Date(28,2,2017)).size() == 1);
		assertTrue(facility.checkAvailability(new Date(1,1,2017), new Date(1,1,2017)).size() == 0);
		assertTrue(facility.checkAvailability(new Date(2,1,2017), new Date(1,1,2017)).size() == 0);
	}
}
