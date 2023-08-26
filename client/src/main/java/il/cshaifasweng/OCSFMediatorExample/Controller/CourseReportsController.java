package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.chart.BarChart;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.*;


public class CourseReportsController {
    private CourseReportsBoundry courseReportsBoundry;
    private Integer current_avg = 0;
    private Integer size = 0;
    private Map< Integer,List<CourseReportsController.GradeData>> map = new HashMap<>();
    private Map<List<Integer>, Integer> median_map = new HashMap<>();
    public CourseReportsController(CourseReportsBoundry courseReportsBoundry)
    {
        EventBus.getDefault().register(this);
        this.courseReportsBoundry = courseReportsBoundry;
    }
    public void setCourseReportsBoundry(CourseReportsBoundry courseReportsBoundry) {
        this.courseReportsBoundry = courseReportsBoundry;
    }
    public CourseReportsBoundry getCourseReportsBoundry() {
        return courseReportsBoundry;
    }

    private boolean isLogoutDialogShown = false;
    public void logOut() throws IOException {
        Message msg = new Message("Logout principle", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }
    @Subscribe
    public void handleLogoutEvent(PrincipleLogoutEvent principleLogoutEvent) {
        System.out.println("logout platform");

        if (principleLogoutEvent.getMessage().getTitle().equals("Logout principle")) {
            System.out.println("LOAI");
            if (!isLogoutDialogShown) {
                isLogoutDialogShown = true;

                Platform.runLater(() -> {
                    // Show the dialog
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "You Logged out");
                    isLogoutDialogShown = false;
                });
            }

            // Unregister this class from the EventBus
            EventBus.getDefault().unregister(this);

            try {
                Platform.runLater(() -> {
                    try {
                        SimpleChatClient.switchScreen("LoginController");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            } catch (IllegalStateException e) {
                e.printStackTrace();
            }
        }
    }
    @Subscribe
    public void handleGetCoursesForPrincipleEvent(GetCoursesForPrincipleEvent getCoursesForPrincipleEvent)
    {
        if ("getListGradeForCourse".equals(getCoursesForPrincipleEvent.getMessage().getTitle()))
        {
            Object[] objects = (Object[]) getCoursesForPrincipleEvent.getMessage().getBody();
            List<Integer> list = (List<Integer>)objects[0];
            System.out.println("before addeding to median_list");
            int id = (Integer)objects[1];
            List<Integer> median_list = new ArrayList<>();
            for (Integer integer : list)
            {
                median_list.add(integer);
                System.out.println("addeding to median_list");
            }
            System.out.println("after addeding to median_list");
            median_map.put(median_list,id);

        }
        else if ("getReadyExamsForCoursesReports".equals(getCoursesForPrincipleEvent.getMessage().getTitle()))
        {
            System.out.println("in here getReadyExamsForCoursesReports sunscribe    " + courseReportsBoundry.temp);

            List<ReadyExam> readyExamList = (List<ReadyExam>)getCoursesForPrincipleEvent.getMessage().getBody();
            fillChartForExam(readyExamList);

        }
        else
        {
            Platform.runLater(()->{
                List<Course> courses = (List<Course>)getCoursesForPrincipleEvent.getMessage().getBody();
                ObservableList<Course> coursesObservableList= FXCollections.observableArrayList(courses);
                courseReportsBoundry.getCoursesList().setItems(coursesObservableList);
                courseReportsBoundry.getCoursesList().setCellFactory(param -> {
                    return new CoursesListCell(false);
                });
            });
        }
    }

    public void getResults(ReadyExam exam)
    {
        Message message = new Message("getReadyExamsForCoursesReports", exam);
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public class GradeData {
        private String gradeRange;
        private int numExams;

        public GradeData(String gradeRange, int numExams) {
            this.gradeRange = gradeRange;
            this.numExams = numExams;
        }

        public String getGradeRange() {
            return gradeRange;
        }

        public int getNumExams() {
            return numExams;
        }
    }
    void fillChartForExam(List<ReadyExam> selectedItems) {

        if (selectedItems.isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no available executed exams for this exam");
            });
        }
        else
        {
            List<CourseReportsController.GradeData> list = new ArrayList<>();
            list.add(new CourseReportsController.GradeData("0-10", 0));
            list.add(new CourseReportsController.GradeData("11-20", 0));
            list.add(new CourseReportsController.GradeData("21-30", 0));
            list.add(new CourseReportsController.GradeData("31-40", 0));
            list.add(new CourseReportsController.GradeData("41-50", 0));
            list.add(new CourseReportsController.GradeData("51-60", 0));
            list.add(new CourseReportsController.GradeData("61-70", 0));
            list.add(new CourseReportsController.GradeData("71-80", 0));
            list.add(new CourseReportsController.GradeData("81-90", 0));
            list.add(new CourseReportsController.GradeData("91<", 0));

            for (ReadyExam readyExam : selectedItems) {
                int grade = readyExam.getGrade();
                if (grade >= 0 && grade <= 10) {
                    list.get(0).numExams++;
                } else if (grade >= 11 && grade <= 20) {
                    list.get(1).numExams++;
                } else if (grade >= 21 && grade <= 30) {
                    list.get(2).numExams++;
                } else if (grade >= 31 && grade <= 40) {
                    list.get(3).numExams++;
                } else if (grade >= 41 && grade <= 50) {
                    list.get(4).numExams++;
                } else if (grade >= 51 && grade <= 60) {
                    list.get(5).numExams++;
                } else if (grade >= 61 && grade <= 70) {
                    list.get(6).numExams++;
                } else if (grade >= 71 && grade <= 80) {
                    list.get(7).numExams++;
                } else if (grade >= 81 && grade <= 90) {
                    list.get(8).numExams++;
                } else if (grade >= 91) {
                    list.get(9).numExams++;
                }
            }

            int examId1 = selectedItems.get(0).getReadyExamOriginalID();
            map.put(examId1, list);

            courseReportsBoundry.flag--;
            if (courseReportsBoundry.flag == 0) {
                Platform.runLater(() -> {
                    System.out.println("loll");
                    List<Integer> list1 = new ArrayList<>();
                    for (List<Integer> list2 : median_map.keySet())
                    {
                        for (Integer integer : list2)
                        {
                            list1.add(integer);
                            System.out.println("in here jjjjj +   " + integer);
                        }
                    }
                    System.out.println("lolllll");
                    Collections.sort(list1);
                    courseReportsBoundry.getMedianTextField().setText(Double.toString(list1.get(list1.size()/2)));
                    CategoryAxis xAxis = new CategoryAxis();
                    NumberAxis yAxis = new NumberAxis();

                    // Create the BarChart
                    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                    barChart.setTitle("Exam Grades Report");

                    for (Integer examId : map.keySet()) {
                        List<CourseReportsController.GradeData> gradeDataList = map.get(examId);
                        XYChart.Series<String, Number> newSeries = new XYChart.Series<>();

                        // Add data points to the Series based on the 'gradeDataList'
                        for (CourseReportsController.GradeData gradeData : gradeDataList) {
                            newSeries.getData().add(new XYChart.Data<>(gradeData.getGradeRange(), gradeData.getNumExams()));
                        }

                        // Set the name of the series to the exam ID
                        newSeries.setName("Exam " + examId);

                        // Add the Series to the BarChart
                        barChart.getData().add(newSeries);
                    }

                    // Create and set up the Scene
                    Scene scene = new Scene(barChart, 800, 600);
                    Stage stage = new Stage();
                    stage.setScene(scene);
                    stage.show();
                });
            }
        }
    }
    private class CoursesListCell extends ListCell<Course> {
        private boolean firstRow;

        CoursesListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Course course, boolean empty) {
            super.updateItem(course, empty);
            if (empty || course == null) {
                setText(null);
                setGraphic(null);
            } else {

                Platform.runLater(() -> {
                    HBox container = new HBox();
                    container.setAlignment(Pos.CENTER_LEFT);
                    container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");
                    Label examTextLabel2 = new Label(course.getName());
                    container.getChildren().addAll(examTextLabel2);

                    setGraphic(container);
                });
            }
        }
    }

    public void getExams(Course selectedItem) throws IOException {
        Message message = new Message("GetExamsForCoursePrinciple", selectedItem);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetExamsForCoursePrinciple(GetExamsForCoursePrincipleEvent getExamsForCoursePrincipleEvent)
    {
        List<ReadyExam> exams = (List<ReadyExam>)getExamsForCoursePrincipleEvent.getMessage().getBody();
        if (exams.isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no avaiable exams for this course");
            });
        }
        else
        {
            Platform.runLater(()->{
                ObservableList<ReadyExam> examObservableList = FXCollections.observableArrayList(exams);
                ObservableList<ReadyExam> test = FXCollections.observableArrayList(examObservableList);
                if (courseReportsBoundry.getListViewExams().getItems() != null)
                    test.addAll(courseReportsBoundry.getListViewExams().getItems());
                courseReportsBoundry.getListViewExams().setItems(test);
                courseReportsBoundry.getListViewExams().setCellFactory(param -> {
                    return new ExamListCell(false);
                });
            });
        }
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
                    Label examTextLabel1 = new Label("Creator username: " + exam.getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getCourse());
                    Label examTextLabel3 = new Label("Subject: " + exam.getExam().getSubject().getName());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExam().getExamPeriod());
                    Label examTextLabel6 = new Label("Exam Type: " + exam.getExamType());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getIdd());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4, examTextLabel6,examTextLabel5);
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
            Platform.runLater(()->
            {
                ObservableList<ReadyExam> questionList = FXCollections.observableArrayList(list);
                courseReportsBoundry.getListViewExams().setItems(questionList);
                courseReportsBoundry.getListViewExams().setCellFactory(param -> {
                    return new ExamListCell(false);
                });
            });
        }
    }


    @Subscribe
    public void handleGetExamQuestions(GetExamQuestionsEvent getExamQuestions)
    {
        Platform.runLater(()->{
            System.out.println("getExamQuestions");
            Exam exam = (Exam)getExamQuestions.getMessage().getBody();
            List<Question> list = exam.getListOfQuestions();

            ObservableList<Question> questionList = FXCollections.observableArrayList(list);
            Platform.runLater(() -> {
                courseReportsBoundry.getListViewExamQuestions().setItems(questionList);
                courseReportsBoundry.getListViewExamQuestions().setCellFactory(param -> {
                    return new ScoredQuestionListCell(false);
                });
            });
            courseReportsBoundry.getExamPeriod().setText(exam.getExamPeriod());
            courseReportsBoundry.getCommentStudet().setText(exam.getStudentComments());
            courseReportsBoundry.getCommentTeacher().setText(exam.getTeacherComments());
            String subject = exam.getSubject().getName();
            String course = exam.getCourse().getName();
        });
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

    public Map<Integer,List<GradeData>> getMap() {
        return map;
    }

    @Subscribe
    public void handllle(PrincipleEvent principleEvent)
    {
        Platform.runLater(()->{
            Object[] objects = (Object[]) principleEvent.getMessage().getBody();
            List<Notification> list = (List<Notification>) objects[0];
            int id = (Integer)objects[1];
            if (id == SimpleClient.getClient().getUser().getId())
            {
                showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go and check the home page");
            }
        });
    }
    public Map<List<Integer>, Integer> getMedian_map() {
        return median_map;
    }
}
 