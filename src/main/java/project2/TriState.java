package project2;

/**
 * Represents a tristate student (NY or CT) for tuition purposes.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class TriState extends NonResident {

    private String state; // "NY" or "CT"

    public TriState() {
        super();
        this.state = "";
    }

    public TriState(Profile profile, Major major, int creditsCompleted, String state) {
        super(profile, major, creditsCompleted);
        this.state = state;
    }

    public String getState() {
        return state;
    }

    @Override
    public double tuition(int creditsEnrolled) {

        double tuition = super.tuition(creditsEnrolled);

        // TriState discount applies ONLY to full-time students (>= 12 credits)
        if (creditsEnrolled >= 12) {
            if ("NY".equals(state)) {
                tuition -= 4000;
            } else if ("CT".equals(state)) {
                tuition -= 5000;
            }
        }

        return tuition;
    }

    @Override
    public String toString() {
        return super.toString().replace("[non-resident]", "")
                + "[tri-state:" + state + "]";
    }
}
