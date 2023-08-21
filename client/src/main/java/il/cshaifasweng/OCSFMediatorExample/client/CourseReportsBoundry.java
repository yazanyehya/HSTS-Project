

/**
 * Sample Skeleton for 'courseReportsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.CourseReportsController;
import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherReportsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;
import org.hibernate.Hibernate;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

public class CourseReportsBoundry {

    @FXML // fx:id="averageTextField"
    private TextField averageTextField; // Value injected by FXMLLoader

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="coursesList"
    private ListView<Course> coursesList; // Value injected by FXMLLoader

    @FXML // fx:id="listViewExams"
    private ListView<ReadyExam> listViewExams; // Value injected by FXMLLoader

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
    @FXML
    private BarChart<String, Integer> theChart;

    @FXML
    private Button compare;
    public int flag = 0;



    @FXML
    private CategoryAxis x;
    @FXML
    private NumberAxis y;

    public  Integer temp = 0;

    private CourseReportsController courseReportsController;

    @FXML
    private Button courseReportsBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;
    @FXML
    private Button logoutBtn;


    @FXML
    private Button studentReportsBtn;

    @FXML
    private Button teacherReportsBtn;

    @FXML
    private Button viewExamsBtn;

    @FXML
    private Button viewGradesBtn;

    @FXML
    private Button viewQuestionsBtn;

    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;

    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(courseReportsController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getTeachersForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
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
    @FXML
    void courseReportsAction(ActionEvent event) {

//        Platform.runLater(() -> {
//            try {
//                showAlertDialog(Alert.AlertType.WARNING, "Error", "You are already in Courses Reports");
//                Principle principle = (Principle) SimpleClient.getClient().getUser();
//                Message message = new Message("getCoursesForPrinciple", principle);
//                SimpleClient.getClient().sendToServer(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        Platform.runLater(() -> {
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in Courses Reports");
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        EventBus.getDefault().unregister(courseReportsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("studentReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getStudentsForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void reportsAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(courseReportsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("reportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(courseReportsController);
                SimpleChatClient.switchScreen("PrincipleBoundry");
                Message newMessage = new Message("getPrincipleNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        courseReportsController.logOut();
    }


    @FXML
    void compareAction(ActionEvent event)
    {

    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(courseReportsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimePrinciple");
                Message message = new Message("ExtraTimePrinciple", null);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void viewExamsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(courseReportsController);
                SimpleChatClient.switchScreen("viewExamsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(courseReportsController);
                SimpleChatClient.switchScreen("viewGradesBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(courseReportsController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

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

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();


        System.out.println("before getting courses");
        listViewExams.setItems(null);
        coursesList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateCoursesList();


        coursesList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check if it's a double-click
                if (listViewExams.getItems() != null)
                {
                    listViewExams.getItems().clear();
                }
                Course course = coursesList.getSelectionModel().getSelectedItem();
                if (course != null)
                {
                    try {
                        courseReportsController.getExams(course);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        // Here, you can use selectedItems.size() to get the updated number of selected items after deselection
        // Do whatever you want with this information
        listViewExams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Platform.runLater(()->{
//        listViewExams.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ReadyExam> change) -> {
//            ObservableList<? extends ReadyExam> selectedItems = change.getList();

            compare.setOnMouseClicked(event->
            {
                courseReportsController.getMedian_map().clear();
                courseReportsController.getMap().clear();
                ObservableList<ReadyExam> selectedExams = listViewExams.getSelectionModel().getSelectedItems();
                flag = selectedExams.size();
                double curr_avg = 0;
                int curr_size = 0;
                for (ReadyExam exam : selectedExams)
                {
                    curr_avg += exam.getAvg()*exam.getSize();
                    curr_size+= exam.getSize();
                }
                double final_avg = curr_avg/curr_size;
                averageTextField.setText(Double.toString(final_avg));
                for (ReadyExam readyExam : selectedExams)
                {
                    Message message = new Message("getListGradeForCourse", readyExam);
                    try {
                        SimpleClient.getClient().sendToServer(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                for (Integer grade1 : readyExam.getListOfGrades())
//                o
//                    median_list.add(grade1);
//                }
                }


                for (ReadyExam exam : selectedExams)
                {
                    courseReportsController.getResults(exam);
                }

            });
        });
    }
    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
                "HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
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

    public ListView<ReadyExam> getListViewExams() {
        return listViewExams;
    }
    public void setListViewExams(ListView<ReadyExam> listViewExams) {
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

    public TextField getAverageTextField() {
        return averageTextField;
    }

    public TextField getMedianTextField() {
        return medianTextField;
    }

    public void setAverageTextField(TextField averageTextField) {
        this.averageTextField = averageTextField;
    }

    public BarChart<String, Integer> getTheChart() {
        return theChart;
    }

    public void setTheChart(BarChart<String, Integer> theChart) {
        this.theChart = theChart;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setMedianTextField(TextField medianTextField) {
        this.medianTextField = medianTextField;
    }

}
 