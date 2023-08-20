package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class StudentController
{
    private StudentBoundry studentBoundry;
    private boolean isLogoutDialogShown = false;
    public StudentController(StudentBoundry studentBoundry)
    {
        EventBus.getDefault().register(this);
        this.studentBoundry = studentBoundry;
    }
    @Subscribe
    public void handleStudentEvents(StudentEvent studentEvent)
    {
        if (studentEvent.getMessage().getTitle().equals("getStudentNotificationList") || studentEvent.getMessage().getTitle().equals("setToRead"))
        {
            Platform.runLater(()->{
                List<Notification> list = (List<Notification>) studentEvent.getMessage().getBody();
                ObservableList<Notification> notifications = FXCollections.observableArrayList(list);
                studentBoundry.getNotificationList().setItems(notifications);
                studentBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));
            });

        }
        else if (studentEvent.getMessage().getTitle().equals("RefreshStudentBell"))
        {
            System.out.println("RefreshStudentBell");
            Platform.runLater(()->{
                Object[] objects = (Object[]) studentEvent.getMessage().getBody();
                List<Notification> list = (List<Notification>) objects[0];
                int id = (Integer)objects[1];
                if (id == SimpleClient.getClient().getUser().getId())
                {
                    studentBoundry.getNotificationList().getItems().clear();
                    ObservableList<Notification> notifications = FXCollections.observableArrayList(list);
                    studentBoundry.getNotificationList().setItems(notifications);
                    studentBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));

                    showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification");
                }
//                studentBoundry.getNotificationList().getItems().addAll(notifications);
//                studentBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));
            });

        }
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


}
