package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.ExtraTimeEvent;
import il.cshaifasweng.OCSFMediatorExample.client.ExtraTimePrincipleBoundry;
import il.cshaifasweng.OCSFMediatorExample.client.ExtraTimePrincipleEvent;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.ExtraTime;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class ExtraTimePrincipleController
{
    private ExtraTimePrincipleBoundry extraTimePrincipleBoundry;

    public ExtraTimePrincipleController(ExtraTimePrincipleBoundry extraTimePrincipleBoundry)
    {
        EventBus.getDefault().register(this);
        this.extraTimePrincipleBoundry = extraTimePrincipleBoundry;
    }

    @Subscribe
    public void handleEvents(ExtraTimePrincipleEvent extraTimePrincipleEvent)
    {
        if (extraTimePrincipleEvent.getMessage().getTitle().equals("ExtraTimePrinciple"))
        {
            List<ExtraTime> list = (List<ExtraTime>)extraTimePrincipleEvent.getMessage().getBody();
            extraTimePrincipleBoundry.getExamIDCol().setCellValueFactory(new PropertyValueFactory<ExtraTime, Integer>("exam_id"));
            extraTimePrincipleBoundry.getTeacherCol().setCellValueFactory(new PropertyValueFactory<ExtraTime, String>("teacherId"));
            extraTimePrincipleBoundry.getCourseCol().setCellValueFactory(new PropertyValueFactory<ExtraTime, String>("course"));
            extraTimePrincipleBoundry.getPressCol().setCellFactory(column -> new ExtraTimePrincipleController.ButtonCell());

            extraTimePrincipleBoundry.getTable().getItems().addAll(list);
        }
    }
    @Subscribe
    public void handleExtraTime(ExtraTimeEvent extraTimeEvent)
    {
        if("refreshTablePrinciple".equals(extraTimeEvent.getMessage().getTitle()))
        {
            List<ExtraTime> list = (List<ExtraTime>) extraTimeEvent.getMessage().getBody();
            extraTimePrincipleBoundry.getTable().getItems().clear();
            extraTimePrincipleBoundry.getTable().getItems().addAll(list);
            extraTimePrincipleBoundry.getTable().refresh();
        }
    }
    private class ButtonCell extends TableCell<ExtraTime, Button> {
        private final Button button = new Button("Ask For Extra Time");

        ButtonCell() {
            // Set button action here, e.g., open the preview for the selected ReadyExam
            button.setOnAction(event -> {
                ExtraTime extraTime = getTableView().getItems().get(getIndex());
                openExtraTimeDialog(extraTime);
                // Call a method in your controller to handle the preview action

            });
        }

        @Override
        protected void updateItem(Button item, boolean empty) {
            super.updateItem(item, empty);
            if (empty) {
                setGraphic(null);
            } else {
                setGraphic(button);
            }
        }
    }
    private void openExtraTimeDialog(ExtraTime extraTime) {
        // Create a new Stage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Enter Extra Time Details");

        // Create the necessary UI components for entering extra time details
        // For example, you can use TextFields for the extra time amount and explanation
        TextField extraTimeAmountField = new TextField();
        extraTimeAmountField.setText(extraTime.getTimeAmount());
        Label timeLabel = new Label("Extra time Amount:");
        Label explanationLabel = new Label("Explanation:");
        TextArea explanationArea = new TextArea();
        explanationArea.setText(extraTime.getExplanation());
        Button approveBtn = new Button("Approve Request");
        Button denyBtn = new Button(("Deny Request"));
        Region region = new Region();
        Region region1 = new Region();
        Region region2 = new Region();
        HBox.setHgrow(region, Priority.ALWAYS);
        VBox.setVgrow(region1,Priority.ALWAYS);
        VBox.setVgrow(region2,Priority.ALWAYS);
        HBox hBox = new HBox(approveBtn,region,denyBtn);
        VBox vBox = new VBox(timeLabel, extraTimeAmountField,region1,explanationLabel,explanationArea, region2, hBox );

        // Create a "Send" button to send the details back
        approveBtn.setOnAction(event -> {
            Message responseMessage = new Message("ApproveExtraTimeRequest", extraTime);
            try {
                SimpleClient.getClient().sendToServer(responseMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Close the dialog stage
            dialogStage.close();
        });
        denyBtn.setOnAction(event -> {
            Message responseMessage = new Message("DenyExtraTimeRequest", extraTime);
            try {
                SimpleClient.getClient().sendToServer(responseMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
            // Close the dialog stage
            dialogStage.close();
        });

        // Create a layout for the dialog stage (e.g., VBox or GridPane)
        // Add the UI components to the layout
        // ...

        // Create a scene and set it to the dialog stage

        Scene scene = new Scene( vBox,400, 400);
        dialogStage.setScene(scene);

        // Show the dialog stage
        dialogStage.show();
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
