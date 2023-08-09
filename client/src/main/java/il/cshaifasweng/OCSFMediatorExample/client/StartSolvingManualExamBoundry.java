
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StartSolvingManualExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.greenrobot.eventbus.EventBus;


import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.Transferable;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    private File selectedFile = null;
    private boolean fileSelected = false;

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    private Stage stage = new Stage();
    @FXML
    void downloadAction(ActionEvent event)
    {

    }


    @FXML
    void addTimeAction(ActionEvent event)
    {
        startSolvingManualExamController.update(3);
    }

    @FXML
    void submitAction(ActionEvent event)
    {

    }


    @FXML
    public void initialize() throws IOException {
        startSolvingManualExamController = new StartSolvingManualExamController(this);
        this.setStartSolvingManualExamController(startSolvingManualExamController);

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();

        this.document = new XWPFDocument();
        downloadBtn.setOnAction(event -> {
            downloaded = "yes";
            handleDownload();
        });

        submitBtn.setOnAction(event -> {
           if (downloaded.equals("yes"))
           {
               askForSubmissionMethod();
           }
           else
           {
               Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
               confirmationAlert.setTitle("Confirm Submission");
               confirmationAlert.setHeaderText("Confirm Submission");
               confirmationAlert.setContentText("Are you sure you want to submit without adding the file to the console");

               confirmationAlert.getDialogPane().getStylesheets().add(getClass().getResource("Sbutton.css").toExternalForm());
               confirmationAlert.showAndWait().ifPresent(response -> {
                   if (response == ButtonType.OK) {
                       Platform.runLater(() -> {
                           try {
                               startSolvingManualExamController.getTimeline().stop();
                               EventBus.getDefault().unregister(startSolvingManualExamController);
                               SimpleChatClient.switchScreen("ConductAnExam");
                               Object object1 = new Object[]{selectedFile, Integer.parseInt(examID), SimpleClient.getClient().getUser()};
                               Message message = new Message("saveManualExam", object1);
                               SimpleClient.getClient().sendToServer(message);
                           } catch (IOException e) {
                               e.printStackTrace();
                           }
                       });
                       stage.close();
                   }
               });
           }
        });


    }
    private void askForSubmissionMethod() {
        // Show options for submission

        VBox vbox = new VBox();
        Button dragButton = new Button("Drag the File");
        Button browseButton = new Button("Browse File");
        dragButton.getStyleClass().add("button");
        browseButton.getStyleClass().add("button");
        Region region1 = new Region();
        Region region2 = new Region();
        Region region3 = new Region();
        Region region4 = new Region();
        HBox.setHgrow(region1, Priority.ALWAYS);
        HBox.setHgrow(region2, Priority.ALWAYS);
        HBox.setHgrow(region3, Priority.ALWAYS);
        HBox.setHgrow(region4, Priority.ALWAYS);

        Region region5 = new Region();
        VBox.setVgrow(region5, Priority.ALWAYS);
        HBox hBox = new HBox(region1, dragButton, region2);
        HBox hBox1 = new HBox(region3, browseButton, region4);
        vbox.getChildren().addAll(dragButton, region5,browseButton);


        dragButton.setOnAction(e -> {
            stage.close();
            handleDragAndDrop(stage);
        });
        browseButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.setTitle("Select Manual Exam File");
            fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx"));
            selectedFile = fileChooser.showOpenDialog(stage);

            if (selectedFile != null) {
                fileSelected = true;
                //showFileDetails();
                askForSubmissionConfirmation();
            }
        });

        vbox.setStyle("-fx-border-width: 4");
        vbox.setStyle("-fx-border-color: #e9692c");
        vbox.setStyle("-fx-border-radius: 90");

        
        Scene scene = new Scene(vbox, 200, 200);
        scene.getStylesheets().add(getClass().getResource("Sbutton.css").toExternalForm());

        stage.setScene(scene);
        stage.show();
    }

    // Method to handle the drag and drop behavior
    private void handleDragAndDrop(Stage stage) {
        Label draggedFileLabel = new Label("Drag a file here");
        VBox vbox = new VBox(draggedFileLabel);
        vbox.setOnDragOver(event -> {
            Dragboard db = event.getDragboard();
            if (db.hasFiles()) {
                event.acceptTransferModes(TransferMode.COPY);
            } else {
                event.consume();
            }
        });
        vbox.setOnDragDropped(event -> {
            Dragboard db = event.getDragboard();
            boolean success = false;
            if (db.hasFiles()) {
                selectedFile = db.getFiles().get(0);
                draggedFileLabel.setText("Dragged File: " + selectedFile.getName());
                success = true;
            }
            event.setDropCompleted(success);
            event.consume();
            fileSelected = true;
            //showFileDetails();
            askForSubmissionConfirmation();
            stage.close();
        });

        Scene scene = new Scene(vbox, 400, 100);
        stage.setScene(scene);
        stage.show();
    }

//    // Method to show the file details (logo and name)
//    private void showFileDetails() {
//        // Show the file details to the user (optional)
//        showAlert(Alert.AlertType.INFORMATION, "Selected File", "You have selected: " + selectedFile.getName());
//        // Show the file logo (optional)
//        Image image = new Image(getClass().getResourceAsStream("/images/word_image.png"));
//        ImageView imageView = new ImageView(image); // Replace with your image path
//        VBox vbox = new VBox(imageView, new Label(selectedFile.getName()));
//        Scene scene = new Scene(vbox, 300, 100);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
//    }

    // Method to ask for submission confirmation
    private void askForSubmissionConfirmation() {
        if (fileSelected) {
            Alert confirmationAlert = new Alert(Alert.AlertType.CONFIRMATION);
            confirmationAlert.setTitle("Confirm Submission");
            confirmationAlert.setHeaderText("Confirm Submission");
            confirmationAlert.setContentText("Are you sure you want to submit the selected file?");
            confirmationAlert.getDialogPane().getStylesheets().add(getClass().getResource("Sbutton.css").toExternalForm());
            ImageView imageView = new ImageView("/images/word_image.png");
            imageView.setFitHeight(20);
            imageView.setFitWidth(20);
            confirmationAlert.getDialogPane().setGraphic(imageView); // Replace with your image path
            confirmationAlert.getDialogPane().setMinWidth(20);
            confirmationAlert.getDialogPane().setMinHeight(20);
            confirmationAlert.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    handleSubmission();
                    stage.close();
                }
            });
        } else {
            showAlert(Alert.AlertType.WARNING, "No File Selected", "Please select a file before submitting.");
        }
    }

    // Method to handle the file submission
    private void handleSubmission() {
        // Now, you can use the selectedFile to process the submission.
        if (selectedFile != null) {
            try (FileInputStream inputStream = new FileInputStream(selectedFile)) {
                Platform.runLater(() -> {
                    try {
                        startSolvingManualExamController.getTimeline().stop();
                        EventBus.getDefault().unregister(startSolvingManualExamController);
                        SimpleChatClient.switchScreen("ConductAnExam");
                        Object object1 = new Object[]{selectedFile, Integer.parseInt(examID), SimpleClient.getClient().getUser()};
                        Message message = new Message("saveManualExam", object1);
                        SimpleClient.getClient().sendToServer(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
                // Read the content of the file
                // Process the file content as needed
                // For demonstration purposes, let's just print the file name
                System.out.println("File submitted: " + selectedFile.getName());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void handleDownload() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Save Manual Exam File");
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("Word Document (*.docx)", "*.docx"));
        selectedFile = fileChooser.showSaveDialog(downloadBtn.getScene().getWindow());

        if (selectedFile != null) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(selectedFile)) {
                document.write(fileOutputStream);
                showAlert(Alert.AlertType.INFORMATION, "Download Successful", "File downloaded successfully.");
            } catch (IOException e) {
                showAlert(Alert.AlertType.ERROR, "Error", "Failed to save the file.");
                e.printStackTrace();
            }
        }
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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public Stage getStage() {
        return stage;
    }

    public void setStage(Stage stage) {
        this.stage = stage;
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


 