package System;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.Scanner;

public class UI {

	private Scanner input = new Scanner(System.in);
	private Facility facility;
	private ArrayList<Room> currentListOfAvailRooms;
	private boolean availabilityChecked = false;
	public static void main(String[] args) {
		UI ui = new UI();
		ui.runMenu();
	}
    
	private int mainMenu() {
		int option = 0;
		boolean correct = false;
		do {
			try {
				System.out.println("  Medical Council Menu");
				System.out.println("  ---------");
				System.out.println("  1) Check room availability");
				System.out.println("  2) Make Reservation");
				System.out.println("  3) Add an Intern");
				System.out.println("  4) Delete a Doctor");
				System.out.println("  5) Edit Doctor's Details");
				System.out
						.println("  6) Display Total Amount of Registration Owed To The Council");
				System.out
						.println("  7) Display Total Amount of Registration Owed By Interns");
				System.out
						.println("  8) Display Total Amount of Registration Owed By General Doctors");
				System.out
						.println("  9) Display Total Amount of Registration Owed By Specialists");
				System.out
						.println("  10) Display Average Amount of Registration Owed To The Council");
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
	 * This is the method that controls the loop.
	 */
	private void runMenu() {
		int option = mainMenu();

		while (option != 0) {

			switch (option) {
			case 1:
				System.out.print("Please enter month of startDate: ");
				int startMonth = 0;
				boolean correct = false;
				do {
					try {
						startMonth = input.nextInt();
						correct = true;
					} catch (InputMismatchException e) {
						input.nextLine();
						System.out.println("Number needed... Please try again");
					}
				} while (!correct);
				System.out.print("Please enter day of startDate: ");
				int startDay = 0;
				correct = false;
				do {
					try {
						startDay = input.nextInt();
						correct = true;
					} catch (InputMismatchException e) {
						input.nextLine();
						System.out.println("Number needed... Please try again");
					}
				} while (!correct);
				System.out.print("Please enter month of startDate: ");
				int endMonth = 0;
				correct = false;
				do {
					try {
						endMonth = input.nextInt();
						correct = true;
					} catch (InputMismatchException e) {
						input.nextLine();
						System.out.println("Number needed... Please try again");
					}
				} while (!correct);
				System.out.print("Please enter day of startDate: ");
				int endDay = 0;
				correct = false;
				do {
					try {
						endDay = input.nextInt();
						correct = true;
					} catch (InputMismatchException e) {
						input.nextLine();
						System.out.println("Number needed... Please try again");
					}
				} while (!correct);
				if((endDay >= startDay && endMonth == startMonth) || endMonth > startMonth) {
					try {
						currentListOfAvailRooms = facility.checkAvailability(new Date(startDay, startMonth, 2017), new Date(endDay, endMonth, 2017));
						if(currentListOfAvailRooms.size() > 0) {
							System.out.println(currentListOfAvailRooms.size() + " rooms available");
						}
						setAvailabilityChecked(true);
					} catch (InputMismatchException e){
						System.out.println("Incorrect date(s)");
					}
				} else {
					System.out.println("Incorrect dates were provided");
				}
				
				break;
			case 2:
				System.out.println("2");
				break;
			case 3:
				System.out.println("3");
				break;
			case 4:
				System.out.println("4");
				break;
			case 5:
				System.out.println("5");
				break;
			case 6:
				System.out.println("6");
				break;
			case 7:
				System.out.println("7");
				break;
			case 8:
				System.out.println("8");
				break;
			case 9:
				System.out.println("9");
				break;
			case 10:
				System.out.println("10");
				break;
			
			default:
				System.out.println("Invalid option entered: " + option);
				break;
			}

			// pause the program so that the user can read what we just printed
			// to the terminal window
			System.out.println("\nPress any key to continue...");
			input.nextLine();
			input.nextLine(); // this second read is required - bug in
			// Scanner
			// class; a String read is ignored straight
			// after reading an int.

			// display the main menu again
			option = mainMenu();
		}

		// the user chose option 0, so exit the program
		System.out.println("Exiting... bye");
		System.exit(0);
	}

	/**
	 * @return the availabilityChecked
	 */
	public boolean isAvailabilityChecked() {
		return availabilityChecked;
	}

	/**
	 * @param availabilityChecked the availabilityChecked to set
	 */
	public void setAvailabilityChecked(boolean availabilityChecked) {
		this.availabilityChecked = availabilityChecked;
	}
}
