package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class TeacherBoundry {

    @FXML
    private Button createAnExamBtn;

    @FXML
    private Button createAnQuestionBtn;

    @FXML
    private Button EditExamsBtn;

    @FXML
    private Button EditQuestionsBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button aquireExamBtn;

    @FXML
    private Button sendExamsToStudentsBtn;

    @FXML
    private Text teacherName;

    @FXML
    private Button approveExamBtn;

    @FXML
    private Button seeResultsBtn;


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
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimeTeacher");
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
    }
}
