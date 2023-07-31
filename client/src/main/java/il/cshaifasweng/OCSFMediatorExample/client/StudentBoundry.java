/**
 * Sample Skeleton for 'studentBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class StudentBoundry {

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
        User user = SimpleClient.getClient().getUser();
        studentName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }
}
