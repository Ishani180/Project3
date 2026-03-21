package org.example.project_3;
import javafx.scene.control.*;
import project2.*;
import javafx.fxml.FXML;
import util.Date;

/**
 * The Controller Class is used to control the GUI
 * @author Ishani, Divena
 */
public class Controller {
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
    }
    private void printLine(String text) {
        outputArea.appendText(text + "\n");
    }
    private class ParsedStudent{
        private Profile profile;
        private Major major;
        private int creditsCompleted;
        public ParsedStudent(Profile profile, Major major, int creditsCompleted){
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
            if (isYoungerThan16(dob)){
                printLine("INVALID: " + dobText + " younger than 16 years old.");
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
            }catch (NumberFormatException e){
                printLine("INVALID: " + creditsText + " is not an integer!");
                return null;
            }
            Profile profile = new Profile(firstName, lastName, dob);
            return new ParsedStudent(profile, major, creditsCompleted);
        }
        @FXML
        private void handleAddStudent(){
            ParsedStudent baseStudent = parseStudentFromFields();
            if (baseStudent == null){
                return;
            }
            Student key = makeStudentKey(baseStudent.getProfile());
            if (studentList.contains(key)){
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
        private void handleClearFields(){
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
        private Major parseMajor(String majorText){
            try{
                return Major.valueOf(majorText.toUpperCase());
            } catch (Exception e){
                return null;
            }
        }
        private boolean isPastDate(Date dob){
            Date today = new Date();
            return dob.compareTo(today) < 0;
        }
        private boolean isYoungerThan16(Date dob){
            java.util.Calendar today = java.util.Calendar.getInstance();
            java.util.Calendar birth = java.util.Calendar.getInstance();
            birth.set(dob.getYear(), dob.getMonth() - 1, dob.getDay());
            int age = today.get(java.util.Calendar.YEAR) - birth.get(java.util.Calendar.YEAR);
            if (today.get(java.util.Calendar.DAY_OF_YEAR) < birth.get(java.util.Calendar.DAY_OF_YEAR)){
                age--;
            }
            return age < 16;
        }
        private Student makeStudentKey(Profile profile){
            return new Resident(profile, Major.CS, 0, 0);
        }

    }

}

