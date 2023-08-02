package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.Objects;

public class ConductAnExamController
{

    private ConductAnExamBoundry conductAnExamBoundry;

    public ConductAnExamController(ConductAnExamBoundry conductAnExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.conductAnExamBoundry = conductAnExamBoundry;
    }

    @Subscribe
    public void handleShowExamFailed(StartExamFailedEvent startExamFailedEvent)
    {
        Platform.runLater(() -> {
            // Login failure
            Object[] objects = (Object[]) startExamFailedEvent.getMessage().getBody();
            String info = (String) objects[0];
            if (Objects.equals(info, "1"))
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "No available exam for this code");
            }
            else if(Objects.equals(info, "2"))
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Your ID is incorrect");
            }
            else
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Your ID is incorrect and there is no available exam for this code");
            }
            //EventBus.getDefault().unregister(this);
        });
    }
    @Subscribe
    public void handleShowExam(ChangeToStartExamEvent changeToStartExamEvent) throws IOException {
        ReadyExam readyExam = (ReadyExam)changeToStartExamEvent.getMessage().getBody();

        Student student = (Student) SimpleClient.getClient().getUser();
        Object object = new Object[]{readyExam, student};
        Message resMessage = new Message("startExam", object);
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(this);
                if (readyExam.getExamType().equals("Manual"))
                {
                    resMessage.setTitle("StartSolvingManualExam");
                    SimpleClient.getClient().sendToServer(resMessage);
                    SimpleChatClient.switchScreen("StartSolvingManualExam");


                }
                else
                {
                    resMessage.setTitle("StartSolvingComputerizedExam");
                    SimpleClient.getClient().sendToServer(resMessage);
                    SimpleChatClient.switchScreen("StartSolvingComputerizedExam");
                }
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
