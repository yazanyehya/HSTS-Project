package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ExtraTimePrincipleController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ExtraTimeTeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.ExtraTime;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.event.ActionEvent;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ExtraTimePrincipleBoundry {

    @FXML
    private TableColumn<ExtraTime, String> courseCol;

    @FXML
    private TableColumn<ExtraTime, Integer> examIDCol;

    @FXML
    private TableColumn<ExtraTime, Button> pressCol;

    @FXML
    private TableView<ExtraTime> table;

    @FXML
    private TableColumn<ExtraTime, String> teacherCol;

    @FXML
    private Button backBtn;

    private ExtraTimePrincipleController extraTimePrincipleController;

    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(extraTimePrincipleController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("PrincipleBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    public void initialize()
    {
         extraTimePrincipleController= new ExtraTimePrincipleController(this);
        this.setExtraTimePrincipleController(extraTimePrincipleController);
    }
    public TableColumn<ExtraTime, Button> getPressCol() {
        return pressCol;
    }

    public TableColumn<ExtraTime, Integer> getExamIDCol() {
        return examIDCol;
    }

    public TableColumn<ExtraTime, String> getCourseCol() {
        return courseCol;
    }

    public TableColumn<ExtraTime, String> getTeacherCol() {
        return teacherCol;
    }

    public TableView<ExtraTime> getTable() {
        return table;
    }

    public void setCourseCol(TableColumn<ExtraTime, String> courseCol) {
        this.courseCol = courseCol;
    }

    public void setTable(TableView<ExtraTime> table) {
        this.table = table;
    }

    public void setExamIDCol(TableColumn<ExtraTime, Integer> examIDCol) {
        this.examIDCol = examIDCol;
    }

    public void setPressCol(TableColumn<ExtraTime, Button> pressCol) {
        this.pressCol = pressCol;
    }

    public void setTeacherCol(TableColumn<ExtraTime, String> teacherCol) {
        this.teacherCol = teacherCol;
    }

    public void setExtraTimePrincipleController(ExtraTimePrincipleController extraTimePrincipleController) {
        this.extraTimePrincipleController = extraTimePrincipleController;
    }

}
