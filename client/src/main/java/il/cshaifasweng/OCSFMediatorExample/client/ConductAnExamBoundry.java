package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ConductAnExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ConductAnExamBoundry {

    @FXML
    private TextField executionCode;

    @FXML
    private TextField id;

    @FXML
    private Button startExamBtn;

    @FXML
    private Button backBtn;

    private ConductAnExamController conductAnExamController;

    @FXML
    void executionCodeAction(ActionEvent event)
    {

    }
    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(conductAnExamController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("studentBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void idAction(ActionEvent event) {

    }
    @FXML
    void initialize()
    {
        conductAnExamController = new ConductAnExamController(this);
        this.setConductAnExamController(conductAnExamController);
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
