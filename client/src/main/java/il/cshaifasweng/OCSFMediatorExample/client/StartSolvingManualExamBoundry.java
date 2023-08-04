package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StartSolvingManualExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.greenrobot.eventbus.EventBus;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StartSolvingManualExamBoundry {

    @FXML
    private Button downloadBtn;

    @FXML
    private Button submitBtn;

    private StartSolvingManualExamController startSolvingManualExamController;

    private XWPFDocument document;

    private String documentName;

    @FXML
    private Button backBtn;

    @FXML
    private Label timerLabel;

    @FXML
    private Button addTimeBtn;

    private int examPeriod;

    private String examID;
    private String finished = "no";

    private String downloaded = "no";
    @FXML
    void downloadAction(ActionEvent event)
    {
        downloaded = "yes";
        List<String> questions = new ArrayList<>();
        questions.add("Question 1: What is the capital of France?");
        questions.add("Question 2: What is 2 + 2?");
        // Add more questions as needed...

        try {
            // Save the Word document to a file
            File file = new File("target/" + documentName);
            FileOutputStream out = new FileOutputStream(file);
            document.write(out);
            out.close();

            System.out.println("Word file downloaded successfully.");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    @FXML
    void addTimeAction(ActionEvent event)
    {
        startSolvingManualExamController.update(3);
    }

    @FXML
    void submitAction(ActionEvent event)
    {
        if (downloaded == "no")
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "You must download the exam before the submittion");
            });
        }
        else {
            try {
                finished = "yes";
                // Read the Word document that the user has filled out
                Object object = examID;
                Message message2 = new Message("SetOnGoingToFalse", object);
                SimpleClient.getClient().sendToServer(message2);
                File file = new File("target/" + documentName);
                XWPFDocument document = new XWPFDocument(new FileInputStream(file));

                // Extract the answers from the Word document
                List<String> answers = new ArrayList<>();
                for (XWPFParagraph paragraph : document.getParagraphs()) {
                    String answer = paragraph.getText();
                    // Add some logic to filter out the questions and only get the answers.
                    // You might need to implement custom logic here based on the structure of your Word document.
                    // For example, you can use some keywords to identify the answers.

                    // Add the extracted answer to the answers list
                    answers.add(answer);
                }

                // Process the answers as needed
                // For example, you might want to compare the answers with the correct answers, calculate the score, etc.
                // Implement your custom logic here.

                // Print the extracted answers (just for demonstration purposes)
                System.out.println("Extracted Answers:");
                for (String answer : answers) {
                    System.out.println(answer);
                }

                // Close the document
                document.close();
                EventBus.getDefault().unregister(startSolvingManualExamController);
                Platform.runLater(() -> {
                    try {
                        SimpleChatClient.switchScreen("ConductAnExam");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });

                // Implement your custom logic for handling the submitted answers here

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void initialize() throws IOException {
        startSolvingManualExamController = new StartSolvingManualExamController(this);
        this.setStartSolvingManualExamController(startSolvingManualExamController);

        this.document = new XWPFDocument();
    }

    public StartSolvingManualExamController getStartSolvingManualExamController() {
        return startSolvingManualExamController;
    }

    public void setStartSolvingManualExamController(StartSolvingManualExamController startSolvingManualExamController) {
        this.startSolvingManualExamController = startSolvingManualExamController;
    }

    public XWPFDocument getDocument() {
        return document;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public void setDocument(XWPFDocument document) {
        this.document = document;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public int getExamPeriod() {
        return examPeriod;
    }

    public void setTimerLabel(Label timerLabel) {
        this.timerLabel = timerLabel;
    }

    public void setExamPeriod(int examPeriod) {
        this.examPeriod = examPeriod;
    }

    public String getExamID() {
        return examID;
    }

    public void setExamID(String examID) {
        this.examID = examID;
    }

    public String getFinished() {
        return finished;
    }

    public void setFinished(String finished) {
        this.finished = finished;
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
}

