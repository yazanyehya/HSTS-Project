package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.StartSolvingManualExamBoundry;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;

import java.io.File;

public class StartSolvingManualExamController
{
    private StartSolvingManualExamBoundry startSolvingManualExamBoundry;

    @FXML
    private VBox examContent; // Assuming this is your exam content represented by a VBox

    private File solvedExamFile; // Store the solved exam file

    @FXML
    void handleDownloadExam(ActionEvent event) {
        // Step 1: Generate the Word File with the Exam
        // Similar to the previous example, generate the Word document
        // containing the exam content and allow the user to download it.
        // Use Apache POI or any other library for creating Word documents.

        // Code to generate and download the Word document...

        // For example, save the generated Word file in the variable solvedExamFile
        solvedExamFile = new File("path/to/generated/exam.docx");
    }
    public StartSolvingManualExamController(StartSolvingManualExamBoundry startSolvingManualExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.startSolvingManualExamBoundry = startSolvingManualExamBoundry;
    }
}
