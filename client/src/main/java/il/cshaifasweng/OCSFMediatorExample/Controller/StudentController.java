package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.LogoutEvent;
import il.cshaifasweng.OCSFMediatorExample.client.LogoutForStudentEvent;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.StudentBoundry;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

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
