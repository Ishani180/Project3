package project2;

/**
 * Represents a section of a course.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class Section {
    private Course course;
    private Instructor instructor;
    private Classroom classroom;
    private Time time;

    private StudentList roster;
    private static final int CAPACITY = 4;

    public Section(Course course, Time time, Instructor instructor, Classroom classroom) {
        this.course = course;
        this.time = time;
        this.instructor = instructor;
        this.classroom = classroom;
        this.roster = new StudentList();
    }

    public Course getCourse() { return course; }
    public Time getTime() { return time; }
    public Instructor getInstructor() { return instructor; }
    public Classroom getClassroom() { return classroom; }

    public int getNumStudents() {
        return roster.size();
    }

    public boolean contains(Student student) {
        return roster.contains(student);
    }

    public boolean isFull() {
        return roster.size() >= CAPACITY;
    }

    public void enroll(Student student) {
        if (student == null) return;
        if (isFull()) return;
        roster.add(student);
    }

    public void drop(Student student) {
        if (student == null) return;
        roster.remove(student);
    }

    public int compareByCourse(Section other) {
        int courseCompare = this.course.getNumber().compareTo(other.course.getNumber());
        if (courseCompare != 0) return courseCompare;
        return periodOf(this.time) - periodOf(other.time);
    }

    public int compareByClassroom(Section other) {
        int campusCompare = this.classroom.getCampus().compareTo(other.classroom.getCampus());
        if (campusCompare != 0) return campusCompare;

        int buildingCompare = this.classroom.getBuilding().compareTo(other.classroom.getBuilding());
        if (buildingCompare != 0) return buildingCompare;

        return this.classroom.toString().compareTo(other.classroom.toString());
    }

    private int periodOf(Time t) {
        return Integer.parseInt(t.name().substring(1));
    }

    @Override
    public boolean equals(Object obj) {
        if (!(obj instanceof Section)) return false;
        Section other = (Section) obj;
        return this.course == other.course && this.time == other.time;
    }

    @Override
    public String toString() {
        return "[" + course.getNumber() + " " + time + "] "
                + "[" + instructor + "] "
                + "[" + classroom + "]";
    }

    public void print() {
        System.out.println(this);

        if (roster.isEmpty()) {
            System.out.println("\t**No students enrolled**");
            return;
        }

        System.out.println("\t**Roster**");
        for (Student s : roster) {
            System.out.println("\t[" + s.getProfile() + "]");
        }
    }
}

