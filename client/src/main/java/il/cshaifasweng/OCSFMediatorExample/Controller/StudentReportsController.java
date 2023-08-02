package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.List;

public class StudentReportsController {
    private StudentReportsBoundry studentReportsBoundry;

    public StudentReportsController(StudentReportsBoundry studentReportsBoundry)
    {
        EventBus.getDefault().register(this);
        this.studentReportsBoundry = studentReportsBoundry;
    }
    public void setStudentReportsBoundry(StudentReportsBoundry studentReportsBoundry) {
        this.studentReportsBoundry = studentReportsBoundry;
    }
    public StudentReportsBoundry getStudentReportsBoundry() {
        return studentReportsBoundry;
    }

    public void getStudents() throws IOException
    {
        Principle principle = (Principle) SimpleClient.getClient().getUser();
        Message message = new Message("getStudentsForPrinciple", principle);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetStudentForPrincipleEvent(GetStudentsForPrincipleEvent getStudentsForPrincipleEvent)
    {
        List<Student> students = (List<Student>)getStudentsForPrincipleEvent.getMessage().getBody();
        ObservableList<Student> studentsObservableList= FXCollections.observableArrayList(students);
        studentReportsBoundry.getStudentsList().setItems(studentsObservableList);
        studentReportsBoundry.getStudentsList().setCellFactory(param -> {
            return new StudentsListCell(false);
        });
    }

    private class StudentsListCell extends ListCell<Student> {
        private boolean firstRow;

        StudentsListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Student student, boolean empty) {
            super.updateItem(student, empty);
            if (empty || student == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    Label examTextLabel2 = new Label(student.getFirstName() + student.getLastName());
                    container.getChildren().addAll(examTextLabel2);

                    setGraphic(container);
                });
            }
        }
    }

    public void getExams(Student selectedItem) throws IOException {
        Message message = new Message("GetExamsForStudentPrinciple", selectedItem);
        SimpleClient.getClient().sendToServer(message);
    }

    @Subscribe
    public void handleGetExamsForStudentPrinciple(GetExamsForStudentPrincipleEvent getExamsForStudentPrincipleEvent)
    {
        List<ReadyExam> exams = (List<ReadyExam>)getExamsForStudentPrincipleEvent.getMessage().getBody();

        ObservableList<ReadyExam> examObservableList = FXCollections.observableArrayList(exams);
        ObservableList<ReadyExam> test = FXCollections.observableArrayList(examObservableList);
        if (studentReportsBoundry.getListViewExams().getItems() != null)
            test.addAll(studentReportsBoundry.getListViewExams().getItems());
        studentReportsBoundry.getListViewExams().setItems(test);
        studentReportsBoundry.getListViewExams().setCellFactory(param -> {
            return new ExamListCell(false);
        });
    }
    private class ExamListCell extends ListCell<ReadyExam> {
        private boolean firstRow;

        ExamListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(ReadyExam exam, boolean empty) {
            super.updateItem(exam, empty);
            if (empty || exam == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    // Top row: Labels

                    // First column: Select (Checkbox)


                    // Second column: Exam
                    Label examTextLabel1 = new Label("Creator username: " + exam.getExam().getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getExam().getCourse().getName());
                    Label examTextLabel3 = new Label("Subject: " + exam.getExam().getSubject().getName());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExam().getExamPeriod());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getId());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4, examTextLabel5);
                    // Add additional components to the examVBox if needed

                    // Add exam components to container
                    container.getChildren().addAll(examVBox);

                    setGraphic(container);
                });
            }
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


    @Subscribe
    public void handleShowExams(ShowExamsEvent showExamsEvent)
    {
        List<ReadyExam> list = (List<ReadyExam>) showExamsEvent.getMessage().getBody();
        if (list.isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Exams for this course, Go create a few");
                //EventBus.getDefault().unregister(this);
            });
        }
        else {
            ObservableList<ReadyExam> questionList = FXCollections.observableArrayList(list);
            studentReportsBoundry.getListViewExams().setItems(questionList);
            studentReportsBoundry.getListViewExams().setCellFactory(param -> {
                return new ExamListCell(false);
            });
        }
    }


    @Subscribe
    public void handleGetExamQuestions(GetExamQuestionsEvent getExamQuestions)
    {
        System.out.println("getExamQuestions");
        ReadyExam exam = (ReadyExam)getExamQuestions.getMessage().getBody();
        List<Question> list = exam.getExam().getListOfQuestions();

        ObservableList<Question> questionList = FXCollections.observableArrayList(list);
        Platform.runLater(() -> {
            studentReportsBoundry.getListViewExamQuestions().setItems(questionList);
            studentReportsBoundry.getListViewExamQuestions().setCellFactory(param -> {
                return new ScoredQuestionListCell(false);
            });
        });
        studentReportsBoundry.getExamPeriod().setText(exam.getExam().getExamPeriod());
        studentReportsBoundry.getCommentStudet().setText(exam.getExam().getStudentComments());
        studentReportsBoundry.getCommentTeacher().setText(exam.getExam().getTeacherComments());
        String subject = exam.getExam().getSubject().getName();
        String course = exam.getCourse();
//        Platform.runLater(() -> {
//            // Set the items for the ComboBox
//            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(exam.getCourse());
//            teacherReportsBoundry.getSelectCourse().setItems(courseObservableList);
//        });
    }

    private class ScoredQuestionListCell extends ListCell<Question> {
        private TextField scoreField;
        private boolean firstRow;

        ScoredQuestionListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);
            if (empty || question == null) {
                setText(null);
                setGraphic(null);
            } else {
                System.out.println(question.getQText() + "  " + question.getScore());
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER_LEFT);
                container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");

                Label questionTextLabel = new Label("Question: " + question.getQText());
                Label answer1 = new Label("a. " + question.getAnswer1());
                Label answer2 = new Label("b. " + question.getAnswer2());
                Label answer3 = new Label("c. " + question.getAnswer3());
                Label answer4 = new Label("d. " + question.getAnswer4());
                VBox questionVBox = new VBox(questionTextLabel, answer1, answer2, answer3, answer4);

                TextField scoreField = new TextField();
                scoreField.setPrefWidth(50);
                scoreField.setText(Integer.toString(question.getScore()));
                scoreField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        int score = Integer.parseInt(newValue);
                        question.setScore(score);
                        System.out.println(question.getQText() + "  " + question.getScore());
                    } catch (NumberFormatException e) {
                        // Handle invalid input
                    }
                });

                Region region1 = new Region();
                Region region2 = new Region();
                Region region3 = new Region();
                HBox.setHgrow(region1, Priority.ALWAYS);
                HBox.setHgrow(region2, Priority.ALWAYS);
                HBox.setHgrow(region3, Priority.ALWAYS);

                container.getChildren().addAll(questionVBox, region3, scoreField);

                setGraphic(container);
            }
        }
    }

}


