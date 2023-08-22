
/**
 * Sample Skeleton for 'principleBoundry.fxml' Controller Class
 */

        package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.PrincipleController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;



import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PrincipleBoundry {
    @FXML
    private ImageView logo;
    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;
    @FXML
    private Button homeBtn;

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader
    @FXML // fx:id="courseReportsBtn"
    private Button courseReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="studentReportsBtn"
    private Button studentReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="teacherReportsBtn"
    private Button teacherReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsBtn"
    private Button viewExamsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesBtn"
    private Button viewGradesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionsBtn"
    private Button viewQuestionsBtn; // Value injected by FXMLLoader

    @FXML
    private Button extraTimeBtn;

    @FXML
    private ImageView bellArrow;

    @FXML
    private Button bellBtn;
    @FXML
    private Text principleName;
    @FXML
    private ListView<Notification> notificationList;

    private boolean pressBell = false;
    private  Label notificationCountLabel = new Label("0");

    private PrincipleController principleController;

    @FXML
    void bellAction(ActionEvent event) {
        if (!pressBell)
        {
            notificationList.setVisible(true);
            bellArrow.setVisible(true);
            pressBell = true;
        }
        else
        {
            notificationList.setVisible(false);
            bellArrow.setVisible(false);
            pressBell = false;
        }
    }
    @FXML
    void homeBtnAction(ActionEvent event)
    {
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in Home Page");
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        principleController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(principleController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimePrinciple");
                Message message = new Message("ExtraTimePrinciple", null);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void viewExamsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewExamsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewGradesBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setPrincipleController(PrincipleController principleController) {
        this.principleController = principleController;
    }

    @FXML
    private Button notificationBtn;


    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(principleController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("PrincipleNotifications");
                Message message = new Message("getNotificationForPrinciple", SimpleClient.getClient().getUser());
                try {
                    SimpleClient.getClient().sendToServer(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void initialize()
    {
        principleController = new PrincipleController(this);
        this.setPrincipleController(principleController);
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
        Image bellIconImage = new Image(getClass().getResourceAsStream("/images/icon_bell.png")); // Replace with your icon's path
        ImageView bellIconImageView = new ImageView(bellIconImage);
        bellIconImageView.setFitWidth(20); // Adjust the size as needed
        bellIconImageView.setFitHeight(20);
        //notificationCountLabel = new Label("1"); // You can update this label text based on the actual count
        notificationList.setVisible(false);
        bellArrow.setVisible(false);
// Set up the button layout (icon + label)
        HBox buttonLayout = new HBox(bellIconImageView, notificationCountLabel);

        bellBtn.setGraphic(buttonLayout);
        notificationList.setCellFactory(listView -> new PrincipleBoundry.NotificationCell());

        Button markAsReadButton = new Button("Mark as Read");
        markAsReadButton.setOnAction(e -> {
            try {
                markSelectedNotificationAsRead(notificationList.getSelectionModel().getSelectedItem());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //layout.getChildren().addAll();


        User user = SimpleClient.getClient().getUser();
        principleName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
//        Image logoImage= new Image(getClass().getResourceAsStream("/images/finallogop.png"));
//        logo.setImage(logoImage);        animationTimer = new AnimationTimer() {
//            @Override
//            public void handle(long now) {
//                updateDateTime();
//            }
//        };
//        animationTimer.start();
    }
//    private void updateDateTime() {
//        // Get the current date and time
//        LocalDateTime currentDateTime = LocalDateTime.now();
//
//
//
//        // Format the date and time as desired (change the pattern as needed)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
//                "HH:mm:ss");
//        String dateTimeString = currentDateTime.format(formatter);
//
//
//
//        // Update the label text
//        timeLabelp.setText(dateTimeString);
//    }
//
//
//
//    // Override the stop method to stop the AnimationTimer when the application exits
//    public void stop() {
//        animationTimer.stop();
//    }
private void updateDateTime() {
    // Get the current date and time
    LocalDateTime currentDateTime = LocalDateTime.now();



    // Format the date and time as desired (change the pattern as needed)
    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
            "HH:mm:ss");
    String dateTimeString = currentDateTime.format(formatter);



    // Update the label text
    timeLabel.setText(dateTimeString);
}
    class NotificationCell extends ListCell<Notification> {
        @Override
        protected void updateItem(Notification item, boolean empty) {
            super.updateItem(item, empty);

            if (empty || item == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox cellLayout = new HBox();
                //cellLayout.setStyle("-fx-background-radius: 40; -fx-border-color:  #000000; -fx-border-radius: 40; -fx-border-width: 3; -fx-background-color:  #ffa500;");
                Button markAsReadButton = new Button("Mark as Read");
                markAsReadButton.setStyle("    -fx-background-color:  #273be2;\n" +
                        "    -fx-background-radius: 30 30 30 30;\n" +
                        "    -fx-border-radius: 30 30 30 30;\n" +
                        "    -fx-border-color: #000000;\n" +
                        "    -fx-border-width: 2px 2px 2px 2px;\n" +
                        "    -fx-effect: dropshadow(gaussian, #273be2, 6, 0.5, 0, 0);");
                markAsReadButton.setOnAction(e -> {
                    try {
                        markNotificationAsRead(item);
                    } catch (IOException ex) {
                        ex.printStackTrace();
                    }
                });

                // Display the notification message
                setText(item.getMessage());
                setStyle("-fx-background-color: transparent;");

                // Add the "Mark as Read" button to the cell layout
                cellLayout.getChildren().addAll(markAsReadButton);
                setGraphic(cellLayout);
            }
        }

        private void markNotificationAsRead(Notification notification) throws IOException {
            notification.setRead(true);
            updateItem(notification, false);
            markSelectedNotificationAsRead(notification);
            // Update the database or perform any necessary action
        }
    }
    @FXML
    private void markSelectedNotificationAsRead(Notification selectedNotification) throws IOException {
        if (selectedNotification != null) {
            // Perform the logic to mark the selected notification as read
            selectedNotification.setRead(true);
            int  count = Integer.parseInt(notificationCountLabel.getText());
            count--;
            notificationCountLabel.setText(Integer.toString(count));

            // Update the display or perform any necessary actions
            // For example, you could refresh the notification list
            Object object = new Object[]{SimpleClient.getClient().getUser(), selectedNotification};
            Message message = new Message("setToReadPrinciple", object);
            SimpleClient.getClient().sendToServer(message);

        }
    }
    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getTeachersForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("courseReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getCoursesForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("studentReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getStudentsForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
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

    public ListView<Notification> getNotificationList() {
        return notificationList;
    }

    public Label getNotificationCountLabel() {
        return notificationCountLabel;
    }

}