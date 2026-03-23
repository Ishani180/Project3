package org.example.project_3;
import javafx.scene.control.*;
import project2.*;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import util.Date;
import javafx.event.ActionEvent;

import java.time.LocalDate;
import java.util.Scanner;
import java.util.StringTokenizer;

/**
 * The Controller Class is used to control the GUI
 * @author Ishani, Divena
 */
public class Controller {
    private static final int MINIMUM_AGE = 16;
    private static final int CREDIT_LIMIT = 20;
    private static final int FIRST_PERIOD = 1;
    private static final int LAST_PERIOD = 6;
    private StudentList studentList = new StudentList();
    private Schedule schedule = new Schedule();
    @FXML
    private TabPane mainTabPane;
    @FXML
    private TextField firstNameField;
    @FXML
    private TextField lastNameField;
    @FXML
    private TextField dobField;
    @FXML
    private ComboBox<String> majorBox;
    @FXML
    private TextField creditsField;
    @FXML
    private RadioButton residentRadio;
    @FXML
    private RadioButton nonResidentRadio;
    @FXML
    private RadioButton triStateRadio;
    @FXML
    private RadioButton internationalRadio;
    @FXML
    private ToggleGroup studentTypeGroup;
    @FXML
    private ComboBox<String> stateBox;
    @FXML
    private CheckBox studyAbroadCheckBox;
    @FXML
    private TextArea outputArea;
    @FXML
    private ComboBox<String> courseBox;
    @FXML
    private ComboBox<String> periodBox;
    @FXML
    private ComboBox<String> instructorBox;
    @FXML
    private ComboBox<String> classroomBox;
    @FXML
    private TextField timeField;
    @FXML
    private TextField dropFirstNameField;
    @FXML
    private TextField dropLastNameField;
    @FXML
    private TextField dropDobField;
    @FXML
    private ComboBox<String> dropCourseBox;
    @FXML
    private ComboBox<String> dropTimeBox;
    @FXML
    private TextField enrollFirstNameField;
    @FXML
    private TextField enrollLastNameField;
    @FXML
    private TextField enrollDobField;
    @FXML
    private ComboBox<String> enrollCourseBox;
    @FXML
    private ComboBox<String> enrollPeriodBox;
    @FXML
    private ComboBox<String> enrollStudentBox;
    @FXML
    private ComboBox<String> dropStudentBox;
    @FXML
    private TextField majorField;
    @FXML
    private TextField scholarshipField;
    @FXML
    private TextField scholarshipFirstNameField;
    @FXML
    private TextField scholarshipLastNameField;
    @FXML
    private TextField scholarshipDobField;
    @FXML
    private void updateExtraFields(){
        boolean isTriState = triStateRadio.isSelected();
        boolean isInternational = internationalRadio.isSelected();
        stateBox.setDisable(!isTriState);
        studyAbroadCheckBox.setDisable(!isInternational);
        if (!isTriState){
            stateBox.setValue(null);
        }
        if (!isInternational){
            studyAbroadCheckBox.setSelected(false);
        }
    }
    public void initialize(){
        studentTypeGroup = new ToggleGroup();
        majorBox.getItems().addAll("CS","MATH","ITI","BAIT","ECE");
        stateBox.getItems().addAll("NY","CT");
        residentRadio.setToggleGroup(studentTypeGroup);
        nonResidentRadio.setToggleGroup(studentTypeGroup);
        triStateRadio.setToggleGroup(studentTypeGroup);
        internationalRadio.setToggleGroup(studentTypeGroup);
        residentRadio.setSelected(true);
        updateExtraFields();
        courseBox.getItems().addAll("CS100","CS200","CS300","CS400","CS442","PHY100", "PHY200","ECE300","ECE400","CCD", "HST");
        courseBox.setPromptText("select a course");
        classroomBox.getItems().addAll("HIL114","ARC103","BEAUD","TIL232","AB2225","MU302");
        courseBox.setPromptText("select a classroom");
        instructorBox.getItems().addAll("PATEL", "LIM","ZIMNES", "HARPER","KAUR","TAYLOR","RAMESH","CERAVOLO","BROWN");
        instructorBox.setPromptText("select an instructor");
        periodBox.getItems().addAll("1 - 8:30", "2 - 10:20","3 - 12:10","4 - 14:00", "5 - 15:50", "6 - 17:40");
        periodBox.setPromptText("select a time");
    }
    private void printLine(String text) {
        outputArea.appendText(text + "\n");
    }
    private class ParsedStudent {
        private Profile profile;
        private Major major;
        private int creditsCompleted;
        public ParsedStudent(Profile profile, Major major, int creditsCompleted) {
            this.profile = profile;
            this.major = major;
            this.creditsCompleted = creditsCompleted;
        }
        public Profile getProfile() {
            return profile;
        }
        public Major getMajor() {
            return major;
        }
        public int getCreditsCompleted() {
            return creditsCompleted;
        }
    }
    private ParsedStudent parseStudentFromFields(){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dobText = dobField.getText().trim();
        String majorText = majorBox.getValue();
        String creditsText = creditsField.getText().trim();
        if (firstName.isEmpty() || lastName.isEmpty() || dobText.isEmpty()
                || majorText == null || creditsText.isEmpty()){
            printLine("Missing data tokens.");
            return null;
        }
        Date dob = new Date(dobText);
        if (!dob.isValid()){
            printLine("INVALID: " + dobText + " is not a valid calendar date!");
            return null;
        }
        if (!isPastDate(dob)){
            printLine("INVALID: " + dobText + " cannot be today or a future day.");
            return null;

        }
        Major major = parseMajor(majorText);
        if (major == null){
            printLine("INVALID: " + majorText + " major does not exist.");
            return null;
        }
        int creditsCompleted;
        try{
            creditsCompleted = Integer.parseInt(creditsText);
        }catch(NumberFormatException e){
            printLine("INVALID: " + creditsText + " is not an integer!");
            return null;
        }
        if (creditsCompleted < 0) {
            printLine("INVALID: credit completed cannot be negative.");
            return null;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        return new ParsedStudent(profile, major, creditsCompleted);

    }
    @FXML
    private void handleAddStudent(){
        ParsedStudent baseStudent = parseStudentFromFields();
        if (baseStudent == null) {
            return;
        }
        Student key = makeStudentKey(baseStudent.getProfile());
        if (studentList.contains(key)) {
            printLine("[" + baseStudent.getProfile() + "] student is already in the list.");
            return;
        }
        Student newStudent;
        if (residentRadio.isSelected()){
            newStudent = new Resident(
                    baseStudent.getProfile(),
                    baseStudent.getMajor(),
                    baseStudent.getCreditsCompleted(),
                    0
            );
            printLine("[" + baseStudent.getProfile() + "][Resident] added to the list.");
        }else if(nonResidentRadio.isSelected()){
            newStudent = new NonResident(
                    baseStudent.getProfile(),
                    baseStudent.getMajor(),
                    baseStudent.getCreditsCompleted()
            );
            printLine("[" + baseStudent.getProfile() + "][Non-Resident] added to the list.");

        }else if(triStateRadio.isSelected()){
            String state = stateBox.getValue();
            if (state == null || (!state.equals("NY") && !state.equals("CT"))){
                printLine("Invalid state code.");
                return;
            }
            newStudent = new TriState(
                    baseStudent.getProfile(),
                    baseStudent.getMajor(),
                    baseStudent.getCreditsCompleted(),
                    state
            );
            printLine("[" + baseStudent.getProfile() + "][Tri-State: " + state + "] added to the list.");
        }else if(internationalRadio.isSelected()){
            boolean studyAbroad = studyAbroadCheckBox.isSelected();
            newStudent = new International(
                    baseStudent.getProfile(),
                    baseStudent.getMajor(),
                    baseStudent.getCreditsCompleted(),
                    studyAbroad
            );
            if (studyAbroad){
                printLine("[" + baseStudent.getProfile() + "][International study abroad] added to the list.");
            }else{
                printLine("[" + baseStudent.getProfile() + "][International] added to the list.");
            }
        }else{
            printLine("Please select a student type.");
            return;
        }
        studentList.add(newStudent);
    }
    @FXML
    private void handleClearFields() {
        firstNameField.clear();
        lastNameField.clear();
        dobField.clear();
        creditsField.clear();
        majorBox.setValue(null);
        stateBox.setValue(null);
        studyAbroadCheckBox.setSelected(false);
        residentRadio.setSelected(true);
        updateExtraFields();
    }
    private Major parseMajor(String majorText) {
        try {
            return Major.valueOf(majorText.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
    private boolean isPastDate(Date dob) {
        Date today = new Date();
        return dob.compareTo(today) < 0;
    }
    private boolean isYoungerThan16(Date dob) {
        java.util.Calendar today = java.util.Calendar.getInstance();
        java.util.Calendar birth = java.util.Calendar.getInstance();
        birth.set(dob.getYear(), dob.getMonth() - 1, dob.getDay());

        int age = today.get(java.util.Calendar.YEAR) - birth.get(java.util.Calendar.YEAR);

        if (today.get(java.util.Calendar.DAY_OF_YEAR) < birth.get(java.util.Calendar.DAY_OF_YEAR)) {
            age--;
        }
        return age < MINIMUM_AGE;
    }
    private Student makeStudentKey(Profile profile) {
        if (residentRadio.isSelected()) {
            return new Resident(profile, Major.CS, 0, 0);
        } else if (nonResidentRadio.isSelected()) {
            return new NonResident(profile, Major.CS, 0);
        } else if (triStateRadio.isSelected()) {
            String state = stateBox.getValue();
            return new TriState(profile, Major.CS, 0, state);
        } else if (internationalRadio.isSelected()) {
            boolean studyAbroad = studyAbroadCheckBox.isSelected();
            return new International(profile, Major.CS, 0, studyAbroad);
        }
        return null;
    }
    @FXML
    private void handleRemove(){
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dobText = dobField.getText().trim();
        if (firstName.isEmpty() || lastName.isEmpty() || dobText.isEmpty()){
            printLine("Missing required fields.");
            return;
        }
        Date dob = new Date(dobText);
        if (!dob.isValid()){
            printLine("INVALID: " + dobText + " is not a valid calendar date!");
            return;
        }
        Profile profile = new Profile(firstName, lastName, dob);
        Student key = makeStudentKey(profile);
        if (key == null){
            printLine("Select a student type.");
            return;
        }
        if(!studentList.contains(key)){
            printLine("[" + profile + "] is not in the student list.");
            return;
        }
        studentList.remove(key);
        printLine("[" + profile + "] removed from the list.");

    }
    @FXML
    private void handleOpenSection() {
        String courseText = courseBox.getValue();
        String periodText = periodBox.getValue();
        String classroomText = classroomBox.getValue();
        String instructorText = instructorBox.getValue();
        if (courseText == null || periodText == null || classroomText == null || instructorText == null) {
            printLine("Missing data in command line.");
            return;
        }
        Course course = parseCourseOrPrint(courseText);
        if (course == null) {
            return;
        }
        Time time = parseTimeOrPrint(periodText);
        if (time == null) {
            return;
        }
        Classroom classroom = parseClassroomOrPrint(classroomText);
        if (classroom == null) {
            return;
        }
        Instructor instructor = parseInstructorOrPrint(instructorText);
        if (instructor == null) {
            return;
        }
        Section section = new Section(course, time, instructor, classroom);
        if (schedule.contains(section)) {
            printLine(course.getNumber() + " " + time + " already exists.");
            return;
        }
        if (!schedule.isInstructorAvailable(time, instructor)) {
            printLine(instructor + " is not available at " + time + ".");
            return;
        }
        if (!schedule.isClassroomAvailable(time, classroom)) {
            printLine(classroom + " is not available at " + time + ".");
            return;
        }
        schedule.add(section);
        printLine(course.getNumber() + " " + time + " added.");
    }
    @FXML
    private void handleCloseSectionButton() {
        String courseText = courseBox.getValue();
        String periodText = periodBox.getValue();
        if (courseText == null || periodText == null) {
            printLine("Missing data in command line.");
            return;
        }
        Course course = parseCourseOrPrint(courseText);
        if (course == null) {
            return;
        }
        Time time = parseTimeOrPrint(periodText);
        if (time == null) {
            return;
        }
        Section lookupKey = makeSectionLookupKey(course, time);
        Section existing = schedule.getSection(lookupKey);
        if (existing == null) {
            printLine(course.getNumber() + " " + time + " does not exist.");
            return;
        }
        if (existing.getNumStudents() > 0) {
            printLine(course.getNumber() + " " + time + " cannot be removed ["
                    + existing.getNumStudents() + " student(s) enrolled]");
            return;
        }
        schedule.remove(lookupKey);
        printLine(course.getNumber() + " " + time + " removed.");
    }
    @FXML
    private void handleEnroll() {
        String first = enrollFirstNameField.getText().trim();
        String last = enrollLastNameField.getText().trim();
        String dobText = enrollDobField.getText().trim();
        if (first.isEmpty() || last.isEmpty() || dobText.isEmpty()) {
            printLine("Missing data in input fields.");
            return;
        }
        Date dob = new Date(dobText);
        if (!dob.isValid()) {
            printLine("Invalid date of birth.");
            return;
        }
        Profile profile = new Profile(first, last, dob);
        Student student = getStudentOrPrint(profile);
        if (student == null) return;
        String courseStr = enrollCourseBox.getValue();
        if (courseStr == null) {
            printLine("Course not selected.");
            return;
        }
        Course course = parseCourseOrPrint(courseStr);
        if (course == null) return;
        String periodStr = enrollPeriodBox.getValue();
        if (periodStr == null) {
            printLine("Period not selected.");
            return;
        }
        Time time = parseTimeOrPrint(periodStr);
        if (time == null) return;
        Section section = getSectionOrPrint(course, time);
        if (section == null) return;
        if (schedule.alreadyEnrolledInCourse(student, course)) {
            printLine("[" + profile + "] already enrolled in " + course.getNumber());
            return;
        }
        if (!meetsStandingPrereq(student, course, profile)) return;
        if (!meetsMajorPrereq(student, course, profile)) return;
        if (schedule.hasTimeConflict(student, time)) {
            printLine("Time conflict.");
            return;
        }
        schedule.enroll(makeSectionLookupKey(course, time), student);

        printLine("[" + profile + "] enrolled in " + course.getNumber() + " " + time);
    }
    @FXML
    private void handleDrop(ActionEvent event) {
        String firstName = dropFirstNameField.getText().trim();
        String lastName = dropLastNameField.getText().trim();
        String dobText = dropDobField.getText().trim();
        String courseText = dropCourseBox.getValue();
        String periodText = dropTimeBox.getValue();
        if (firstName.isEmpty() || lastName.isEmpty() || dobText.isEmpty()
                || courseText == null || periodText == null) {
            printLine("Missing data in command line.");
            return;
        }
        Date dob = new Date(dobText);
        Profile profile = new Profile(firstName, lastName, dob);
        Student student = getStudentOrPrint(profile);
        if (student == null) {
            return;
        }
        Course course = parseCourseOrPrint(courseText);
        if (course == null) {
            return;
        }
        Time time = parseTimeOrPrint(periodText);
        if (time == null) {
            return;
        }
        Section section = getSectionOrPrint(course, time);
        if (section == null) {
            return;
        }
        if (!section.contains(student)) {
            printLine("[" + profile + "] is not enrolled in this section.");
            return;
        }
        schedule.drop(makeSectionLookupKey(course, time), student);
        printLine("[" + profile + "] dropped from " + course.getNumber() + " " + time);
    }
    @FXML
    private void handleLoad() {
        java.io.File file = new java.io.File("students.txt");
        try (Scanner fileScanner = new Scanner(file)) {
            while (fileScanner.hasNextLine()) {
                String line = fileScanner.nextLine().trim();
                if (line.isEmpty()) continue;
                StringTokenizer st = new StringTokenizer(line);
                if (st.countTokens() < 6) {
                    printLine("Invalid data tokens.");
                    return;
                }
                String type = st.nextToken().toUpperCase();
                String first = st.nextToken();
                String last = st.nextToken();
                String dobToken = st.nextToken();
                String majorToken = st.nextToken();
                String creditsToken = st.nextToken();
                Date dob = new Date(dobToken);
                if (!dob.isValid()) {
                    printLine("Invalid data tokens.");
                    return;
                }
                Major major = parseMajor(majorToken);
                if (major == null) {
                    printLine("Invalid data tokens.");
                    return;
                }
                int credits;
                try {
                    credits = Integer.parseInt(creditsToken);
                } catch (Exception e) {
                    printLine("Invalid data tokens.");
                    return;
                }
                if (credits < 0) {
                    printLine("Invalid data tokens.");
                    return;
                }
                Profile profile = new Profile(first, last, dob);
                if (studentList.contains(makeStudentKey(profile))) {
                    printLine("[" + profile + "] student is already in the list.");
                    continue;
                }
                Student student;
                switch (type) {
                    case "R":
                        student = new Resident(profile, major, credits, 0);
                        break;

                    case "N":
                        student = new NonResident(profile, major, credits);
                        break;

                    case "T":
                        if (!st.hasMoreTokens()) {
                            printLine("Invalid data tokens.");
                            return;
                        }
                        String state = st.nextToken().toUpperCase();
                        if (!state.equals("NY") && !state.equals("CT")) {
                            printLine("Invalid data tokens.");
                            return;
                        }
                        student = new TriState(profile, major, credits, state);
                        break;

                    case "I":
                        if (!st.hasMoreTokens()) {
                            printLine("Invalid data tokens.");
                            return;
                        }
                        String abroadToken = st.nextToken();
                        if (!abroadToken.equalsIgnoreCase("true") && !abroadToken.equalsIgnoreCase("false")) {
                            printLine("Invalid data tokens.");
                            return;
                        }
                        boolean abroad = Boolean.parseBoolean(abroadToken);
                        student = new International(profile, major, credits, abroad);
                        break;

                    default:
                        printLine("Invalid data tokens.");
                        return;
                }

                studentList.add(student);
                String label;
                if (student instanceof TriState) {
                    label = "TriState: " + ((TriState) student).getState();
                } else if (student instanceof International) {
                    International intl = (International) student;
                    label = intl.isStudyAbroad() ? "International study abroad" : "International";
                } else if (student instanceof NonResident) {
                    label = "Non-Resident";
                } else if (student instanceof Resident) {
                    label = "Resident";
                } else {
                    label = "Student";
                }

                printLine("[" + profile + "][" + label + "] added to the list.");
            }
            populateStudentBoxes();
            printLine("Student list loaded from the text file.");

        } catch (java.io.FileNotFoundException e) {
            printLine("File not found.");
        }
    }
    @FXML
    private void handleScholarshipFX() {
        outputArea.clear();

        String first = scholarshipFirstNameField.getText().trim();
        String last = scholarshipLastNameField.getText().trim();
        String dobText = scholarshipDobField.getText().trim();
        if (first.isEmpty() || last.isEmpty() || dobText.isEmpty()) {
            outputArea.setText("ERROR: Missing profile data (first, last, dob).");
            return;
        }
        Date dob = new Date(dobText);
        if (!dob.isValid()) {
            outputArea.setText("ERROR: Invalid date of birth.");
            return;
        }
        Profile profile = new Profile(first, last, dob);
        Student student = getStudentOrPrint(profile);
        if (student == null) {
            outputArea.setText("ERROR: Student not found: " + profile);
            return;
        }
        String amountInput = scholarshipField.getText().trim();
        int amount;
        try {
            amount = Integer.parseInt(amountInput);
        } catch (NumberFormatException e) {
            outputArea.setText("ERROR: Scholarship amount must be an integer.");
            return;
        }

        if (amount <= 0 || amount > 10000) {
            outputArea.setText("ERROR: Scholarship must be 1 to 10,000.");
            return;
        }

        if (!(student instanceof Resident)) {
            outputArea.setText("ERROR: " + profile + " is a non-resident and not eligible.");
            return;
        }

        int enrolledCredits = schedule.creditsEnrolled(student);
        if (enrolledCredits < 12) {
            outputArea.setText("ERROR: " + profile + " enrolled less than 12 credits.");
            return;
        }

        Resident resident = (Resident) student;
        resident.setScholarship(amount);

        outputArea.setText("SUCCESS: Scholarship $" + String.format("%,d", amount) +
                " updated for " + profile);
    }
    @FXML
    private void handleTuitionReportFX() {
        outputArea.clear();
        if (schedule.isEmpty()) {
            outputArea.appendText("Schedule is empty!\n");
            return;
        }
        outputArea.appendText("* Tuition dues ordered by student. *\n");
        util.List<Student> students = new util.List<>();
        for (Student s : studentList) {
            students.add(s);
        }
        util.Sort.sort(students);
        for (Student student : students) {
            int credits = schedule.creditsEnrolled(student);
            outputArea.appendText("[" + student.getProfile() + "]" + studentTypeString(student) + "\n");
            if (credits == 0) {
                outputArea.appendText("\t\t**not enrolled.\n");
                continue;
            }
            util.List<Section> enrolled = new util.List<>();
            for (Section sec : schedule) {
                if (sec.contains(student)) {
                    enrolled.add(sec);
                }
            }
            util.Sort.sortSectionsByCourse(enrolled);
            for (Section sec : enrolled) {
                outputArea.appendText("\t\t"
                        + sec.getCourse().name()
                        + "[" + sec.getTime() + "] "
                        + "[credit: " + sec.getCourse().getCredits() + "]\n");
            }
            if (student instanceof International && credits < 12) {
                outputArea.appendText("\t\t**International student must enroll at least 12 credits.\n");
                continue;
            }
            double tuition = student.tuition(credits);
            outputArea.appendText("\t\t**Total credits enrolled: " + credits
                    + " [tuition due: $" + String.format("%,.2f", tuition) + "]\n");
        }
        outputArea.appendText("* end of list *\n");
    }
    @FXML
    private void handleGraduationReportFX() {
        outputArea.clear();
        if (schedule.isEmpty()) {
            outputArea.appendText("Schedule is empty!\n");
            return;
        }
        outputArea.appendText("* List of students eligible for graduation, ordered by major *\n");
        util.List<Student> copy = new util.List<>();
        for (Student s : studentList) {
            copy.add(s);
        }
        sortStudentsByMajorThenProfile(copy);
        for (Student student : copy) {
            int totalCredits = student.getCreditsCompleted() + schedule.creditsEnrolled(student);
            if (totalCredits >= 120) {
                outputArea.appendText("[" + student.getProfile() + "]["
                        + student.getMajor() + "," + student.getMajor().getSchool() + "]\n");
            }
        }
        outputArea.appendText("* end of list *\n");
    }
    //Helper Methods
    private Section makeSectionLookupKey(Course course, Time time) {
        return new Section(course, time, Instructor.PATEL, Classroom.HIL114);
    }
    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
    private Course parseCourse(String token) {
        try {
            return Course.valueOf(token.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
    private Instructor parseInstructorOrPrint(String token) {
        Instructor instructor = parseInstructor(token);
        if (instructor == null) {
            printLine("INVALID: faculty " + token + " does not exist.");
        }
        return instructor;
    }
    private Classroom parseClassroomOrPrint(String token) {
        Classroom classroom = parseClassroom(token);
        if (classroom == null) {
            printLine("INVALID: location " + token + " does not exist.");
        }
        return classroom;
    }
    private Integer parseInt(String token) {
        try {
            return Integer.parseInt(token);
        } catch (Exception e) {
            return null;
        }
    }
    private Course parseCourseOrPrint(String token) {
        Course course = parseCourse(token);
        if (course == null) {
            printLine("INVALID: course name " + token + " does not exist.");
        }
        return course;
    }
    private Instructor parseInstructor(String token) {
        try {
            return Instructor.valueOf(token.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
    private Classroom parseClassroom(String token) {
        try {
            return Classroom.valueOf(token.toUpperCase());
        } catch (Exception e) {
            return null;
        }
    }
    private Time parseTimeOrPrint(String periodToken) {
        Integer period = parseInt(periodToken);
        if (period == null || period < FIRST_PERIOD || period > LAST_PERIOD) {
            printLine("INVALID: period " + periodToken + " does not exist.");
            return null;
        }
        return Time.valueOf("P" + period);
    }
    private int periodOf(Time time) {
        return Integer.parseInt(time.name().substring(1));
    }
    private Profile readProfileFromFields() {
        String firstName = firstNameField.getText().trim();
        String lastName = lastNameField.getText().trim();
        String dobText = dobField.getText().trim();

        Date dob = new Date(dobText);
        if (!dob.isValid()) {
            printLine("INVALID: " + dobText + " is not a valid calendar date!");
            return null;
        }

        return new Profile(firstName, lastName, dob);
    }
    private String standingPretty(Standing standing) {
        switch (standing) {
            case FRESHMAN:
                return "Freshman";
            case SOPHOMORE:
                return "Sophomore";
            case JUNIOR:
                return "Junior";
            default:
                return "Senior";
        }
    }
    private Profile readProfile(StringTokenizer tokenizer) {
        String firstName = tokenizer.nextToken();
        String lastName = tokenizer.nextToken();
        String dobToken = tokenizer.nextToken();
        Date dob = new Date(dobToken);
        return new Profile(firstName, lastName, dob);
    }
    private Student getStudentOrPrint(Profile profile) {
        Student key = makeStudentKey(profile);
        if (!studentList.contains(key)) {
            printLine("INVALID: [" + profile + "] does not exist.");
            return null;
        }
        return studentList.get(key);
    }
    private Section getSectionOrPrint(Course course, Time time) {
        Section key = makeSectionLookupKey(course, time);
        Section section = schedule.getSection(key);
        if (section == null) {
            printLine("INVALID: " + course.getNumber() + " " + time + " does not exist.");
            return null;
        }
        return section;
    }
    private boolean meetsStandingPrereq(Student student, Course course, Profile profile) {
        if (student.getStandingEnum().ordinal() < course.getStanding().ordinal()) {
            printLine("Prereq: " + standingPretty(course.getStanding()) + " - ["
                    + profile + "] [" + standingPretty(student.getStandingEnum()) + "]");
            return false;
        }
        return true;
    }
    private boolean meetsMajorPrereq(Student student, Course course, Profile profile) {
        if (course.getMajorRestriction() != null && student.getMajor() != course.getMajorRestriction()) {
            System.out.println("Prereq: major only - [" + profile + "] [" + student.getMajor() + "]");
            return false;
        }
        return true;
    }
    private boolean exceedsCreditLimit(Student student, Course course) {
        int currentlyEnrolled = schedule.creditsEnrolled(student);
        int afterEnroll = currentlyEnrolled + course.getCredits();
        return afterEnroll > CREDIT_LIMIT;
    }
    private void populateStudentBoxes() {
        enrollStudentBox.getItems().clear();
        dropStudentBox.getItems().clear();

        for (int i = 0; i < studentList.size(); i++) {
            Student s = studentList.get(i);
            if (s != null) {
                String display = s.getProfile().toString();
                enrollStudentBox.getItems().add(display);
                dropStudentBox.getItems().add(display);
            }
        }
    }
    private String studentTypeString(Student student) {

        if (student instanceof Resident) {
            return "[Resident]";
        }
        if (student instanceof TriState) {
            TriState ts = (TriState) student;
            return "[Tristate: " + ts.getState() + "]";
        }
        if (student instanceof International) {
            International intl = (International) student;
            return intl.isStudyAbroad() ? "[International study abroad]" : "[International]";
        }
        if (student instanceof NonResident) {
            return "[Noresident]";
        }
        return "";
    }
    private void sortStudentsByMajorThenProfile(util.List<Student> list) {
        for (int i = 0; i < list.size() - 1; i++) {
            int minIndex = i;
            for (int j = i + 1; j < list.size(); j++) {

                Student a = list.get(j);
                Student b = list.get(minIndex);

                int majorCompare = a.getMajor().compareTo(b.getMajor());
                if (majorCompare < 0 ||
                        (majorCompare == 0 && a.compareTo(b) < 0)) {
                    minIndex = j;
                }
            }
            if (minIndex != i) {
                Student temp = list.get(i);
                list.set(i, list.get(minIndex));
                list.set(minIndex, temp);
            }
        }
    }
}

