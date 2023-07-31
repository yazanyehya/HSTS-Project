package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.Controller.EditSelectedQuestionController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class EditSelectedQuestionBoundry {

    @FXML
    private TextField answerA;

    @FXML
    private TextField answerB;

    @FXML
    private TextField answerC;

    @FXML
    private TextField answerD;

    @FXML
    private Button backBtn;

    @FXML
    private ToggleGroup chooseAnswer;

    @FXML
    private RadioButton optionA;

    @FXML
    private RadioButton optionB;

    @FXML
    private RadioButton optionC;

    @FXML
    private RadioButton optionD;

    @FXML
    private TextField questionTextTXT;

    @FXML
    private Button saveBtn;

    @FXML
    private ListView<Course> courseList;

    @FXML
    private ComboBox<Subject> selectSubject;
    @FXML
    private Button showCourseBtn;

    private EditSelectedQuestionController editSelectedQuestionController;
    @FXML
    void answerAAction(ActionEvent event) {

    }

    @FXML
    void answerBAction(ActionEvent event) {

    }

    @FXML
    void answerCAction(ActionEvent event) {

    }

    @FXML
    void answerDAction(ActionEvent event) {

    }

    @FXML
    void backAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
                EventBus.getDefault().unregister(editSelectedQuestionController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void optionAAction(ActionEvent event) {

    }

    @FXML
    void optionBAction(ActionEvent event) {

    }

    @FXML
    void optionCAction(ActionEvent event) {

    }

    @FXML
    void optionDAction(ActionEvent event) {

    }

    @FXML
    void questionTextAction(ActionEvent event) {

    }

    @FXML
    void saveAction(ActionEvent event) throws IOException
    {
        RadioButton selectedRadioButton = (RadioButton) chooseAnswer.getSelectedToggle();
        Question question;
        String correctAnswer = "";
        List<Course> selectedCourses = courseList.getSelectionModel().getSelectedItems();
        List<String> courses = new ArrayList<>();

        for (Course c : selectedCourses)
        {
            courses.add(c.getName());
        }
        Subject selectedSubject = selectSubject.getValue();
        List<String> list = new ArrayList<>();
        if (selectedRadioButton == null || selectedCourses == null || Objects.equals(getAnswerA().getText(), "") || Objects.equals(getAnswerB().getText(), "") || Objects.equals(getAnswerC().getText(), "") || Objects.equals(getAnswerD().getText(), "") || selectedSubject == null )
        {
            list = null;
        }
        else
        {
            correctAnswer = selectedRadioButton.getText();
//            question = new Question(questionTextTXT.getText(), answerA.getText(), answerB.getText()
//                    , answerC.getText(), answerD.getText(), selectedSubject, (Teacher) SimpleClient.getClient().getUser(), correctAnswer);
            list.add(questionTextTXT.getText());
            list.add(answerA.getText());
            list.add(answerB.getText());
            list.add(answerC.getText());
            list.add(answerD.getText());
            list.add(selectedSubject.getName());
            list.add(((Teacher) SimpleClient.getClient().getUser()).getUsername());
            list.add(correctAnswer);
            for (String s : courses)
            {
                list.add(s);
            }

        }
        editSelectedQuestionController.saveQuestion(list);
    }

    @FXML
    void selectCourseAction(ActionEvent event) throws IOException
    {
        editSelectedQuestionController.getCourse(selectSubject.getSelectionModel().getSelectedItem());
    }
    @FXML
    void selectSubjectAction(ActionEvent event) {

    }
    public Button getBackBtn() {
        return backBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }


    public ToggleGroup getChooseAnswer() {
        return chooseAnswer;
    }

    public RadioButton getOptionA() {
        return optionA;
    }

    public RadioButton getOptionC() {
        return optionC;
    }

    public RadioButton getOptionB() {
        return optionB;
    }

    public RadioButton getOptionD() {
        return optionD;
    }

    public TextField getAnswerA() {
        return answerA;
    }

    public TextField getAnswerB() {
        return answerB;
    }

    public TextField getAnswerC() {
        return answerC;
    }

    public TextField getAnswerD() {
        return answerD;
    }

    public TextField getQuestionTextTXT() {
        return questionTextTXT;
    }

    public ListView<Course> getCourseList() {
        return courseList;
    }

    public Button getShowCourseBtn() {
        return showCourseBtn;
    }

    public void setShowCourseBtn(Button showCourseBtn) {
        this.showCourseBtn = showCourseBtn;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setCourseList(ListView<Course> courseList) {
        this.courseList = courseList;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setAnswerA(TextField answerA) {
        this.answerA = answerA;
    }

    public void setAnswerB(TextField answerB) {
        this.answerB = answerB;
    }

    public void setAnswerC(TextField answerC) {
        this.answerC = answerC;
    }

    public void setAnswerD(TextField answerD) {
        this.answerD = answerD;
    }

    public void setChooseAnswer(ToggleGroup chooseAnswer) {
        this.chooseAnswer = chooseAnswer;
    }

    public void setOptionB(RadioButton optionB) {
        this.optionB = optionB;
    }

    public void setOptionA(RadioButton optionA) {
        this.optionA = optionA;
    }

    public void setOptionC(RadioButton optionC) {
        this.optionC = optionC;
    }

    public void setOptionD(RadioButton optionD) {
        this.optionD = optionD;
    }

    public void setQuestionTextTXT(TextField questionTextTXT) {
        this.questionTextTXT = questionTextTXT;
    }

    public void setSaveBtn(Button saveBtn) {
        this.saveBtn = saveBtn;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public void setEditSelectedQuestionController(EditSelectedQuestionController editSelectedQuestionController) {
        this.editSelectedQuestionController = editSelectedQuestionController;
    }

    public EditSelectedQuestionController getEditSelectedQuestionController() {
        return editSelectedQuestionController;
    }
    private void populateCourseComboBox() {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();

        //ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        //selectCourse.setItems(courses);

        // Set the cell factory to display the course name
        selectSubject.setCellFactory(new Callback<ListView<Subject>, ListCell<Subject>>() {
            @Override
            public ListCell<Subject> call(ListView<Subject> param) {
                return new ListCell<Subject>() {
                    @Override
                    protected void updateItem(Subject item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        selectSubject.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Subject fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });


    }
    public void initialize() throws IOException {
        editSelectedQuestionController = new EditSelectedQuestionController(this);
        this.setEditSelectedQuestionController(editSelectedQuestionController);
        courseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        //selectCourse.getSelectionModel().getSelectedItem()
        editSelectedQuestionController.getSubjects();
        populateCourseComboBox();
    }
}
