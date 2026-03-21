package project2;

import util.Date;

/**
 * Represents a student profile containing
 * first name, last name, and date of birth.
 *
 * Provides comparison and equality functionality.
 */
public class Profile implements Comparable<Profile> {

    /* Instance variables */
    private String fname;
    private String lname;
    private Date dob;

    /**
     * Default constructor.
     */
    public Profile() {
    }

    /**
     * Constructs a Profile with given values.
     *
     * @param fname first name
     * @param lname last name
     * @param dob   date of birth
     */
    public Profile(String fname, String lname, Date dob) {
        this.fname = fname;
        this.lname = lname;
        this.dob = dob;
    }

    /**
     * Copy constructor (performs deep copy of Date).
     *
     * @param profile Profile object to copy
     */
    public Profile(Profile profile) {
        this.fname = profile.fname;
        this.lname = profile.lname;
        this.dob = (profile.dob == null)
                ? null
                : new Date(profile.dob);
    }

    /**
     * Determines whether two Profile objects are equal.
     * Comparison is case-insensitive for names.
     *
     * @param obj object to compare
     * @return true if profiles match, false otherwise
     */
    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Profile)) {
            return false;
        }

        Profile other = (Profile) obj;

        if (this.fname == null || this.lname == null || this.dob == null) {
            return false;
        }

        if (other.fname == null || other.lname == null || other.dob == null) {
            return false;
        }

        return other.fname.equalsIgnoreCase(this.fname)
                && other.lname.equalsIgnoreCase(this.lname)
                && other.dob.equals(this.dob);
    }

    /**
     * Returns string representation of Profile.
     *
     * @return formatted profile string
     */
    @Override
    public String toString() {
        return fname + " " + lname + " " + dob;
    }

    /**
     * Compares two Profile objects.
     *
     * Order of comparison:
     * 1. Last name (case-insensitive)
     * 2. First name (case-insensitive)
     * 3. Date of birth
     *
     * @param other Profile to compare
     * @return negative if less than,
     *         positive if greater than,
     *         zero if equal
     */
    @Override
    public int compareTo(Profile other) {

        int lastCompare = this.lname.compareToIgnoreCase(other.lname);
        if (lastCompare != 0) {
            return Integer.signum(lastCompare);
        }

        int firstCompare = this.fname.compareToIgnoreCase(other.fname);
        if (firstCompare != 0) {
            return Integer.signum(firstCompare);
        }

        return Integer.signum(this.dob.compareTo(other.dob));
    }

    /**
     * Testbed main method for compareTo().
     *
     * Includes:
     * 3 cases returning negative
     * 3 cases returning positive
     * 1 case returning zero
     *
     * @param args command-line arguments
     */
    public static void main(String[] args) {

        Profile p1 = new Profile("John", "Doe", new Date(1, 1, 2000));
        Profile p2 = new Profile("Jane", "Doe", new Date(1, 1, 2000));
        Profile p3 = new Profile("John", "Smith", new Date(1, 1, 2000));
        Profile p4 = new Profile("John", "Doe", new Date(1, 1, 1999));
        Profile p5 = new Profile("John", "Doe", new Date(1, 1, 2001));
        Profile p6 = new Profile("Adam", "Doe", new Date(1, 1, 2000));
        Profile p7 = new Profile("John", "Doe", new Date(1, 1, 2000));

        System.out.println("---- compareTo() Test Cases ----");

        // Negative results
        System.out.println(p1.compareTo(p3)); // Doe < Smith
        System.out.println(p6.compareTo(p1)); // Adam < John
        System.out.println(p4.compareTo(p1)); // older DOB < newer DOB

        // Positive results
        System.out.println(p3.compareTo(p1)); // Smith > Doe
        System.out.println(p1.compareTo(p6)); // John > Adam
        System.out.println(p5.compareTo(p1)); // newer DOB > older DOB

        // Zero result
        System.out.println(p1.compareTo(p7)); // identical profile
    }
}

