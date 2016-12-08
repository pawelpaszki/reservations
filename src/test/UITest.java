package test;

import static org.junit.Assert.*;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import system.Date;
import system.Facility;
import system.Guest;
import system.Reservation;
import system.Room;
import system.UI;

public class UITest {

	private Facility facility;
	private Room room1;
	private Room room2;
	private Reservation reservation1;
	private Reservation reservation2;
	private Reservation reservation3;
	private Reservation reservation4;
	private Guest guest;

	private UI ui;

	@Before
	public void setUp() throws Exception {
		facility = new Facility();
		room1 = new Room(2, 1);
		room2 = new Room(2, 2);
		facility.addRoom(room1);
		facility.addRoom(room2);
		ui = new UI(facility);
		guest = new Guest("Joe Bloggs", "jbloggs@gmail.com", "051-123456");
		// reservation1 = new Reservation(new Date (1,1,2017), guest);
		// reservation2 = new Reservation(new Date (2,2,2017), guest);
		// reservation3 = new Reservation(new Date (3,3,2017), guest);
		// reservation4 = new Reservation(new Date (4,4,2017), guest);
		// room1.addReservation(reservation1);
		// room1.addReservation(reservation2);
		// room2.addReservation(reservation3);
		// room2.addReservation(reservation4);
		// room2.addReservation(reservation1);
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeReservation() {
		assertEquals(room1.getReservationList().size(), 0);
		ui.makeReservation(guest, room1, new Date(1, 1, 2017), new Date(4, 1, 2017));
		assertEquals(room1.getReservationList().size(), 4);

		Date reservedDate1 = room1.getReservationList().get(0).getDate();
		Date reservedDate2 = room1.getReservationList().get(1).getDate();
		
		Date reservedDate3 = room1.getReservationList().get(2).getDate();
		Date reservedDate4 = room1.getReservationList().get(3).getDate();
		assertEquals(reservedDate1.getDay(), 1);
		assertEquals(reservedDate2.getDay(), 2);
		assertEquals(reservedDate3.getDay(), 3);
		assertEquals(reservedDate4.getDay(), 4);

		assertEquals(reservedDate1.getMonth(), 1);
		assertEquals(reservedDate2.getMonth(), 1);
		assertEquals(reservedDate3.getMonth(), 1);
		assertEquals(reservedDate4.getMonth(), 1);

		Guest reservedGuest = room1.getReservationList().get(0).getGuest();
		assertEquals(reservedGuest.getName(), "Joe Bloggs");
		assertEquals(reservedGuest.getEmailAddress(), "jbloggs@gmail.com");
		assertEquals(reservedGuest.getPhoneNumber(), "051-123456");
	}

}
