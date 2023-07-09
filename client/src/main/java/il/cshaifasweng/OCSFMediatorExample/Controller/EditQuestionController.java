package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
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

    public EditQuestionController(EditQuestionBoundry editQuestionBoundry) {
        EventBus.getDefault().register(this);
        this.editQuestionBoundry = editQuestionBoundry;

    }

    public void setEditQuestionBoundry(EditQuestionBoundry editQuestionBoundry) {
        this.editQuestionBoundry = editQuestionBoundry;
    }

    public EditQuestionBoundry getEditQuestionBoundry() {
        return editQuestionBoundry;
    }

    public void getSubjects(Teacher teacher) throws IOException {
        Message message = new Message("getSubjects", teacher);
        SimpleClient.getClient().sendToServer(message);
    }

    @Subscribe
    public void handleGetSubjects(GetSubjectsEvent getSubjectsEvent) {
        System.out.println("fffffffffffffff");
        List<Subject> list = (List<Subject>) getSubjectsEvent.getMessage().getBody();
        System.out.println(list.size());
        ObservableList<Subject> subjects = FXCollections.observableArrayList(list);
        editQuestionBoundry.getSelectSubject().setItems(subjects);
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
        editQuestionBoundry.getListViewQ().setItems(questionList);
        editQuestionBoundry.getListViewQ().setCellFactory(param -> {
            return new EditQuestionController.QuestionListCell(false);
        });
    }
}

