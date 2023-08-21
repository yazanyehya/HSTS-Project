

/**
 * Sample Skeleton for 'viewQuestionsBoundry.fxml' Controller Class
 */

 package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.EditQuestionController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewQuestionsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ViewQuestionsBoundry {
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

    @FXML // fx:id="listViewQuestions"
    private ListView<Question> listViewQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="selectCourse"
    private ComboBox<Course> selectCourse; // Value injected by FXMLLoader
    @FXML
    private ListView<Course> courseList;

    @FXML // fx:id="selectSubject"
    private ComboBox<Subject> selectSubject; // Value injected by FXMLLoader

    @FXML // fx:id="showQuestionsBtn"
    private Button showQuestionsBtn; // Value injected by FXMLLoader
    @FXML
    private Label timeLabel;

    private AnimationTimer animationTimer;
    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewQuestionsController);
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
                EventBus.getDefault().unregister(viewQuestionsController);
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
                EventBus.getDefault().unregister(viewQuestionsController);
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
        EventBus.getDefault().unregister(viewQuestionsController);

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
                EventBus.getDefault().unregister(viewQuestionsController);
                SimpleChatClient.switchScreen("PrincipleBoundry");
                Message newMessage = new Message("getPrincipleNotificationList", SimpleClient.getClient().getUser());
                SimpleClient.getClient().sendToServer(newMessage);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException
    {
        viewQuestionsController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewQuestionsController);
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
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewQuestionsController);
                SimpleChatClient.switchScreen("viewExamsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewQuestionsController);
                SimpleChatClient.switchScreen("viewGradesBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
//        Platform.runLater(() -> {
//            try {
//                EventBus.getDefault().unregister(viewQuestionsController);
//                SimpleChatClient.switchScreen("viewQuestionsBoundry");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in View Questions page");
        });
    }

    @FXML
    void selectCourseAction(ActionEvent event) {
        showQuestionsBtn.setVisible(true);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        viewQuestionsController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showQuestionsAction(ActionEvent event) throws IOException {
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
            Course course = getSelectCourse().getValue();
            Message message = new Message("showQuestionsForPrinciple", course);
            SimpleClient.getClient().sendToServer(message);
        }
    }

    ViewQuestionsController viewQuestionsController;
    public void setViewQuestionsController(ViewQuestionsController viewQuestionsController) {
        this.viewQuestionsController = viewQuestionsController;
    }
    public ViewQuestionsController getViewQuestionsController() {
        return viewQuestionsController;
    }

    private void populateCourseComboBox() {
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
    @FXML
    void initialize() throws IOException {

        viewQuestionsController = new ViewQuestionsController(this);
        this.setViewQuestionsController(viewQuestionsController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
        System.out.println("before getting subjects");
        viewQuestionsController.getSubjects();
        populateCourseComboBox();
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
    public void showAlertDialog(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ListView<Question> getListViewQuestions() {
        return listViewQuestions;
    }

    public void setListViewQuestions(ListView<Question> listViewQuestions) {
        this.listViewQuestions = listViewQuestions;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public Button getShowQBtn() {
        return showQuestionsBtn;
    }
    public void setShowQBtn(Button showQuestionsBtn) {
        this.showQuestionsBtn = showQuestionsBtn;
    }

    public ListView<Course> getCourseList() {
        return courseList;
    }
}