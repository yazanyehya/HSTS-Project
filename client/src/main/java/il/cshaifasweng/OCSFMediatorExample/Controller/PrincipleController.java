package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.PrincipleBoundry;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;


public class PrincipleController {
    private PrincipleBoundry principleBoundry;
    private boolean isLogoutDialogShown = false;
    public PrincipleController(PrincipleBoundry principleBoundry)
    {
        EventBus.getDefault().register(this);
        this.principleBoundry = principleBoundry;
    }
    public void setPrincipleBoundry(PrincipleBoundry principleBoundry) {
        this.principleBoundry = principleBoundry;
    }

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

    @Subscribe
    public void handleEvents(ExtraTimeEvent extraTimeEvent) throws IOException
    {
        if(extraTimeEvent.getMessage().getTitle().equals("AskPrincipleForExtraTime"))
        {
            System.out.println(SimpleClient.getClient().getUser().getUsername() + "ahmah homo ");
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.INFORMATION, "You have received a new message", "Extra Time Request");
            });
            Message message = new Message("ExtraTimeRequest", extraTimeEvent.getMessage().getBody());
            SimpleClient.getClient().sendToServer(message);
        }
    }
    @Subscribe
    public void handlePrincipleEvents(PrincipleEvent principleEvent)
    {
        if ("getPrincipleNotificationList".equals(principleEvent.getMessage().getTitle()) || "setToReadPrinciple".equals(principleEvent.getMessage().getTitle()))
        {
            Platform.runLater(()->{
                List<Notification> list = (List<Notification>) principleEvent.getMessage().getBody();
                ObservableList<Notification> notifications = FXCollections.observableArrayList(list);
                principleBoundry.getNotificationList().setItems(notifications);
                principleBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));
            });
        }
        else if (principleEvent.getMessage().getTitle().equals("RefreshPrincipleBell"))
        {
            System.out.println("RefreshStudentBell");
            Platform.runLater(()->{
                Object[] objects = (Object[]) principleEvent.getMessage().getBody();
                List<Notification> list = (List<Notification>) objects[0];
                int id = (Integer)objects[1];
                if (id == SimpleClient.getClient().getUser().getId())
                {
                    principleBoundry.getNotificationList().getItems().clear();
                    ObservableList<Notification> notifications = FXCollections.observableArrayList(list);
                    principleBoundry.getNotificationList().setItems(notifications);
                    principleBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));

                    showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification");
                }
//                studentBoundry.getNotificationList().getItems().addAll(notifications);
//                studentBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));
            });

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