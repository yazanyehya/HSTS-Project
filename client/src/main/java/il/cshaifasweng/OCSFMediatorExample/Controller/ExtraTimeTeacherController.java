package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;


import java.io.IOException;
import java.util.List;

public class ExtraTimeTeacherController
{
    private ExtraTimeTeacherBoundry extraTimeBoundry;
    private boolean isLogoutDialogShown = false;

    public ExtraTimeTeacherController(ExtraTimeTeacherBoundry extraTimeBoundry)
    {
        EventBus.getDefault().register(this);
        this.extraTimeBoundry = extraTimeBoundry;
    }


    @Subscribe
    public void handleExtraTimeEvent(ExtraTimeEvent extraTimeEvent)
    {

        if (extraTimeEvent.getMessage().getTitle().equals("GetOnGoingExamsForExtraTime"))
        {
            List<ReadyExam> list = (List<ReadyExam>) extraTimeEvent.getMessage().getBody();
            System.out.println("ahmaddd33");
            Platform.runLater(()->{


                extraTimeBoundry.getExamIdCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("readyExamOriginalID"));
                extraTimeBoundry.getNumberOfExaminees().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("numOfOnGoingExams"));
                extraTimeBoundry.getCourseCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, String>("course"));
                extraTimeBoundry.getPressForExtraTimeCol().setCellFactory(column -> new ExtraTimeTeacherController.ButtonCell());
                extraTimeBoundry.getStatusCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, String>("extraTimeApproved"));
                extraTimeBoundry.getTable().getItems().addAll(list);
            });
        }
        else if("AskPrincipleForExtraTime".equals(extraTimeEvent.getMessage().getTitle()))
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Request has been sent");

            });
        }
        else if("refreshTable".equals(extraTimeEvent.getMessage().getTitle()))
        {
            List<ReadyExam> list = (List<ReadyExam>) extraTimeEvent.getMessage().getBody();
            extraTimeBoundry.getTable().getItems().clear();
            extraTimeBoundry.getTable().getItems().addAll(list);
            extraTimeBoundry.getTable().refresh();
        }
        else if(extraTimeEvent.getMessage().getTitle().equals("DenyExtraTimeRequest"))
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.WARNING, "Notice", "Extra time request has been denied");

            });
        }
        else if (extraTimeEvent.getMessage().getTitle().equals("ApproveExtraTimeRequest"))
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.WARNING, "Notice", "Extra time request has been apporved");

            });
        }
    }
    private class ButtonCell extends TableCell<ReadyExam, Button> {
        private final Button button = new Button("Ask For Extra Time");

        ButtonCell() {
            // Set button action here, e.g., open the preview for the selected ReadyExam
            button.setOnAction(event -> {
                ReadyExam readyExam = getTableView().getItems().get(getIndex());
                openExtraTimeDialog(readyExam);
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
    private void openExtraTimeDialog(ReadyExam readyExam) {
        // Create a new Stage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Enter Extra Time Details");

        // Create the necessary UI components for entering extra time details
        // For example, you can use TextFields for the extra time amount and explanation
        TextField extraTimeAmountField = new TextField();
        extraTimeAmountField.promptTextProperty().setValue("Enter Amount of Extra Time");
        TextArea explanationArea = new TextArea();
        explanationArea.promptTextProperty().setValue("Enter Explanation");

        // Create a "Send" button to send the details back
        Button sendButton = new Button("Send");
        sendButton.setOnAction(event -> {
            // Get the entered extra time amount and explanation from the TextFields
            String extraTimeAmount = extraTimeAmountField.getText();
            String explanation = explanationArea.getText();

            // Do something with the entered details (e.g., send them back to the server)
            // ...
            Object object = new Object[]{extraTimeAmount, explanation, readyExam};
            Message message = new Message("AskPrincipleForExtraTime", object);
            try {
                SimpleClient.getClient().sendToServer(message);
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
        GridPane layout = new GridPane();
        layout.setHgap(10);
        layout.setVgap(10);
        layout.setPadding(new Insets(10));

        // Add the UI components to the layout
        layout.add(new Label("Extra Time Amount:"), 0, 0);
        layout.add(extraTimeAmountField, 1, 0);
        layout.add(new Label("Explanation:"), 0, 1);
        layout.add(explanationArea, 1, 1);
        layout.add(sendButton, 1, 2);
        Scene scene = new Scene( layout,400, 200);
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
    public void logOut() throws IOException {
        Message msg = new Message("LogoutET", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }



    @Subscribe
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("LogoutET")) {
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

}

