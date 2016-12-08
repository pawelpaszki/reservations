package System;
import java.util.InputMismatchException;

public class Date {
	
	
	private int day;
	private int month;
	private int year;
	
	public Date(int day, int month, int year) {
		
		if (year >= 2016 && year <= 2017 && month >= 1 && month <= 12
				&& day > 0 && day <= 31 && isValid(year, month, day)) {
			this.day = day;
			this.month = month;
			this.year = year;
		} else {
			System.out.println("***");
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
	 * @param month the month to set
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
	 * @param year the year to set
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
	 * @param day the day to set
	 */
	public void setDay(int day) {
		this.day = day;
	}
	
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
}
