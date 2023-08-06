
/**
 * Sample Skeleton for 'principleBoundry.fxml' Controller Class
 */

        package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.PrincipleController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;


import java.awt.*;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrincipleBoundry {
    @FXML
    private ImageView logo;
    @FXML
    private Label timeLabelp;
    private AnimationTimer animationTimer;
    @FXML
    private Button homeBtn;

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader
    @FXML // fx:id="courseReportsBtn"
    private Button courseReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="studentReportsBtn"
    private Button studentReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="teacherReportsBtn"
    private Button teacherReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsBtn"
    private Button viewExamsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesBtn"
    private Button viewGradesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionsBtn"
    private Button viewQuestionsBtn; // Value injected by FXMLLoader

    @FXML
    private Button extraTimeBtn;

    private PrincipleController principleController;


    @FXML
    void homeBtnAction(ActionEvent event)
    {
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in Home Page");
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        principleController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(principleController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimePrinciple");
                Message message = new Message("ExtraTimePrinciple", null);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void viewExamsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewExamsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewGradesBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setPrincipleController(PrincipleController principleController) {
        this.principleController = principleController;
    }

    @FXML
    public void initialize()
    {
        principleController = new PrincipleController(this);
        this.setPrincipleController(principleController);
//        Image logoImage= new Image(getClass().getResourceAsStream("/images/finallogop.png"));
//        logo.setImage(logoImage);        animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                updateDateTime();
//            }
//        };
//        animationTimer.start();
    }
//    private void updateDateTime() {
//        // Get the current date and time
//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//
//
//        // Format the date and time as desired (change the pattern as needed)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
//                "HH:mm:ss");
//        String dateTimeString = currentDateTime.format(formatter);
//
//
//
//        // Update the label text
//        timeLabelp.setText(dateTimeString);
//    }
//
//
//
//    // Override the stop method to stop the AnimationTimer when the application exits
//    public void stop() {
//        animationTimer.stop();
//    }

    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getTeachersForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("courseReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getCoursesForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("studentReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getStudentsForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
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
}