package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.CourseReportsController;
import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherReportsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeacherReportsBoundry {

    @FXML
    private TextField averageTextField;

    @FXML
    private Button compare;

    @FXML
    private Button courseReportsBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ListView<ReadyExam> listViewExams;

    @FXML
    private Button logoutBtn;

    @FXML
    private TextField medianTextField;

    @FXML
    private Button studentReportsBtn;

    @FXML
    private Button teacherReportsBtn;

    @FXML
    private ListView<Teacher> teachersList;

    @FXML
    private Button viewExamsBtn;

    @FXML
    private Button viewGradesBtn;

    @FXML
    private Button viewQuestionsBtn;

    public int flag = 0;

    private TeacherReportsController teacherReportsController;

    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;


    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(teacherReportsController);
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
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(teacherReportsController);
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
        EventBus.getDefault().unregister(teacherReportsController);

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
                EventBus.getDefault().unregister(teacherReportsController);
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
        teacherReportsController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherReportsController);
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
                EventBus.getDefault().unregister(teacherReportsController);
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
                EventBus.getDefault().unregister(teacherReportsController);
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
                EventBus.getDefault().unregister(teacherReportsController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }



    @FXML
    void initialize() throws IOException {

        teacherReportsController = new TeacherReportsController(this);
        setTeacherReportsController(teacherReportsController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();

        System.out.println("before getting courses");
        listViewExams.setItems(null);
        teachersList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateCoursesList();


        teachersList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check if it's a double-click
                if (listViewExams.getItems() != null)
                {
                    listViewExams.getItems().clear();
                }
                Teacher teacher = teachersList.getSelectionModel().getSelectedItem();
                if (teacher != null)
                {
                    try {
                        teacherReportsController.getExams(teacher);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        listViewExams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        Platform.runLater(()->{

            compare.setOnMouseClicked(event->
            {
                teacherReportsController.getMedian_map().clear();
                teacherReportsController.getMap().clear();
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
                    Message message = new Message("getListGradeForTeacher", readyExam);
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
                    teacherReportsController.getResults(exam);
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

    @FXML
    void averageTextFieldAction(ActionEvent event) {

    }

    @FXML
    void compareAction(ActionEvent event) {

    }


    @FXML
    void medianTextFieldAction(ActionEvent event) {

    }


    @FXML
    void teacherReportsAction(ActionEvent event)
    {
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in Teacher Reports");
        });
    }



    public Button getViewQuestionsBtn() {
        return viewQuestionsBtn;
    }

    public Button getViewGradesBtn() {
        return viewGradesBtn;
    }

    public Button getTeacherReportsBtn() {
        return teacherReportsBtn;
    }

    public Button getViewExamsBtn() {
        return viewExamsBtn;
    }

    public Button getStudentReportsBtn() {
        return studentReportsBtn;
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }

    public Button getHomeBtn() {
        return homeBtn;
    }

    public Button getExtraTimeBtn() {
        return extraTimeBtn;
    }

    public Button getCourseReportsBtn() {
        return courseReportsBtn;
    }

    public Button getCompare() {
        return compare;
    }

    public TextField getMedianTextField() {
        return medianTextField;
    }

    public TextField getAverageTextField() {
        return averageTextField;
    }

    public ListView<ReadyExam> getListViewExams() {
        return listViewExams;
    }

    public ListView<Teacher> getTeachersList() {
        return teachersList;
    }

    public void setAverageTextField(TextField averageTextField) {
        this.averageTextField = averageTextField;
    }

    public void setViewGradesBtn(Button viewGradesBtn) {
        this.viewGradesBtn = viewGradesBtn;
    }

    public void setMedianTextField(TextField medianTextField) {
        this.medianTextField = medianTextField;
    }

    public void setCompare(Button compare) {
        this.compare = compare;
    }

    public void setCourseReportsBtn(Button courseReportsBtn) {
        this.courseReportsBtn = courseReportsBtn;
    }

    public void setExtraTimeBtn(Button extraTimeBtn) {
        this.extraTimeBtn = extraTimeBtn;
    }

    public void setHomeBtn(Button homeBtn) {
        this.homeBtn = homeBtn;
    }

    public void setListViewExams(ListView<ReadyExam> listViewExams) {
        this.listViewExams = listViewExams;
    }

    public void setLogoutBtn(Button logoutBtn) {
        this.logoutBtn = logoutBtn;
    }

    public void setTeacherReportsBtn(Button teacherReportsBtn) {
        this.teacherReportsBtn = teacherReportsBtn;
    }

    public void setStudentReportsBtn(Button studentReportsBtn) {
        this.studentReportsBtn = studentReportsBtn;
    }

    public void setTeachersList(ListView<Teacher> teachersList) {
        this.teachersList = teachersList;
    }

    public void setViewExamsBtn(Button viewExamsBtn) {
        this.viewExamsBtn = viewExamsBtn;
    }

    public void setViewQuestionsBtn(Button viewQuestionsBtn) {
        this.viewQuestionsBtn = viewQuestionsBtn;
    }

    public void setTeacherReportsController(TeacherReportsController teacherReportsController) {
        this.teacherReportsController = teacherReportsController;
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
}
 