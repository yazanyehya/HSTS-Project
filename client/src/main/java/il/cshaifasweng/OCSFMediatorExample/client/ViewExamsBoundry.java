package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.EditExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewExamsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class ViewExamsBoundry {

    @FXML
    private Button courseReportsBtn;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;
    @FXML
    private Button logoutBtn;


    @FXML
    private Button studentReportsBtn;

    @FXML
    private Button teacherReportsBtn;

    @FXML
    private Button viewExamsBtn;

    @FXML
    private Button viewGradesBtn;

    @FXML
    private Button viewQuestionsBtn;


    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="examPeriod"
    private TextField examPeriod; // Value injected by FXMLLoader
    @FXML // fx:id="commentStudet"
    private TextArea commentStudet; // Value injected by FXMLLoader

    @FXML // fx:id="commentTeacher"
    private TextArea commentTeacher; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExams"
    private ListView<Exam> listViewExams; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExamQuestions"
    private ListView<Question> listViewExamQuestions; // Value injected by FXMLLoader
    @FXML // fx:id="selectCourse"
    private ComboBox<Course> selectCourse; // Value injected by FXMLLoader

    @FXML // fx:id="selectSubject"
    private ComboBox<Subject> selectSubject; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionsBtn"
    private Button showQuestionsBtn; // Value injected by FXMLLoader
    @FXML // fx:id="showExamsBtn"
    private Button showExamsBtn; // Value injected by FXMLLoader
    private ViewExamsController viewExamsController;

    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;

    @FXML
    private Button notificationBtn;


    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewExamsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("PrincipleNotifications");
                Message message = new Message("getNotificationForPrinciple", SimpleClient.getClient().getUser());
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
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewExamsController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getTeachersForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewExamsController);
                SimpleChatClient.switchScreen("courseReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getCoursesForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewExamsController);
                SimpleChatClient.switchScreen("studentReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getStudentsForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void reportsAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(viewExamsController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("reportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewExamsController);
                SimpleChatClient.switchScreen("PrincipleBoundry");
                Message newMessage = new Message("getPrincipleNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        viewExamsController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewExamsController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimePrinciple");
                Message message = new Message("ExtraTimePrinciple", null);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void viewExamsAction(ActionEvent event) throws IOException {
//        Platform.runLater(() -> {
//            try {
//                EventBus.getDefault().unregister(viewExamsController);
//                SimpleChatClient.switchScreen("viewExamsBoundry");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in View Exams");
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewExamsController);
                SimpleChatClient.switchScreen("viewGradesBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewExamsController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
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
    @FXML
    void selectCourseAction(ActionEvent event) {
        showExamsBtn.setVisible(true);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        viewExamsController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }
    @FXML
    void examPeriodAction(ActionEvent event) {

    }
    @FXML
    void showQuestionsAction(ActionEvent event) {
        if (listViewExams.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select An Exam!");
            });
        }
        else
        {
//            Platform.runLater(() -> {
//                try {
//                    Exam exam = listViewExams.getSelectionModel().getSelectedItem();
//                    Message msg = new Message("getExamQuestions", exam);
//                    SimpleClient.getClient().sendToServer(msg);
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }
//            });
            Exam exam = listViewExams.getSelectionModel().getSelectedItem();
            List<Question> list = exam.getListOfQuestions();

            ObservableList<Question> questionList = FXCollections.observableArrayList(list);
            Platform.runLater(() -> {
                listViewExamQuestions.setItems(questionList);
                listViewExamQuestions.setCellFactory(param -> {
                    return new ViewExamsBoundry.ScoredQuestionListCell(false);
                });
            });
            examPeriod.setText(exam.getExamPeriod());
            commentStudet.setText(exam.getStudentComments());
            commentTeacher.setText(exam.getTeacherComments());
//            String subject = exam.getSubject().getName();
//            String course = exam.getCourse().getName();
//            Platform.runLater(() -> {
//                // Set the items for the ComboBox
//                ObservableList<Course> courseObservableList = FXCollections.observableArrayList(exam.getCourse());
//                selectCourse.setItems(courseObservableList);
//            });
        }
    }
    private class ScoredQuestionListCell extends ListCell<Question> {
        private TextField scoreField;
        private boolean firstRow;

        ScoredQuestionListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);
            if (empty || question == null) {
                setText(null);
                setGraphic(null);
            } else {
                System.out.println(question.getQText() + "  " + question.getScore());
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER_LEFT);
                container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");

                Label questionTextLabel = new Label("Question: " + question.getQText());
                Label answer1 = new Label("a. " + question.getAnswer1());
                Label answer2 = new Label("b. " + question.getAnswer2());
                Label answer3 = new Label("c. " + question.getAnswer3());
                Label answer4 = new Label("d. " + question.getAnswer4());
                VBox questionVBox = new VBox(questionTextLabel, answer1, answer2, answer3, answer4);

                TextField scoreField = new TextField();
                scoreField.setPrefWidth(50);
                scoreField.setText(Integer.toString(question.getScore()));
                scoreField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        int score = Integer.parseInt(newValue);
                        question.setScore(score);
                        System.out.println(question.getQText() + "  " + question.getScore());
                    } catch (NumberFormatException e) {
                        // Handle invalid input
                    }
                });

                Region region1 = new Region();
                Region region2 = new Region();
                Region region3 = new Region();
                HBox.setHgrow(region1, Priority.ALWAYS);
                HBox.setHgrow(region2, Priority.ALWAYS);
                HBox.setHgrow(region3, Priority.ALWAYS);

                container.getChildren().addAll(questionVBox, region3, scoreField);

                setGraphic(container);
            }
        }
    }
    @FXML
    void showExamsAction(ActionEvent event) throws IOException {
        Course course = getSelectCourse().getValue();
        Message message = new Message("showExamsForPrinciple", course);
        SimpleClient.getClient().sendToServer(message);
    }
    public ViewExamsController getViewExamsController() {
        return viewExamsController;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public Button getShowExamBtn() {
        return showExamsBtn;
    }

    public void setViewExamsController(ViewExamsController viewExamsController) {
        this.viewExamsController = viewExamsController;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setShowExamBtn(Button showExamBtn) {
        this.showExamsBtn = showExamBtn;
    }

    public ListView<Exam> getListViewExams() {
        return listViewExams;
    }

    public TextArea getCommentTeacher() {
        return commentTeacher;
    }
    public void setCommentTeacher(TextArea commentTeacher) {
        this.commentTeacher = commentTeacher;
    }

    public void setCommentStudet(TextArea commentStudet) {
        this.commentStudet = commentStudet;
    }

    public TextArea getCommentStudet() {
        return commentStudet;
    }
    public TextField getExamPeriod() {
        return examPeriod;
    }
    public void setExamPeriod(TextField examPeriod) {
        this.examPeriod = examPeriod;
    }
    public void setListViewExams(ListView<Exam> listViewExams) {
        this.listViewExams = listViewExams;
    }
    public ListView<Question> getListViewExamQuestions() {
        return listViewExamQuestions;
    }

    public void setListViewExamQuestions(ListView<Question> listViewExamQuestions) {
        this.listViewExamQuestions = listViewExamQuestions;
    }
    @FXML
    public void initialize() throws IOException {
        viewExamsController = new ViewExamsController(this);
        this.setViewExamsController(viewExamsController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();

        viewExamsController.getSubjects();

        selectCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
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

        selectCourse.setConverter(new StringConverter<Course>() {
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
            public String toString(Subject subject) {
                if (subject == null) {
                    return null;
                }
                return subject.getName();
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }
        });

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

}
 