package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.Controller.QuestionController;
import javafx.application.Platform;
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
import java.util.ArrayList;
import java.util.List;
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
    private ListView<Course> courseList;

    @FXML
    private Button showCourseBtn;

    @FXML
    private ComboBox<Subject> selectSubject;

    private QuestionController questionController;

    public TextField getAnswerD() {
        return answerD;
    }

    public TextField getAnswerC() {
        return answerC;
    }

    public TextField getAnswerB() {
        return answerB;
    }

    public TextField getAnswerA() {
        return answerA;
    }

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
        EventBus.getDefault().unregister(questionController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        //Teacher teacher = (Teacher) SimpleClient.getClient().getUser();

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
    @FXML
    void saveAction(ActionEvent event)
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
            String answer = "";
            correctAnswer = selectedRadioButton.getText();

            if(correctAnswer.equals("a."))
            {
                answer = answerA.getText();
            }
            else if (correctAnswer.equals("b."))
            {
                answer = answerB.getText();
            }
            else if (correctAnswer.equals("c."))
            {
                answer = answerC.getText();
            }
            else if (correctAnswer.equals("d."))
            {
                answer = answerD.getText();
            }
            correctAnswer = selectedRadioButton.getText() + " " + answer;
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
        questionController.createQuestion(list);
    }

    @FXML
    void selectCourseAction(ActionEvent event) {

    }

    @FXML
    void selectSubjectAction(ActionEvent event) {

    }
    public void setQuestionController(QuestionController questionController) {
        this.questionController = questionController;
    }

    @FXML
    public void initialize() throws IOException {
        questionController = new QuestionController(this);
        this.setQuestionController(questionController);

        courseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        questionController.getSubjects();
        populateCourseComboBox();
        questionTextTXT.setText("");
        answerA.setText("");
        answerB.setText("");
        answerC.setText("");
        answerD.setText("");
    }

    @FXML
    void shoqCourseBtn(ActionEvent event) throws IOException {
        questionController.getCourse(selectSubject.getSelectionModel().getSelectedItem());
    }

    public ListView<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ListView<Course> courseList) {
        this.courseList = courseList;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

}
