package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.EditSelectedQuestionBoundry;
import il.cshaifasweng.OCSFMediatorExample.client.EditSelectedQuestionEvent;
import il.cshaifasweng.OCSFMediatorExample.client.SaveEditedQuestionEvent;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;

public class EditSelectedQuestionController
{
    EditSelectedQuestionBoundry editSelectedQuestionBoundry;

    public EditSelectedQuestionController(EditSelectedQuestionBoundry editSelectedQuestionBoundry)
    {
        EventBus.getDefault().register(this);
        this.editSelectedQuestionBoundry = editSelectedQuestionBoundry;
    }
    @Subscribe
    public void handleSelectedQuestion(EditSelectedQuestionEvent editSelectedQuestionEvent)
    {
        Question question = (Question)editSelectedQuestionEvent.getMessage().getBody();
        editSelectedQuestionBoundry.getQuestionTextTXT().setText(question.getQText());
        editSelectedQuestionBoundry.getAnswerA().setText(question.getAnswer1());
        editSelectedQuestionBoundry.getAnswerB().setText(question.getAnswer2());
        editSelectedQuestionBoundry.getAnswerC().setText(question.getAnswer3());
        editSelectedQuestionBoundry.getAnswerD().setText(question.getAnswer4());

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
    public void handleSaveEditedQuestion(SaveEditedQuestionEvent saveEditedQuestionEvent)
    {
        if (saveEditedQuestionEvent.getMessage().getBody() != null)
        {

            Platform.runLater(() -> {
                // Login success
                showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Question Edited and Saved Successfully");
                //EventBus.getDefault().unregister(this);
            });

        }
        else
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "One of the Field is not assigned");
                //EventBus.getDefault().unregister(this);
            });
        }
    }
    public void saveQuestion(Question question) throws IOException {
        Message message = new Message("saveEditedQuestion", question);
        SimpleClient.getClient().sendToServer(message);
    }
}
