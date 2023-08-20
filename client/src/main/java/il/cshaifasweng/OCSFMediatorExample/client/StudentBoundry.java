/**
 * Sample Skeleton for 'studentBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.animation.AnimationTimer;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentController;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentBoundry {

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;


    @FXML
    private ImageView logostd;

    @FXML // fx:id="ConductAnExamBTN"
    private Button ConductAnExamBTN; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesBTN"
    private Button viewGradesBTN; // Value injected by FXMLLoader

    @FXML
    private Button logoutBtn;

    @FXML
    private Label studentName;


    @FXML
    private Text belIcon;

    @FXML
    private Button notificationBtn;

    @FXML
    private Button bellBtn;

    private StudentController studentController;

    private  Label notificationCountLabel = new Label("0");
    @FXML
    private ImageView bellArrow;

    @FXML
    private VBox layout;

    @FXML
    private ListView<Notification> notificationList;

    private boolean pressBell = false;
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
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            studentController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Home Page");

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
    void notificationAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(studentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("StudentNotifications");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Message message = new Message("getNotificationForStudent", SimpleClient.getClient().getUser());
        SimpleClient.getClient().sendToServer(message);
    }

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
    public void initialize()
    {
        studentController = new StudentController(this);
        this.setStudentController(studentController);

        Image bellIconImage = new Image(getClass().getResourceAsStream("/images/icon_bell.png")); // Replace with your icon's path
        ImageView bellIconImageView = new ImageView(bellIconImage);
        bellIconImageView.setFitWidth(20); // Adjust the size as needed
        bellIconImageView.setFitHeight(20);
        //notificationCountLabel = new Label("1"); // You can update this label text based on the actual count
        notificationList.setVisible(false);
        bellArrow.setImage(new Image(getClass().getResourceAsStream("/images/arrow.png")));
        bellArrow.setVisible(false);
// Set up the button layout (icon + label)
        HBox buttonLayout = new HBox(bellIconImageView, notificationCountLabel);

        bellBtn.setGraphic(buttonLayout);

        notificationList.setCellFactory(listView -> new NotificationCell());

        Button markAsReadButton = new Button("Mark as Read");
        markAsReadButton.setOnAction(e -> {
            try {
                markSelectedNotificationAsRead(notificationList.getSelectionModel().getSelectedItem());
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });

        //layout.getChildren().addAll();

        Image logoImage2= new  Image(getClass().getResourceAsStream("/images/orangeHSTS.png"));
        logostd.setImage(logoImage2);
        User user = SimpleClient.getClient().getUser();
        studentName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
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
                markAsReadButton.setStyle("    -fx-background-color:  #e9692c;\n" +
                        "    -fx-background-radius: 30 30 30 30;\n" +
                        "    -fx-border-radius: 30 30 30 30;\n" +
                        "    -fx-border-color: #000000;\n" +
                        "    -fx-border-width: 2px 2px 2px 2px;\n" +
                        "    -fx-effect: dropshadow(gaussian, #e9692c, 6, 0.5, 0, 0);");
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
            Message message = new Message("setToRead", object);
            SimpleClient.getClient().sendToServer(message);

        }
    }
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



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }

    public ListView<Notification> getNotificationList() {
        return notificationList;
    }

    public Label getNotificationCountLabel() {
        return notificationCountLabel;
    }

    public void setNotificationCountLabel(Label notificationCountLabel) {
        this.notificationCountLabel = notificationCountLabel;
    }
}
