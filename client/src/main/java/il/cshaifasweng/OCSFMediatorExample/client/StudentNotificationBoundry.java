package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentNotificationController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForStudentController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentNotificationBoundry {

    private StudentNotificationController studentNotificationController;

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    @FXML
    private Button ConductAnExamBTN;

    @FXML
    private Button ConductAnExamBTN1;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableColumn<Notification, String> notificationContent;

    @FXML
    private TableColumn<Notification, Integer> notificationID;

    @FXML
    private TableColumn<Notification, String> statuscol;

    @FXML
    private TableView<Notification> table;

    @FXML
    private TableColumn<Notification, String> date;

    @FXML
    private Button viewGradesBTN;
    @FXML
    private Button notificationBtn;

    @FXML
    void conductAnExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(studentNotificationController);
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
        EventBus.getDefault().unregister(studentNotificationController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("StudentBoundry");
                Message newMessage = new Message("getStudentNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void notificationAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            // Show the dialog
            studentNotificationController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Notifications Page");

        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Message msg = new Message("LogoutForStudent", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(studentNotificationController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForStudent");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Message message = new Message("viewGradesForStudent", SimpleClient.getClient().getUser());
        SimpleClient.getClient().sendToServer(message);
    }


    @FXML
    public void initialize()
    {
        studentNotificationController = new StudentNotificationController(this);
        this.setStudentNotificationController(studentNotificationController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
    }

    public void setStudentNotificationController(StudentNotificationController studentNotificationController) {
        this.studentNotificationController = studentNotificationController;
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
    public TableColumn<Notification, String> getStatuscol() {
        return statuscol;
    }

    public TableColumn<Notification, Integer> getNotificationID() {
        return notificationID;
    }

    public TableColumn<Notification, String> getNotificationContent() {
        return notificationContent;
    }

    public TableView<Notification> getTable()
    {
        return table;
    }

    public TableColumn<Notification, String> getDate() {
        return date;
    }
}
