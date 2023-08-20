package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ApproveExamController;
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

public class ApproveExamBoundry {

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    @FXML
    private ListView<ReadyExam> readyExamList;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private Button showExamBtn;

    @FXML
    private Button previewExamBtn;

    @FXML
    private ImageView logo;
    @FXML
    private ImageView send;
    @FXML
    private ImageView repository;
    @FXML
    private ImageView grade;
    @FXML
    private ImageView extratime;
    @FXML
    private ImageView home;
    @FXML
    private ImageView approve;
    @FXML
    private ImageView logout;
    @FXML
    private ImageView editQ;
    @FXML
    private ImageView editE;
    @FXML
    private ImageView createQ;
    @FXML
    private ImageView createE;

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


    private ApproveExamController approveExamController;

    @FXML
    void createAnExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(approveExamController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }  @FXML
    void createAnQuestionAction(ActionEvent event) {
        EventBus.getDefault().unregister(approveExamController);

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
        EventBus.getDefault().unregister(approveExamController);

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
        EventBus.getDefault().unregister(approveExamController);

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
        Platform.runLater(() -> {
            // Show the dialog
            approveExamController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Approve Exam Page");

        });
    }

    @FXML
    void aquireExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(approveExamController);

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
        EventBus.getDefault().unregister(approveExamController);
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
      approveExamController.logOut();
    }

    @FXML
    void seeResultsAction(ActionEvent event) {
        EventBus.getDefault().unregister(approveExamController);

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
        EventBus.getDefault().unregister(approveExamController);

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
        EventBus.getDefault().unregister(approveExamController);

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
    void selectCourseAction(ActionEvent event)
    {
        showExamBtn.setVisible(true);
    }

    @FXML
    void PreviewExamAction(ActionEvent event) throws IOException {
        if (readyExamList.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select an exam");
            });
        }
        else
        {
            SimpleChatClient.switchScreen("PreviewToApprove");
            ReadyExam readyExam = getReadyExamList().getSelectionModel().getSelectedItem();
            Message message = new Message("SendToPreview", readyExam);
            SimpleClient.getClient().sendToServer(message);
        }
    }
    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        approveExamController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showExamAction(ActionEvent event) throws IOException
    {
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a subject");
            });
        }
        else if(selectCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else
        {
            readyExamList.setVisible(true);
            previewExamBtn.setVisible(true);
            Course course = getSelectCourse().getValue();
            Teacher teacher = (Teacher)SimpleClient.getClient().getUser();
            Object object = new Object[]{course, teacher};
            Message message = new Message("showReadyExamsAPP", object);
            SimpleClient.getClient().sendToServer(message);
        }

    }

    @FXML
    void initialize() throws IOException {
        approveExamController = new ApproveExamController(this);
        this.setApproveExamController(approveExamController);
        selectCourse.setVisible(true);
        showExamBtn.setVisible(true);
        previewExamBtn.setVisible(true);
        approveExamController.getSubjects();




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

    public void setApproveExamController(ApproveExamController approveExamController) {
        this.approveExamController = approveExamController;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setShowExamBtn(Button showExamBtn) {
        this.showExamBtn = showExamBtn;
    }

    public void setReadyExamList(ListView<ReadyExam> readyExamList) {
        this.readyExamList = readyExamList;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public Button getShowExamBtn() {
        return showExamBtn;
    }

    public ApproveExamController getApproveExamController() {
        return approveExamController;
    }

    public ListView<ReadyExam> getReadyExamList() {
        return readyExamList;
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

