package project2;

/**
 * Represents an international student for tuition purposes.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class International extends NonResident {

    private static final double HEALTH_INSURANCE = 2650;
    private static final double ADMIN_FEE = 500;
    private static final double UNIVERSITY_FEE = 3891;

    private boolean isStudyAbroad;

    public International() {
        super();
        this.isStudyAbroad = false;
    }

    public International(Profile profile, Major major, int creditsCompleted, boolean isStudyAbroad) {
        super(profile, major, creditsCompleted);
        this.isStudyAbroad = isStudyAbroad;
    }

    public boolean isStudyAbroad() {
        return isStudyAbroad;
    }

    public void setStudyAbroad(boolean studyAbroad) {
        isStudyAbroad = studyAbroad;
    }

    @Override
    public double tuition(int creditsEnrolled) {

        // Study abroad case
        if (isStudyAbroad) {
            return UNIVERSITY_FEE + HEALTH_INSURANCE + ADMIN_FEE;
        }

        // Regular international student
        double tuition = super.tuition(creditsEnrolled);

        tuition += HEALTH_INSURANCE;
        tuition += ADMIN_FEE;

        return tuition;
    }
    @Override
    public String toString() {
        String base = super.toString().replace("[non-resident]", "");
        if (isStudyAbroad()) {
            return base + "[international:study abroad]";
        }
        return base + "[international]";
    }
}
