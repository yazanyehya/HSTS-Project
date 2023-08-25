package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.ExtraTime;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.RadioButton;
import javafx.scene.layout.VBox;
import javafx.util.Duration;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.util.Units;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTBorder;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTPBdr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTSectPr;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.*;
import org.apache.poi.xwpf.usermodel.*;

import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.awt.Color;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class StartSolvingManualExamController
{
    private StartSolvingManualExamBoundry startSolvingManualExamBoundry;
    int seconds = 0;

    int minutes;

    int hours;


    private Timeline timeline;


    public StartSolvingManualExamController(StartSolvingManualExamBoundry startSolvingManualExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.startSolvingManualExamBoundry = startSolvingManualExamBoundry;
    }
    public void update(int extraTimeInMinutes) {
        // Convert extra time to seconds
        int extraTimeInSeconds = extraTimeInMinutes * 60;

        // Calculate the total time in seconds for the current countdown
        int totalSecondsRemaining = (hours * 3600) + (minutes * 60) + seconds;

        // Add the extra time in seconds
        totalSecondsRemaining += extraTimeInSeconds;

        // Update hours, minutes, and seconds accordingly
        hours = totalSecondsRemaining / 3600;
        totalSecondsRemaining %= 3600;
        minutes = totalSecondsRemaining / 60;
        seconds = totalSecondsRemaining % 60;

        // Stop the timeline and update the timer label with the new value
        timeline.stop();
        startSolvingManualExamBoundry.getTimerLabel().setText(String.format("      %02d:%02d:%02d", hours, minutes, seconds));

        // Restart the timeline to continue the countdown with the new remaining time
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


    @Subscribe
    public void handleExtraTime(ExtraTimeEvent extraTimeEvent)
    {
        if (extraTimeEvent.getMessage().getTitle().equals("ApproveExtraTimeRequest"))
        {
            ExtraTime extraTime = (ExtraTime) extraTimeEvent.getMessage().getBody();
            int time = Integer.parseInt(extraTime.getTimeAmount());
            update(time);
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.WARNING, "Notice", "There have been added " + time + " more minutes");

            });
        }
    }
    @Subscribe
    public void handleEvents(StartSolvingManualExamEvent startSolvingManualExamEvent) throws IOException, InvalidFormatException {
        System.out.println("lollllllll");
        if ("saveManualExam".equals(startSolvingManualExamEvent.getMessage().getTitle()))
        {
            Platform.runLater(()->{
                EventBus.getDefault().unregister(this);
                showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam saved Successfully");
            });
        }
        else if (startSolvingManualExamEvent.getMessage().getTitle().equals("StartSolvingManualExam"))
        {
            ReadyExam readyExam = (ReadyExam) startSolvingManualExamEvent.getMessage().getBody();
            Object object = new Object[]{readyExam, SimpleClient.getClient().getUser()};

            Message message = new Message("SetOnGoingToTrue", object);
            SimpleClient.getClient().sendToServer(message);

            int extra = 0;
            if (readyExam.getExtraTimeApproved().equals("Approved"))
            {
                extra = readyExam.getAddedTime();
            }

            startSolvingManualExamBoundry.setExamPeriod(Integer.parseInt(readyExam.getExam().getExamPeriod())+extra);

            Platform.runLater(() -> {
                startSolvingManualExamBoundry.setExamID(Integer.toString(readyExam.getId()));
                int time = startSolvingManualExamBoundry.getExamPeriod();
                hours = time/ 60;
                minutes = time % 60;
                startSolvingManualExamBoundry.getTimerLabel().setText(String.format("      %02d:%02d:%02d", hours ,minutes, seconds));

                timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
                    if (minutes == 0 && seconds == 0 && hours == 0)
                    {
                        if (Objects.equals(startSolvingManualExamBoundry.getFinished(), "no"))
                        {
                            Platform.runLater(() -> {

                                showAlertDialog(Alert.AlertType.ERROR, "Error", "There is no longer time left to solve the exam");
                                try {
                                    EventBus.getDefault().unregister(this);
                                    SimpleChatClient.switchScreen("ConductAnExam");
                                    Message message2 = new Message("SetOnGoingToFalse", Integer.toString(readyExam.getId()));
                                    SimpleClient.getClient().sendToServer(message2);
                                    timeline.stop();
                                    startSolvingManualExamBoundry.getStage().close();

                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                        }
                    }

                    else {
                        // Update the timer
                        if (seconds == 0) {
                            if (minutes == 0) {
                                hours--;
                                minutes = 59;
                                seconds = 59;
                            } else {
                                minutes--;
                                seconds = 59;
                            }
                        } else {
                            seconds--;
                        }
                    }
                    // Update the timer label with the new value
                    startSolvingManualExamBoundry.getTimerLabel().setText(String.format("      %02d:%02d:%02d", hours ,minutes, seconds));
                }));
                timeline.setCycleCount(Timeline.INDEFINITE); // This will make the timeline run indefinitely until stopped manually
                timeline.play();
            });

            XWPFParagraph paragraph1 = startSolvingManualExamBoundry.getDocument().createParagraph();
            XWPFParagraph paragraph = startSolvingManualExamBoundry.getDocument().createParagraph();

            //ReadyExam readyExam = (ReadyExam) startSolvingManualExamEvent.getMessage().getBody();
            // Add questions to the Word document

            XWPFRun run1, run3;
            run3 = paragraph1.createRun();
            run3.setText("HIGH SCHOOL TEST SYSTEM");
            run3.setFontSize(40);
            run3.setText("HSTS");
            run3.setColor("e9692c");
            run3.setFontFamily("American Typewriter");

            // Create a File object with the file name

            // Get the absolute path of the file
            paragraph1.setAlignment(ParagraphAlignment.CENTER);
            run1 = paragraph.createRun();
            run1.setFontSize(20);
            run1.setBold(true);
            run1.setColor("e9692c");
            run1.setText("Student Name: " + readyExam.getFullName());
            run1.addBreak();
            run1.setText("Student ID: " + SimpleClient.getClient().getUser().getIdd());
            run1.addBreak();
            run1.addBreak();
            run1.addBreak();
            int cnt = 1;
            for (Question question : readyExam.getExam().getListOfQuestions())
            {
                XWPFRun run, run2;
                run2 = paragraph.createRun();
                run = paragraph.createRun();
                run2.setText(cnt + ". " +question.getQText());
                run.addBreak();
                run.setText("a. " + question.getAnswer1());
                run.addBreak();
                run.setText("b. " + question.getAnswer2());
                run.addBreak();
                run.setText("c. " + question.getAnswer3());
                run.addBreak();
                run.setText("d. " + question.getAnswer4());
                run.addBreak();
                cnt++;
                run.addBreak();

                run2.setBold(true);
                run2.setFontFamily("American Typewriter");
                run.setFontFamily("American Typewriter");
                run2.setFontSize(20);
                run.setFontSize(16);
            }
            System.out.println(readyExam.getExaminee() + "_" + readyExam.getId() + ".docx");
            startSolvingManualExamBoundry.setDocumentName(readyExam.getExaminee() + "_" + readyExam.getId() + ".docx");
        }
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


    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }
}
 