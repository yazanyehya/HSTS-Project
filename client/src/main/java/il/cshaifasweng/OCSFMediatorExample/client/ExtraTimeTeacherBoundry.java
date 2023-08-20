package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ExtraTimeTeacherController;
import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtraTimeTeacherBoundry {
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
    private TableColumn<ReadyExam, Button> PressForExtraTimeCol;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<ReadyExam, String> courseCol;

    @FXML
    private TableColumn<ReadyExam, Integer> examIdCol;

    @FXML
    private TableColumn<ReadyExam, Integer> numberOfExaminees;

    @FXML
    private TableColumn<ReadyExam, String> statusCol;

    @FXML
    private TableView<ReadyExam> table;


    private ExtraTimeTeacherController extraTimeController;

    @FXML
    void logoutAction(ActionEvent event) throws IOException
    {
        extraTimeController.logOut();
    }

    @FXML
    void approveExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(extraTimeController);
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
        EventBus.getDefault().unregister(extraTimeController);
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
        EventBus.getDefault().unregister(extraTimeController);
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
        EventBus.getDefault().unregister(extraTimeController);
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
        EventBus.getDefault().unregister(extraTimeController);
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
        EventBus.getDefault().unregister(extraTimeController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        EventBus.getDefault().unregister(extraTimeController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
                Message newMessage = new Message("getTeacherNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void aquireExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(extraTimeController);
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
        Platform.runLater(() -> {
            // Show the dialog
            extraTimeController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Extra Time Page");

        });
    }

    @FXML
    void seeResultsAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(extraTimeController);
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
        extraTimeController = new ExtraTimeTeacherController(this);
        this.setExtraTimeController(extraTimeController);
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

    public void setTable(TableView<ReadyExam> table) {
        this.table = table;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setCourseCol(TableColumn<ReadyExam, String> courseCol) {
        this.courseCol = courseCol;
    }

    public void setExamIdCol(TableColumn<ReadyExam, Integer> examIdCol) {
        this.examIdCol = examIdCol;
    }


    public void setExtraTimeController(ExtraTimeTeacherController extraTimeController) {
        this.extraTimeController = extraTimeController;
    }

    public void setPressForExtraTimeCol(TableColumn<ReadyExam, Button> pressForExtraTimeCol) {
        PressForExtraTimeCol = pressForExtraTimeCol;
    }

    public void setNumberOfExaminees(TableColumn<ReadyExam, Integer> numberOfExaminees) {
        this.numberOfExaminees = numberOfExaminees;
    }

    public TableColumn<ReadyExam, String> getCourseCol() {
        return courseCol;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public TableView<ReadyExam> getTable() {
        return table;
    }

    public ExtraTimeTeacherController getExtraTimeController() {
        return extraTimeController;
    }

    public TableColumn<ReadyExam, Button> getPressForExtraTimeCol() {
        return PressForExtraTimeCol;
    }

    public TableColumn<ReadyExam, Integer> getExamIdCol() {
        return examIdCol;
    }

    public TableColumn<ReadyExam, Integer> getNumberOfExaminees() {
        return numberOfExaminees;
    }

    public TableColumn<ReadyExam, String> getStatusCol() {
        return statusCol;
    }

    public void setStatusCol(TableColumn<ReadyExam, String> statusCol) {
        this.statusCol = statusCol;
    }
}
