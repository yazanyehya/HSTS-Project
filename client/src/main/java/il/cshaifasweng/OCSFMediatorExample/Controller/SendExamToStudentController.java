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

public class SendExamToStudentController
{
    private SendExamToStudentBoundry sendExamToStudentBoundry;
    private boolean isLogoutDialogShown = false;

    public SendExamToStudentController(SendExamToStudentBoundry sendExamToStudentBoundry)
    {
        EventBus.getDefault().register(this);
        this.sendExamToStudentBoundry = sendExamToStudentBoundry;
    }
    public void logOut() throws IOException {
        Message msg = new Message("LogoutSE", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }



    @Subscribe
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("LogoutSE")) {
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
    public void getSubjects() throws IOException
    {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("getSubjectsForTeacherSE", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Object object = new Object[]{selectedItem, SimpleClient.getClient().getUser()};
        Message message = new Message("getCoursesSE", object);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjects(GetSubjectsForTeacherSEEvent getSubjectsForTeacherSEEvent)
    {
        List<Subject> subjects = (List<Subject>)getSubjectsForTeacherSEEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
            sendExamToStudentBoundry.getSelectSubject().setItems(subjectObservableList);
        });
    }
    @Subscribe
    public void handleCoursesForSubjectEQ(GetCoursesSEEvent getCoursesSEEvent)
    {
        List<Course> courses = (List<Course>)getCoursesSEEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
            sendExamToStudentBoundry.getSelectCourse().setItems(courseObservableList);
        });
    }
    @Subscribe
    public void handleShowReadyExams(ShowReadyExamsEvent showReadyExamsEvent)
    {
        List<ReadyExam> list = (List<ReadyExam>) showReadyExamsEvent.getMessage().getBody();
        if (list.isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Acquired Exams for this course, Go Acquire a few");
                //EventBus.getDefault().unregister(this);
            });
        }
        else {
            ObservableList<ReadyExam> questionList = FXCollections.observableArrayList(list);
            sendExamToStudentBoundry.getAcquiredExams().setItems(questionList);
            sendExamToStudentBoundry.getAcquiredExams().setCellFactory(param -> {
                return new SendExamToStudentController.ExamListCell(false);
            });
        }
    }
    @Subscribe
    public void handleShowStudents(ShowStudentsEvent showStudentsEvent)
    {
        List<Student> students = (List<Student>) showStudentsEvent.getMessage().getBody();
        if (students.isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no students registered for this course!");
                //EventBus.getDefault().unregister(this);
            });
        }
        else
        {
            ObservableList<Student> studentsList = FXCollections.observableArrayList(students);
            sendExamToStudentBoundry.getStudents().setItems(studentsList);
            sendExamToStudentBoundry.getStudents().setCellFactory(param -> {
                return new SendExamToStudentController.StudentList();
            });
        }
    }
    @Subscribe
    public void handleSendToStudent(SendToStudentEvent sendToStudentEvent)
    {
        if(sendToStudentEvent.getMessage().getBody() != null)
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam sent Successfully");
            });
        }
        else
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Exam already sent to student");
            });
        }

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

                    // First column: Select (Checkbox)


                    // Second column: Exam
                    Label examTextLabel1 = new Label("Creator username: " + exam.getExam().getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getExam().getCourse().getName());
                    Label examTextLabel3 = new Label("Subject: " + exam.getExam().getSubject().getName());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExam().getExamPeriod());
                    Label examTextLabel6 = new Label("Exam Type: " + exam.getExamType());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getIdd());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4, examTextLabel6,examTextLabel5);
                    // Add additional components to the examVBox if needed

                    // Add exam components to container
                    container.getChildren().addAll(examVBox);

                    setGraphic(container);
                });
            }
        }
    }
    private class StudentList extends ListCell<Student> {
        private boolean firstRow;


        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);
            if (empty || student == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    // Top row: Labels

                    // First column: Select (Checkbox)


                    // Second column: Exam
                    Label examTextLabel1 = new Label("Name: "+student.getFirstName() + " " + student.getLastName() + ", Id: " +student.getId()+".");


                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox studentVbox = new VBox(examTextLabel1);
                    // Add additional components to the examVBox if needed

                    // Add exam components to container
                    container.getChildren().addAll(studentVbox);

                    setGraphic(container);
                });
            }
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
}

