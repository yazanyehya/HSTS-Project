package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

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
    void EditExamsAction(ActionEvent event) throws IOException
    {
        teacherController.changeToEditExamBoundry();
    }

    @FXML
    void EditQuestionsAction(ActionEvent event) throws IOException
    {
        teacherController.changeToEditQuestionBoundry();
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
