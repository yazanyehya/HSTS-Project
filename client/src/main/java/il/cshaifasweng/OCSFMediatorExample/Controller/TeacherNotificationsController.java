package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class TeacherNotificationsController
{
    private TeacherNotificationsBoundry teacherNotificationsBoundry;
    private boolean isLogoutDialogShown = false;

    public TeacherNotificationsController(TeacherNotificationsBoundry teacherNotificationsBoundry)
    {
        EventBus.getDefault().register(this);
        this.teacherNotificationsBoundry = teacherNotificationsBoundry;
    }
    @Subscribe
    public void handleEvents(NotificationForTeacherEvent notificationForTeacherEvent)
    {
        List<Notification> list = (List<Notification>) notificationForTeacherEvent.getMessage().getBody();

        Platform.runLater(()->{
            teacherNotificationsBoundry.getNotificationID().setCellValueFactory(new PropertyValueFactory<Notification, Integer>("id"));
            teacherNotificationsBoundry.getNotificationContent().setCellValueFactory(new PropertyValueFactory<Notification, String>("message"));
            teacherNotificationsBoundry.getStatuscol().setCellValueFactory(new PropertyValueFactory<Notification, String>("readOrNot"));
            teacherNotificationsBoundry.getDate().setCellValueFactory(new PropertyValueFactory<Notification, String>("date"));
            teacherNotificationsBoundry.getTable().getItems().addAll(list);
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
    public void logOut() throws IOException {
        Message msg = new Message("LogoutNoti", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("LogoutNoti")) {
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
}
