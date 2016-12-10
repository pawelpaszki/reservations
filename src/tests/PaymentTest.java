package tests;

import static org.junit.Assert.*;

import java.util.HashSet;
import org.junit.Test;

import system.Date;
import system.Facility;
import system.Guest;
import system.Payment;
import system.Room;
import system.SpecialRequest;

public class PaymentTest {

	@Test
	public void testSetupPayment() {
		Guest defaultGuest = new Guest("Joe Bloggs", "jbloggs@gmail.com", "051-123456");
		Room room1 = new Room(2, 1);
		Payment defaultPayment = new Payment();
		HashSet<SpecialRequest> defaultSpecialRequests = new HashSet<SpecialRequest>();
		defaultSpecialRequests.add(SpecialRequest.COT);
		
		Facility facility = new Facility();
		facility.addRoom(room1);
		
		room1.makeReservation(defaultGuest, new Date(1, 1, 2017), new Date(4, 1, 2017), defaultPayment, defaultSpecialRequests);		
		int bookingID = room1.getReservationList().get(0).getBookingId();		
		double roomCost = room1.getCost();
		
		defaultPayment.setReservationID(bookingID);
		defaultPayment.setAmount(roomCost);
		defaultPayment.setStatus("paid");
		defaultPayment.setMethod("paid by card");

		assertEquals(defaultPayment.getAmount(), roomCost, 0.01);
		assertEquals(defaultPayment.getStatus(), "paid");
		assertEquals(defaultPayment.getMethod(), "paid by card");
		assertEquals(defaultPayment.getReservationID(), bookingID);
	}

}
