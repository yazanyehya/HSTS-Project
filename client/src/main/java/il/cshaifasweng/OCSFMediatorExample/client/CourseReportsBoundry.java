/**
 * Sample Skeleton for 'courseReportsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.CourseReportsController;
import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherReportsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class CourseReportsBoundry {

    @FXML // fx:id="averageTextField"
    private TextField averageTextField; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="coursesList"
    private ListView<Course> coursesList; // Value injected by FXMLLoader

    @FXML // fx:id="listViewExams"
    private ListView<Exam> listViewExams; // Value injected by FXMLLoader

    @FXML // fx:id="medianTextField"
    private TextField medianTextField; // Value injected by FXMLLoader

    @FXML // fx:id="showExams"
    private Button showExams; // Value injected by FXMLLoader

    @FXML // fx:id="showTeachers"
    private Button showCourses; // Value injected by FXMLLoader
    @FXML // fx:id="examPeriod"
    private TextField examPeriod; // Value injected by FXMLLoader
    @FXML // fx:id="commentStudet"
    private TextArea commentStudet; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionsBtn"
    private Button showQuestionsBtn; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExamQuestions"
    private ListView<Question> listViewExamQuestions; // Value injected by FXMLLoader
    @FXML // fx:id="commentTeacher"
    private TextArea commentTeacher; // Value injected by FXMLLoader
    @FXML // fx:id="theChart"
    private BarChart<String, Integer> theChart; // Value injected by FXMLLoader
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;
    CourseReportsController courseReportsController;
    public void setCourseReportsController(CourseReportsController courseReportsController) {
        this.courseReportsController = courseReportsController;
    }
    public CourseReportsController getCourseReportsController() {
        return courseReportsController;
    }

    @FXML
    void averageTextFieldAction(ActionEvent event) {

    }

    @FXML
    void examPeriodAction(ActionEvent event) {

    }
    @FXML
    void showQuestionsAction(ActionEvent event) {
        if (listViewExams.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select An Exam!");
            });
        }
        else
        {
            Platform.runLater(() -> {
                try {
                    Exam exam = listViewExams.getSelectionModel().getSelectedItem();
                    Message msg = new Message("getExamQuestions", exam);
                    SimpleClient.getClient().sendToServer(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    @FXML
    void showExamssAction(ActionEvent event) {

        ObservableList<Course> selectedItems = coursesList.getSelectionModel().getSelectedItems();

        for (Course item : selectedItems) {
            // Perform actions based on the selected item(s)
            try {
                courseReportsController.getExams(item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // CALCULATE THE AVERAGE AND THE MEDIAN AND THE CHART: (DO ALL ON selectedItems List)
        // retrieve grade by: Course -> Exam -> Grade
        // average = sumGrades / numOfGrades
        // median = ??
        // the chart = you can initialize 10 variables, for each Interval, by zero,
        // and increase them every time we have a grade in that Interval
    }

    @FXML
    void showCoursesAction(ActionEvent event) throws IOException {
        courseReportsController.getCourses();
    }

    @FXML
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(courseReportsController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void medianTextFieldAction(ActionEvent event) {

    }

    @FXML
    void initialize() throws IOException {

        courseReportsController = new CourseReportsController(this);
        this.setCourseReportsController(courseReportsController);

        System.out.println("before getting courses");
        listViewExams.setItems(null);
        coursesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateCoursesList();

        XYChart.Series series1 = new XYChart.Series();
        series1.setName("Students Number");
        series1.getData().add(new XYChart.Data("0-10", 0));
        series1.getData().add(new XYChart.Data("11-20", 0));
        series1.getData().add(new XYChart.Data("21-30", 0));
        series1.getData().add(new XYChart.Data("31-40", 0));
        series1.getData().add(new XYChart.Data("41-50", 0));
        series1.getData().add(new XYChart.Data("51-60", 0));
        series1.getData().add(new XYChart.Data("61-70", 0));
        series1.getData().add(new XYChart.Data("71-80", 0));
        series1.getData().add(new XYChart.Data("81-90", 0));
        series1.getData().add(new XYChart.Data("91<", 0));
        theChart.getData().addAll(series1);

    }

    private void populateCoursesList() {
        // Set the cell factory to display the course name
        coursesList.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
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
    }
    public void showAlertDialog(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public ListView<Exam> getListViewExams() {
        return listViewExams;
    }
    public void setListViewExams(ListView<Exam> listViewExams) {
        this.listViewExams = listViewExams;
    }
    public ListView<Course> getCoursesList() {
        return coursesList;
    }
    public void setCoursesList(ListView<Course> coursesList) {
        this.coursesList = coursesList;
    }
    public TextArea getCommentTeacher() {
        return commentTeacher;
    }
    public void setCommentTeacher(TextArea commentTeacher) {
        this.commentTeacher = commentTeacher;
    }

    public void setCommentStudet(TextArea commentStudet) {
        this.commentStudet = commentStudet;
    }

    public TextArea getCommentStudet() {
        return commentStudet;
    }
    public TextField getExamPeriod() {
        return examPeriod;
    }
    public void setExamPeriod(TextField examPeriod) {
        this.examPeriod = examPeriod;
    }
    public ListView<Question> getListViewExamQuestions() {
        return listViewExamQuestions;
    }
}
