package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForStudentController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.event.ActionEvent;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewGradesForStudentBoundry {

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    @FXML
    private Button ConductAnExamBTN;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;


    @FXML
    private TableColumn<ReadyExam, String> course;

    @FXML
    private TableColumn<ReadyExam, Integer> examID;

    @FXML
    private TableColumn<ReadyExam, Integer> grade;

    @FXML
    private TableColumn<ReadyExam, Button> previewOption;

    @FXML
    private TableColumn<ReadyExam, String> subject;

    @FXML
    private TableView<ReadyExam> table;

    @FXML
    private Button backBtn;

    @FXML
    private Button notificationBtn;

    private ViewGradesForStudentController viewGradesForStudentController;

    @FXML
    void conductAnExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewGradesForStudentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ConductAnExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        EventBus.getDefault().unregister(viewGradesForStudentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("studentBoundry");
                Message newMessage = new Message("getStudentNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    @FXML
    void notificationAction(ActionEvent event) {
        EventBus.getDefault().unregister(viewGradesForStudentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("StudentNotifications");
                Message message = new Message("getNotificationForStudent", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Message msg = new Message("LogoutVGS", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {

        Platform.runLater(() -> {
            // Show the dialog
            viewGradesForStudentController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In View Grades Page");

        });


    }

    @FXML
    public void initialize()
    {
        viewGradesForStudentController = new ViewGradesForStudentController(this);
        this.setViewGradesForStudentController(viewGradesForStudentController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
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



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
    }

    public void setViewGradesForStudentController(ViewGradesForStudentController viewGradesForStudentController) {
        this.viewGradesForStudentController = viewGradesForStudentController;
    }

    public TableColumn<ReadyExam, Button> getPreviewOption() {
        return previewOption;
    }

    public TableView<ReadyExam> getTable() {
        return table;
    }

    public TableColumn<ReadyExam, Integer> getExamID() {
        return examID;
    }

    public TableColumn<ReadyExam, Integer> getGrade() {
        return grade;
    }

    public TableColumn<ReadyExam, String> getCourse() {
        return course;
    }

    public TableColumn<ReadyExam, String> getSubject() {
        return subject;
    }

    public ViewGradesForStudentController getViewGradesForStudentController() {
        return viewGradesForStudentController;
    }

    public void setGrade(TableColumn<ReadyExam, Integer> grade) {
        this.grade = grade;
    }

    public void setCourse(TableColumn<ReadyExam, String> course) {
        this.course = course;
    }

    public void setExamID(TableColumn<ReadyExam, Integer> examID) {
        this.examID = examID;
    }

    public void setPreviewOption(TableColumn<ReadyExam, Button> previewOption) {
        this.previewOption = previewOption;
    }

    public void setSubject(TableColumn<ReadyExam, String> subject) {
        this.subject = subject;
    }

    public void setTable(TableView<ReadyExam> table) {
        this.table = table;
    }
}
