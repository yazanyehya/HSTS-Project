package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class TeacherController
{

    private TeacherBoundry teacherBoundry;
    private boolean isLogoutDialogShown = false;
    public TeacherController(TeacherBoundry teacherBoundry)
    {
        EventBus.getDefault().register(this);
        this.teacherBoundry = teacherBoundry;
    }

    public void createAnQuestionAction()
    {

    }
    public void changeToQuestionBoundry() throws IOException
    {

        Message message = new Message("changeToQuestionBoundry", null);
        SimpleClient.getClient().sendToServer(message);
    }
    public void changeToEditQuestionBoundry() throws IOException
    {
        Message message = new Message("changeToEditQuestionBoundry", null);
        SimpleClient.getClient().sendToServer(message);
    }
    public void changeToExamBoundry() throws IOException
    {
        Message message = new Message("changeToExamBoundry", null);
        SimpleClient.getClient().sendToServer(message);
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
    public void handleChangeToExamBoundry(ChangeToExamBoundry changeToExamBoundry)
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleChangeToEditQuestionBoundry(ChangeToEditQuestionEvent editQuestionBoundry)
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleChangeToEditExamBoundry(ChangeToEditQuestionEvent editQuestionBoundry)
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleChangeToQuestionBoundry(ChangeToQuestionBoundry changeToQuestionBoundry) throws IOException
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("QuestionBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
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
}
