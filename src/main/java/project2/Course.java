package project2;

public enum Course {
    CS100("CS100", 4, Standing.FRESHMAN, null),
    CS200("CS200", 4, Standing.SOPHOMORE, null),
    CS300("CS300", 4, Standing.JUNIOR, Major.CS),
    CS400("CS400", 4, Standing.JUNIOR, Major.CS),
    CS442("CS442", 3, Standing.JUNIOR, null),
    PHY100("PHY100", 5, Standing.FRESHMAN, null),
    PHY200("PHY200", 5, Standing.SOPHOMORE, null),
    ECE300("ECE300", 4, Standing.JUNIOR, Major.ECE),
    ECE400("ECE400", 4, Standing.SENIOR, Major.ECE),
    CCD("CCD", 4, Standing.FRESHMAN, null),
    HST("HST", 3, Standing.FRESHMAN, null);

    private final String number;
    private final int credits;
    private final Standing standing;
    private final Major majorRestriction;

    Course(String number, int credits, Standing standing, Major majorRestriction) {
        this.number = number;
        this.credits = credits;
        this.standing = standing;
        this.majorRestriction = majorRestriction;
    }

    public String getNumber() { return number; }
    public int getCredits() { return credits; }
    public Standing getStanding() { return standing; }
    public Major getMajorRestriction() { return majorRestriction; }
}
