 package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
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

public class PreviewToApproveController
{

    private PreviewToApproveBoundry previewToApproveBoundry;

    public PreviewToApproveController(PreviewToApproveBoundry previewToApproveBoundry)
    {
        EventBus.getDefault().register(this);
        this.previewToApproveBoundry = previewToApproveBoundry;
    }
    private void disableOtherRadioButtons(ToggleGroup toggleGroup, RadioButton selectedRadioButton) {
        toggleGroup.getToggles().forEach(toggle -> {
            RadioButton radioButton = (RadioButton) toggle;
            if (!radioButton.equals(selectedRadioButton)) {
                radioButton.setDisable(true);
            }
        });
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

        String selectedAnswer = question.getStudentAnswer();
        String correctAnswer = question.getCorrectAnswer();
        int flag = -1;
        if (selectedAnswer != null) {
            if (selectedAnswer.equals("a. " + question.getAnswer1()))
            {
                flag = 1;
                answer1RadioButton.setSelected(true);
                disableOtherRadioButtons(toggleGroup, answer1RadioButton);
            }
            else if (selectedAnswer.equals("b. " + question.getAnswer2()))
            {
                flag = 2;
                answer2RadioButton.setSelected(true);
                disableOtherRadioButtons(toggleGroup, answer2RadioButton);
            }
            else if (selectedAnswer.equals("c. " + question.getAnswer3()))
            {
                flag = 3;
                answer3RadioButton.setSelected(true);
                disableOtherRadioButtons(toggleGroup, answer3RadioButton);
            }
            else if (selectedAnswer.equals("d. " + question.getAnswer4()))
            {
                flag = 4;
                answer4RadioButton.setSelected(true);
                disableOtherRadioButtons(toggleGroup, answer4RadioButton);
            }
        }
//        if (selectedAnswer.equals(question.getCorrectAnswer()))
//        {
//            if (flag == 1)
//            {
//                answer1RadioButton.setStyle("-fx-text-fill: #90EE90;");
//            }
//            else if(flag == 2)
//            {
//                answer2RadioButton.setStyle("-fx-text-fill: #90EE90;");
//            }
//            else if(flag == 3)
//            {
//                answer3RadioButton.setStyle("-fx-text-fill: #90EE90;");
//            }
//            else if(flag == 4)
//            {
//                answer4RadioButton.setStyle("-fx-text-fill: #90EE90;");
//            }
//
//        }

        if (!answer1RadioButton.getText().equals(question.getCorrectAnswer()) && answer1RadioButton.getText().equals(selectedAnswer))
        {
            answer1RadioButton.setStyle("-fx-text-fill: #FF6347 ;");
        }
        else if (!answer2RadioButton.getText().equals(question.getCorrectAnswer()) && answer2RadioButton.getText().equals(selectedAnswer))
        {
            answer2RadioButton.setStyle("-fx-text-fill: #FF6347 ;");
        }
        else if (!answer3RadioButton.getText().equals(question.getCorrectAnswer()) && answer3RadioButton.getText().equals(selectedAnswer))
        {
            answer3RadioButton.setStyle("-fx-text-fill: #FF6347 ;");
        }
        else if (!answer4RadioButton.getText().equals(question.getCorrectAnswer()) && answer4RadioButton.getText().equals(selectedAnswer))
        {
            answer4RadioButton.setStyle("-fx-text-fill: #FF6347 ;");
        }
        if(answer1RadioButton.getText().equals(correctAnswer))
        {
            answer1RadioButton.setStyle("-fx-text-fill: #228B22;");
        }
        else if(answer2RadioButton.getText().equals(correctAnswer))
        {
            answer2RadioButton.setStyle("-fx-text-fill: #228B22;");
        }
        else if(answer3RadioButton.getText().equals(correctAnswer))
        {
            answer3RadioButton.setStyle("-fx-text-fill: #228B22;");
        }
        else if(answer4RadioButton.getText().equals(correctAnswer))
        {
            answer4RadioButton.setStyle("-fx-text-fill: #228B22;");
        }
        VBox vBox = new VBox();
        vBox.getChildren().addAll(answer1RadioButton, answer2RadioButton, answer3RadioButton, answer4RadioButton);

        return vBox;
    }
    @Subscribe
    public void handlePreviewExam(SendToPreviewEvent sendToPreviewEvent)
    {

        Object[] objects = (Object[]) sendToPreviewEvent.getMessage().getBody();

        ReadyExam readyExam = (ReadyExam) objects[0];
        previewToApproveBoundry.setExamID(readyExam.getId());
        Student student = (Student)objects[1];
        Platform.runLater(() -> {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            VBox examDetails = new VBox();
            AnchorPane anchorPane1 = new AnchorPane();
            AnchorPane anchorPane2 = new AnchorPane();
            BorderPane borderPane = new BorderPane();
            Image logo = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
            ImageView imageViewLogo = new ImageView(logo);
            imageViewLogo.setFitWidth(150); // Set the width
            imageViewLogo.setFitHeight(150); // Set the height
            Font font = new Font("American Typewriter", 24);
            previewToApproveBoundry.getGrade().setText(Integer.toString(readyExam.getGrade()));
            previewToApproveBoundry.setTempGade(readyExam.getGrade());
            previewToApproveBoundry.setOld(readyExam.getExam().getStudentComments());
            //Label HighSchoolNameLabel = new Label("High School Test System");
            Label courseLabel = new Label("Exam in " + readyExam.getCourse() + " course, " + readyExam.getExam().getSubject().getName());
            Label teacherName = new Label((readyExam.getExam().getTeacherFullName()));
            teacherName.setFont(font);
            teacherName.setStyle("-fx-font-weight: bold;-fx-underline: true; -fx-text-fill: #1E90FF;");
            //HighSchoolNameLabel.setFont(font);
            //HighSchoolNameLabel.setStyle("-fx-text-fill: #87CEFA;-fx-underline: true;");

            courseLabel.setFont(font);
            courseLabel.setStyle("-fx-text-fill: #1E90FF;-fx-underline: true;");
            examDetails.getChildren().addAll(imageViewLogo, courseLabel, teacherName);
            examDetails.setAlignment(Pos.CENTER);
            borderPane.setCenter(examDetails);

            VBox studentDetails = new VBox();

            previewToApproveBoundry.setStudentID(student.getId());
            Label studentName = new Label("Student name: " + student.getFirstName() + " " + student.getLastName());
            Label studentId = new Label("Student ID: " + student.getId());
            studentName.setFont(font);
            studentId.setFont(font);

            studentName.setStyle("-fx-font-weight: bold;-fx-underline: true; -fx-text-fill: #1E90FF;");
            studentId.setStyle("-fx-font-weight: bold;-fx-underline: true; -fx-text-fill: #1E90FF;");
            studentDetails.getChildren().addAll(studentName, studentId);

            VBox questions = new VBox();
            int cnt = 1;
            for (Question q : readyExam.getExam().getListOfQuestions()) {
                System.out.println("alohaa");
                // Creating a question
                Label Qtext = new Label(cnt + ". " +q.getQText() + "(" + q.getScore() + " Points):");
                Qtext.setFont(font);
                cnt++;
                // Create a ToggleGroup and get the VBox containing the RadioButtons
                VBox vBox1 = createAnswerToggleGroup(q);

                // Add the question label and RadioButtons VBox to the main VBox
                vBox1.getChildren().add(0, Qtext); // Add the question label as the first child
                Region region9 = new Region();
                region9.setMinHeight(10); // Example: Set a minimum height
                region9.setPrefHeight(20); // Example: Set a preferred height
                region9.setMaxHeight(25);

                VBox.setVgrow(region9, Priority.ALWAYS);
                vBox1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #1f75fe;");
                questions.getChildren().addAll(vBox1,region9);

            }
            Label gradeLabel = new Label("Grade: ");
            previewToApproveBoundry.getGrade().prefWidth(35);
            previewToApproveBoundry.getGrade().prefHeight(25);
            gradeLabel.prefWidth(51);
            HBox hBox2 = new HBox(gradeLabel, previewToApproveBoundry.getGrade());
            questions.getChildren().add(hBox2);

            Region region1 = new Region();
            Region region2 = new Region();
            Region region3 = new Region();
            Region region4 = new Region();
            Region region5 = new Region();
            Region region6 = new Region();
            Region region7 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);
            HBox.setHgrow(region2, Priority.ALWAYS);

            region3.setMinHeight(10); // Example: Set a minimum height
            region3.setPrefHeight(40); // Example: Set a preferred height
            region3.setMaxHeight(40); // Example: Set a maximum height

            region4.setMinHeight(10); // Example: Set a minimum height
            region4.setPrefHeight(40); // Example: Set a preferred height
            region4.setMaxHeight(40); // Example: Set a maximum height

            region5.setMinHeight(10); // Example: Set a minimum height
            region5.setPrefHeight(40); // Example: Set a preferred height
            region5.setMaxHeight(40); // Example: Set a maximum height

            region6.setMinHeight(10); // Example: Set a minimum height
            region6.setPrefHeight(40); // Example: Set a preferred height
            region6.setMaxHeight(40); // Example: Set a maximum height

            region7.setMinHeight(10); // Example: Set a minimum height
            region7.setPrefHeight(40); // Example: Set a preferred height
            region7.setMaxHeight(40); // Example: Set a maximum height

            VBox.setVgrow(region3, Priority.ALWAYS);
            VBox.setVgrow(region4, Priority.ALWAYS);
            VBox.setVgrow(region5, Priority.ALWAYS);
            VBox.setVgrow(region6, Priority.ALWAYS);
            VBox.setVgrow(region7, Priority.ALWAYS);
            hBox.getChildren().addAll(region1, borderPane, region2);

            previewToApproveBoundry.getStudentComments().setText(readyExam.getExam().getStudentComments());
            previewToApproveBoundry.getTeacherComments().setText(readyExam.getExam().getTeacherComments());
            vBox.getChildren().addAll(hBox, studentDetails, region3, questions, region4,previewToApproveBoundry.getTeacherContainer(), region6,previewToApproveBoundry.getStudentContainer(),region5, previewToApproveBoundry.getApproveBtn(), region7,previewToApproveBoundry.getBackBtn());
//            AnchorPane pane = new AnchorPane();
//            pane.setStyle("-fx-border-color: #FFFFFF; -fx-border-width: 1px 1px 1px 1px");
//            vBox.setStyle("-fx-background-color: #FFFFFF");
            vBox.setStyle("-fx-background-color: #ffffff");
            vBox.setPrefHeight(500);
            vBox.setPrefWidth(900);
            previewToApproveBoundry.getScrollPane().setContent(vBox);
            //previewToApproveBoundry.getBorder().setCenter(vBox);
            //pane.getChildren().add(previewToApproveBoundry.getBorder());
            //previewToApproveBoundry.getExamContainer().getChildren().addAll(previewToApproveBoundry.getBorder());
        });
    }
    @Subscribe
    public void handleApproveExamEvent(ApproveExamEvent approveExamEvent)
    {
        Platform.runLater(() -> {
            showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam Approved Successfully");
            try {
                EventBus.getDefault().unregister(this);
                SimpleChatClient.switchScreen("ApproveExam");
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
 