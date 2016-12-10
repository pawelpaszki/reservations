package system;

import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map.Entry;
import java.util.Scanner;

import util.InputValidator;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a driver class for the system. It uses scanner to get
 * user input, where needed. 
 */

public class UI {

	private Scanner input = new Scanner(System.in);
	private Facility facility = new Facility();

	public static void main(String[] args) {
		UI ui = new UI();
		ui.runMenu();
	}

	/**
	 * During construction number of rooms is added to facility
	 */
	public UI() {
		for (int i = 1; i < 6; i++) {
			facility.addRoom(new Room(4, i));
		}
	}

	/**
	 * Parameterized constructor
	 * @param facility 
	 */
	public UI(Facility facility) {
		this.facility = facility;
	}

	/**
	 * 
	 * @return menu option
	 */
	private int mainMenu() {
		int option = 0;
		boolean correct = false;
		do {
			try {
				System.out.println("  Hotel reservation system");
				System.out.println("  ---------");
				System.out.println("  1) Check room availability");
				System.out.println("  2) Make Reservation");
				System.out.println("  3) Get Reservation Details");
				System.out.println("  4) Update Personal Details for Reservation");
				System.out.println("  5) Add Special Requests to Reservation");
				System.out.println("  6) Remove reservation");
				System.out.println("  0) Exit");
				System.out.print("==>> ");
				option = input.nextInt();
				correct = true;
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("\nNumber needed... Please try again\n");
			}
		} while (!correct);
		return option;
	}

	/**
	 * This is the method that controls the menu
	 */
	private void runMenu() {
		int option = mainMenu();

		while (option != 0) {

			switch (option) {
			case 1:
				checkAvailability(null);
				break;
			case 2:
				buildReservationQuery(facility);
				break;
			case 3:
				String emailAddress = getValidEmailAddress();
				System.out.println(
						facility.getReservationDetails(emailAddress, promptForBookingID(facility, emailAddress)));
				break;
			case 4:
				String emailForGuestToUpdate = getValidEmailAddress();
				int reservationToUpdate = promptForBookingID(facility, emailForGuestToUpdate);
				if (reservationToUpdate == -1) {
					System.out.println("No bookings to update");
				} else {
					Guest updatedGuest = getGuestInformation();
					facility.updateGuest(emailForGuestToUpdate, reservationToUpdate, updatedGuest);
				}
				break;
			case 5:
				String emailaddressToUpdateRequests = getValidEmailAddress();
				reservationToUpdate = promptForBookingID(facility, emailaddressToUpdateRequests);
				if (reservationToUpdate == -1) {
					System.out.println("No bookings to update");
				} else {
					int roomNumber = facility.getBookingIdsForGuest(emailaddressToUpdateRequests).get(reservationToUpdate);
					HashSet<SpecialRequest> specialRequests = getSpecialRequests(facility.getRoom(roomNumber).getSpecialRequests(reservationToUpdate));
					
					facility.updateSpecialRequests(emailaddressToUpdateRequests, reservationToUpdate, specialRequests);
				}
				break;
			case 6:
				String emailForRemoveReservation = getValidEmailAddress();
				System.out.println(facility.removeReservation(emailForRemoveReservation,
						promptForBookingID(facility, emailForRemoveReservation)));
				break;
			default:
				System.out.println("Invalid option entered: " + option);
				break;
			}

			// pause the program so that the user can read what we just printed
			// to the terminal window
			System.out.println("\nPress any key to continue...");
			input.nextLine();
			input.nextLine(); 
			option = mainMenu();
		}

		// if 0 is chosen - exit the program
		System.out.println("Exiting... bye");
		System.exit(0);
	}

	/**
	 * 
	 * @param facility
	 * @param emailAddress
	 * @return bookingID to be used in order to deal with associated Reservations
	 */
	public int promptForBookingID(Facility facility, String emailAddress) {
		int bookingID = -1;

		LinkedHashMap<Integer, Integer> bookingIDs = facility.getBookingIdsForGuest(emailAddress);
		if (bookingIDs.isEmpty()) {
			return -1;
		}
		try {
			do {
				System.out.println("Please choose from the list of available booking ids: ");
				Iterator<Entry<Integer, Integer>> it = bookingIDs.entrySet().iterator();
				while (it.hasNext()) {
					Entry<Integer, Integer> pair = it.next();
					System.out.println(">" + pair.getKey());
				}
				bookingID = input.nextInt();
			} while (!bookingIDs.containsKey(bookingID));

		} catch (InputMismatchException e) {
			System.out.println("Number expected. ");
		}
		return bookingID;
	}

	/**
	 * This method builds Reservation Query
	 * @param facility
	 */
	public void buildReservationQuery(Facility facility) {
		ReservationQuery query = new ReservationQuery();
		checkAvailability(query);
		int roomNumber = query.getRoomNumber();
		if (roomNumber != -1) {
			Payment payment = getPayment(facility.getRoom(roomNumber).getCost());
			facility.registerReservation(roomNumber, getGuestInformation(), query.getStartDate(), query.getEndDate(),
					payment, getSpecialRequests(null));
		}
	}

	/**
	 * 
	 * @param requests
	 * @return updated SpecialRequests
	 */
	private HashSet<SpecialRequest> getSpecialRequests(HashSet<SpecialRequest> requests) {
		HashSet<SpecialRequest> specialRequests = new HashSet<SpecialRequest>();
		if (requests != null) {
			specialRequests = requests;
		} else {
			System.out.println("Do you require any special requests? Type yes to continue");
		}
		String response = input.nextLine();
		if (response.equalsIgnoreCase("yes") || requests != null) {
			boolean finished = false;
			do {
				System.out.println("Type the special request, which is displayed below or type end to exit");
				for (SpecialRequest sr : SpecialRequest.values()) {
					if (!specialRequests.contains(sr)) {
						System.out.println(sr.name());
					}
				}
				System.out.print(">");
				response = input.nextLine();
				if (response.equalsIgnoreCase("end")) {
					finished = true;
				} else {
					if (response.equalsIgnoreCase("COT")) {
						specialRequests.add(SpecialRequest.COT);
					} else if (response.equalsIgnoreCase("BALCONY")) {
						specialRequests.add(SpecialRequest.BALCONY);
					} else if (response.equalsIgnoreCase("NONSMOKING")) {
						specialRequests.add(SpecialRequest.NONSMOKING);
					}
				}
			} while (!finished);
		}
		return specialRequests;
	}

	/**
	 * 
	 * @return valid email address
	 */
	private String getValidEmailAddress() {
		String emailAddress = "";
		boolean correct = false;
		do {
			System.out.print("Please enter your email address:");
			emailAddress = input.nextLine();
			if (InputValidator.isValidEmailAddress(emailAddress)) {
				correct = true;
			} else {
				System.out.println("Invalid format of email address. Please try again...");
			}
		} while (!correct);
		return emailAddress;
	}

	/**
	 * 
	 * @return Guest object
	 */
	private Guest getGuestInformation() {
		input.nextLine();

		System.out.print("Please enter your name:");
		String name = input.nextLine();
		String emailAddress = getValidEmailAddress();
		boolean correct = false;
		String phoneNumber;
		do {
			System.out.print("Please enter phone number (format: 051-123456):");
			phoneNumber = input.nextLine();
			if (InputValidator.isValidPhoneNumber(phoneNumber)) {
				correct = true;
			} else {
				System.out.println("Invalid format of phone number. Please try again...");
			}
		} while (!correct);

		return new Guest(name, emailAddress, phoneNumber);
	}

	/**
	 * This method initiates checking availability 
	 * @param query
	 */
	private void checkAvailability(ReservationQuery query) {

		System.out.print("Please enter month of startDate: ");
		int startMonth = getNumberInput();
		System.out.print("Please enter day of startDate: ");
		int startDay = getNumberInput();
		System.out.print("Please enter month of endDate: ");
		int endMonth = getNumberInput();
		System.out.print("Please enter day of endDate: ");
		int endDay = getNumberInput();

		facility.checkAvailability(query, startMonth, startDay, endMonth, endDay);
		
	}

	/**
	 * 
	 * @param amount
	 * @return Payment object
	 */
	private Payment getPayment(double amount) {
		Payment payment = new Payment();
		int paymentType;
		do {
			try {
				System.out.println("Please select type of payment: ");
				System.out.println("  1) Card payment");
				System.out.println("  2) Payment at arrival");
				paymentType = input.nextInt();
				if (paymentType == 2) {
					payment.setMethod("pay at arrival");
					payment.setStatus("not paid");
					payment.setAmount(amount);
					return payment;
				} else if (paymentType == 1) {
					boolean correct = false;
					long cardNumber = 0l;
					do {
						try {
							System.out.println("Please enter card number: ");
							cardNumber = input.nextLong();
							// simple validation for card number - must return 16 digit number
							if ((long) Math.pow(10, 15) <= cardNumber && (long) Math.pow(10, 16) > cardNumber) {
								correct = true;
							} else {
								System.out.println("Invalid number. Please try again...");
							}
						} catch (InputMismatchException e) {
							System.out.println("Incorrect value: ");
						}
					} while (!correct);
					correct = false;
					int expiryMonth = 0;
					do {
						try {
							System.out.println("Please enter expiry month: ");
							expiryMonth = input.nextInt();
							// month validation
							if (expiryMonth > 0 && expiryMonth < 13) {
								correct = true;
							} else {
								System.out.println("Please enter valid month. ");
							}
						} catch (InputMismatchException e) {
							System.out.println("Incorrect value: ");
						}
					} while (!correct);
					correct = false;
					int expiryYear = 0;
					do {
						try {
							System.out.println("Please enter expiry year: ");
							expiryYear = input.nextInt();
							// year validation
							if (expiryYear > 2016 && expiryYear < 2026) {
								correct = true;
							} else {
								System.out.println("Please enter valid year. ");
							}
						} catch (InputMismatchException e) {
							System.out.println("Incorrect value: ");
						}
					} while (!correct);
					payment.setMethod("pay by card");
					payment.setStatus("paid");
					payment.setAmount(amount);
					return payment;
				} else {
					System.out.println("Incorrect type of payment. Please try again...");
				}
			} catch (InputMismatchException e) {
				System.out.println("Incorrect value");
			}
		} while (true);
	}

	/**
	 * 
	 * @return valid number (ie int)
	 */
	private int getNumberInput() {
		do {
			try {
				return input.nextInt();
			} catch (InputMismatchException e) {
				input.nextLine();
				System.out.println("Number needed... Please try again");
			}
		} while (true);
	}
}
