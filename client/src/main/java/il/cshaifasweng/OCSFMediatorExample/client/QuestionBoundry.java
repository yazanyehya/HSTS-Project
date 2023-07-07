package il.cshaifasweng.OCSFMediatorExample.client;

import com.mysql.cj.xdevapi.Client;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.server.QuestionController;
import il.cshaifasweng.OCSFMediatorExample.server.TeacherController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.Objects;

public class QuestionBoundry {

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

    private QuestionController questionController;

    @FXML
    void answerAAction(ActionEvent event)
    {

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
    void backAction(ActionEvent event) throws IOException {
        questionController.pressBack();
    }

    @FXML
    void optionAAction(ActionEvent event)
    {

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

    private void populateCourseComboBox() {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Hibernate.initialize(teacher.getCourses());
        ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        selectCourse.setItems(courses);

        // Set the cell factory to display the course name
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
    @FXML
    void saveAction(ActionEvent event)
    {
        RadioButton selectedRadioButton = (RadioButton) chooseAnswer.getSelectedToggle();
        String correctAnswer = selectedRadioButton.getText();

        Course selectedCourse = selectCourse.getValue();
        if (selectedCourse == null) {
            // Handle the case when no course is selected
            return;
        }
        questionController.createQuestion(new Question(questionTextTXT.getText(), answerA.getText(), answerB.getText()
                , answerC.getText(), answerD.getText(), ((Teacher) SimpleClient.getClient().getUser()).getSubject(), (Teacher) SimpleClient.getClient().getUser(), correctAnswer,selectedCourse));
    }

    @FXML
    void selectCourseAction(ActionEvent event) {

    }

    public void setQuestionController(QuestionController questionController) {
        this.questionController = questionController;
    }

    @FXML
    public void initialize()
    {
        questionController = new QuestionController(this);
        this.setQuestionController(questionController);
        populateCourseComboBox();
        questionTextTXT.setText("");
        answerA.setText("");
        answerB.setText("");
        answerC.setText("");
        answerD.setText("");
    }

}
