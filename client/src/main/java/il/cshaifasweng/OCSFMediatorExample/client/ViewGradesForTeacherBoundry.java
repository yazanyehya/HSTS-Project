package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForTeacherController;
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

public class ViewGradesForTeacherBoundry {


    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    @FXML
    private ListView<ReadyExam> examList;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    private ViewGradesForTeacherController viewGradesForTeacherController;


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
    void createAnExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(viewGradesForTeacherController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }  @FXML
    void createAnQuestionAction(ActionEvent event) {
        EventBus.getDefault().unregister(viewGradesForTeacherController);

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
        EventBus.getDefault().unregister(viewGradesForTeacherController);

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
        EventBus.getDefault().unregister(viewGradesForTeacherController);

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
        EventBus.getDefault().unregister(viewGradesForTeacherController);

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
        EventBus.getDefault().unregister(viewGradesForTeacherController);

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
        EventBus.getDefault().unregister(viewGradesForTeacherController);
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
     viewGradesForTeacherController.logOut();
    }
    @FXML
    void seeResultsAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            viewGradesForTeacherController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In View Grades Page");

        });

    }
    @FXML
    void sendExamsToStudentsAction(ActionEvent event) {
        EventBus.getDefault().unregister(viewGradesForTeacherController);
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
        EventBus.getDefault().unregister(viewGradesForTeacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    void selectCourseAction(ActionEvent event) throws IOException {

        Object obj = new Object[]{SimpleClient.getClient().getUser(), getSelectCourse().getSelectionModel().getSelectedItem()};
        examList.getItems().clear();
        Message message = new Message("showExamsForTeacherCourses", obj);
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        examList.getItems().clear();
        viewGradesForTeacherController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
        Object obj = new Object[]{SimpleClient.getClient().getUser(), getSelectSubject().getSelectionModel().getSelectedItem()};
        Message message = new Message("ShowExamsForTeacherSubjects", obj);
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    public void initialize() throws IOException {
        viewGradesForTeacherController = new ViewGradesForTeacherController(this);
        this.setViewGradesForTeacherController(viewGradesForTeacherController);

        selectCourse.setVisible(true);


        viewGradesForTeacherController.getSubjects();


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
        examList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                ReadyExam selectedExam = examList.getSelectionModel().getSelectedItem();
                if (selectedExam != null) {
                    System.out.println("lol");
                    // Perform your double-click action here
                    try {
                        EventBus.getDefault().unregister(viewGradesForTeacherController);
                        SimpleChatClient.switchScreen("ViewGradesForTeacherII");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message("ShowReadyExamsForViewGradesForTeacher", selectedExam);
                    try {
                        SimpleClient.getClient().sendToServer(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // For example, you can open a new window or perform any other action you want.
                }
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




    public ListView<ReadyExam> getExamList() {
        return examList;
    }

    public ViewGradesForTeacherController getViewGradesForTeacherController() {
        return viewGradesForTeacherController;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setExamList(ListView<ReadyExam> examList) {
        this.examList = examList;
    }

    public void setViewGradesForTeacherController(ViewGradesForTeacherController seeGradesController) {
        this.viewGradesForTeacherController = seeGradesController;
    }

    public ViewGradesForTeacherController getSeeGradesController() {
        return viewGradesForTeacherController;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }
}
