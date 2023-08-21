

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ExtraTimePrincipleController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ExtraTimeTeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.ExtraTime;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ExtraTimePrincipleBoundry {

    @FXML
    private TableColumn<ExtraTime, String> courseCol;

    @FXML
    private TableColumn<ExtraTime, Integer> examIDCol;

    @FXML
    private TableColumn<ExtraTime, Button> pressCol;

    @FXML
    private TableView<ExtraTime> table;

    @FXML
    private TableColumn<ExtraTime, String> teacherCol;

    @FXML
    private Button backBtn;

    private ExtraTimePrincipleController extraTimePrincipleController;

    @FXML
    private Button courseReportsBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;
    @FXML
    private Button logoutBtn;


    @FXML
    private Button studentReportsBtn;

    @FXML
    private Button teacherReportsBtn;

    @FXML
    private Button viewExamsBtn;

    @FXML
    private Button viewGradesBtn;

    @FXML
    private Button viewQuestionsBtn;
    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;

    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(extraTimePrincipleController);
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
                EventBus.getDefault().unregister(extraTimePrincipleController);
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
                EventBus.getDefault().unregister(extraTimePrincipleController);
                SimpleChatClient.switchScreen("studentReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getStudentsForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void reportsAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(extraTimePrincipleController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("reportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(extraTimePrincipleController);
                SimpleChatClient.switchScreen("PrincipleBoundry");
                Message newMessage = new Message("getPrincipleNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        extraTimePrincipleController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(extraTimePrincipleController);
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
                EventBus.getDefault().unregister(extraTimePrincipleController);
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
                EventBus.getDefault().unregister(extraTimePrincipleController);
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
                EventBus.getDefault().unregister(extraTimePrincipleController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    public void initialize()
    {
        extraTimePrincipleController= new ExtraTimePrincipleController(this);
        this.setExtraTimePrincipleController(extraTimePrincipleController);

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
    public TableColumn<ExtraTime, Button> getPressCol() {
        return pressCol;
    }

    public TableColumn<ExtraTime, Integer> getExamIDCol() {
        return examIDCol;
    }

    public TableColumn<ExtraTime, String> getCourseCol() {
        return courseCol;
    }

    public TableColumn<ExtraTime, String> getTeacherCol() {
        return teacherCol;
    }

    public TableView<ExtraTime> getTable() {
        return table;
    }

    public void setCourseCol(TableColumn<ExtraTime, String> courseCol) {
        this.courseCol = courseCol;
    }

    public void setTable(TableView<ExtraTime> table) {
        this.table = table;
    }

    public void setExamIDCol(TableColumn<ExtraTime, Integer> examIDCol) {
        this.examIDCol = examIDCol;
    }

    public void setPressCol(TableColumn<ExtraTime, Button> pressCol) {
        this.pressCol = pressCol;
    }

    public void setTeacherCol(TableColumn<ExtraTime, String> teacherCol) {
        this.teacherCol = teacherCol;
    }

    public void setExtraTimePrincipleController(ExtraTimePrincipleController extraTimePrincipleController) {
        this.extraTimePrincipleController = extraTimePrincipleController;
    }

}
 