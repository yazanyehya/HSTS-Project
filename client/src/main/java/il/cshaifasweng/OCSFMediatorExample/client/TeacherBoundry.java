package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.server.LoginController;
import il.cshaifasweng.OCSFMediatorExample.server.TeacherController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class TeacherBoundry {

    @FXML
    private Button createAnExamBtn;

    @FXML
    private Button createAnQuestionBtn;

    @FXML
    private Button viewAllExamsBtn;

    @FXML
    private Button viewAllQuestionsBtn;

    @FXML
    private Button logoutBtn;

    private TeacherController teacherController;

    @FXML
    void logoutAction(ActionEvent event) throws IOException
    {
        teacherController.logOut();
    }

    @FXML
    void createAnExamAction(ActionEvent event) throws IOException
    {
        teacherController.changeToExamBoundry();
    }

    @FXML
    void createAnQuestionAction(ActionEvent event) throws IOException
    {
        teacherController.changeToQuestionBoundry();
    }

    @FXML
    void viewAllExamsAction(ActionEvent event) throws IOException
    {
    }

    @FXML
    void viewAllQuestionsAction(ActionEvent event)
    {

    }

    public void setTeacherController(TeacherController teacherController) {
        this.teacherController = teacherController;
    }

    @FXML
    public void initialize()
    {
        teacherController = new TeacherController(this);
        this.setTeacherController(teacherController);
    }
}
