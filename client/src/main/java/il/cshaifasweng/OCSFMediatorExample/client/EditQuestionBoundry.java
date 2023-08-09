package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.Controller.EditQuestionController;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
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

public class EditQuestionBoundry {

    @FXML
    private Button backBtn;

    @FXML
    private Button editBtn;

    @FXML
    private ListView<Question> listViewQ;

    @FXML
    private ListView<Course> courseList;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private ComboBox<Course> selectCourse;
    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    @FXML
    private Button showQBtn;
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


    EditQuestionController editQuestionController;

    public void setEditQuestionController(EditQuestionController editQuestionController) {
        this.editQuestionController = editQuestionController;
    }

    public EditQuestionController getEditQuestionController() {
        return editQuestionController;
    }

    @FXML
    void createAnExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(editQuestionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }  @FXML
    void createAnQuestionAction(ActionEvent event) {
        EventBus.getDefault().unregister(editQuestionController);

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
        EventBus.getDefault().unregister(editQuestionController);

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
        Platform.runLater(() -> {
            // Show the dialog
            editQuestionController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Edit Question Page");

        });
    }

    @FXML
    void approveExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(editQuestionController);

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
        EventBus.getDefault().unregister(editQuestionController);

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
        EventBus.getDefault().unregister(editQuestionController);
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
       editQuestionController.logOut();
    }
    @FXML
    void seeResultsAction(ActionEvent event) {
        EventBus.getDefault().unregister(editQuestionController);

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
        EventBus.getDefault().unregister(editQuestionController);

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
        EventBus.getDefault().unregister(editQuestionController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("TeacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    private void populateCourseComboBox() {
        //Teacher teacher = (Teacher) SimpleClient.getClient().getUser();

        // Set the cell factory to display the course name

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
    void initialize() throws IOException {

        editQuestionController = new EditQuestionController(this);
        this.setEditQuestionController(editQuestionController);
        Image logoImage = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);
        System.out.println("before getting subjects");
        editQuestionController.getSubjects();
        populateCourseComboBox();

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
    void EditAction(ActionEvent event)
    {
        if (getListViewQ().getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select A Question!");
            });
        }
        else
        {
            Platform.runLater(() -> {
                try {
                    SimpleChatClient.switchScreen("EditSelectedQuestion");
                    Object object = new Object[]{selectSubject.getSelectionModel().getSelectedItem(), getListViewQ().getSelectionModel().getSelectedItem()};
                    Message message = new Message("editSelectedQuestion",object);
                    SimpleClient.getClient().sendToServer(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    void backAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
                EventBus.getDefault().unregister(editQuestionController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void selectCourseAction(ActionEvent event) throws IOException {
        showQBtn.setVisible(true);
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a subject");
            });
        }
        else
        {
            Message message = new Message("getQuestionsForSubjectAndCourse", selectCourse.getSelectionModel().getSelectedItem());
            SimpleClient.getClient().sendToServer(message);
        }
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        editQuestionController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
        Message message = new Message("getQuestionsForSubject", selectSubject.getSelectionModel().getSelectedItem());
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    void showQuestionsAction(ActionEvent event) throws IOException
    {
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a subject");
            });
        }
        else if (selectCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else
        {
            Course course = getSelectCourse().getValue();
            Message message = new Message("showQuestions", course);
            SimpleClient.getClient().sendToServer(message);
        }
    }

    public Button getShowQBtn() {
        return showQBtn;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ListView<Question> getListViewQ() {
        return listViewQ;
    }

    public void setListViewQ(ListView<Question> listViewQ) {
        this.listViewQ = listViewQ;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setShowQBtn(Button showQBtn) {
        this.showQBtn = showQBtn;
    }

    public ListView<Course> getCourseList() {
        return courseList;
    }
}
