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

public class EditQuestionController {
    EditQuestionBoundry editQuestionBoundry;
    private boolean isLogoutDialogShown = false;

    public EditQuestionController(EditQuestionBoundry editQuestionBoundry) {
        EventBus.getDefault().register(this);
        this.editQuestionBoundry = editQuestionBoundry;

    }
    public void logOut() throws IOException {
        Message msg = new Message("LogoutEQB", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("LogoutEQB")) {
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

    public void showAlertDialog(Alert.AlertType alertType, String title, String message) {
        Platform.runLater(() -> {
            Alert alert = new Alert(alertType);
            alert.setTitle(title);
            alert.setHeaderText(null);
            alert.setContentText(message);
            alert.showAndWait();
        });
    }
    public void setEditQuestionBoundry(EditQuestionBoundry editQuestionBoundry) {
        this.editQuestionBoundry = editQuestionBoundry;
    }

    public EditQuestionBoundry getEditQuestionBoundry() {
        return editQuestionBoundry;
    }

    public void getSubjects() throws IOException
    {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("getSubjectsForTeacherEQ", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjectsForTeacherEQ(GetSubjectsForTeacherEventEQ getSubjectsForTeacherEvent)
    {
        if("getQuestionsForSubject".equals(getSubjectsForTeacherEvent.getMessage().getTitle()))
        {
            List<Question> list = (List<Question>) getSubjectsForTeacherEvent.getMessage().getBody();
            ObservableList<Question> questionList = FXCollections.observableArrayList(list);
            Platform.runLater(() -> {
                if (list.isEmpty())
                {
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "No available questions for this subject");
                }
                else {
                    editQuestionBoundry.getListViewQ().setItems(questionList);
                    editQuestionBoundry.getListViewQ().setCellFactory(param -> {
                        return new EditQuestionController.QuestionListCell(false);
                    });
                }
            });
        }
        else if("getQuestionsForSubjectAndCourse".equals(getSubjectsForTeacherEvent.getMessage().getTitle()))
        {
            List<Question> list = (List<Question>) getSubjectsForTeacherEvent.getMessage().getBody();
            ObservableList<Question> questionList = FXCollections.observableArrayList(list);
            Platform.runLater(() -> {
                if (list.isEmpty())
                {
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "No available questions for this course");
                }
                else
                {
                    editQuestionBoundry.getListViewQ().setItems(questionList);
                    editQuestionBoundry.getListViewQ().setCellFactory(param -> {
                        return new EditQuestionController.QuestionListCell(false);
                    });
                }
            });
        }
        else
        {
            List<Subject> subjects = (List<Subject>)getSubjectsForTeacherEvent.getMessage().getBody();

            Platform.runLater(() -> {
                // Set the items for the ComboBox
                ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
                editQuestionBoundry.getSelectSubject().setItems(subjectObservableList);
            });
        }
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Object object = new Object[]{selectedItem, SimpleClient.getClient().getUser()};
        Message message = new Message("getCoursesForSubjectsEQ", object);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleCoursesForSubjectEQ(GetCoursesForSubjectsEventEQ getCoursesForSubjectsEvent)
    {
        List<Course> courses = (List<Course>)getCoursesForSubjectsEvent.getMessage().getBody();

        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
            editQuestionBoundry.getSelectCourse().setItems(courseObservableList);
        });
    }
//    @Subscribe
//    public void handleGetSubjects(GetSubjectsEvent getSubjectsEvent) {
//        System.out.println("fffffffffffffff");
//        List<Subject> list = (List<Subject>) getSubjectsEvent.getMessage().getBody();
//        System.out.println(list.size());
//        ObservableList<Subject> subjects = FXCollections.observableArrayList(list);
//        editQuestionBoundry.getSelectSubject().setItems(subjects);
//    }


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
                    Label questionTextLabel = new Label(question.getQText());
                    Label answer1 = new Label(question.getAnswer1());
                    Label answer2 = new Label(question.getAnswer2());
                    Label answer3 = new Label(question.getAnswer3());
                    Label answer4 = new Label(question.getAnswer4());
                    VBox questionVBox = new VBox(questionTextLabel, answer1, answer2, answer3, answer4);


                    // Add question components to container
                    container.getChildren().addAll(questionVBox);

                    setGraphic(container);
                });
            }
        }
    }
    @Subscribe
    public void handleShowQuestions(ShowQuestionsEvent showQuestionsEvent) {
        List<Question> list = (List<Question>) showQuestionsEvent.getMessage().getBody();
        ObservableList<Question> questionList = FXCollections.observableArrayList(list);
        Platform.runLater(() -> {
            editQuestionBoundry.getListViewQ().setItems(questionList);
            editQuestionBoundry.getListViewQ().setCellFactory(param -> {
                return new EditQuestionController.QuestionListCell(false);
            });
        });
    }

}

