package il.cshaifasweng.OCSFMediatorExample.client;

import com.sun.javafx.tk.Toolkit;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.server.ExamController;
import il.cshaifasweng.OCSFMediatorExample.server.ExamController111;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

public class ExamBoundry{

    @FXML
    private  Button backBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<Course> chooseCourse;

    @FXML
    private ListView<Question> questionListView;


    @FXML
    private Button selectBtn;

    @FXML
    private TextField examPeriod;

    private ExamController examController;

    public void setExamPeriod(TextField examPeriod) {
        this.examPeriod = examPeriod;
    }

    public TextField getExamPeriod() {
        return examPeriod;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setChooseCourse(ComboBox<Course> chooseCourse) {
        this.chooseCourse = chooseCourse;
    }

    public void setExamController(ExamController examController) {
        this.examController = examController;
    }

    public void setQuestionListView(ListView<Question> questionListView) {
        this.questionListView = questionListView;
    }

    public void setSelectBtn(Button selectBtn) {
        this.selectBtn = selectBtn;
    }

    public Button getSelectBtn() {
        return selectBtn;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public ComboBox<Course> getChooseCourse() {
        return chooseCourse;
    }

    public ExamController getExamController() {
        return examController;
    }

    public ListView<Question> getQuestionListView() {
        return questionListView;
    }


    @FXML
    void initialize() {

        questionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        examPeriod.setText("");
        examController = new ExamController(this);
        this.setExamController(examController);
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Hibernate.initialize(teacher.getCourses());
        ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        chooseCourse.setItems(courses);

        // Set the cell factory to display the course name
        chooseCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
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
        chooseCourse.setConverter(new StringConverter<Course>() {
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
        // Set up the question list view
    }

    @FXML
    void selectAction(ActionEvent event) throws IOException
    {
        System.out.println(chooseCourse.getValue());
        examController.selectAction(chooseCourse.getValue());
    }
    @FXML
    void saveExam(ActionEvent event) throws IOException {
        List<Question> selectedQuestions = getQuestionListView().getSelectionModel().getSelectedItems();
        System.out.println("incline bench press");
        examController.saveExam(selectedQuestions);
        System.out.println("incline smith bench press");
    }
    @FXML
    void examPeriodAction(ActionEvent event)
    {
        System.out.println("printing exam period: " + examPeriod.getText());
    }

    @FXML
    void examTypeAction(ActionEvent event)
    {

    }

    @FXML
    void backAction(ActionEvent event) throws IOException {
        examController.pressBack();
    }

    @FXML
    void chooseCourseAction(ActionEvent event)
    {
        // Handle course selection action

    }
}
