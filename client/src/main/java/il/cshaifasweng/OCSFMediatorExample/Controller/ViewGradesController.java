 package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
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

import java.io.IOException;
import java.util.List;

public class ViewGradesController {
    private ViewGradesBoundry viewGradesBoundry;
    private boolean isLogoutDialogShown = false;

    public ViewGradesController(ViewGradesBoundry viewGradesBoundry)
    {
        EventBus.getDefault().register(this);
        this.viewGradesBoundry = viewGradesBoundry;
    }
    public void setViewGradesBoundry(ViewGradesBoundry viewGradesBoundry) {
        this.viewGradesBoundry = viewGradesBoundry;
    }
    public ViewGradesBoundry getViewGradesBoundry() {
        return viewGradesBoundry;
    }

    public void getStudents() throws IOException
    {
        Principle principle = (Principle) SimpleClient.getClient().getUser();
        Message message = new Message("getStudentsGradesForPrinciple", principle);
        SimpleClient.getClient().sendToServer(message);
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
    public void handleGetStudentsGradesForPrincipleEvent(GetStudentsGradesForPrincipleEvent getStudentsGradesForPrincipleEvent)
    {
        if ("getStudentsToViewGradePrinciple".equals(getStudentsGradesForPrincipleEvent.getMessage().getTitle()))
        {
            List<ReadyExam> list = (List<ReadyExam>) getStudentsGradesForPrincipleEvent.getMessage().getBody();
            if (list.isEmpty())
            {
                Platform.runLater(()->{
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "No available data for this student!");
                });
            }
            else
            {

                Platform.runLater(()->{
                    viewGradesBoundry.getCourseCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, String>("Course"));
                    viewGradesBoundry.getExamIDCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("id"));
                    viewGradesBoundry.getGrade().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("Grade"));
                    viewGradesBoundry.getPreviewCol().setCellFactory(column -> new ViewGradesController.ButtonCell());
                    viewGradesBoundry.getStudentIDCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, Integer>("studentId"));
                    viewGradesBoundry.getStudentFullNameCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, String>("FullName"));
                    viewGradesBoundry.getSubjectCol().setCellValueFactory(new PropertyValueFactory<ReadyExam, String>("subject"));

                    viewGradesBoundry.getTable().getItems().addAll(list);
                });
            }
        }
        else
        {        List<Student> students = (List<Student>)getStudentsGradesForPrincipleEvent.getMessage().getBody();

            Platform.runLater(() -> {
                // Set the items for the ComboBox
                ObservableList<Student> studentObservableList = FXCollections.observableArrayList(students);
                viewGradesBoundry.getSelectStudent().setItems(studentObservableList);
            });}
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

        //Student student = (Student) SimpleClient.getClient().getUser();
        Platform.runLater(() -> {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            VBox examDetails = new VBox();
            AnchorPane anchorPane1 = new AnchorPane();
            AnchorPane anchorPane2 = new AnchorPane();
            BorderPane borderPane = new BorderPane();
            Image logo = new Image(getClass().getResourceAsStream("/images/finallogop.png"));
            ImageView imageViewLogo = new ImageView(logo);
            imageViewLogo.setFitWidth(150); // Set the width
            imageViewLogo.setFitHeight(150); // Set the height
            Font font = new Font("American Typewriter", 24);


            //Label HighSchoolNameLabel = new Label("High School Test System");
            Label courseLabel = new Label("Exam in "+ readyExam.getCourse() + " course, " + readyExam.getExam().getSubject().getName());
            Label teacherName = new Label((readyExam.getExam().getTeacherFullName()));
            //HighSchoolNameLabel.setFont(font);
            //HighSchoolNameLabel.setStyle("-fx-text-fill: #87CEFA;-fx-underline: true;");

            courseLabel.setFont(font);
            courseLabel.setStyle("-fx-text-fill: #7b68ee;-fx-underline: true;");
            teacherName.setFont(font);
            teacherName.setStyle("-fx-text-fill: #7b68ee;-fx-underline: true;");
            examDetails.getChildren().addAll(imageViewLogo, courseLabel, teacherName);
            examDetails.setAlignment(Pos.CENTER);
            borderPane.setCenter(examDetails);

            VBox studentDetails = new VBox();

            Label studentName = new Label("Student name: " + readyExam.getFullName());
            Label studentId = new Label("Student ID: " + readyExam.getStudentId());
            Label grade = new Label("Grade: "+readyExam.getGrade());
            studentName.setFont(font);
            studentId.setFont(font);
            grade.setFont(font);

            studentName.setStyle("-fx-text-fill: #7b68ee;-fx-underline: true;");
            studentId.setStyle("-fx-text-fill: #7b68ee;-fx-underline: true;");
            studentDetails.getChildren().addAll(studentName, studentId, grade);

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
                vBox1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #7b68ee;");
                questions.getChildren().addAll(vBox1);
            }
            Region region1 = new Region();
            Region region2 = new Region();
            Region region3 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);
            HBox.setHgrow(region2, Priority.ALWAYS);
            HBox.setHgrow(region3, Priority.ALWAYS);
            hBox.getChildren().addAll(region1, borderPane, region2);
            region3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #7b68ee;");
            HBox hBox1 = new HBox(region3);
            Label studentComments = new Label("Comments for Student");
            TextField textFieldStudentComments = new TextField(readyExam.getExam().getStudentComments());
            textFieldStudentComments.setDisable(true);
            vBox.getChildren().addAll(hBox,studentDetails,hBox1, questions, studentComments, textFieldStudentComments);
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vBox);
            // Create a new stage and set the VBox as its root
            Stage previewStage = new Stage();
            previewStage.setScene(new Scene(scrollPane));
            previewStage.setHeight(900);
            previewStage.setWidth(500);


            // Show the stage
            previewStage.show();
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
 