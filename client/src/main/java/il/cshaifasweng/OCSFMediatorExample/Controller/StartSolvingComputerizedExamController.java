package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
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

    public StartSolvingComputerizedExamController(StartSolvingComputerizedExamBoundry startSolvingExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.startSolvingExamBoundry = startSolvingExamBoundry;
    }
    private VBox createAnswerToggleGroup(Question question) {
        ToggleGroup toggleGroup = new ToggleGroup();

        RadioButton answer1RadioButton = new RadioButton("a. " + question.getAnswer1());
        answer1RadioButton.setToggleGroup(toggleGroup);

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
    }

    @Subscribe
    public void handleStartExam(StartExamEvent startExamEvent)
    {

        System.out.println("here");
        ReadyExam readyExam = (ReadyExam) startExamEvent.getMessage().getBody();
        startSolvingExamBoundry.setExamPeriod(Integer.parseInt(readyExam.getExam().getExamPeriod()));
        startSolvingExamBoundry.setExamID(Integer.toString(readyExam.getId()));

        if (readyExam == null)
        {
            System.out.println("is null");
        }
        Platform.runLater(() -> {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            VBox examDetails = new VBox();
            AnchorPane anchorPane1 = new AnchorPane();
            AnchorPane anchorPane2 = new AnchorPane();
            BorderPane borderPane = new BorderPane();
            Image logo = new Image(getClass().getResourceAsStream("/images/logo.jpg"));
            ImageView imageViewLogo = new ImageView(logo);
            imageViewLogo.setFitWidth(150); // Set the width
            imageViewLogo.setFitHeight(150); // Set the height
            Font font = new Font("American Typewriter", 24);

            int time = Integer.parseInt(readyExam.getExam().getExamPeriod());
            hours = time/ 60;
            minutes = time % 60;
            startSolvingExamBoundry.getTimerLabel().setText(String.format("%02d:%02d:%02d", hours ,minutes, seconds));

            Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                if (minutes == 0 && seconds == 0)
                {
                    if (Objects.equals(startSolvingExamBoundry.getFinished(), "no"))
                    {
                        Platform.runLater(() -> {

                            showAlertDialog(Alert.AlertType.ERROR, "Error", "There is no longer time left to solve the exam");
                            try {
                                startSolvingExamBoundry.getExamContainer().getChildren().clear();
                                EventBus.getDefault().unregister(this);
                                SimpleChatClient.switchScreen("ConductAnExam");
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

                if (seconds == 0) {
                    minutes--;
                    seconds = 59;
                }
                else
                {
                    seconds--;
                }

                // Update the timer label with the new value
                startSolvingExamBoundry.getTimerLabel().setText(String.format("%02d%02d:%02d", hours ,minutes, seconds));
            }));
            //Label HighSchoolNameLabel = new Label("High School Test System");
            Label courseLabel = new Label("Exam in "+ readyExam.getCourse() + " course, " + readyExam.getExam().getSubject().getName());
            //HighSchoolNameLabel.setFont(font);
            //HighSchoolNameLabel.setStyle("-fx-text-fill: #87CEFA;-fx-underline: true;");

            courseLabel.setFont(font);
            courseLabel.setStyle("-fx-text-fill: #1E90FF;-fx-underline: true;");
            examDetails.getChildren().addAll(imageViewLogo, courseLabel);
            examDetails.setAlignment(Pos.CENTER);
            borderPane.setCenter(examDetails);
            borderPane.setTop(startSolvingExamBoundry.getTimerLabel());

            VBox studentDetails = new VBox();

            Label studentName = new Label("Student name: ");
            Label studentId = new Label("Student ID: ");
            studentName.setFont(font);
            studentId.setFont(font);

            studentName.setStyle("-fx-font-weight: bold;-fx-underline: true;");
            studentId.setStyle("-fx-font-weight: bold;-fx-underline: true;");
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
                vBox1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #87CEFA;");
                questions.getChildren().addAll(vBox1);
                startSolvingExamBoundry.getSelectedAnswersMap().put(q, startSolvingExamBoundry.getSelectedAnswersMap().get(q));
            }

            Region region1 = new Region();
            Region region2 = new Region();
            Region region3 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);
            HBox.setHgrow(region2, Priority.ALWAYS);
            HBox.setHgrow(region3, Priority.ALWAYS);
            hBox.getChildren().addAll(region1, borderPane, region2);
            region3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #87CEFA;");
            HBox hBox1 = new HBox(region3);
            vBox.getChildren().addAll(hBox,studentDetails,hBox1, questions);
            AnchorPane pane = new AnchorPane();
            pane.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 1px 1px 1px 1px");
            vBox.setStyle("-fx-background-color: #FFFFFF");
            pane.getChildren().add(vBox);
            startSolvingExamBoundry.getExamContainer().getChildren().addAll(hBox, studentDetails, hBox1, questions);

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
