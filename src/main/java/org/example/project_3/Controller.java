package org.example.project_3;
import javafx.scene.control.*;
import project2.StudentList;
import project2.Schedule;
import javafx.fxml.FXML;

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
    }

}

