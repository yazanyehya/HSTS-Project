package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class TeacherController
{

    private TeacherBoundry teacherBoundry;
    private boolean isLogoutDialogShown = false;
    public TeacherController(TeacherBoundry teacherBoundry)
    {
        EventBus.getDefault().register(this);
        this.teacherBoundry = teacherBoundry;
    }

    public void logOut() throws IOException {
        Message msg = new Message("Logout", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
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
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("Logout")) {
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





    public void setTeacherBoundry(TeacherBoundry teacherBoundry) {
        this.teacherBoundry = teacherBoundry;
    }

    public void changeToEditExamBoundry()
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleTeacherEvent(TeacherEvent teacherEvent)
    {
        if ("getTeacherNotificationList".equals(teacherEvent.getMessage().getTitle()) || "setToReadTeacher".equals(teacherEvent.getMessage().getTitle()))
        {
            Platform.runLater(()->{
                List<Notification> list = (List<Notification>) teacherEvent.getMessage().getBody();
                ObservableList<Notification> notifications = FXCollections.observableArrayList(list);
                teacherBoundry.getNotificationList().setItems(notifications);
                teacherBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));
            });
        }
        else if (teacherEvent.getMessage().getTitle().equals("RefreshTeacherBell"))
        {
            System.out.println("RefreshStudentBell");
            Platform.runLater(()->{
                Object[] objects = (Object[]) teacherEvent.getMessage().getBody();
                List<Notification> list = (List<Notification>) objects[0];
                int id = (Integer)objects[1];
                if (id == SimpleClient.getClient().getUser().getId())
                {
                    teacherBoundry.getNotificationList().getItems().clear();
                    ObservableList<Notification> notifications = FXCollections.observableArrayList(list);
                    teacherBoundry.getNotificationList().setItems(notifications);
                    teacherBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));

                    showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification");
                }
//                studentBoundry.getNotificationList().getItems().addAll(notifications);
//                studentBoundry.getNotificationCountLabel().setText(Integer.toString(notifications.size()));
            });

        }
    }
}

