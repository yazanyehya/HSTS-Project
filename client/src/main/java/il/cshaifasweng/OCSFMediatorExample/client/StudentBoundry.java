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
    private Text studentName;

    @FXML
    private Text belIcon;

    @FXML
    private Button notificationBtn;

    @FXML
    private Button bellBtn;

    private StudentController studentController;

    private  Label notificationCountLabel;
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
    void notificationAction(ActionEvent event)
    {

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
        notificationCountLabel = new Label("1"); // You can update this label text based on the actual count
        notificationList.setVisible(false);
        bellArrow.setImage(new Image(getClass().getResourceAsStream("/images/arrow.png")));
        bellArrow.setVisible(false);
// Set up the button layout (icon + label)
        HBox buttonLayout = new HBox(bellIconImageView, notificationCountLabel);

        bellBtn.setGraphic(buttonLayout);

//        notificationList.setCellFactory(listView -> new NotificationCell());
//        Notification notification = new Notification("New Grade Has been published", LocalDateTime.now(), false);
//        ObservableList<Notification> notifications = FXCollections.observableArrayList(notification);
//// Populate the notifications list from your data source
//        notificationList.setItems(notifications);

        Button markAsReadButton = new Button("Mark as Read");
        markAsReadButton.setOnAction(e -> markSelectedNotificationAsRead(notificationList.getSelectionModel().getSelectedItem()));

        VBox layout = new VBox(notificationList, markAsReadButton);


        Image logoImage2= new  Image(getClass().getResourceAsStream("/images/orangeHSTS.png"));
        logostd.setImage(logoImage2);
        User user = SimpleClient.getClient().getUser();
        //studentName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
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
                Button markAsReadButton = new Button("Mark as Read");
                markAsReadButton.setOnAction(e -> markNotificationAsRead(item));

                // Display the notification message
                setText(item.getMessage());

                // Add the "Mark as Read" button to the cell layout
                cellLayout.getChildren().addAll(new Button("Mark as Read"), markAsReadButton);
                setGraphic(cellLayout);
            }
        }

        private void markNotificationAsRead(Notification notification) {
            notification.setRead(true);
            updateItem(notification, false);
            // Update the database or perform any necessary action
        }
    }
    @FXML
    private void markSelectedNotificationAsRead(Notification selectedNotification) {
        if (selectedNotification != null) {
            // Perform the logic to mark the selected notification as read
            selectedNotification.setRead(true);
            int  count = Integer.parseInt(notificationCountLabel.getText());
            count--;
            notificationCountLabel.setText(Integer.toString(count));

            // Update the display or perform any necessary actions
            // For example, you could refresh the notification list
            notificationList.refresh();
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

}
