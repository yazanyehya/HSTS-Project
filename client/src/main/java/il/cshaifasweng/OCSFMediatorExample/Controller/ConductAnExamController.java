package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class ConductAnExamController
{

    private ConductAnExamBoundry conductAnExamBoundry;
    private boolean isLogoutDialogShown = false;


    public ConductAnExamController(ConductAnExamBoundry conductAnExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.conductAnExamBoundry = conductAnExamBoundry;
    }

    @Subscribe
    public void  handleExtraTimeRequest(ExtraTimeEvent extraTimeEvent)
    {

    }
    @Subscribe
    public void handleShowExamFailed(StartExamFailedEvent startExamFailedEvent)
    {
        Platform.runLater(() -> {
            // Login failure
            Object[] objects = (Object[]) startExamFailedEvent.getMessage().getBody();
            String info = (String) objects[0];
            if (Objects.equals(info, "0"))
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "You already did the exam");
            }
            else if (Objects.equals(info, "1"))
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "No available exam for this code");
            }
            else if(Objects.equals(info, "2"))
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Your ID is incorrect");
            }
            else
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Your ID is incorrect and there is no available exam for this code");
            }
            //EventBus.getDefault().unregister(this);
        });
    }
    @Subscribe
    public void handleShowExam(ChangeToStartExamEvent changeToStartExamEvent) throws IOException {
        ReadyExam readyExam = (ReadyExam)changeToStartExamEvent.getMessage().getBody();

        Student student = (Student) SimpleClient.getClient().getUser();
        Object object = new Object[]{readyExam, student};
        Message resMessage = new Message("startExam", object);
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(this);
                if (readyExam.getExamType().equals("Manual"))
                {
                    resMessage.setTitle("StartSolvingManualExam");
                    SimpleClient.getClient().sendToServer(resMessage);
                    SimpleChatClient.switchScreen("StartSolvingManualExam");


                }
                else
                {
                    resMessage.setTitle("StartSolvingComputerizedExam");
                    SimpleClient.getClient().sendToServer(resMessage);
                    SimpleChatClient.switchScreen("StartSolvingComputerizedExam");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
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
    @Subscribe
    public void handleStudentEvents(StudentEvent studentEvent)
    {
        Platform.runLater(()->{
            Object[] objects = (Object[]) studentEvent.getMessage().getBody();
            List<Notification> list = (List<Notification>) objects[0];
            int id = (Integer)objects[1];
            if (id == SimpleClient.getClient().getUser().getId())
            {
                showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go check the home page");            }
        });
    }
    @Subscribe
    public void handleLogoutEvent(LogoutForStudentEvent logoutForStudentEvent) {
        System.out.println("logout platform");

        if (logoutForStudentEvent.getMessage().getTitle().equals("LogoutCE")) {
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
    public void handleFinishExamEvent(FinishExamEvent finishExamEvent)
    {

        System.out.println("here");
        Platform.runLater(() -> {

            showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam has been submitted");
        });
    }
    @Subscribe
    public void handleTimeIsUp(TimeIsUpEvent timeIsUpEvent)
    {
        if (timeIsUpEvent.getMessage().getTitle().equals("NotInTime"))
        {
            Platform.runLater(() -> {

                showAlertDialog(Alert.AlertType.ERROR, "Error", "There is no longer time left to solve the exam");
            });
        }
    }
}
