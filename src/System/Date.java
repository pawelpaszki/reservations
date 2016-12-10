package system;

import java.util.Arrays;
import java.util.HashSet;
import java.util.InputMismatchException;

/**
 * 
 * @author Thai Kha Le, Pawel Paszki
 * @version 10/12/2016
 * 
 * This is a custom Date class with validations working for year 2017 only
 */


public class Date {

	private static final HashSet<Integer> monthWith31 = new HashSet<>(
			Arrays.asList(new Integer[] { 1, 3, 5, 7, 8, 10, 12 }));
	private static final HashSet<Integer> monthWith30 = new HashSet<>(Arrays.asList(new Integer[] { 4, 6, 9, 11 }));

	private int day;
	private int month;
	private int year;

	/**
	 *  Constructor for the Date objects with three following parameters:
	 * @param day 
	 * @param month
	 * @param year
	 */
	public Date(int day, int month, int year) {

		if (year == 2017 && month >= 1 && month <= 12 && day > 0 && day <= 31 && isValid(year, month, day)) {
			this.day = day;
			this.month = month;
			this.year = year;
		} else {
			System.out.println(day + "/" + month + "/" + year);
			throw new InputMismatchException("incorrect data");
		}
	}

	/**
	 * @return the month
	 */
	public int getMonth() {
		return month;
	}

	/**
	 * @param month
	 *            the month to set
	 */
	public void setMonth(int month) {
		this.month = month;
	}

	/**
	 * @return the year
	 */
	public int getYear() {
		return year;
	}

	/**
	 * @param year
	 *            the year to set
	 */
	public void setYear(int year) {
		this.year = year;
	}

	/**
	 * @return the day
	 */
	public int getDay() {
		return day;
	}

	/**
	 * @param day
	 *            the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}

	/**
	 * This method validates day, month and year arguments as the components
	 * for Date object - validation for leap years is not complete, but sufficient
	 * for this project's needs
	 * @param year
	 * @param month
	 * @param day
	 * @return
	 */
	private boolean isValid(int year, int month, int day) {
		switch (month) {
		case 1:
		case 3:
		case 5:
		case 7:
		case 8:
		case 10:
		case 12:
			return true;
		case 4:
		case 6:
		case 9:
		case 11:
			if (day <= 30) {
				return true;
			}
		case 2:
			if (year % 4 == 0) {
				if (day <= 29) {
					return true;
				}
			} else {
				if (day <= 28) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 
	 * @param currentDate - reference point for next date
	 * @param limit - last date in sequence
	 * @return next Date in sequence
	 */
	public static Date getNextDate(Date currentDate, Date limit) {
		if (currentDate.compareTo(limit) >= 0) {
			return null;
		}
		int month = currentDate.getMonth();
		int day = currentDate.getDay();
		Date nextDate = clone(currentDate);
		if ((monthWith31.contains(Integer.valueOf(month)) && day == 31)
				|| (monthWith30.contains(Integer.valueOf(month)) && day == 30) || (day == 28 && month == 2)) {
			nextDate.setMonth(++month);
			nextDate.setDay(1);
		} else {
			nextDate.setDay(++day);
		}
		return nextDate;
	}

	/**
	 * 
	 * @param that
	 * @return comparison result of "this" Date and Date passed as a parameter
	 */
	public int compareTo(Date that) {
		if (month < that.month) {
			return -1;
		} else if (month > that.month) {
			return 1;
		} else if (day < that.day) {
			return -1;
		} else if (day > that.day) {
			return 1;
		}
		return 0;
	}

	/**
	 * 
	 * @param d - Date to clone
	 * @return cloned Date
	 */
	public static Date clone(Date d) {
		return new Date(d.day, d.month, d.year);
	}

	/**
	 * String representation of Date instance
	 */
	public String toString() {
		return day + "/" + month + "/" + year;
	}
}
