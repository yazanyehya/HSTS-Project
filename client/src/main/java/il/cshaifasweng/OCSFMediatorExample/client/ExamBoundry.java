package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;
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
import java.util.List;

public class ExamBoundry{

    @FXML
    private  Button backBtn;

    @FXML
    private Button saveBtn;

    @FXML
    private ComboBox<Course> chooseCourse;

    @FXML
    private ComboBox<Subject> selectSubject;


    @FXML
    private ListView<Question> questionListView;

    @FXML
    private TextArea commentStudet;

    @FXML
    private TextArea commentTeacher;
    @FXML
    private Button selectBtn;

    @FXML
    private TextField examPeriod;
    @FXML
    private ImageView logo;

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
    private Label timeLabel;
    private AnimationTimer animationTimer;

    private ExamController examController;

    public void setExamPeriod(TextField examPeriod) {
        this.examPeriod = examPeriod;
    }

    public TextField getExamPeriod() {
        return examPeriod;
    }
    @FXML
    void createAnExamAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            examController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Create Exam Page");

        });

    }  @FXML
    void createAnQuestionAction(ActionEvent event) {
        EventBus.getDefault().unregister(examController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("QuestionBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void EditExamsAction(ActionEvent event) {
        EventBus.getDefault().unregister(examController);

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
        EventBus.getDefault().unregister(examController);
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
        EventBus.getDefault().unregister(examController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ApproveExam                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                        ");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void aquireExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(examController);

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
        EventBus.getDefault().unregister(examController);
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

        examController.logOut();
    }
    @FXML
    void seeResultsAction(ActionEvent event) {
        EventBus.getDefault().unregister(examController);

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
        EventBus.getDefault().unregister(examController);

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
        EventBus.getDefault().unregister(examController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
                Message newMessage = new Message("getTeacherNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    private Button notificationBtn;
    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(examController);
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


    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setChooseCourse(ComboBox<Course> chooseCourse) {
        this.chooseCourse = chooseCourse;
    }

    public void setExamController(ExamController examController) {
        this.examController = examController;
    }

    public void setQuestionListView(ListView<Question> questionListView) {
        this.questionListView = questionListView;
    }

    public void setSelectBtn(Button selectBtn) {
        this.selectBtn = selectBtn;
    }

    public Button getSelectBtn() {
        return selectBtn;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public ComboBox<Course> getChooseCourse() {
        return chooseCourse;
    }

    public ExamController getExamController() {
        return examController;
    }

    public ListView<Question> getQuestionListView() {
        return questionListView;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public TextArea getCommentStudet() {
        return commentStudet;
    }

    public TextArea getCommentTeacher() {
        return commentTeacher;
    }

    @FXML
    void initialize() throws IOException {

        questionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        examPeriod.setText("");
        examController = new ExamController(this);
        this.setExamController(examController);
        chooseCourse.setVisible(true);
        examController.getSubjects();
        Image logoImage = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);
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
        // Set the cell factory to display the course name
        chooseCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> param) {
                return new ListCell<Course>() {
                    @Override
                    protected void updateItem(Course item, boolean empty) {
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
        chooseCourse.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Course fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });
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
    void selectAction(ActionEvent event) throws IOException
    {
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a subject");
            });
        }
        else if (chooseCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else
        {
            examController.selectAction(chooseCourse.getValue());
        }
    }
    @FXML
    void saveExam(ActionEvent event) throws IOException {
        if (questionListView.getSelectionModel().isEmpty()) {
            Platform.runLater(() ->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select questions and set grades");
            });
        } else if (examPeriod.getText().equals("") || !examPeriod.getText().matches("\\d+"))
        {
            Platform.runLater(() ->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Enter a valid exam period");
            });
        }
        else
        {
            List<Question> selectedQuestions = getQuestionListView().getSelectionModel().getSelectedItems();
            System.out.println("incline bench press");
            examController.saveExam(selectedQuestions);
            System.out.println("incline smith bench press");
        }
    }
    @FXML
    void examPeriodAction(ActionEvent event)
    {
        System.out.println("printing exam period: " + examPeriod.getText());
    }

    @FXML
    void examTypeAction(ActionEvent event)
    {

    }


    @FXML
    void chooseCourseAction(ActionEvent event)
    {
        // Handle course selection action

    }
    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException
    {
        examController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        chooseCourse.setVisible(true);
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
