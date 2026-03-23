package util;

import java.util.Calendar;
import java.util.StringTokenizer;

/**
 * Represents a calendar date in the format month/day/year.
 *
 * Provides validation, comparison, and formatting functionality.
 *
 * @Divena Deshmukh, Ishani Rajeshirke
 */
public class Date implements Comparable<Date> {

    /* Instance variables */
    private int year;
    private int month;
    private int day;

    /* Month constants (Calendar months are 0-based, so +1 makes them 1–12) */
    private static final int JAN = Calendar.JANUARY + 1;
    private static final int FEB = Calendar.FEBRUARY + 1;
    private static final int MAR = Calendar.MARCH + 1;
    private static final int APR = Calendar.APRIL + 1;
    private static final int MAY = Calendar.MAY + 1;
    private static final int JUN = Calendar.JUNE + 1;
    private static final int JUL = Calendar.JULY + 1;
    private static final int AUG = Calendar.AUGUST + 1;
    private static final int SEP = Calendar.SEPTEMBER + 1;
    private static final int OCT = Calendar.OCTOBER + 1;
    private static final int NOV = Calendar.NOVEMBER + 1;
    private static final int DEC = Calendar.DECEMBER + 1;

    /* Day constants */
    private static final int DAYS_31 = 31;
    private static final int DAYS_30 = 30;
    private static final int DAYS_28 = 28;
    private static final int DAYS_29 = 29;

    /* Leap year constants */
    private static final int QUADRENNIAL = 4;
    private static final int CENTENNIAL = 100;
    private static final int QUATERCENTENNIAL = 400;

    private static final int MIN_YEAR = 1900;

    /**
     * Default constructor.
     */
    public Date() {
        Calendar cal = Calendar.getInstance();
        this.year = cal.get(Calendar.YEAR);
        this.month = cal.get(Calendar.MONTH) + 1;
        this.day = cal.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * Constructs a Date using integer values.
     *
     * @param month month (1–12)
     * @param day   day of month
     * @param year  year
     */
    public Date(int month, int day, int year) {
        this.year = year;
        this.month = month;
        this.day = day;
    }

    /**
     * Constructs a Date from a string in the format "mm/dd/yyyy".
     *
     * @param date date string
     */
    public Date(String date) {
        StringTokenizer tokenizer = new StringTokenizer(date, "/");
        this.month = Integer.parseInt(tokenizer.nextToken());
        this.day = Integer.parseInt(tokenizer.nextToken());
        this.year = Integer.parseInt(tokenizer.nextToken());
    }

    /**
     * Copy constructor.
     *
     * @param date Date object to copy
     */
    public Date(Date date) {
        this.year = date.year;
        this.month = date.month;
        this.day = date.day;
    }

    /**
     * Determines whether a given year is a leap year.
     *
     * @param year year to check
     * @return true if leap year, false otherwise
     */
    private boolean isLeap(int year) {
        if (year % QUADRENNIAL != 0) {
            return false;
        }
        if (year % CENTENNIAL != 0) {
            return true;
        }
        return year % QUATERCENTENNIAL == 0;
    }

    /**
     * Determines whether this Date object represents a valid calendar date.
     *
     * @return true if valid, false otherwise
     */
    public boolean isValid() {

        if (year < MIN_YEAR) {
            return false;
        }

        if (month < JAN || month > DEC) {
            return false;
        }

        int maxDays;

        switch (month) {
            case JAN:
            case MAR:
            case MAY:
            case JUL:
            case AUG:
            case OCT:
            case DEC:
                maxDays = DAYS_31;
                break;

            case APR:
            case JUN:
            case SEP:
            case NOV:
                maxDays = DAYS_30;
                break;

            case FEB:
                maxDays = isLeap(year) ? DAYS_29 : DAYS_28;
                break;

            default:
                return false;
        }

        return day >= 1 && day <= maxDays;
    }

    /**
     * Compares this Date to another Date.
     *
     * @param other Date to compare with
     * @return negative if earlier, positive if later, zero if equal
     */
    @Override
    public int compareTo(Date other) {
        if (this.year != other.year) {
            return this.year - other.year;
        }

        if (this.month != other.month) {
            return this.month - other.month;
        }

        return this.day - other.day;
    }
    public int getYear() {
        return year;
    }

    public int getMonth() {
        return month;
    }

    public int getDay() {
        return day;
    }

    /**
     * Determines if two Date objects are equal.
     *
     * @param obj object to compare
     * @return true if same date, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Date)) {
            return false;
        }

        Date other = (Date) obj;
        return this.year == other.year
                && this.month == other.month
                && this.day == other.day;
    }

    /**
     * Returns the string representation of this Date.
     *
     * @return date formatted as mm/dd/yyyy
     */
    @Override
    public String toString() {
        return month + "/" + day + "/" + year;
    }

    /**
     * Testbed main method.
     * Includes at least 4 invalid and 2 valid test cases.
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        System.out.println("---- isValid() Test Cases ----");

        // Invalid dates
        System.out.println(new Date(13, 10, 2023).isValid()); // Invalid month
        System.out.println(new Date(4, 31, 2023).isValid());  // April has 30 days
        System.out.println(new Date(2, 29, 2023).isValid());  // Not leap year
        System.out.println(new Date(11, 21, 800).isValid());  // Year before 1900

        // Valid dates
        System.out.println(new Date(2, 29, 2024).isValid());  // Leap year
        System.out.println(new Date(12, 31, 2023).isValid()); // Valid date
    }
}


