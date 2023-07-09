package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
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

import java.io.IOException;
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
    private ComboBox<Course> selectCourse;

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
        Course selectedCourse = selectCourse.getValue();
        if (selectedRadioButton == null || selectedCourse == null || Objects.equals(getAnswerA().getText(), "") || Objects.equals(getAnswerB().getText(), "") || Objects.equals(getAnswerC().getText(), "") || Objects.equals(getAnswerD().getText(), ""))
        {
            question = null;
        }
        else
        {
            correctAnswer = selectedRadioButton.getText();
            question = new Question(questionTextTXT.getText(), answerA.getText(), answerB.getText()
                    , answerC.getText(), answerD.getText(), ((Teacher) SimpleClient.getClient().getUser()).getSubject(), (Teacher) SimpleClient.getClient().getUser(), correctAnswer,selectedCourse);
        }

        editSelectedQuestionController.saveQuestion(question);
    }

    @FXML
    void selectCourseAction(ActionEvent event) {

    }

    public Button getBackBtn() {
        return backBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
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

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
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

    public void setEditSelectedQuestionController(EditSelectedQuestionController editSelectedQuestionController) {
        this.editSelectedQuestionController = editSelectedQuestionController;
    }

    public EditSelectedQuestionController getEditSelectedQuestionController() {
        return editSelectedQuestionController;
    }

    public void initialize()
    {
        editSelectedQuestionController = new EditSelectedQuestionController(this);
        this.setEditSelectedQuestionController(editSelectedQuestionController);

        Teacher teacher = (Teacher)SimpleClient.getClient().getUser();


        ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        selectCourse.setItems(courses);
        selectCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> param) {
                return new ListCell<Course>() {
                    @Override
                    protected void updateItem(Course item, boolean empty) {
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

        selectCourse.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Course fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });
    }
}
