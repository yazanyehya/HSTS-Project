package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.ExtraTime;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
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
    private boolean isLogoutDialogShown = false;
    public void logOut() throws IOException {
        Message msg = new Message("Logout principle", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void handleLogoutEvent(PrincipleLogoutEvent principleLogoutEvent) {
        System.out.println("logout platform");

        if (principleLogoutEvent.getMessage().getTitle().equals("Logout principle")) {
            System.out.println("LOAI");
            if (!isLogoutDialogShown) {
                isLogoutDialogShown = true;

                Platform.runLater(() -> {
                    // Show the dialog
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "You Logged out");
                    isLogoutDialogShown = false;
                });
            }

            // Unregister this class from the EventBus
            EventBus.getDefault().unregister(this);

            try {
                Platform.runLater(() -> {
                    try {
                        SimpleChatClient.switchScreen("LoginController");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
    @Subscribe
    public void handleEvents(ExtraTimePrincipleEvent extraTimePrincipleEvent)
    {
        Platform.runLater(()->
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
        });
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
    @Subscribe
    public void handllle(PrincipleEvent principleEvent)
    {
        Platform.runLater(()->{
            Object[] objects = (Object[]) principleEvent.getMessage().getBody();
            List<Notification> list = (List<Notification>) objects[0];
            int id = (Integer)objects[1];
            if (id == SimpleClient.getClient().getUser().getId())
            {
                showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go and check the home page");
            }
        });
    }
}
 