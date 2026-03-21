package project2;

public enum Time {
    P1(1, "8:30"),
    P2(2, "10:20"),
    P3(3, "12:10"),
    P4(4, "14:00"),
    P5(5, "15:50"),
    P6(6, "17:40");

    private final int period;
    private final String time;

    Time(int period, String time) {
        this.period = period;
        this.time = time;
    }

    public int getPeriod() {
        return period;
    }

    @Override
    public String toString() {
        return time;
    }
}
