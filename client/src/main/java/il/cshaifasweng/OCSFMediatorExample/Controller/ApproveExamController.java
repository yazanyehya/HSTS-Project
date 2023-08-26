 package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class ApproveExamController
{
    private ApproveExamBoundry approveExamBoundry;
    private boolean isLogoutDialogShown = false;


    public ApproveExamController (ApproveExamBoundry approveExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.approveExamBoundry  = approveExamBoundry;
    }

    public void getSubjects() throws IOException
    {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("getSubjectsForTeacherAPP", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Object object = new Object[]{selectedItem, SimpleClient.getClient().getUser()};
        Message message = new Message("getCoursesAPP", object);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjects(GetSubjectsForTeacherAPPEvent getSubjectsForTeacherAPPEvent)
    {
        List<Subject> subjects = (List<Subject>)getSubjectsForTeacherAPPEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
            approveExamBoundry.getSelectSubject().setItems(subjectObservableList);
        });
    }
    @Subscribe
    public void handleCoursesForSubjectEQ(GetCoursesAPPEvent getCoursesAPPEvent)
    {
        List<Course> courses = (List<Course>)getCoursesAPPEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
            approveExamBoundry.getSelectCourse().setItems(courseObservableList);
        });
    }
    private class ExamListCell extends ListCell<ReadyExam> {
        private boolean firstRow;

        ExamListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(ReadyExam exam, boolean empty) {
            super.updateItem(exam, empty);
            if (empty || exam == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    // Top row: Labels

                    // Second column: Exam
                    Label examTextLabel1 = new Label("Creator username: " + exam.getExam().getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getExam().getCourse().getName());
                    Label examTextLabel3 = new Label("Subject: " + exam.getExam().getSubject().getName());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExam().getExamPeriod());
                    Label examTextLabel6 = new Label("Exam Type: " + exam.getExamType());
                    Label examTextLabel7 = new Label("Exam Grade: " + exam.getGrade());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getIdd());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4, examTextLabel6, examTextLabel7,examTextLabel5);
                    // Add additional components to the examVBox if needed

                    // Add exam components to container
                    container.getChildren().addAll(examVBox);

                    setGraphic(container);
                });
            }
        }
    }
    @Subscribe
    public void handleShowReadyExams(ShowReadyExamsAPPEvent showReadyExamsAPPEvent)
    {
        List<ReadyExam> list = (List<ReadyExam>) showReadyExamsAPPEvent.getMessage().getBody();
        if (list.isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Ready Exams for this course to approve");
                //EventBus.getDefault().unregister(this);
            });
        }
        else {
            ObservableList<ReadyExam> exams = FXCollections.observableArrayList(list);
            approveExamBoundry.getReadyExamList().setItems(exams);
            approveExamBoundry.getReadyExamList().setCellFactory(param -> {
                return new ApproveExamController.ExamListCell(false);
            });
        }
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
        Message msg = new Message("LogoutAP", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }



    @Subscribe
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("LogoutAP")) {
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
    public void handleTeacherEvents(TeacherEvent teacherEvent)
    {
        Platform.runLater(()->{
            Object[] objects = (Object[]) teacherEvent.getMessage().getBody();
            int id = (Integer)objects[1];
            if (id == SimpleClient.getClient().getUser().getId())
            {
                showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go and check the home page");
            }
        });
    }
    @Subscribe
    public void handleStudent(StudentEvent studentEvent)
    {

    }
}
 