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

public class EditExamController
{
    EditExamBoundry editExamBoundry;
    static private boolean flag = false;

    public EditExamController(EditExamBoundry editExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.editExamBoundry = editExamBoundry;
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
        editExamBoundry.getSelectSubject().setItems(subjects);
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
                    Label examTextLabel1 = new Label(exam.getTeacherName());
                    Label examTextLabel2 = new Label(editExamBoundry.getSelectCourse().getSelectionModel().getSelectedItem().getName());
                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel2);
                    // Add additional components to the examVBox if needed

                    // Add exam components to container
                    container.getChildren().addAll(examVBox);

                    setGraphic(container);
                });
            }
        }
    }

    @Subscribe
    public void handleShowExams(ShowExamsEvent showExamsEvent)
    {
        List<Exam> list = (List<Exam>) showExamsEvent.getMessage().getBody();
        ObservableList<Exam> questionList = FXCollections.observableArrayList(list);
        editExamBoundry.getListViewE().setItems(questionList);
        editExamBoundry.getListViewE().setCellFactory(param -> {
            return new EditExamController.ExamListCell(false);
        });
    }
}
