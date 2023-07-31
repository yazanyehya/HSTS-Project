package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForTeacherController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForTeacherIIController;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ViewGradesForTeacherIIBoundry {

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<ReadyExam, Integer> gradeCol;

    @FXML
    private TableColumn<ReadyExam, Button> previewCol;

    @FXML
    private TableColumn<ReadyExam, Integer> studentIDCol;

    @FXML
    private TableColumn<ReadyExam, String> studentNameCol;

    @FXML
    private TableView<ReadyExam> table;

    private ViewGradesForTeacherIIController viewGradesForTeacherIIController;

    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewGradesForTeacherIIController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForTeacher");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void initialize() throws IOException {
        viewGradesForTeacherIIController = new ViewGradesForTeacherIIController(this);
        this.setViewGradesForTeacherIIController(viewGradesForTeacherIIController);
    }

    public void setViewGradesForTeacherIIController(ViewGradesForTeacherIIController viewGradesForTeacherIIController) {
        this.viewGradesForTeacherIIController = viewGradesForTeacherIIController;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public TableView<ReadyExam> getTable() {
        return table;
    }

    public TableColumn<ReadyExam, Button> getPreviewCol() {
        return previewCol;
    }

    public TableColumn<ReadyExam, Integer> getGradeCol() {
        return gradeCol;
    }

    public TableColumn<ReadyExam, Integer> getStudentIDCol() {
        return studentIDCol;
    }

    public TableColumn<ReadyExam, String> getStudentNameCol() {
        return studentNameCol;
    }

    public ViewGradesForTeacherIIController getViewGradesForTeacherIIController() {
        return viewGradesForTeacherIIController;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setTable(TableView<ReadyExam> table) {
        this.table = table;
    }

    public void setGradeCol(TableColumn<ReadyExam, Integer> gradeCol) {
        this.gradeCol = gradeCol;
    }

    public void setPreviewCol(TableColumn<ReadyExam, Button> previewCol) {
        this.previewCol = previewCol;
    }

    public void setStudentIDCol(TableColumn<ReadyExam, Integer> studentIDCol) {
        this.studentIDCol = studentIDCol;
    }

    public void setStudentNameCol(TableColumn<ReadyExam, String> studentNameCol) {
        this.studentNameCol = studentNameCol;
    }
}
