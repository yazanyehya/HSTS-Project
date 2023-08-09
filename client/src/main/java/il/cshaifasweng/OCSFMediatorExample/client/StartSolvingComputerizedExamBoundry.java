
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StartSolvingComputerizedExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.event.ActionEvent;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;

public class StartSolvingComputerizedExamBoundry
{
    private Map<Question, RadioButton> selectedAnswersMap = new HashMap<>();

    private StartSolvingComputerizedExamController startSolvingExamController;

    private String examID;

    private String finished = "no";

    @FXML
    private VBox examContainer;

    @FXML
    private Button finishExamBtn;

    @FXML
    private Label timerLabel;

    private int examPeriod;

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;
    @FXML
    void finishExamAction(ActionEvent event) throws IOException {
        finished = "yes";
        startSolvingExamController.finish("InTime");
    }

    public Map<Question, RadioButton> getSelectedAnswersMap()
    {
        return selectedAnswersMap;
    }
    public void setSelectedAnswersMap(Map<Question, RadioButton> selectedAnswersMap)
    {
        this.selectedAnswersMap = selectedAnswersMap;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public StartSolvingComputerizedExamController getStartSolvingExamController() {
        return startSolvingExamController;
    }

    public void setExamContainer(VBox examContainer) {
        this.examContainer = examContainer;
    }

    public VBox getExamContainer() {
        return examContainer;
    }

    public int getExamPeriod() {
        return examPeriod;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public void setExamPeriod(int examPeriod) {
        this.examPeriod = examPeriod;
    }
    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void setStartSolvingExamController(StartSolvingComputerizedExamController startSolvingExamController) {
        this.startSolvingExamController = startSolvingExamController;
    }

    public String getFinished() {
        return finished;
    }

    public String getExamID() {
        return examID;
    }

    @FXML
    public void initialize()
    {
        startSolvingExamController = new StartSolvingComputerizedExamController(this);
        this.setStartSolvingExamController(startSolvingExamController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
    }


    public Button getFinishExamBtn() {
        return finishExamBtn;
    }

    public void setFinishExamBtn(Button finishExamBtn) {
        this.finishExamBtn = finishExamBtn;
    }
    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd \n" +
                "HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }

}
 