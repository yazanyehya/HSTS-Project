package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ConductAnExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ConductAnExamBoundry {

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;


    @FXML
    private TextField executionCode;

    @FXML
    private TextField id;

    @FXML
    private Button startExamBtn;

    @FXML
    private Button ConductAnExamBTN;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;


    @FXML
    private Button viewGradesBTN;

    @FXML
    private Button notificationBtn;


    private ConductAnExamController conductAnExamController;

    @FXML
    void executionCodeAction(ActionEvent event)
    {

    }

    @FXML
    void idAction(ActionEvent event) {

    }
    @FXML
    void conductAnExamAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            // Show the dialog
            conductAnExamController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Conduct An Exam Page");

        });
    }

    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(conductAnExamController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("StudentNotifications");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Message message = new Message("getNotificationForStudent", SimpleClient.getClient().getUser());
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    void homeBtnAction(ActionEvent event) {
        EventBus.getDefault().unregister(conductAnExamController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("StudentBoundry");
                Message newMessage = new Message("getStudentNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }


    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Message msg = new Message("LogoutCE", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {

        EventBus.getDefault().unregister(conductAnExamController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForStudent");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Message message = new Message("viewGradesForStudent", SimpleClient.getClient().getUser());
        SimpleClient.getClient().sendToServer(message);
    }
    @FXML
    void initialize()
    {
        conductAnExamController = new ConductAnExamController(this);
        this.setConductAnExamController(conductAnExamController);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
    }



    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
                "HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
    }

    public void setConductAnExamController(ConductAnExamController conductAnExamController) {
        this.conductAnExamController = conductAnExamController;
    }

    @FXML
    void startExamAction(ActionEvent event) throws IOException {

        if (executionCode.getText() == "")
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Execution code is missing!");
                //EventBus.getDefault().unregister(this);
            });
        }
        else if (id.getText() == "")
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Your id is missing!");
                //EventBus.getDefault().unregister(this);
            });
        }
        else
        {
            Student student = (Student) SimpleClient.getClient().getUser();
            Object object = new Object[]{executionCode.getText(), id.getText(), student};
            Message message = new Message("changeToStartExam", object);
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