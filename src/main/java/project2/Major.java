package project2;

public enum Major {

    CS("School of Arts & Sciences"),
    MATH("School of Arts & Sciences"),
    ITI("School of Communication and Information"),
    BAIT("Rutgers Business School"),
    ECE("School of Engineering");

    private final String school;

    Major(String school) {
        this.school = school;
    }

    public String getSchool() {
        return school;
    }
}
