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

public class ViewGradesForTeacherController
{
    private ViewGradesForTeacherBoundry viewGradesForTeacherBoundry;

    public ViewGradesForTeacherController(ViewGradesForTeacherBoundry viewGradesForTeacherBoundry)
    {
        EventBus.getDefault().register(this);
        this.viewGradesForTeacherBoundry = viewGradesForTeacherBoundry;
    }
    public void getSubjects() throws IOException
    {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("viewGradesForTeacherSubjects", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Message message = new Message("viewGradesForTeacherCourses", selectedItem);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjects(ViewGradesForTeacherEvent viewGradesForTeacherEvent)
    {
        if ("viewGradesForTeacherSubjects".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<Subject> subjects = (List<Subject>)viewGradesForTeacherEvent.getMessage().getBody();

            Platform.runLater(() -> {
                // Set the items for the ComboBox
                ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
                viewGradesForTeacherBoundry.getSelectSubject().setItems(subjectObservableList);
            });
        }
        else if("viewGradesForTeacherCourses".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<Course> courses = (List<Course>)viewGradesForTeacherEvent.getMessage().getBody();

            Platform.runLater(() -> {
                // Set the items for the ComboBox
                ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
                viewGradesForTeacherBoundry.getSelectCourse().setItems(courseObservableList);
            });
        }
        else if("showExamsForTeacherCourses".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<Exam> list = (List<Exam>) viewGradesForTeacherEvent.getMessage().getBody();
            if (list.isEmpty())
            {
                Platform.runLater(() -> {
                    // Login failure
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Exams For this Course");
                    //EventBus.getDefault().unregister(this);
                });
            }
            else {
                ObservableList<Exam> exams = FXCollections.observableArrayList(list);
                viewGradesForTeacherBoundry.getExamList().setItems(exams);
                viewGradesForTeacherBoundry.getExamList().setCellFactory(param -> {
                    return new ViewGradesForTeacherController.ExamListCell(false);
                });
            }
        }
        else if("ShowExamsForTeacherSubjects".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<Exam> list = (List<Exam>) viewGradesForTeacherEvent.getMessage().getBody();
            if (list.isEmpty())
            {
                Platform.runLater(() -> {
                    // Login failure
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Exams For this Subject");
                    //EventBus.getDefault().unregister(this);
                });
            }
            else {
                ObservableList<Exam> exams = FXCollections.observableArrayList(list);
                viewGradesForTeacherBoundry.getExamList().setItems(exams);
                viewGradesForTeacherBoundry.getExamList().setCellFactory(param -> {
                    return new ViewGradesForTeacherController.ExamListCell(false);
                });
            }
        }

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

                    // Second column: Exam
                    Label examTextLabel1 = new Label("Creator username: " + exam.getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getCourse().getName());
                    Label examTextLabel3 = new Label("Subject: " + exam.getSubject().getName());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExamPeriod());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getId());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4,examTextLabel5);
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
}
