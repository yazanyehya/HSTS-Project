package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.client.ViewGradesForTeacherIIBoundry;
import il.cshaifasweng.OCSFMediatorExample.client.ViewGradesForTeacherIIBoundryEvent;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.List;

public class ViewGradesForTeacherIIController
{
    private ViewGradesForTeacherIIBoundry viewGradesForTeacherIIBoundry;

    public ViewGradesForTeacherIIController(ViewGradesForTeacherIIBoundry viewGradesForTeacherIIBoundry)
    {
        EventBus.getDefault().register(this);
        this.viewGradesForTeacherIIBoundry = viewGradesForTeacherIIBoundry;
    }

    @Subscribe
    public void handleEvents(ViewGradesForTeacherIIBoundryEvent viewGradesForTeacherIIBoundryEvent)
    {
        if (viewGradesForTeacherIIBoundryEvent.getMessage().getTitle().equals("ShowReadyExamsForViewGradesForTeacher"))
        {
            List<ReadyExam> list = (List<ReadyExam>) viewGradesForTeacherIIBoundryEvent.getMessage().getBody();

            Platform.runLater(()->
            {
                viewGradesForTeacherIIBoundry.getStudentNameCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, String>("FullName"));
                viewGradesForTeacherIIBoundry.getStudentIDCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("studentId"));
                viewGradesForTeacherIIBoundry.getGradeCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("Grade"));
                viewGradesForTeacherIIBoundry.getPreviewCol().setCellFactory(column -> new ViewGradesForTeacherIIController.ButtonCell());

                viewGradesForTeacherIIBoundry.getTable().getItems().addAll(list);
            });
        }
    }
    private class ButtonCell extends TableCell<ReadyExam, Button> {
        private final Button button = new Button("Preview");

        ButtonCell() {
            // Set button action here, e.g., open the preview for the selected ReadyExam
            button.setOnAction(event -> {
                ReadyExam readyExam = getTableView().getItems().get(getIndex());
                // Call a method in your controller to handle the preview action
                handlePreviewAction(readyExam);
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
    private void handlePreviewAction(ReadyExam readyExam)
    {

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


            //Label HighSchoolNameLabel = new Label("High School Test System");
            Label courseLabel = new Label("Exam in "+ readyExam.getCourse() + " course, " + readyExam.getExam().getSubject().getName());
            //HighSchoolNameLabel.setFont(font);
            //HighSchoolNameLabel.setStyle("-fx-text-fill: #87CEFA;-fx-underline: true;");

            courseLabel.setFont(font);
            courseLabel.setStyle("-fx-text-fill: #1E90FF;-fx-underline: true;");
            examDetails.getChildren().addAll(imageViewLogo, courseLabel);
            examDetails.setAlignment(Pos.CENTER);
            borderPane.setCenter(examDetails);

            VBox studentDetails = new VBox();

            Label studentName = new Label("Student name: " + readyExam.getFullName() + ".");
            Label studentId = new Label("Student ID: " + readyExam.getStudentId()+ ".");
            Label grade = new Label("Grade: "+readyExam.getGrade());
            studentName.setFont(font);
            studentId.setFont(font);
            grade.setFont(font);

            studentName.setStyle("-fx-font-weight: bold;-fx-underline: true;");
            studentId.setStyle("-fx-font-weight: bold;-fx-underline: true;");
            studentDetails.getChildren().addAll(studentName, studentId,grade);

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
                vBox1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #87CEFA;");
                questions.getChildren().addAll(vBox1);
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
            Label studentComments = new Label("Comments for Student");
            TextField textFieldStudentComments = new TextField(readyExam.getExam().getStudentComments());
            textFieldStudentComments.setDisable(true);
            vBox.getChildren().addAll(hBox,studentDetails,hBox1, questions, studentComments, textFieldStudentComments);
            ScrollPane scrollPane = new ScrollPane();
            vBox.setStyle("-fx-background-color: #ffffff");
            scrollPane.setContent(vBox);
            scrollPane.setStyle("-fx-background-color: #ffffff");
            // Create a new stage and set the VBox as its root
            Stage previewStage = new Stage();
            previewStage.setScene(new Scene(scrollPane));
            previewStage.setHeight(800);
            previewStage.setWidth(800);


            // Show the stage
            previewStage.show();
        });
    }
}
 