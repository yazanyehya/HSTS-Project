package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentNotificationController;
import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherNotificationsController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeacherNotificationsBoundry {

    @FXML
    private Button approveExamBtn;

    @FXML
    private Button aquireExamBtn;

    @FXML
    private Button createExamBtn;

    @FXML
    private Button createQuestionBtn;

    @FXML
    private TableColumn<Notification, String> date;

    @FXML
    private Button editExamBtn;

    @FXML
    private Button editQuestionBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView logo;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button notificationBtn;

    @FXML
    private TableColumn<Notification, String> notificationContent;

    @FXML
    private TableColumn<Notification, Integer> notificationID;

    @FXML
    private Button seeResultsBtn;

    @FXML
    private Button sendExamsToStudentsBtn;

    @FXML
    private TableColumn<Notification, String> statuscol;

    @FXML
    private TableView<Notification> table;

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    private TeacherNotificationsController teacherNotificationsController;

    @FXML
    void EditExamsAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void EditQuestionsAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void approveExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ApproveExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void aquireExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("AquireExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void createAnExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void createAnQuestionAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("QuestionBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void extraTimeAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimeTeacher");
                System.out.println("ahmadddggg");
                Message message = new Message("GetOnGoingExamsForExtraTime", SimpleClient.getClient().getUser().getUsername());
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
                Message newMessage = new Message("getTeacherNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        teacherNotificationsController.logOut();
    }

    @FXML
    void notificationAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            teacherNotificationsController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Notification Page");

        });
    }

    @FXML
    void seeResultsAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForTeacher");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void sendExamsToStudentsAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherNotificationsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("SendExamToStudentBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void initialize()
    {
        teacherNotificationsController = new TeacherNotificationsController(this);
        this.setTeacherNotificationsController(teacherNotificationsController);

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
    public void setTeacherNotificationsController(TeacherNotificationsController teacherNotificationsController) {
        this.teacherNotificationsController = teacherNotificationsController;
    }

    public TableView<Notification> getTable() {
        return table;
    }

    public TableColumn<Notification, String> getNotificationContent() {
        return notificationContent;
    }

    public TableColumn<Notification, Integer> getNotificationID() {
        return notificationID;
    }

    public TableColumn<Notification, String> getDate() {
        return date;
    }

    public TableColumn<Notification, String> getStatuscol() {
        return statuscol;
    }
}
