package project2;

public enum Classroom {
    HIL114("HIL114", "Hill Center", "Busch"),
    ARC103("ARC103", "Allison Road Classroom", "Busch"),
    BEAUD("BEAUD", "Beck Hall", "Livingston"),
    TIL232("TIL232", "Tillett Hall", "Livingston"),
    AB2225("AB2225", "Academic Building", "College Avenue"),
    MU302("MU302", "Murray Hall", "College Avenue");

    private final String room;
    private final String building;
    private final String campus;

    Classroom(String room, String building, String campus) {
        this.room = room;
        this.building = building;
        this.campus = campus;
    }

    public String getCampus() { return campus; }
    public String getBuilding() { return building; }

    @Override
    public String toString() {
        return room + ", " + building + ", " + campus;
    }
}
