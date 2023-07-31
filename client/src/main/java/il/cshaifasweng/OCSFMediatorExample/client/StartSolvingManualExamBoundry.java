package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StartSolvingManualExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForTeacherIIController;
import javafx.fxml.FXML;

import java.io.IOException;

public class StartSolvingManualExamBoundry
{

    private StartSolvingManualExamController startSolvingManualExamController;
    @FXML
    public void initialize() throws IOException {
        startSolvingManualExamController = new StartSolvingManualExamController(this);
        this.setStartSolvingManualExamController(startSolvingManualExamController);
    }

    public StartSolvingManualExamController getStartSolvingManualExamController() {
        return startSolvingManualExamController;
    }

    public void setStartSolvingManualExamController(StartSolvingManualExamController startSolvingManualExamController) {
        this.startSolvingManualExamController = startSolvingManualExamController;
    }
}
