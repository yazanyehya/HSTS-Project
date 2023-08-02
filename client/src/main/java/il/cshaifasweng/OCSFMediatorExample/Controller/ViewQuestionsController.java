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

public class ViewQuestionsController {
    private ViewQuestionsBoundry viewQuestionsBoundry;
    
    public ViewQuestionsController(ViewQuestionsBoundry viewQuestionsBoundry)
    {
        EventBus.getDefault().register(this);
        this.viewQuestionsBoundry = viewQuestionsBoundry;
    }
    public void setViewQuestionsBoundry(ViewQuestionsBoundry viewQuestionsBoundry) {
        this.viewQuestionsBoundry = viewQuestionsBoundry;
    }

    public ViewQuestionsBoundry getViewQuestionsBoundry() {
        return viewQuestionsBoundry;
    }

    public void getSubjects() throws IOException
    {
        Principle principle = (Principle) SimpleClient.getClient().getUser();
        Message message = new Message("getSubjectsForPrinciple", principle);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjectsForPrinciple(GetSubjectsForPrincipleEvent getSubjectsForPrincipleEvent)
    {
        List<Subject> subjects = (List<Subject>)getSubjectsForPrincipleEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
            viewQuestionsBoundry.getSelectSubject().setItems(subjectObservableList);
        });
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Message message = new Message("getCoursesForSubjectsPrinciple", selectedItem);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleCoursesForSubjectForPrinciple(GetCoursesForSubjectsPrincipleEvent getCoursesForSubjectsPrincipleEvent)
    {
        List<Course> courses = (List<Course>)getCoursesForSubjectsPrincipleEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
            viewQuestionsBoundry.getSelectCourse().setItems(courseObservableList);
        });
    }
    private class QuestionListCell extends ListCell<Question> {
        private boolean firstRow;

        QuestionListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);
            if (empty || question == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    // Top row: Labels

                    // First column: Select (Checkbox)


                    // Second column: Question
                    Label questionTextLabel = new Label("Question: " + question.getQText());
                    Label answer1 = new Label("a. " + question.getAnswer1());
                    Label answer2 = new Label("b. " + question.getAnswer2());
                    Label answer3 = new Label("c. " + question.getAnswer3());
                    Label answer4 = new Label("d. " + question.getAnswer4());
                    VBox questionVBox = new VBox(questionTextLabel, answer1, answer2, answer3, answer4);


                    // Add question components to container
                    container.getChildren().addAll(questionVBox);

                    setGraphic(container);
                });
            }
        }
    }
    @Subscribe
    public void handleShowQuestions(ShowQuestionsForPrincipleEvent showQuestionsForPrincipleEvent) {
        List<Question> list = (List<Question>) showQuestionsForPrincipleEvent.getMessage().getBody();
        if (list.isEmpty()) {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Quesions for this course, Go create a few");
                //EventBus.getDefault().unregister(this);
            });
        }
        else {
            ObservableList<Question> questionList = FXCollections.observableArrayList(list);
            viewQuestionsBoundry.getListViewQuestions().setItems(questionList);
            viewQuestionsBoundry.getListViewQuestions().setCellFactory(param -> {
                return new ViewQuestionsController.QuestionListCell(false);
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

}
