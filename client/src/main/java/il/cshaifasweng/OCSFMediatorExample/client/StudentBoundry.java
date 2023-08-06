/**
 * Sample Skeleton for 'studentBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentBoundry {

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;


    @FXML
    private ImageView logostd;

    @FXML // fx:id="ConductAnExamBTN"
    private Button ConductAnExamBTN; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesBTN"
    private Button viewGradesBTN; // Value injected by FXMLLoader

    @FXML
    private Button logoutBtn;

    @FXML
    private Text studentName;

    private StudentController studentController;
    @FXML
    void conductAnExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(studentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ConductAnExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            studentController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Home Page");

        });

    }


    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        Message msg = new Message("LogoutForStudent", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {

        EventBus.getDefault().unregister(studentController);
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
    public void initialize()
    {
        studentController = new StudentController(this);
        this.setStudentController(studentController);

        Image logoImage2= new  Image(getClass().getResourceAsStream("/images/orangeHSTS.png"));
        logostd.setImage(logoImage2);
        User user = SimpleClient.getClient().getUser();
        //studentName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
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

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

}
