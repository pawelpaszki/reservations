package tests;

import static org.junit.Assert.assertEquals;

import java.util.HashSet;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import system.Date;
import system.Facility;
import system.Guest;
import system.Payment;
import system.Reservation;
import system.Room;
import system.SpecialRequest;
import system.UI;

public class UITest {

	private Facility facility;
	private Room room1;
	private Room room2;
	private Guest guest1, guest2;
	private Payment payment;
	private UI ui;
	private HashSet<SpecialRequest> specialRequests;

	@Before
	public void setUp() throws Exception {
		Reservation.setIdCounter(0);

		facility = new Facility();
		room1 = new Room(2, 1);
		room2 = new Room(2, 2);
		guest1 = new Guest("Joe Bloggs", "jbloggs@gmail.com", "051-123456");
		guest2 = new Guest("a", "abc@email.com", "051-123456");
		payment = new Payment();
		specialRequests = new HashSet<SpecialRequest>();
		specialRequests.add(SpecialRequest.COT);

		// initialize UI and add room to facility in each test case
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void testMakeReservation() {
		facility.addRoom(room1);
		facility.addRoom(room2);
		ui = new UI(facility);
		assertEquals(room1.getReservationList().size(), 0);
		ui.makeReservation(guest1, room1, new Date(1, 1, 2017), new Date(4, 1, 2017), payment, specialRequests);
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

		int bookingID = room1.getReservationList().get(0).getBookingId();
		double roomCost = room1.getCost();
		payment.setReservationID(bookingID);
		payment.setAmount(roomCost);
		payment.setStatus("paid");
		payment.setMethod("paid by card");

		assertEquals(payment.getAmount(), roomCost, 0.01);
		assertEquals(payment.getStatus(), "paid");
		assertEquals(payment.getMethod(), "paid by card");
		assertEquals(payment.getReservationID(), bookingID);
	}

	@Test
	public void testGetReservationDetails1Guest() {
		facility.addRoom(room1);
		ui = new UI(facility);
		ui.makeReservation(guest1, room1, new Date(1, 1, 2017), new Date(10, 1, 2017), payment, specialRequests);
		// Booked from: 1/1/2017 to: 10//2017(cost per night: 50.0)
		String reservationInfo = ui.getReservationDetails(facility,"jbloggs@gmail.com", 1);
		assertEquals(reservationInfo, "Booked from: 1/1/2017 to: 10/1/2017(cost per night: 50.0)");
	}

	@Test
	public void testGetReservationDetails2Guest() {
		facility.addRoom(room1);
		facility.addRoom(room2);
		ui = new UI(facility);

		ui.makeReservation(guest1, room1, new Date(1, 1, 2017), new Date(2, 1, 2017), payment, specialRequests);
		ui.makeReservation(guest2, room2, new Date(1, 2, 2017), new Date(2, 2, 2017), payment, specialRequests);

		String reservationInfo1 = ui.getReservationDetails(facility,"jbloggs@gmail.com", 1);
		String reservationInfo2 = ui.getReservationDetails(facility,"abc@email.com", 2);

		assertEquals(reservationInfo1, "Booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");
		assertEquals(reservationInfo2, "Booked from: 1/2/2017 to: 2/2/2017(cost per night: 50.0)");
	}

	@Test
	public void testGetReservationDetailsNoGuest() {
		facility.addRoom(room1);
		facility.addRoom(room2);
		ui = new UI(facility);

		String reservationInfo = ui.getReservationDetails(facility,"jbloggs@gmail.com", 1);
		assertEquals(reservationInfo, "No reservations for: jbloggs@gmail.com");
	}

	@Test
	public void testRemoveReservation(){
		facility.addRoom(room1);
		facility.addRoom(room2);
		ui = new UI(facility);		
		
		ui.makeReservation(guest1, room1, new Date(1, 1, 2017), new Date(2, 1, 2017), payment, specialRequests);
		
		String reservationInfo1 = ui.getReservationDetails(facility,"jbloggs@gmail.com", 1);
		
		assertEquals(reservationInfo1, "Booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");
		
		String removeReservation = facility.removeReservation("jbloggs@gmail.com", 1);
		assertEquals(removeReservation, "Removed reservation booked from: 1/1/2017 to: 2/1/2017(cost per night: 50.0)");
		String getReservationDetails = ui.getReservationDetails(facility,"jbloggs@gmail.com", 1);
		assertEquals(getReservationDetails, "No reservations for: jbloggs@gmail.com");
		String removeNonExistentReservation = facility.removeReservation("jbloggs@gmail.com", 1);
		assertEquals(removeNonExistentReservation, "No reservations for: jbloggs@gmail.com");
	}
}
