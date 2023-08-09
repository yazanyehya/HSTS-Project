package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.QuestionController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QuestionBoundry {
    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    @FXML
    private TextField answerA;

    @FXML
    private TextField answerB;

    @FXML
    private TextField answerC;

    @FXML
    private TextField answerD;


    @FXML
    private ToggleGroup chooseAnswer;

    @FXML
    private RadioButton optionA;

    @FXML
    private RadioButton optionB;

    @FXML
    private RadioButton optionC;

    @FXML
    private RadioButton optionD;

    @FXML
    private TextField questionTextTXT;

    @FXML
    private Button saveBtn;

    @FXML
    private ListView<Course> courseList;
    @FXML
    private ImageView logo;

    @FXML
    private Button showCourseBtn;
    @FXML
    private Button createExamBtn;
    @FXML
    private Button createQuestionBtn;
    @FXML
    private Button editExamBtn;
    @FXML
    private Button editQuestionBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button approveExamBtn;
    @FXML
    private Button aquireExamBtn;
    @FXML
    private Button extraTimeBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button seeResultsBtn;
    @FXML
    private Button sendExamsToStudentsBtn;

    @FXML
    private ComboBox<Subject> selectSubject;

    private QuestionController questionController;

    public TextField getAnswerD() {
        return answerD;
    }

    public TextField getAnswerC() {
        return answerC;
    }

    public TextField getAnswerB() {
        return answerB;
    }

    public TextField getAnswerA() {
        return answerA;
    }


    @FXML
    void answerAAction(ActionEvent event)
    {

    }

    @FXML
    void answerBAction(ActionEvent event) {

    }

    @FXML
    void answerCAction(ActionEvent event) {

    }

    @FXML
    void answerDAction(ActionEvent event) {

    }
    @FXML
    void createAnExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }  @FXML
    void createAnQuestionAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            questionController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Create Question Page");

        });
    }
    @FXML
    void EditExamsAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void EditQuestionsAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void approveExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ApproveExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void aquireExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

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
        EventBus.getDefault().unregister(questionController);
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
    void logoutAction(ActionEvent event) throws IOException {
        //EventBus.getDefault().unregister(questionController);
        questionController.logOut();


    }
    @FXML
    void seeResultsAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForTeacher");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    @FXML
    void sendExamsToStudentsAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("SendExamToStudentBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void homeBtnAction(ActionEvent event) {
        EventBus.getDefault().unregister(questionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void optionAAction(ActionEvent event)
    {

    }

    @FXML
    void optionBAction(ActionEvent event) {

    }

    @FXML
    void optionCAction(ActionEvent event) {

    }

    @FXML
    void optionDAction(ActionEvent event) {

    }

    @FXML
    void questionTextAction(ActionEvent event) {

    }

    private void populateCourseComboBox() {
        //Teacher teacher = (Teacher) SimpleClient.getClient().getUser();

        //ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        //selectCourse.setItems(courses);

        // Set the cell factory to display the course name
        selectSubject.setCellFactory(new Callback<ListView<Subject>, ListCell<Subject>>() {
            @Override
            public ListCell<Subject> call(ListView<Subject> param) {
                return new ListCell<Subject>() {
                    @Override
                    protected void updateItem(Subject item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        selectSubject.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Subject fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });
    }

    @FXML
    void saveAction(ActionEvent event)
    {
        RadioButton selectedRadioButton = (RadioButton) chooseAnswer.getSelectedToggle();
        Question question;
        String correctAnswer = "";
        List<Course> selectedCourses = courseList.getSelectionModel().getSelectedItems();
        List<String> courses = new ArrayList<>();

        for (Course c : selectedCourses)
        {
            courses.add(c.getName());
        }
        Subject selectedSubject = selectSubject.getValue();
        List<String> list = new ArrayList<>();
        if (selectedRadioButton == null || courseList.getSelectionModel().isEmpty()  || Objects.equals(getAnswerA().getText(), "") || Objects.equals(getAnswerB().getText(), "") || Objects.equals(getAnswerC().getText(), "") || Objects.equals(getAnswerD().getText(), "") || selectedSubject == null )
        {
            list = null;
        }
        else
        {
            String answer = "";
            correctAnswer = selectedRadioButton.getText();

            if(correctAnswer.equals("a."))
            {
                answer = answerA.getText();
            }
            else if (correctAnswer.equals("b."))
            {
                answer = answerB.getText();
            }
            else if (correctAnswer.equals("c."))
            {
                answer = answerC.getText();
            }
            else if (correctAnswer.equals("d."))
            {
                answer = answerD.getText();
            }
            correctAnswer = selectedRadioButton.getText() + " " + answer;
//            question = new Question(questionTextTXT.getText(), answerA.getText(), answerB.getText()
//                    , answerC.getText(), answerD.getText(), selectedSubject, (Teacher) SimpleClient.getClient().getUser(), correctAnswer);
            list.add(questionTextTXT.getText());
            list.add(answerA.getText());
            list.add(answerB.getText());
            list.add(answerC.getText());
            list.add(answerD.getText());
            list.add(selectedSubject.getName());
            list.add(((Teacher) SimpleClient.getClient().getUser()).getUsername());
            list.add(correctAnswer);
            for (String s : courses)
            {
                list.add(s);
            }

        }
        questionController.createQuestion(list);
    }

    @FXML
    void selectCourseAction(ActionEvent event) {

    }

    @FXML
    void selectSubjectAction(ActionEvent event) {

    }
    public void setQuestionController(QuestionController questionController) {
        this.questionController = questionController;
    }

    @FXML
    public void initialize() throws IOException {
        questionController = new QuestionController(this);
        this.setQuestionController(questionController);
        Image logoImage = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);


        courseList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        questionController.getSubjects();
        populateCourseComboBox();
        questionTextTXT.setText("");
        answerA.setText("");
        answerB.setText("");
        answerC.setText("");
        answerD.setText("");
        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
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


    @FXML
    void shoqCourseBtn(ActionEvent event) throws IOException {
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a Subject");
            });
        }
        else
        {
            questionController.getCourse(selectSubject.getSelectionModel().getSelectedItem());
        }
    }
    public ListView<Course> getCourseList() {
        return courseList;
    }

    public void setCourseList(ListView<Course> courseList) {
        this.courseList = courseList;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
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
