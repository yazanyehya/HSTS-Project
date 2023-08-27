package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForTeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.ObservableList;
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
    private Button notificationBtn;
    @FXML
    private TextField averageTextField;
    @FXML
    private Button compare;
    @FXML
    private TextField medianTextField;
    public int flag = 0;

    @FXML
    void averageTextFieldAction(ActionEvent event) {

    }

    @FXML
    void compareAction(ActionEvent event) {

    }
    @FXML
    void medianTextFieldAction(ActionEvent event) {

    }
    @FXML
    void notificationAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewGradesForTeacherController);
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
                Message newMessage = new Message("getTeacherNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
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

        examList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

        compare.setOnMouseClicked(event->
        {
            if (examList.getSelectionModel().isEmpty())
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select an exam");
            }
            else
            {
                viewGradesForTeacherController.getMedian_map().clear();
                viewGradesForTeacherController.getMap().clear();
                ObservableList<ReadyExam> selectedExams = examList.getSelectionModel().getSelectedItems();
                flag = selectedExams.size();
                double curr_avg = 0;
                int curr_size = 0;
                for (ReadyExam exam : selectedExams)
                {
                    curr_avg += exam.getAvg()*exam.getSize();
                    curr_size+= exam.getSize();
                }
                double final_avg = curr_avg/curr_size;
                averageTextField.setText(Double.toString(final_avg));
                for (ReadyExam readyExam : selectedExams)
                {
                    Message message = new Message("getListGradeForTeacherIN", readyExam);
                    try {
                        SimpleClient.getClient().sendToServer(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
//                for (Integer grade1 : readyExam.getListOfGrades())
//                o
//                    median_list.add(grade1);
//                }
                }


                for (ReadyExam exam : selectedExams)
                {
                    viewGradesForTeacherController.getResults(exam);
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

    public TextField getAverageTextField() {
        return averageTextField;
    }

    public TextField getMedianTextField() {
        return medianTextField;
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
