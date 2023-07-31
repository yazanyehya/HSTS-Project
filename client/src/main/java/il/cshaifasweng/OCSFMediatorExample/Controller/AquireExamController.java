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

public class AquireExamController
{

    private AquireExamBoundry aquireExamBoundry;
    public AquireExamController(AquireExamBoundry aquireExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.aquireExamBoundry = aquireExamBoundry;

    }

    public void getSubjects() throws IOException
    {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("getSubjectsForTeacherAE", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Message message = new Message("getCoursesAE", selectedItem);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjects(GetSubjectsForTeacherEventAE getSubjectsForTeacherEventAE)
    {
        List<Subject> subjects = (List<Subject>)getSubjectsForTeacherEventAE.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
            aquireExamBoundry.getSelectSubject().setItems(subjectObservableList);
        });
    }
    @Subscribe
    public void handleCoursesForSubjectEQ(GetCoursesAEEvent getCoursesAEEvent)
    {
        List<Course> courses = (List<Course>)getCoursesAEEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
            aquireExamBoundry.getSelectCourse().setItems(courseObservableList);
        });
    }
    private class ExamListCell extends ListCell<Exam> {
        private boolean firstRow;

        ExamListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Exam exam, boolean empty) {
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
                    Label examTextLabel1 = new Label("Creator username: " + exam.getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getCourse().getName());
                    Label examTextLabel3 = new Label("Subject: " + exam.getSubject().getName());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExamPeriod());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getId());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4, examTextLabel5);
                    // Add additional components to the examVBox if needed

                    // Add exam components to container
                    container.getChildren().addAll(examVBox);

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
    @Subscribe
    public void handleShowExamsAE(ShowExamAEEvent showExamAEEvent)
    {
        List<Exam> list = (List<Exam>) showExamAEEvent.getMessage().getBody();
        if (list.isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Exams for this course, Go create a few");
                //EventBus.getDefault().unregister(this);
            });
        }
        else {
            ObservableList<Exam> questionList = FXCollections.observableArrayList(list);
            aquireExamBoundry.getListViewE().setItems(questionList);
            aquireExamBoundry.getListViewE().setCellFactory(param -> {
                return new AquireExamController.ExamListCell(false);
            });
        }
    }
    @Subscribe
    public void handleAquireExam(AquireExamEvent aquireExamEvent)
    {
        Platform.runLater(() -> {
            // Login failure
            showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam Aquired successfully");
            //EventBus.getDefault().unregister(this);
            ReadyExam readyExam = (ReadyExam) aquireExamEvent.getMessage().getBody();
            if (readyExam == null)
            {
                System.out.println("is nulllllll");
            }
        });
    }
}