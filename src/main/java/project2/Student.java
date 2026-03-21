package project2;

/**
 * Represents a student in the registration system.
 * A student is uniquely identified by their Profile and has a major and credits completed.
 *
 * This is an abstract base class for Resident, NonResident,
 * TriState, and International students.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public abstract class Student implements Comparable<Student> {

    // ---------------- constants ----------------
    private static final int FRESHMAN_MAX_CREDITS = 29;
    private static final int SOPHOMORE_MAX_CREDITS = 59;
    private static final int JUNIOR_MAX_CREDITS = 89;

    // ---------------- instance variables ----------------
    private Profile profile;
    private Major major;
    private int creditsCompleted;

    // ---------------- constructors ----------------

    public Student() {
        // intentionally empty
    }

    public Student(Profile profile, Major major, int creditsCompleted) {
        this.profile = profile;
        this.major = major;
        this.creditsCompleted = creditsCompleted;
    }

    // ---------------- required abstract method ----------------

    /**
     * Calculates the tuition based on credits enrolled.
     * Each subclass must implement its own tuition calculation.
     *
     * @param creditsEnrolled number of credits enrolled
     * @return tuition amount
     */
    public abstract double tuition(int creditsEnrolled);

    // ---------------- public methods ----------------

    public Profile getProfile() {
        return profile;
    }

    public Major getMajor() {
        return major;
    }

    public int getCreditsCompleted() {
        return creditsCompleted;
    }

    public Standing getStandingEnum() {
        if (creditsCompleted <= FRESHMAN_MAX_CREDITS) {
            return Standing.FRESHMAN;
        }
        if (creditsCompleted <= SOPHOMORE_MAX_CREDITS) {
            return Standing.SOPHOMORE;
        }
        if (creditsCompleted <= JUNIOR_MAX_CREDITS) {
            return Standing.JUNIOR;
        }
        return Standing.SENIOR;
    }

    @Override
    public int compareTo(Student other) {
        return this.profile.compareTo(other.profile);
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Student)) {
            return false;
        }
        Student other = (Student) obj;
        return profile.equals(other.profile);
    }

    private String standingPretty(Standing standing) {
        switch (standing) {
            case FRESHMAN: return "Freshman";
            case SOPHOMORE: return "Sophomore";
            case JUNIOR: return "Junior";
            default: return "Senior";
        }
    }

    @Override
    public String toString() {
        return "[" + profile + "] "
                + "[" + major + "," + major.getSchool() + "] "
                + "credits earned: " + creditsCompleted + " "
                + "[" + standingPretty(getStandingEnum()) + "]";
    }
}


