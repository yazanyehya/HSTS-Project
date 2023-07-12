package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import il.cshaifasweng.OCSFMediatorExample.Controller.ExamController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.util.List;

public class ExamBoundry{

    @FXML
    private  Button backBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<Course> chooseCourse;

    @FXML
    private ComboBox<Subject> selectSubject;


    @FXML
    private ListView<Question> questionListView;

    @FXML
    private TextArea commentStudet;

    @FXML
    private TextArea commentTeacher;
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

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public TextArea getCommentStudet() {
        return commentStudet;
    }

    public TextArea getCommentTeacher() {
        return commentTeacher;
    }

    @FXML
    void initialize() throws IOException {

        questionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        examPeriod.setText("");
        examController = new ExamController(this);
        this.setExamController(examController);
        chooseCourse.setVisible(false);
        examController.getSubjects();

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
    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException
    {
        examController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        chooseCourse.setVisible(true);
    }

}
