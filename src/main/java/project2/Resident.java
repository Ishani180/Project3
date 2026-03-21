package project2;

/**
 * Represents a resident student for tuition purposes.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class Resident extends Student {

    private static final int FULL_TIME_MIN = 12;
    private static final int FULL_TIME_MAX_NO_EXTRA = 16;

    private static final double FULL_TIME_TUITION = 14933;
    private static final double UNIVERSITY_FEE = 3891;
    private static final double PART_TIME_CREDIT_RATE = 482;

    private int scholarship;

    public Resident() {
        super();
        this.scholarship = 0;
    }

    public Resident(Profile profile, Major major, int creditsCompleted, int scholarship) {
        super(profile, major, creditsCompleted);
        this.scholarship = scholarship;
    }

    public int getScholarship() {
        return scholarship;
    }

    public void setScholarship(int scholarship) {
        this.scholarship = scholarship;
    }

    @Override
    public double tuition(int creditsEnrolled) {

        double tuition;

        if (creditsEnrolled < FULL_TIME_MIN) {
            // Part-time
            tuition = creditsEnrolled * PART_TIME_CREDIT_RATE
                    + (UNIVERSITY_FEE * 0.5);
        } else {
            // Full-time
            tuition = FULL_TIME_TUITION + UNIVERSITY_FEE;

            // Extra credits over 16
            if (creditsEnrolled > FULL_TIME_MAX_NO_EXTRA) {
                int extraCredits = creditsEnrolled - FULL_TIME_MAX_NO_EXTRA;
                tuition += extraCredits * PART_TIME_CREDIT_RATE;
            }

            // Apply scholarship ONLY for full-time
            tuition -= scholarship;
        }

        return tuition;
    }
    @Override
    public String toString() {
        String result = super.toString() + "[resident]";
        if (scholarship > 0) {
            result += " [scholarship: $" + String.format("%,d", scholarship) + "]";
        }
        return result;
    }
}
