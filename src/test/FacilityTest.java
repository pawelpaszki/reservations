package test;


import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import System.Date;
import System.Facility;
import System.Reservation;
import System.Room;

public class FacilityTest {

	private Facility facility;
	private Room room1;
	private Room room2;
	private Reservation reservation1;
	private Reservation reservation2;
	private Reservation reservation3;
	private Reservation reservation4;
	
	@Before
	public void setUp() throws Exception {
		facility = new Facility();
		room1 = new Room(2, 1);
		room2 = new Room(2, 2);
		facility.addRoom(room1);
		facility.addRoom(room2);
		reservation1 = new Reservation(new Date (1,1,2017));
		reservation2 = new Reservation(new Date (2,2,2017));
		reservation3 = new Reservation(new Date (3,3,2017));
		reservation4 = new Reservation(new Date (4,4,2017));
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
		assertTrue(facility.checkAvailability(new Date(2,1,2017), new Date(30,1,2017)).size() == 2);
		assertTrue(facility.checkAvailability(new Date(2,1,2017), new Date(28,2,2017)).size() == 1);
		assertTrue(facility.checkAvailability(new Date(1,1,2017), new Date(1,1,2017)).size() == 0);
	}
}
