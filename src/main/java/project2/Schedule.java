package project2;

import util.List;
import util.Sort;

/**
 * Represents the schedule (list of sections).
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class Schedule extends List<Section> {

    private static final int NOT_FOUND = -1;

    private int indexOfSection(Section section) {
        return indexOf(section); // uses List<E>.equals-based search
    }

    public Section getSection(Section key) {
        int idx = indexOfSection(key);
        if (idx == NOT_FOUND) return null;
        return super.get(idx);
    }

    // ---------- checks for OPEN ----------
    public boolean isInstructorAvailable(Time time, Instructor instructor) {
        for (Section s : this) {
            if (s.getTime() == time && s.getInstructor() == instructor) {
                return false;
            }
        }
        return true;
    }

    public boolean isClassroomAvailable(Time time, Classroom classroom) {
        for (Section s : this) {
            if (s.getTime() == time && s.getClassroom() == classroom) {
                return false;
            }
        }
        return true;
    }

    // ---------- checks for ENROLL ----------
    public boolean alreadyEnrolledInCourse(Student student, Course course) {
        for (Section s : this) {
            if (s.getCourse() == course && s.contains(student)) {
                return true;
            }
        }
        return false;
    }

    public boolean hasTimeConflict(Student student, Time time) {
        for (Section s : this) {
            if (s.getTime() == time && s.contains(student)) {
                return true;
            }
        }
        return false;
    }

    public int creditsEnrolled(Student student) {
        int total = 0;
        for (Section s : this) {
            if (s.contains(student)) {
                total += s.getCourse().getCredits();
            }
        }
        return total;
    }

    // ---------- actions ----------
    public void enroll(Section key, Student student) {
        Section s = getSection(key);
        if (s == null || student == null) return;
        s.enroll(student);
    }

    public void drop(Section key, Student student) {
        Section s = getSection(key);
        if (s == null || student == null) return;
        s.drop(student);
    }

    // ---------- prints ----------
    public void printByCourse() {
        System.out.println("* List of sections ordered by course name, section time *");

        List<Section> copy = new List<>();
        for (Section s : this) {
            copy.add(s);
        }

        Sort.sortSectionsByCourse(copy);

        for (Section s : copy) {
            s.print();
        }
        System.out.println("* end of list *");
    }

    public void printByClassroom() {
        System.out.println("* List of sections ordered by campus, building *");

        List<Section> copy = new List<>();
        for (Section s : this) {
            copy.add(s);
        }

        Sort.sortSectionsByClassroom(copy);

        for (Section s : copy) {
            s.print();
        }
        System.out.println("* end of list **");
    }
}


