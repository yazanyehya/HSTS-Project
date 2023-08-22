package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.PrincipleController;
import il.cshaifasweng.OCSFMediatorExample.Controller.PrincipleNotifiactionController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrincipleNotificationBoundry {

    @FXML
    private Button courseReportsBtn;

    @FXML
    private TableColumn<Notification, String> date;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button notificationBtn;

    @FXML
    private TableColumn<Notification, String> notificationContent;

    @FXML
    private TableColumn<Notification, Integer> notificationID;

    @FXML
    private TableColumn<Notification, String> statuscol;

    @FXML
    private Button studentReportsBtn;

    @FXML
    private TableView<Notification> table;

    @FXML
    private Button teacherReportsBtn;

    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;

    @FXML
    private Button viewExamsBtn;

    @FXML
    private Button viewGradesBtn;

    @FXML
    private Button viewQuestionsBtn;

    private PrincipleNotifiactionController principleNotifiactionController;

    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleNotifiactionController);
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
    void notificationAction(ActionEvent event)
    {
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in Notification Page");
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleNotifiactionController);
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
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleNotifiactionController);
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
    void homeBtnAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleNotifiactionController);
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
        principleNotifiactionController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(principleNotifiactionController);
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
                EventBus.getDefault().unregister(principleNotifiactionController);
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
                EventBus.getDefault().unregister(principleNotifiactionController);
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
                EventBus.getDefault().unregister(principleNotifiactionController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    public TableColumn<Notification, Integer> getNotificationID() {
        return notificationID;
    }

    public TableColumn<Notification, String> getStatuscol() {
        return statuscol;
    }

    public TableColumn<Notification, String> getDate() {
        return date;
    }

    public TableColumn<Notification, String> getNotificationContent() {
        return notificationContent;
    }

    public TableView<Notification> getTable() {
        return table;
    }
    @FXML
    public void initialize() {
        principleNotifiactionController = new PrincipleNotifiactionController(this);
        this.setPrincipleNotifiactionController(principleNotifiactionController);
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
    public void setPrincipleNotifiactionController(PrincipleNotifiactionController principleNotifiactionController) {
        this.principleNotifiactionController = principleNotifiactionController;
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
