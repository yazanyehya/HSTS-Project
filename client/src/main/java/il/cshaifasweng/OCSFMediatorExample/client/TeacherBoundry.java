package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.TeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Notification;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TeacherBoundry {
    @FXML
    private ImageView approve;
    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    @FXML
    private Button approveExamBtn;

    @FXML
    private Button aquireExamBtn;

    @FXML
    private Button createExamBtn;

    @FXML
    private Button createQuestionBtn;

    @FXML
    private Button editExamBtn;

    @FXML
    private Button editQuestionBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private ImageView logo;


    @FXML
    private Button logoutBtn;


    @FXML
    private Button seeResultsBtn;

    @FXML
    private Button sendExamsToStudentsBtn;

    @FXML
    private Text teacherName;


    @FXML
    private ImageView bellArrow;

    @FXML
    private Button bellBtn;

    @FXML
    private ListView<Notification> notificationList;

    private TeacherController teacherController;
    private boolean pressBell = false;
    private  Label notificationCountLabel = new Label("0");

    @FXML
    private Button notificationBtn;
    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("TeacherNotifications");
                Message message = new Message("getNotificationForTeacher", SimpleClient.getClient().getUser());
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
    void logoutAction(ActionEvent event) throws IOException
    {
        teacherController.logOut();
    }

    @FXML
    void approveExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ApproveExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void createAnExamAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void sendExamsToStudentsAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("SendExamToStudentBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void createAnQuestionAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("QuestionBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void EditExamsAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void EditQuestionsAction(ActionEvent event) throws IOException
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setTeacherController(TeacherController teacherController)
    {
        this.teacherController = teacherController;
    }
    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            teacherController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Home Page");

        });
    }

    @FXML
    void aquireExamAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("AquireExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void extraTimeAction(ActionEvent event) {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimeTeacher");
                System.out.println("ahmadddggg");
                Message message = new Message("GetOnGoingExamsForExtraTime", SimpleClient.getClient().getUser().getUsername());
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void seeResultsAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(teacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForTeacher");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
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
        teacherController = new TeacherController(this);
        this.setTeacherController(teacherController);

        Image bellIconImage = new Image(getClass().getResourceAsStream("/images/icon_bell.png")); // Replace with your icon's path
        ImageView bellIconImageView = new ImageView(bellIconImage);
        bellIconImageView.setFitWidth(20); // Adjust the size as needed
        bellIconImageView.setFitHeight(20);
        //notificationCountLabel = new Label("1"); // You can update this label text based on the actual count
        notificationList.setVisible(false);
        bellArrow.setImage(new Image(getClass().getResourceAsStream("/images/blue_arrow.png")));
        bellArrow.setVisible(false);
// Set up the button layout (icon + label)
        HBox buttonLayout = new HBox(bellIconImageView, notificationCountLabel);

        bellBtn.setGraphic(buttonLayout);
        notificationList.setCellFactory(listView -> new TeacherBoundry.NotificationCell());

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
        teacherName.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
        Image logoImage = new  Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);
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
            Message message = new Message("setToReadTeacher", object);
            SimpleClient.getClient().sendToServer(message);

        }
    }

    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n" +
                " HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
    }

    public ListView<Notification> getNotificationList() {
        return notificationList;
    }

    public Label getNotificationCountLabel() {
        return notificationCountLabel;
    }
}

