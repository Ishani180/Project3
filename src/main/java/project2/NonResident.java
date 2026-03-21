package project2;

/**
 * Represents a non-resident student for tuition purposes.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class NonResident extends Student {

    private static final int FULL_TIME_MIN = 12;
    private static final int FULL_TIME_MAX_NO_EXTRA = 16;

    private static final double FULL_TIME_TUITION = 35758;
    private static final double UNIVERSITY_FEE = 3891;
    private static final double PART_TIME_CREDIT_RATE = 1162;

    public NonResident() {
        super();
    }

    public NonResident(Profile profile, Major major, int creditsCompleted) {
        super(profile, major, creditsCompleted);
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

            // Extra credit charge if > 16
            if (creditsEnrolled > FULL_TIME_MAX_NO_EXTRA) {
                int extraCredits = creditsEnrolled - FULL_TIME_MAX_NO_EXTRA;
                tuition += extraCredits * PART_TIME_CREDIT_RATE;
            }
        }

        return tuition;
    }

    @Override
    public String toString() {
        return super.toString() + "[non-resident]";
    }
}