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

	

	
}
