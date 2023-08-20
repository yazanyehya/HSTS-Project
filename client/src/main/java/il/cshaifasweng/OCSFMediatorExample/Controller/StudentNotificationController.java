package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class StudentNotificationController
{

    private StudentNotificationBoundry studentNotificationBoundry;
    private boolean isLogoutDialogShown = false;

    public StudentNotificationController (StudentNotificationBoundry studentNotificationBoundry)
    {
        EventBus.getDefault().register(this);
        this.studentNotificationBoundry = studentNotificationBoundry;
    }
    @Subscribe
    public void handleEvents(NotificationForStudentEvent notificationForStudentEvent)
    {
        List<Notification> list = (List<Notification>) notificationForStudentEvent.getMessage().getBody();

        Platform.runLater(()->{
            studentNotificationBoundry.getNotificationID().setCellValueFactory(new PropertyValueFactory<Notification, Integer>("id"));
            studentNotificationBoundry.getNotificationContent().setCellValueFactory(new PropertyValueFactory<Notification, String>("message"));
            studentNotificationBoundry.getStatuscol().setCellValueFactory(new PropertyValueFactory<Notification, String>("readOrNot"));

            studentNotificationBoundry.getTable().getItems().addAll(list);
        });
    }
    @Subscribe
    public void handleLogoutEvent(LogoutForStudentEvent logoutForStudentEvent) {
        System.out.println("logout platform");

        if (logoutForStudentEvent.getMessage().getTitle().equals("LogoutForStudent")) {
            if (!isLogoutDialogShown) {
                isLogoutDialogShown = true;

                Platform.runLater(() -> {
                    // Show the dialog
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "You Logged out");
                    isLogoutDialogShown = false;
                });
            }

            // Unregister this class from the EventBus
            EventBus.getDefault().unregister(this);

            try {
                Platform.runLater(() -> {
                    try {
                        SimpleChatClient.switchScreen("LoginController");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
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
    @Subscribe
    public void handleStudentEvents(StudentEvent studentEvent)
    {
        showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go check the home page");
    }
}
