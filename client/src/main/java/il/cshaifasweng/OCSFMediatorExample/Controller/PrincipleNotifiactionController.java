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

public class PrincipleNotifiactionController
{

    private PrincipleNotificationBoundry principleNotificationBoundry;

    public PrincipleNotifiactionController(PrincipleNotificationBoundry principleNotificationBoundry)
    {
        EventBus.getDefault().register(this);
        this.principleNotificationBoundry = principleNotificationBoundry;
    }
    @Subscribe
    public void handlePrinciple(NotificationForPrincipleEvent notificationForPrincipleEvent)
    {
        List<Notification> list = (List<Notification>) notificationForPrincipleEvent.getMessage().getBody();

        Platform.runLater(() -> {
            principleNotificationBoundry.getNotificationID().setCellValueFactory(new PropertyValueFactory<Notification, Integer>("id"));
            principleNotificationBoundry.getNotificationContent().setCellValueFactory(new PropertyValueFactory<Notification, String>("message"));
            principleNotificationBoundry.getStatuscol().setCellValueFactory(new PropertyValueFactory<Notification, String>("readOrNot"));
            principleNotificationBoundry.getDate().setCellValueFactory(new PropertyValueFactory<Notification, String>("date"));
            principleNotificationBoundry.getTable().getItems().addAll(list);
        });
    }
    private boolean isLogoutDialogShown = false;
    public void logOut() throws IOException {
        Message msg = new Message("Logout principle", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void handleLogoutEvent(PrincipleLogoutEvent principleLogoutEvent) {
        System.out.println("logout platform");

        if (principleLogoutEvent.getMessage().getTitle().equals("Logout principle")) {
            System.out.println("LOAI");
            if (!isLogoutDialogShown) {
                isLogoutDialogShown = true;

                Platform.runLater(() -> {
                    // Show the dialog
                    principleNotificationBoundry.showAlertDialog(Alert.AlertType.INFORMATION, "Success", "You Logged out");
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
