package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForStudentController;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import javafx.scene.control.cell.PropertyValueFactory;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ViewGradesForStudentBoundry {

    @FXML
    private TableColumn<ReadyExam, String> course;

    @FXML
    private TableColumn<ReadyExam, Integer> examID;

    @FXML
    private TableColumn<ReadyExam, Integer> grade;

    @FXML
    private TableColumn<ReadyExam, Button> previewOption;

    @FXML
    private TableColumn<ReadyExam, String> subject;

    @FXML
    private TableView<ReadyExam> table;

    @FXML
    private Button backBtn;


    private ViewGradesForStudentController viewGradesForStudentController;

    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewGradesForStudentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("studentBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }


    @FXML
    public void initialize()
    {
        viewGradesForStudentController = new ViewGradesForStudentController(this);
        this.setViewGradesForStudentController(viewGradesForStudentController);



    }

    public void setViewGradesForStudentController(ViewGradesForStudentController viewGradesForStudentController) {
        this.viewGradesForStudentController = viewGradesForStudentController;
    }

    public TableColumn<ReadyExam, Button> getPreviewOption() {
        return previewOption;
    }

    public TableView<ReadyExam> getTable() {
        return table;
    }

    public TableColumn<ReadyExam, Integer> getExamID() {
        return examID;
    }

    public TableColumn<ReadyExam, Integer> getGrade() {
        return grade;
    }

    public TableColumn<ReadyExam, String> getCourse() {
        return course;
    }

    public TableColumn<ReadyExam, String> getSubject() {
        return subject;
    }

    public ViewGradesForStudentController getViewGradesForStudentController() {
        return viewGradesForStudentController;
    }

    public void setGrade(TableColumn<ReadyExam, Integer> grade) {
        this.grade = grade;
    }

    public void setCourse(TableColumn<ReadyExam, String> course) {
        this.course = course;
    }

    public void setExamID(TableColumn<ReadyExam, Integer> examID) {
        this.examID = examID;
    }

    public void setPreviewOption(TableColumn<ReadyExam, Button> previewOption) {
        this.previewOption = previewOption;
    }

    public void setSubject(TableColumn<ReadyExam, String> subject) {
        this.subject = subject;
    }

    public void setTable(TableView<ReadyExam> table) {
        this.table = table;
    }
}
