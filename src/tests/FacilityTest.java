package tests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.HashSet;

import org.junit.Before;
import org.junit.Test;

import system.*;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This test class tests all functions from Facility class
 */

public class FacilityTest {

	private Facility facility;
	private Guest defaultGuest;
	private Payment defaultPayment;
	private HashSet<SpecialRequest> defaultSpecialRequests;

	@Before
	public void setUp() throws Exception {
		Reservation.setIdCounter(0);

		facility = new Facility();
		defaultGuest = new Guest("Joe Bloggs", "jbloggs@gmail.com", "051-123456");
		defaultPayment = new Payment();
		defaultSpecialRequests = new HashSet<SpecialRequest>();
		defaultSpecialRequests.add(SpecialRequest.COT);
	}

	/**
	 * Availability is checked by checking size of returned ArrayList of Rooms
	 * after reservations are made
	 */
	@Test
	public void testCheckAvailability() {
		Room room1 = new Room(2, 1);
		Room room2 = new Room(2, 2);

		facility.addRoom(room1);
		facility.addRoom(room2);

		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(2, 2, 2017), defaultPayment,
				defaultSpecialRequests);
		room2.makeReservation(defaultGuest, new Date(3, 3, 2017), new Date(4, 4, 2017), defaultPayment,
				defaultSpecialRequests);

		ArrayList<Room> roomsA = facility.getAvailableRooms(new Date(2, 1, 2017), new Date(30, 1, 2017));
		ArrayList<Room> roomsB = facility.getAvailableRooms(new Date(2, 1, 2017), new Date(28, 2, 2017));
		ArrayList<Room> roomsC = facility.getAvailableRooms(new Date(1, 1, 2017), new Date(1, 1, 2017));
		ArrayList<Room> roomsD = facility.getAvailableRooms(new Date(2, 1, 2017), new Date(1, 1, 2017));

		assertEquals(roomsA.size(), 1);
		assertTrue(roomsB.size() == 1);
		assertTrue(roomsC.size() == 1);
		assertTrue(roomsD.size() == 0);

		assertEquals(roomsA.get(0).getNumber(), 2);
		assertEquals(roomsB.get(0).getNumber(), 2);
		assertEquals(roomsC.get(0).getNumber(), 2);
	}

	/**
	 * Make reservation is checked by checking if dates for made reservations are
	 * as expected and if number of days booked for given room matches expected values
	 */
	@Test
	public void testMakeReservation() {
		Room room1 = new Room(2, 1);
		Room room2 = new Room(2, 2);

		facility.addRoom(room1);
		facility.addRoom(room2);

		assertEquals(room1.getReservationList().size(), 0);
		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(4, 1, 2017), defaultPayment,
				defaultSpecialRequests);
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

	/**
	 * Reservation details are tested by checking returned String with expected value
	 */
	@Test
	public void testGetReservationDetails1Guest() {
		Room room1 = new Room(2, 1);
		facility.addRoom(room1);

		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(10, 1, 2017), defaultPayment,
				defaultSpecialRequests);
		// Booked from: 1/1/2017 to: 10//2017(cost per night: 50.0)
		String reservationInfo = facility.getReservationDetails("jbloggs@gmail.com", 1);
		assertEquals(reservationInfo, "Booked from: 1/1/2017 to: 10/1/2017(cost per night: 50.0)");
	}

	/**
	 * Reservation details are tested by checking returned String with expected value
	 */
	@Test
	public void testGetReservationDetails2Guest() {
		Room room1 = new Room(2, 1);
		Room room2 = new Room(2, 2);

		facility.addRoom(room1);
		facility.addRoom(room2);

		Guest guest1 = defaultGuest;
		Guest guest2 = new Guest("a", "abc@email.com", "051-123456");

		room1.makeReservation(guest1, new Date(1, 1, 2017), new Date(2, 1, 2017), defaultPayment,
				defaultSpecialRequests);
		room2.makeReservation(guest2, new Date(1, 2, 2017), new Date(2, 2, 2017), defaultPayment,
				defaultSpecialRequests);

		String reservationInfo1 = facility.getReservationDetails("jbloggs@gmail.com", 1);
		String reservationInfo2 = facility.getReservationDetails("abc@email.com", 2);

		assertEquals(room1.getReservationList().get(0).getBookingId(), 1);
		assertEquals(room2.getReservationList().get(0).getBookingId(), 2);
		assertEquals(reservationInfo1, "Booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");
		assertEquals(reservationInfo2, "Booked from: 1/2/2017 to: 2/2/2017(cost per night: 50.0)");
	}

	/**
	 * Reservation details are tested by checking returned String with expected value
	 */
	@Test
	public void testGetReservationDetailsNoGuest() {
		Room room1 = new Room(2, 1);
		Room room2 = new Room(2, 2);

		facility.addRoom(room1);
		facility.addRoom(room2);

		String reservationInfo = facility.getReservationDetails("jbloggs@gmail.com", 1);
		assertEquals(reservationInfo, "No reservations for: jbloggs@gmail.com");
	}

	/**
	 * Removed reservation details are tested by checking returned String with expected value
	 */
	@Test
	public void testRemove1Reservation() {
		Room room1 = new Room(2, 1);
		facility.addRoom(room1);

		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(2, 1, 2017), defaultPayment,
				defaultSpecialRequests);

		String reservationInfo1 = facility.getReservationDetails("jbloggs@gmail.com", 1);
		assertEquals(reservationInfo1, "Booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");

		String removeReservation = facility.removeReservation("jbloggs@gmail.com", 1);
		String getReservationDetails = facility.getReservationDetails("jbloggs@gmail.com", 1);
		String removeNonExistentReservation = facility.removeReservation("jbloggs@gmail.com", 1);

		assertEquals(removeReservation, "Removed reservation booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");
		assertEquals(getReservationDetails, "No reservations for: jbloggs@gmail.com");
		assertEquals(removeNonExistentReservation, "No reservations for: jbloggs@gmail.com");
	}

	/**
	 * Removed reservation details are tested by checking returned String with expected value
	 */
	@Test
	public void testRemove2Reservations() {
		Room room1 = new Room(2, 1);
		Room room2 = new Room(2, 2);

		facility.addRoom(room1);
		facility.addRoom(room2);

		Guest guest1 = defaultGuest;
		Guest guest2 = new Guest("a", "abc@email.com", "051-123456");
		
		room1.makeReservation(guest1, new Date(1, 1, 2017), new Date(2, 1, 2017), defaultPayment,
				defaultSpecialRequests);
		room2.makeReservation(guest2, new Date(10, 2, 2017), new Date(28, 2, 2017), defaultPayment,
				defaultSpecialRequests);

		String reservationInfo1 = facility.getReservationDetails("jbloggs@gmail.com", 1);
		String reservationInfo2 = facility.getReservationDetails("abc@email.com", 2);
		
		assertEquals(reservationInfo1, "Booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");		
		assertEquals(reservationInfo2, "Booked from: 10/2/2017 to: 28/2/2017(cost per night: 50.0)");

		String removeReservation1 = facility.removeReservation("jbloggs@gmail.com", 1);
		String getReservationDetails1 = facility.getReservationDetails("jbloggs@gmail.com", 1);
		String removeNonExistentReservation1 = facility.removeReservation("jbloggs@gmail.com", 1);
		
		String removeReservation2 = facility.removeReservation("abc@email.com", 2);
		String getReservationDetails2 = facility.getReservationDetails("abc@email.com", 2);
		String removeNonExistentReservation2 = facility.removeReservation("abc@email.com", 2);

		assertEquals(removeReservation1, "Removed reservation booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");
		assertEquals(getReservationDetails1, "No reservations for: jbloggs@gmail.com");
		assertEquals(removeNonExistentReservation1, "No reservations for: jbloggs@gmail.com");
		
		assertEquals(removeReservation2, "Removed reservation booked from: 10/2/2017 to: 28/2/2017(cost per night: 50.0)");
		assertEquals(getReservationDetails2, "No reservations for: abc@email.com");
		assertEquals(removeNonExistentReservation2, "No reservations for: abc@email.com");
	}

	/**
	 * Removed reservation details are tested by checking returned String with expected value
	 */
	@Test
	public void testRemoveReservationNoReservation() {
		Room room1 = new Room(2, 1);
		facility.addRoom(room1);

		assertEquals(room1.getReservationList().size(), 0);

		String reservationInfo = facility.getReservationDetails("jbloggs@gmail.com", 1);
		assertEquals(reservationInfo, "No reservations for: jbloggs@gmail.com");
	}
	
	/**
	 * Update Guest details is tested by confirming that the Guest details for 
	 * given booking number are changed
	 */
	@Test
	public void testUpdateGuestForReservation() {
		Room room1 = new Room(2,1);
		facility.addRoom(room1);
		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(4, 1, 2017), defaultPayment,
				defaultSpecialRequests);
		Guest reservedGuest = room1.getReservationList().get(0).getGuest();
		assertEquals(reservedGuest.getName(), "Joe Bloggs");
		assertEquals(reservedGuest.getEmailAddress(), "jbloggs@gmail.com");
		assertEquals(reservedGuest.getPhoneNumber(), "051-123456");
		
		Guest updatedGuest = new Guest("James Bloggs", "jb@gmail.com", "051-654321");
		facility.updateGuest(reservedGuest.getEmailAddress(), 1, updatedGuest);
		reservedGuest = room1.getReservationList().get(0).getGuest();
		assertNotEquals(reservedGuest.getName(), "Joe Bloggs");
		assertNotEquals(reservedGuest.getEmailAddress(), "jbloggs@gmail.com");
		assertNotEquals(reservedGuest.getPhoneNumber(), "051-123456");
		
		assertEquals(reservedGuest.getName(), updatedGuest.getName());
		assertEquals(reservedGuest.getEmailAddress(), updatedGuest.getEmailAddress());
		assertEquals(reservedGuest.getPhoneNumber(), updatedGuest.getPhoneNumber());
	}
	
	/**
	 * Update Special Requests is tested by confirming that Special Requests for 
	 * given booking number are changed
	 */
	@Test
	public void testUpdateSpecialRequests() {
		Room room1 = new Room(2,1);
		facility.addRoom(room1);
		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(4, 1, 2017), defaultPayment,
				defaultSpecialRequests);
		HashSet<SpecialRequest> specialRequests = room1.getReservationList().get(0).getSpecialRequests();
		assertEquals(specialRequests.size(), 1);
		
		HashSet<SpecialRequest> updatedSpecialRequests = new HashSet<SpecialRequest>();
		updatedSpecialRequests.remove(SpecialRequest.COT);
		updatedSpecialRequests.add(SpecialRequest.BALCONY);
		updatedSpecialRequests.add(SpecialRequest.NONSMOKING);
		facility.updateSpecialRequests(defaultGuest.getEmailAddress(), 1, updatedSpecialRequests);
		
		HashSet<SpecialRequest> newSpecialRequests = room1.getReservationList().get(0).getSpecialRequests();
		assertEquals(newSpecialRequests.size(), 2);
		assertTrue(newSpecialRequests.contains(SpecialRequest.NONSMOKING));
		assertTrue(newSpecialRequests.contains(SpecialRequest.BALCONY));
		assertTrue(!newSpecialRequests.contains(SpecialRequest.COT));
	}
	
}
