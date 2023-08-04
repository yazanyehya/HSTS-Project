package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.ExtraTime;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.util.Duration;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StartSolvingComputerizedExamController
{
    private StartSolvingComputerizedExamBoundry startSolvingExamBoundry;

    int seconds = 0;

    int minutes;

    int hours;

    String examID;

    private Timeline timeline;

    public StartSolvingComputerizedExamController(StartSolvingComputerizedExamBoundry startSolvingExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.startSolvingExamBoundry = startSolvingExamBoundry;
    }
    private VBox createAnswerToggleGroup(Question question) {
        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton answer1RadioButton = new RadioButton("a. " + question.getAnswer1());
        answer1RadioButton.setToggleGroup(toggleGroup);
        //answer1RadioButton.setStyle(getClass().getResource());

        RadioButton answer2RadioButton = new RadioButton("b. " + question.getAnswer2());
        answer2RadioButton.setToggleGroup(toggleGroup);

        RadioButton answer3RadioButton = new RadioButton("c. " + question.getAnswer3());
        answer3RadioButton.setToggleGroup(toggleGroup);

        RadioButton answer4RadioButton = new RadioButton("d. " + question.getAnswer4());
        answer4RadioButton.setToggleGroup(toggleGroup);

        // Add listeners to capture selected answers
        answer1RadioButton.setOnAction(e -> startSolvingExamBoundry.getSelectedAnswersMap().put(question, answer1RadioButton));
        answer2RadioButton.setOnAction(e -> startSolvingExamBoundry.getSelectedAnswersMap().put(question, answer2RadioButton));
        answer3RadioButton.setOnAction(e -> startSolvingExamBoundry.getSelectedAnswersMap().put(question, answer3RadioButton));
        answer4RadioButton.setOnAction(e -> startSolvingExamBoundry.getSelectedAnswersMap().put(question, answer4RadioButton));

        VBox vBox = new VBox();
        vBox.getChildren().addAll(answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton);

        return vBox;
    }

    public void finish() throws IOException {
        Map<Question, String> map = new HashMap<>();
        for (Map.Entry<Question, RadioButton> entry : startSolvingExamBoundry.getSelectedAnswersMap().entrySet()) {
            Question question = entry.getKey();
            RadioButton selectedAnswerRadioButton = entry.getValue();

            if (selectedAnswerRadioButton != null) {
                String selectedAnswer = selectedAnswerRadioButton.getText();
                // Now you have the question and the selected answer for each question.
                // You can store this information or use it as needed.
                System.out.println("Question: " + question.getQText());
                System.out.println("Selected Answer: " + selectedAnswer);
                map.put(question, selectedAnswer);
            } else {
                // The examinee has not selected an answer for this question
                map.put(question, "");
                System.out.println("Question: " + question.getQText());
                System.out.println("Selected Answer: None");
            }
        }

        Object object = new Object[]{map, startSolvingExamBoundry.getExamID()};
        Message message = new Message("finishExam", object);
        SimpleClient.getClient().sendToServer(message);

        Message message2 = new Message("SetOnGoingToFalse", examID);
        SimpleClient.getClient().sendToServer(message2);
    }

    public void update(int extraTimeInMinutes) {
        // Convert extra time to seconds
        int extraTimeInSeconds = extraTimeInMinutes * 60;

        // Calculate the total time in seconds for the current countdown
        int totalSecondsRemaining = (hours * 3600) + (minutes * 60) + seconds;

        // Add the extra time in seconds
        totalSecondsRemaining += extraTimeInSeconds;

        // Update hours, minutes, and seconds accordingly
        hours = totalSecondsRemaining / 3600;
        totalSecondsRemaining %= 3600;
        minutes = totalSecondsRemaining / 60;
        seconds = totalSecondsRemaining % 60;

        // Stop the timeline and update the timer label with the new value
        timeline.stop();
        startSolvingExamBoundry.getTimerLabel().setText(String.format("     %02d:%02d:%02d", hours, minutes, seconds));

        // Restart the timeline to continue the countdown with the new remaining time
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    @Subscribe
    public void handleExtraTime(ExtraTimeEvent extraTimeEvent)
    {
        if (extraTimeEvent.getMessage().getTitle().equals("ApproveExtraTimeRequest"))
        {
            ExtraTime extraTime = (ExtraTime) extraTimeEvent.getMessage().getBody();
            int time = Integer.parseInt(extraTime.getTimeAmount());
            Platform.runLater(()->{
                update(time);
                showAlertDialog(Alert.AlertType.WARNING, "Notice", "There have been added " + time + " more minutes");

            });
        }
    }
    @Subscribe
    public void handleStartExam(StartExamEvent startExamEvent) throws IOException {

        System.out.println("here");
        ReadyExam readyExam = (ReadyExam) startExamEvent.getMessage().getBody();
        Message message = new Message("SetOnGoingToTrue", readyExam);
        SimpleClient.getClient().sendToServer(message);

        examID = Integer.toString(readyExam.getId());
        int extra = 0;
        if (readyExam.getExtraTimeApproved().equals("Approved"))
        {
            System.out.println("lollll");
            extra = readyExam.getAddedTime();
            System.out.println(extra);
        }
        startSolvingExamBoundry.setExamPeriod(Integer.parseInt(readyExam.getExam().getExamPeriod()) + extra);
        startSolvingExamBoundry.setExamID(Integer.toString(readyExam.getId()));

        if (readyExam == null)
        {
            System.out.println("is null");
        }
        Platform.runLater(() -> {
            HBox hBox = new HBox();
            VBox examDetails = new VBox();
            AnchorPane anchorPane1 = new AnchorPane();
            AnchorPane anchorPane2 = new AnchorPane();
            BorderPane borderPane = new BorderPane();
            Image logo = new Image(getClass().getResourceAsStream("/images/student_logo.png"));
            ImageView imageViewLogo = new ImageView(logo);
            imageViewLogo.setFitWidth(300); // Set the width
            imageViewLogo.setFitHeight(300); // Set the height
            imageViewLogo.setLayoutX(300);
            imageViewLogo.setLayoutY(50);
            Font font = new Font("American Typewriter", 24);

            int time = startSolvingExamBoundry.getExamPeriod();
            hours = time/ 60;
            minutes = time % 60;
            startSolvingExamBoundry.getTimerLabel().setText(String.format("      %02d:%02d:%02d", hours ,minutes, seconds));

             timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (minutes == 0 && seconds == 0 && hours == 0)
                {
                    if (Objects.equals(startSolvingExamBoundry.getFinished(), "no"))
                    {
                        Platform.runLater(() -> {

                            showAlertDialog(Alert.AlertType.ERROR, "Error", "There is no longer time left to solve the exam");
                            try {
                                startSolvingExamBoundry.getExamContainer().getChildren().clear();
                                EventBus.getDefault().unregister(this);
                                SimpleChatClient.switchScreen("ConductAnExam");
                                Message message2 = new Message("SetOnGoingToFalse", Integer.toString(readyExam.getId()));
                                SimpleClient.getClient().sendToServer(message2);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
                        try {
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }

                }

                else {
                    // Update the timer
                    if (seconds == 0) {
                        if (minutes == 0) {
                            hours--;
                            minutes = 59;
                            seconds = 59;
                        } else {
                            minutes--;
                            seconds = 59;
                        }
                    } else {
                        seconds--;
                    }
                }
                // Update the timer label with the new value
                startSolvingExamBoundry.getTimerLabel().setText(String.format("      %02d:%02d:%02d", hours ,minutes, seconds));
            }));
            //Label HighSchoolNameLabel = new Label("High School Test System");
            Label courseLabel = new Label("Exam in "+ readyExam.getCourse() + " course, " + readyExam.getExam().getSubject().getName());
            //HighSchoolNameLabel.setFont(font);
            //HighSchoolNameLabel.setStyle("-fx-text-fill: #87CEFA;-fx-underline: true;");

            courseLabel.setFont(font);
            courseLabel.setStyle("-fx-text-fill: #ffa500;-fx-underline: true;");
            examDetails.getChildren().addAll(imageViewLogo, courseLabel);
            examDetails.setAlignment(Pos.CENTER);
            borderPane.setCenter(examDetails);
            borderPane.setTop(startSolvingExamBoundry.getTimerLabel());

            VBox studentDetails = new VBox();

            Label studentName = new Label("Student name: " + SimpleClient.getClient().getUser().getFirstName() + " "+ SimpleClient.getClient().getUser().getLastName() + ".");
            Label studentId = new Label("Student ID: " + SimpleClient.getClient().getUser().getId());
            studentName.setFont(font);
            studentId.setFont(font);

            studentName.setStyle("-fx-font-weight: bold;-fx-underline: true; -fx-text-fill: #ffa500;");
            studentId.setStyle("-fx-font-weight: bold;-fx-underline: true; -fx-text-fill: #ffa500;");
            studentDetails.getChildren().addAll(studentName, studentId);

            VBox questions = new VBox();
            for (Question q : readyExam.getExam().getListOfQuestions()) {
                System.out.println("alohaa");
                // Creating a question
                Label Qtext = new Label(q.getQText() + "(" + q.getScore() + " Points):");
                Qtext.setFont(font);

                // Create a ToggleGroup and get the VBox containing the RadioButtons
                VBox vBox1 = createAnswerToggleGroup(q);

                // Add the question label and RadioButtons VBox to the main VBox
                vBox1.getChildren().add(0, Qtext); // Add the question label as the first child
                Region region9 = new Region();
                region9.setMinHeight(10); // Example: Set a minimum height
                region9.setPrefHeight(20); // Example: Set a preferred height
                region9.setMaxHeight(25);

                VBox.setVgrow(region9, Priority.ALWAYS);
                vBox1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #e9692c;");
                questions.getChildren().addAll(vBox1,region9);
                startSolvingExamBoundry.getSelectedAnswersMap().put(q, startSolvingExamBoundry.getSelectedAnswersMap().get(q));
            }

            Region region1 = new Region();
            Region region2 = new Region();
            Region region3 = new Region();
            Region region4 = new Region();
            Region region5 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);
            HBox.setHgrow(region2, Priority.ALWAYS);
            hBox.getChildren().addAll(region1, borderPane, region2);
            region3.setMinHeight(10); // Example: Set a minimum height
            region3.setPrefHeight(40); // Example: Set a preferred height
            region3.setMaxHeight(40); // Example: Set a maximum height

            region4.setMinHeight(10); // Example: Set a minimum height
            region4.setPrefHeight(40); // Example: Set a preferred height
            region4.setMaxHeight(40); // Example: Set a maximum height

            region5.setMinHeight(10); // Example: Set a minimum height
            region5.setPrefHeight(40); // Example: Set a preferred height
            region5.setMaxHeight(40); // Example: Set a maximum height
            VBox.setVgrow(region3, Priority.ALWAYS);
            VBox.setVgrow(region4, Priority.ALWAYS);
            VBox.setVgrow(region5, Priority.ALWAYS);
            VBox vBox = new VBox(hBox, region5, studentDetails, region4, questions, region3,startSolvingExamBoundry.getFinishExamBtn());
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vBox);
            scrollPane.setFitToHeight(true);
            scrollPane.setFitToWidth(true);
            startSolvingExamBoundry.getExamContainer().getChildren().addAll(scrollPane);

            // Set the cycle count to indefinite so the timer continues running
            timeline.setCycleCount(Timeline.INDEFINITE);
            timeline.play();
        });
    }
    @Subscribe
    public void handleFinishExamEvent(FinishExamEvent finishExamEvent)
    {
        Platform.runLater(() -> {

            showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam has been submitted");
            try {
                EventBus.getDefault().unregister(this);
                SimpleChatClient.switchScreen("ConductAnExam");
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
