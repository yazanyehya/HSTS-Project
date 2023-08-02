/**
 * Sample Skeleton for 'studentReportsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.CourseReportsController;
import il.cshaifasweng.OCSFMediatorExample.Controller.StudentReportsController;
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

public class StudentReportsBoundry {


    @FXML
    private TextField averageTextField;

    @FXML
    private Button backBtn;

    @FXML
    private ListView<ReadyExam> listViewExams;

    @FXML
    private TextField medianTextField;

    @FXML
    private Button showExams;

    @FXML
    private Button showStudents;

    @FXML
    private ListView<Student> studentsList;

    @FXML
    private BarChart<?, ?> theChart;
    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

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
    @FXML
    void averageTextFieldAction(ActionEvent event) {

    }
    StudentReportsController studentReportsController;
    public void setStudentReportsController(StudentReportsController studentReportsController) {
        this.studentReportsController = studentReportsController;
    }
    public StudentReportsController getStudentReportsController() {
        return studentReportsController;
    }

    @FXML
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(studentReportsController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void medianTextFieldAction(ActionEvent event) {

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
                    ReadyExam exam = listViewExams.getSelectionModel().getSelectedItem();
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

        ObservableList<Student> selectedItems = studentsList.getSelectionModel().getSelectedItems();

        for (Student item : selectedItems) {
            // Perform actions based on the selected item(s)
            try {
                studentReportsController.getExams(item);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        // CALCULATE THE AVERAGE AND THE MEDIAN AND THE CHART: (DO ALL ON selectedItems List)
        // retrieve grade by: Student ->  Exam -> Grade
        // average = sumGrades / numOfGrades
        // median = ??
        // the chart = you can initialize 10 variables, for each Interval, by zero,
        // and increase them every time we have a grade in that Interval
    }

    @FXML
    void showStudentsAction(ActionEvent event) throws IOException {
        studentReportsController.getStudents();
    }

    @FXML
    void initialize() throws IOException {

        studentReportsController = new StudentReportsController(this);
        this.setStudentReportsController(studentReportsController);

        System.out.println("before getting students");
        listViewExams.setItems(null);
        studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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

    private void populateCoursesList() { // Set the cell factory to display the course name
        studentsList.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                return new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getFirstName() + item.getLastName());
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

    public ListView<ReadyExam> getListViewExams() {
        return listViewExams;
    }

    public void setListViewExams(ListView<ReadyExam> listViewExams) {
        this.listViewExams = listViewExams;
    }
    public ListView<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ListView<Student> studentsList) {
        this.studentsList = studentsList;
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
