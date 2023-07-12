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

public class QuestionController
{
    private QuestionBoundry questionBoundry;


    public QuestionController(QuestionBoundry questionBoundry)
    {
        EventBus.getDefault().register(this);
        this.questionBoundry = questionBoundry;
    }
    public QuestionBoundry getQuestionBoundry() {
        return questionBoundry;
    }

    public void createQuestion(List<String> question)
    {
        Message msg = new Message("createQuestion", question);
        try {
            SimpleClient.getClient().sendToServer(msg);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void pressBack() throws IOException
    {
        Message message = new Message("pressBack", null);
        SimpleClient.getClient().sendToServer(message);
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
    public  void handlePressBack(PressBackEvent pressBackEvent)
    {
        EventBus.getDefault().unregister(this);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleEvent(QuestionEvent event)
    {
        if (event.getMessage().getBody() != null)
        {
            if (event.getMessage().getTitle().equals("createQuestion"))
            {
                Platform.runLater(() -> {
                    // Login success
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Question Added Successfully");
                    //EventBus.getDefault().unregister(this);
                });

            }
        }
        else {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "One of the Field is not assigned");
                //EventBus.getDefault().unregister(this);
            });
        }
    }

    public void getSubjects() throws IOException {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("getSubjectsForTeacher", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjectForTeacher(GetSubjectsForTeacherEvent getSubjectsForTeacherEvent)
    {
        List<Subject> subjects = (List<Subject>)getSubjectsForTeacherEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
            questionBoundry.getSelectSubject().setItems(subjectObservableList);
        });
    }

    public void getCourse(Subject selectedItem) throws IOException {
        Message message = new Message("getCoursesForSubjects", selectedItem);
        SimpleClient.getClient().sendToServer(message);
    }
    private class CourseListCell extends ListCell<Course> {
        private boolean firstRow;

        CourseListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Course exam, boolean empty) {
            super.updateItem(exam, empty);
            if (empty || exam == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    Label examTextLabel2 = new Label(exam.getName());
                    container.getChildren().addAll(examTextLabel2);

                    setGraphic(container);
                });
            }
        }
    }
    @Subscribe
    public void handleGetCoursesForSubject(GetCoursesForSubjectsEvent getCoursesForSubjectsEvent)
    {
        List<Course> courses = (List<Course>)getCoursesForSubjectsEvent.getMessage().getBody();
        ObservableList<Course> courseObservableList= FXCollections.observableArrayList(courses);
        questionBoundry.getCourseList().setItems(courseObservableList);
        questionBoundry.getCourseList().setCellFactory(param -> {
            return new QuestionController.CourseListCell(false);
        });
    }
}
