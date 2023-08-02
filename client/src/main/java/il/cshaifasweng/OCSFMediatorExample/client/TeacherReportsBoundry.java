/**
 * Sample Skeleton for 'teacherReportsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherReportsController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewQuestionsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TeacherReportsBoundry {

    @FXML // fx:id="averageTextField"
    private TextField averageTextField; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader
@FXML // fx:id="showTeachers"
private Button showTeachers; // Value injected by FXMLLoader

    @FXML // fx:id="medianTextField"
    private TextField medianTextField; // Value injected by FXMLLoader

//    @FXML // fx:id="selectTheTeacher"
//    private ComboBox<Teacher> selectTheTeacher; // Value injected by FXMLLoader
@FXML // fx:id="studentsList"
private ListView<Teacher> teachersList; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExams"
    private ListView<Exam> listViewExams; // Value injected by FXMLLoader
    @FXML // fx:id="theChart"
    private BarChart<?, ?> theChart; // Value injected by FXMLLoader
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
    @FXML // fx:id="showExams"
    private Button showExams; // Value injected by FXMLLoader
    TeacherReportsController teacherReportsController;
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
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(teacherReportsController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void medianTextFieldAction(ActionEvent event) {

    }
    public void setTeacherReportsController(TeacherReportsController teacherReportsController) {
        this.teacherReportsController = teacherReportsController;
    }
    public TeacherReportsController getTeacherReportsController() {
        return teacherReportsController;
    }

    @FXML
    void showExamssAction(ActionEvent event) {
            listViewExams.setItems(null);
            if (teachersList.getSelectionModel().isEmpty())
            {
                Platform.runLater(() -> {
                    // Login failure
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select A Teacher!");
                    });
             }
            ObservableList<Teacher> selectedItems = teachersList.getSelectionModel().getSelectedItems();
//            if (selectedItems.isEmpty())
//            {
//                Platform.runLater(() -> {
//                    // Login failure
//                    showAlertDialog(Alert.AlertType.ERROR, "Error", "The teacher has no exams. Please select another teacher!");
//                });
//            }
            for (Teacher item : selectedItems) {
                // Perform actions based on the selected item(s)
                try {
                    teacherReportsController.getExams(item);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        // CALCULATE THE AVERAGE AND THE MEDIAN AND THE CHART: (DO ALL ON selectedItems List)
        // retrieve grade by: Teacher -> Course -> Exam -> Grade
        // average = sumGrades / numOfGrades
        // median = ??
        // the chart = you can initialize 10 variables, for each Interval, by zero,
        // and increase them every time we have a grade in that Interval
    }
    @FXML
    void initialize() throws IOException {

        teacherReportsController = new TeacherReportsController(this);
        this.setTeacherReportsController(teacherReportsController);

        System.out.println("before getting teachers");
        listViewExams.setItems(null);
        teachersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewExams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateTeachersList();

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
    @FXML
    void showTeachersAction(ActionEvent event) throws IOException {
        //questionController.getCourse(selectSubject.getSelectionModel().getSelectedItem());
        teacherReportsController.getTeachers();
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
    private void populateTeachersList() {
        // Set the cell factory to display the teacher name
        teachersList.setCellFactory(new Callback<ListView<Teacher>, ListCell<Teacher>>() {
            @Override
            public ListCell<Teacher> call(ListView<Teacher> param) {
                return new ListCell<Teacher>() {
                    @Override
                    protected void updateItem(Teacher item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getFirstName() + " " + item.getLastName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
    }

    public ListView<Exam> getListViewExams() {
        return listViewExams;
    }

    public void setListViewExams(ListView<Exam> listViewExams) {
        this.listViewExams = listViewExams;
    }
    public ListView<Teacher> getTeachersList() {
        return teachersList;
    }

    public void setTeachersList(ListView<Teacher> teachersList) {
        this.teachersList = teachersList;
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

    public void setListViewExamQuestions(ListView<Question> listViewExamQuestions) {
        this.listViewExamQuestions = listViewExamQuestions;
    }
}
