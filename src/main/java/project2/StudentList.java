package project2;

import util.List;
import util.Sort;

/**
 * Represents a list of students.
 *
 * @author Ishani Rajeshirke, Divena Deshmukh
 */
public class StudentList extends List<Student> {

    /**
     * Returns the actual stored Student object that matches the key,
     * or null if not found.
     *
     * @param key student key (usually same Profile)
     * @return stored student or null
     */
    public Student get(Student key) {
        int idx = indexOf(key);
        if (idx == -1) return null;
        return super.get(idx);
    }

    /**
     * Prints the student list ordered by profile.
     * Printing is allowed in StudentList. :contentReference[oaicite:3]{index=3}
     */
    public void print() {
        if (isEmpty()) {
            System.out.println("Student list is empty!");
            return;
        }

        System.out.println("* Student list ordered by last, first name, DOB *");

        // Make a copy so printing doesn't permanently reorder the list (safe)
        List<Student> copy = new List<>();
        for (Student s : this) {
            copy.add(s);
        }

        Sort.sort(copy);

        for (Student s : copy) {
            System.out.println(s);
        }

        System.out.println("* end of list **");
    }
}



