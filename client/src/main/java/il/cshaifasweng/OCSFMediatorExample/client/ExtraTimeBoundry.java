package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ExtraTimeController;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ExtraTimeBoundry {

    @FXML
    private TableColumn<ReadyExam, Button> PressForExtraTimeCol;

    @FXML
    private Button backBtn;

    @FXML
    private TableColumn<ReadyExam, String> courseCol;

    @FXML
    private TableColumn<ReadyExam, Integer> examIdCol;

    @FXML
    private TableColumn<ReadyExam, Integer> numberOfExaminees;


    @FXML
    private TableView<ReadyExam> table;


    private ExtraTimeController extraTimeController;

    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(extraTimeController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    public void initialize()
    {
        extraTimeController = new ExtraTimeController(this);
        this.setExtraTimeController(extraTimeController);
    }

    public void setTable(TableView<ReadyExam> table) {
        this.table = table;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setCourseCol(TableColumn<ReadyExam, String> courseCol) {
        this.courseCol = courseCol;
    }

    public void setExamIdCol(TableColumn<ReadyExam, Integer> examIdCol) {
        this.examIdCol = examIdCol;
    }


    public void setExtraTimeController(ExtraTimeController extraTimeController) {
        this.extraTimeController = extraTimeController;
    }

    public void setPressForExtraTimeCol(TableColumn<ReadyExam, Button> pressForExtraTimeCol) {
        PressForExtraTimeCol = pressForExtraTimeCol;
    }

    public void setNumberOfExaminees(TableColumn<ReadyExam, Integer> numberOfExaminees) {
        this.numberOfExaminees = numberOfExaminees;
    }

    public TableColumn<ReadyExam, String> getCourseCol() {
        return courseCol;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public TableView<ReadyExam> getTable() {
        return table;
    }

    public ExtraTimeController getExtraTimeController() {
        return extraTimeController;
    }

    public TableColumn<ReadyExam, Button> getPressForExtraTimeCol() {
        return PressForExtraTimeCol;
    }

    public TableColumn<ReadyExam, Integer> getExamIdCol() {
        return examIdCol;
    }

    public TableColumn<ReadyExam, Integer> getNumberOfExaminees() {
        return numberOfExaminees;
    }
}
