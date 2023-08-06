package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeacherBoundry {
    @FXML
    private ImageView approve;
    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    @FXML
    private Button approveExamBtn;

    @FXML
    private Button aquireExamBtn;

    @FXML
    private Button createExamBtn;

    @FXML
    private Button createQuestionBtn;

    @FXML
    private Button editExamBtn;

    @FXML
    private Button editQuestionBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView logo;


    @FXML
    private Button logoutBtn;


    @FXML
    private Button seeResultsBtn;

    @FXML
    private Button sendExamsToStudentsBtn;

    @FXML
    private Text teacherName;

    private TeacherController teacherController;

    @FXML
    void logoutAction(ActionEvent event) throws IOException
    {
        teacherController.logOut();
    }

    @FXML
    void approveExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ApproveExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void createAnExamAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void sendExamsToStudentsAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("SendExamToStudentBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void createAnQuestionAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("QuestionBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void EditExamsAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void EditQuestionsAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setTeacherController(TeacherController teacherController)
    {
        this.teacherController = teacherController;
    }
    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            teacherController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Home Page");

        });
    }

    @FXML
    void aquireExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("AquireExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void extraTimeAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimeTeacher");
                System.out.println("ahmadddggg");
                Message message = new Message("GetOnGoingExamsForExtraTime", SimpleClient.getClient().getUser().getUsername());
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void seeResultsAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForTeacher");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    public void initialize()
    {
        teacherController = new TeacherController(this);
        this.setTeacherController(teacherController);

        User user = SimpleClient.getClient().getUser();
        teacherName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
        Image logoImage = new  Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);
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
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n" +
                " HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
    }




    }

