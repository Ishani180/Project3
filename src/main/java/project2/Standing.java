package project2;

public enum Standing {
    FRESHMAN("Freshman"),
    SOPHOMORE("Sophomore"),
    JUNIOR("Junior"),
    SENIOR("Senior");

    private final String label;

    Standing(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return label;
    }
}
