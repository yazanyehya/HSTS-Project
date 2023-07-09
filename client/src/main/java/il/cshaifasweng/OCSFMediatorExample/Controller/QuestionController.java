package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class QuestionController
{
    private QuestionBoundry questionBoundry;


    public QuestionController(QuestionBoundry questionBoundry)
    {
        EventBus.getDefault().register(this);
        this.questionBoundry = questionBoundry;
    }
    public QuestionBoundry getQuestionBoundry() {
        return questionBoundry;
    }

    public void createQuestion(Question question)
    {
        Message msg = new Message("createQuestion", question);
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pressBack() throws IOException
    {
        Message message = new Message("pressBack", null);
        SimpleClient.getClient().sendToServer(message);
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

    @Subscribe
    public  void handlePressBack(PressBackEvent pressBackEvent)
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleEvent(QuestionEvent event)
    {
        if (event.getMessage().getBody() != null)
        {
            if (event.getMessage().getTitle().equals("createQuestion"))
            {
                Platform.runLater(() -> {
                    // Login success
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Question Added Successfully");
                    //EventBus.getDefault().unregister(this);
                });

            }
        }
        else {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "One of the Field is not assigned");
                //EventBus.getDefault().unregister(this);
            });
        }
    }
}
