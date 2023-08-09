package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.AquireExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.SendExamToStudentController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class SendExamToStudentBoundry {
    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    @FXML
    private ListView<ReadyExam> acquiredExams;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private Button sendToStudentsBtn;

    @FXML
    private Button showAcquiredExamsBtn;

    @FXML
    private Button showStudentsBtn;

    @FXML
    private ListView<Student> students;

    @FXML
    private Button backBtn;

    @FXML
    private Button PerviewExamBtn;
    @FXML
    private ImageView logo;


    @FXML
    private Button showCourseBtn;
    @FXML
    private Button createExamBtn;
    @FXML
    private Button createQuestionBtn;
    @FXML
    private Button editExamBtn;
    @FXML
    private Button editQuestionBtn;
    @FXML
    private Button homeBtn;
    @FXML
    private Button approveExamBtn;
    @FXML
    private Button aquireExamBtn;
    @FXML
    private Button extraTimeBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Button seeResultsBtn;
    @FXML
    private Button sendExamsToStudentsBtn;


    private SendExamToStudentController sendExamToStudentController;


    @FXML
    void SendToStudentsAction(ActionEvent event) throws IOException {
        if (acquiredExams.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select an exam");
            });
        }
        else if(students.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select at least one student");
            });
        }
        else
        {
            List<Student> studentList = students.getSelectionModel().getSelectedItems();
            List<String> list = new ArrayList<String>();
            for (Student s : studentList)
            {
                list.add(s.getUsername());
            }
            ReadyExam readyExam = acquiredExams.getSelectionModel().getSelectedItem();
            Object object = new Object[]{list, readyExam};
            //Object object = new Object[]{readyExam};
            Message message = new Message("sendToStudent", object);
            SimpleClient.getClient().sendToServer(message);
        }
    }

    @FXML
    void PerviewExamAction(ActionEvent event) throws IOException {
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a subject");
            });
        }
        else if(selectCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else if (acquiredExams.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select an exam");
            });
        }
        else
        {
            showExam();
        }
    }
    private void showExam()
    {
        ReadyExam readyExam = acquiredExams.getSelectionModel().getSelectedItem();
        Platform.runLater(() -> {
            VBox vBox = new VBox();
            HBox hBox = new HBox();
            VBox examDetails = new VBox();
            AnchorPane anchorPane1 = new AnchorPane();
            AnchorPane anchorPane2 = new AnchorPane();
            BorderPane borderPane = new BorderPane();
            Image logo = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
            ImageView imageViewLogo = new ImageView(logo);
            imageViewLogo.setFitWidth(150); // Set the width
            imageViewLogo.setFitHeight(150); // Set the height
            Font font = new Font("American Typewriter", 24);


            //Label HighSchoolNameLabel = new Label("High School Test System");
            Label courseLabel = new Label("Exam in "+ readyExam.getCourse() + " course, " + readyExam.getExam().getSubject().getName());
            Label teacherName = new Label(("Creator:" + readyExam.getExam().getTeacherFullName()));
            //HighSchoolNameLabel.setFont(font);
            //HighSchoolNameLabel.setStyle("-fx-text-fill: #87CEFA;-fx-underline: true;");

            courseLabel.setFont(font);
            courseLabel.setStyle("-fx-text-fill: #1E90FF;-fx-underline: true;");
            examDetails.getChildren().addAll(imageViewLogo, courseLabel, teacherName);
            examDetails.setAlignment(Pos.CENTER);
            borderPane.setCenter(examDetails);

            VBox studentDetails = new VBox();

            Label studentName = new Label("Student name: ");
            Label studentId = new Label("Student ID: ");
            studentName.setFont(font);
            studentId.setFont(font);

            studentName.setStyle("-fx-font-weight: bold;-fx-underline: true;");
            studentId.setStyle("-fx-font-weight: bold;-fx-underline: true;");
            studentDetails.getChildren().addAll(studentName, studentId);

            VBox questions = new VBox();
            for (Question q : readyExam.getExam().getListOfQuestions()) {

                //creating A question
                Label Qtext = new Label(q.getQText() + "(" + q.getScore() + " Points):");
                Label answer1 = new Label("a. " + q.getAnswer1());
                Label answer2 = new Label("b. " +q.getAnswer2());
                Label answer3 = new Label("c. " +q.getAnswer3());
                Label answer4 = new Label("d. " +q.getAnswer4());

                // setting font
                Qtext.setFont(font);
                answer1.setFont(font);
                answer2.setFont(font);
                answer3.setFont(font);
                answer4.setFont(font);

                VBox vBox1 = new VBox(Qtext, answer1, answer2, answer3, answer4);
                vBox1.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #87CEFA;");
                questions.getChildren().addAll(vBox1);
            }
            Region region1 = new Region();
            Region region2 = new Region();
            Region region3 = new Region();
            HBox.setHgrow(region1, Priority.ALWAYS);
            HBox.setHgrow(region2, Priority.ALWAYS);
            HBox.setHgrow(region3, Priority.ALWAYS);
            hBox.getChildren().addAll(region1, borderPane, region2);
            region3.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: #87CEFA;");
            HBox hBox1 = new HBox(region3);
            vBox.getChildren().addAll(hBox,studentDetails,hBox1, questions);
            vBox.setStyle("-fx-background-color: #ffffff");
            ScrollPane scrollPane = new ScrollPane();
            scrollPane.setContent(vBox);
            // Create a new stage and set the VBox as its root
            Stage previewStage = new Stage();
            previewStage.setScene(new Scene(scrollPane));
            previewStage.setHeight(800);
            previewStage.setWidth(800);


            // Show the stage
            previewStage.show();
        });
    }
    @FXML
    void createAnExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }  @FXML
    void createAnQuestionAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("QuestionBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void EditExamsAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void EditQuestionsAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("EditQuestion");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void approveExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ApproveExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }

    @FXML
    void aquireExamAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("AquireExamBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void extraTimeAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimeTeacher");
                System.out.println("ahmadddggg");
                Message message = new Message("GetOnGoingExamsForExtraTime", SimpleClient.getClient().getUser().getUsername());
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {

        sendExamToStudentController.logOut();
    }
    @FXML
    void seeResultsAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ViewGradesForTeacher");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });

    }
    @FXML
    void sendExamsToStudentsAction(ActionEvent event) {
        Platform.runLater(() -> {
            // Show the dialog
            sendExamToStudentController.showAlertDialog(Alert.AlertType.ERROR, "Error", "You Are Already In Send To Student Page");

        });
    }
    @FXML
    void homeBtnAction(ActionEvent event) {
        EventBus.getDefault().unregister(sendExamToStudentController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void selectCourseAction(ActionEvent event)
    {
        if (selectSubject.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a subject");
            });
        }
        acquiredExams.getItems().clear();
    }
    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException
    {
        sendExamToStudentController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showAcquiredExamsAction(ActionEvent event) throws IOException
    {
        if (selectCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else
        {
            acquiredExams.setVisible(true);
            showStudentsBtn.setVisible(true);
            Course course = getSelectCourse().getValue();
            Teacher teacher = (Teacher)SimpleClient.getClient().getUser();
            Object object = new Object[]{course, teacher};
            Message message = new Message("showReadyExams", object);
            SimpleClient.getClient().sendToServer(message);
        }
    }
    @FXML
    void showStudentsAction(ActionEvent event) throws IOException {

        if (selectCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else
        {
            Message message = new Message("showStudents", selectCourse.getSelectionModel().getSelectedItem());
            SimpleClient.getClient().sendToServer(message);
        }

    }
    @FXML
    public void initialize() throws IOException {
        sendExamToStudentController = new SendExamToStudentController(this);
        this.setSendExamToStudentController(sendExamToStudentController);
        Image logoImage = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);


        students.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        selectCourse.setVisible(true);
        showAcquiredExamsBtn.setVisible(true);
        acquiredExams.setVisible(true);
        showStudentsBtn.setVisible(true);
        students.setVisible(true);
        sendToStudentsBtn.setVisible(true);

        sendExamToStudentController.getSubjects();



        selectCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> param) {
                return new ListCell<Course>() {
                    @Override
                    protected void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        selectCourse.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Course fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });
        selectSubject.setCellFactory(new Callback<ListView<Subject>, ListCell<Subject>>() {
            @Override
            public ListCell<Subject> call(ListView<Subject> param) {
                return new ListCell<Subject>() {
                    @Override
                    protected void updateItem(Subject item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        selectSubject.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                if (subject == null) {
                    return null;
                }
                return subject.getName();
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }
        });

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
    }



    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
                "HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
    }
    public void setSendExamToStudentController(SendExamToStudentController sendExamToStudentController) {
        this.sendExamToStudentController = sendExamToStudentController;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setStudents(ListView<Student> students) {
        this.students = students;
    }

    public void setAcquiredExams(ListView<ReadyExam> acquiredExams) {
        this.acquiredExams = acquiredExams;
    }

    public void setSendToStudentsBtn(Button sendToStudentsBtn) {
        this.sendToStudentsBtn = sendToStudentsBtn;
    }

    public void setShowAcquiredExamsBtn(Button showAcquiredExamsBtn) {
        this.showAcquiredExamsBtn = showAcquiredExamsBtn;
    }

    public void setShowStudentsBtn(Button showStudentsBtn) {
        this.showStudentsBtn = showStudentsBtn;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public Button getSendToStudentsBtn() {
        return sendToStudentsBtn;
    }

    public Button getShowAcquiredExamsBtn() {
        return showAcquiredExamsBtn;
    }

    public Button getShowStudentsBtn() {
        return showStudentsBtn;
    }

    public ListView<ReadyExam> getAcquiredExams() {
        return acquiredExams;
    }

    public ListView<Student> getStudents() {
        return students;
    }

    public SendExamToStudentController getSendExamToStudentController() {
        return sendExamToStudentController;
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
