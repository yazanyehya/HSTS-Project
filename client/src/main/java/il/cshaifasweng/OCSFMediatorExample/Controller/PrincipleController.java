package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.PrincipleBoundry;
import org.greenrobot.eventbus.EventBus;
import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;


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