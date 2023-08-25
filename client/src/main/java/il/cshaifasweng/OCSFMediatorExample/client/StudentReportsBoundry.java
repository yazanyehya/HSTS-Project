 package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentReportsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.*;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import javafx.util.Callback;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

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
    private ListView<Student> studentsList;

    public int flag = 0;

    private StudentReportsController studentReportsController;

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
    private Button compare;


    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;

    @FXML
    private Button notificationBtn;


    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(studentReportsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("PrincipleNotifications");
                Message message = new Message("getNotificationForPrinciple", SimpleClient.getClient().getUser());
                try {
                    SimpleClient.getClient().sendToServer(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(studentReportsController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getTeachersForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(studentReportsController);
                SimpleChatClient.switchScreen("courseReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getCoursesForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
//        Platform.runLater(() -> {
//            try {
//                EventBus.getDefault().unregister(studentReportsController);
//                SimpleChatClient.switchScreen("studentReportsBoundry");
//                Principle principle = (Principle) SimpleClient.getClient().getUser();
//                Message message = new Message("getStudentsForPrinciple", principle);
//                SimpleClient.getClient().sendToServer(message);
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in Students Reports");
        });
    }
    @FXML
    void reportsAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(studentReportsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("reportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void compareAction(ActionEvent event) {

    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(studentReportsController);
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
        studentReportsController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(studentReportsController);
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
                EventBus.getDefault().unregister(studentReportsController);
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
                EventBus.getDefault().unregister(studentReportsController);
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
                EventBus.getDefault().unregister(studentReportsController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void averageTextFieldAction(ActionEvent event) {

    }
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
    void initialize() throws IOException {

        studentReportsController = new StudentReportsController(this);
        this.setStudentReportsController(studentReportsController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();


        System.out.println("before getting students");
        listViewExams.setItems(null);
        //studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateCoursesList();


        studentsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check if it's a double-click
                if (listViewExams.getItems() != null)
                {
                    listViewExams.getItems().clear();
                }
                Student student = studentsList.getSelectionModel().getSelectedItem();
                if (student != null)
                {
                    try {
                        studentReportsController.getExams(student);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listViewExams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Platform.runLater(()->{
//        listViewExams.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ReadyExam> change) -> {
//            ObservableList<? extends ReadyExam> selectedItems = change.getList();

            compare.setOnMouseClicked(event -> {
                studentReportsController.getMedian_map().clear();
                studentReportsController.getMap().clear();
                ObservableList<ReadyExam> selectedExams = listViewExams.getSelectionModel().getSelectedItems();
                flag = selectedExams.size();
                double avg = 0;

                List<Integer> list = new ArrayList<>(); // Changed from List<Integer> to List<Number>
                CategoryAxis xAxis = new CategoryAxis();
                NumberAxis yAxis = new NumberAxis();
                BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                barChart.setTitle("Selected Exams Grades");

                for (ReadyExam readyExam : selectedExams) {
                    list.add(readyExam.getGrade());
                    avg += readyExam.getGrade();

                    // Create a new series for the BarChart
                    XYChart.Series<String, Number> series = new XYChart.Series<>();
                    series.setName(" Exam ID = " + readyExam.getOri_idd());
                    series.getData().add(new XYChart.Data<>("Grade", readyExam.getGrade()));

                    // Add the series to the BarChart
                    barChart.getData().add(series);
                }

                avg = avg / selectedExams.size();
                getAverageTextField().setText(Double.toString(avg));
                getMedianTextField().setText(Double.toString(list.get(list.size() / 2)));

                // Create a new stage to show the BarChart
                Stage stage = new Stage();
                stage.setTitle("Exams Comparison");
                stage.setScene(new Scene(new StackPane(barChart), 600, 400));
                stage.show();
            });



//                for (ReadyExam exam : selectedExams)
//                {
//                    studentReportsController.getResults(exam);
//                }

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
    private class GradeData {
        private String gradeRange;
        private int numExams;

        public GradeData(String gradeRange, int numExams) {
            this.gradeRange = gradeRange;
            this.numExams = numExams;
        }

        public String getGradeRange() {
            return gradeRange;
        }

        public int getNumExams() {
            return numExams;
        }
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

    public TextField getMedianTextField() {
        return medianTextField;
    }

    public TextField getAverageTextField() {
        return averageTextField;
    }
}
 