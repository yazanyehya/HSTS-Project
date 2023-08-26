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
    private boolean isLogoutDialogShown = false;
    public void logOut() throws IOException {
        Message msg = new Message("Logout principle", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void handleLogoutEvent(PrincipleLogoutEvent principleLogoutEvent) {
        System.out.println("logout platform");

        if (principleLogoutEvent.getMessage().getTitle().equals("Logout principle")) {
            System.out.println("LOAI");
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
        Object object = new Object[]{selectedItem};
        Message message = new Message("getCoursesForSubjectsPrinciple", object);
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
    @Subscribe
    public void handllle(PrincipleEvent principleEvent)
    {
        Platform.runLater(()->{
            Object[] objects = (Object[]) principleEvent.getMessage().getBody();
            List<Notification> list = (List<Notification>) objects[0];
            int id = (Integer)objects[1];
            if (id == SimpleClient.getClient().getUser().getId())
            {
                showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go and check the home page");
            }
        });
    }
}
 