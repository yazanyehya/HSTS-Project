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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.*;

public class ViewGradesForTeacherController
{
    private boolean isLogoutDialogShown = false;
    private Map< Integer,List<ViewGradesForTeacherController.GradeData>> map = new HashMap<>();
    private Map<List<Integer>, Integer> median_map = new HashMap<>();
    private ViewGradesForTeacherBoundry viewGradesForTeacherBoundry;

    public ViewGradesForTeacherController(ViewGradesForTeacherBoundry viewGradesForTeacherBoundry)
    {
        EventBus.getDefault().register(this);
        this.viewGradesForTeacherBoundry = viewGradesForTeacherBoundry;
    }
    public void getSubjects() throws IOException
    {
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        Message message = new Message("viewGradesForTeacherSubjects", teacher);
        SimpleClient.getClient().sendToServer(message);
    }
    public void getCourses(Subject selectedItem) throws IOException
    {
        Object object = new Object[]{selectedItem, SimpleClient.getClient().getUser()};
        Message message = new Message("viewGradesForTeacherCourses", object);
        SimpleClient.getClient().sendToServer(message);
    }
    @Subscribe
    public void handleGetSubjects(ViewGradesForTeacherEvent viewGradesForTeacherEvent)
    {
        if ("viewGradesForTeacherSubjects".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<Subject> subjects = (List<Subject>)viewGradesForTeacherEvent.getMessage().getBody();

            Platform.runLater(() -> {
                // Set the items for the ComboBox
                ObservableList<Subject> subjectObservableList = FXCollections.observableArrayList(subjects);
                viewGradesForTeacherBoundry.getSelectSubject().setItems(subjectObservableList);
            });
        }
        else if("viewGradesForTeacherCourses".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<Course> courses = (List<Course>)viewGradesForTeacherEvent.getMessage().getBody();

            Platform.runLater(() -> {
                // Set the items for the ComboBox
                ObservableList<Course> courseObservableList = FXCollections.observableArrayList(courses);
                viewGradesForTeacherBoundry.getSelectCourse().setItems(courseObservableList);
            });
        }
        else if("showExamsForTeacherCourses".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<ReadyExam> list = (List<ReadyExam>) viewGradesForTeacherEvent.getMessage().getBody();
            if (list.isEmpty())
            {
                Platform.runLater(() -> {
                    // Login failure
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Exams For this Course");
                    //EventBus.getDefault().unregister(this);
                });
            }
            else {
                ObservableList<ReadyExam> exams = FXCollections.observableArrayList(list);
                viewGradesForTeacherBoundry.getExamList().setItems(exams);
                viewGradesForTeacherBoundry.getExamList().setCellFactory(param -> {
                    return new ViewGradesForTeacherController.ExamListCell(false);
                });
            }
        }
        else if("ShowExamsForTeacherSubjects".equals(viewGradesForTeacherEvent.getMessage().getTitle()))
        {
            List<ReadyExam> list = (List<ReadyExam>) viewGradesForTeacherEvent.getMessage().getBody();
            if (list.isEmpty())
            {
                Platform.runLater(() -> {
                    // Login failure
                    showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no Available Exams For this Subject");
                    //EventBus.getDefault().unregister(this);
                });
            }
            else {
                ObservableList<ReadyExam> exams = FXCollections.observableArrayList(list);
                viewGradesForTeacherBoundry.getExamList().setItems(exams);
                viewGradesForTeacherBoundry.getExamList().setCellFactory(param -> {
                    return new ViewGradesForTeacherController.ExamListCell(false);
                });
            }
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

                    // Second column: Exam
                    Label examTextLabel1 = new Label("Creator username: " + exam.getUsername());
                    Label examTextLabel2 = new Label("Course: " + exam.getCourse());
                    Label examTextLabel3 = new Label("Subject: " + exam.getSubject());
                    Label examTextLabel4 = new Label("Exam Period: " + exam.getExam().getExamPeriod());
                    Label examTextLabel5 = new Label("Exam ID: " + exam.getIdd());

                    //Label examTextLabel3 = new Label(exam.getSubject().getName());
                    // Add additional labels or components for exam details if needed

                    VBox examVBox = new VBox(examTextLabel1, examTextLabel3,examTextLabel2, examTextLabel4,examTextLabel5);
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
    public void logOut() throws IOException {
        Message msg = new Message("LogoutVG", SimpleClient.getClient().getUser());
        System.out.println(SimpleClient.getClient().getUser().getUsername());
        SimpleClient.getClient().sendToServer(msg);
    }



    @Subscribe
    public void handleLogoutEvent(LogoutEvent logoutEvent) {
        System.out.println("logout platform");

        if (logoutEvent.getMessage().getTitle().equals("LogoutVG")) {
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
    public void handleTeacherEvents(TeacherEvent teacherEvent)
    {
        if ("getReadyExamsForTeacherReportsIN".equals(teacherEvent.getMessage().getTitle()))
        {
            List<ReadyExam> readyExamList = (List<ReadyExam>)teacherEvent.getMessage().getBody();
            fillChartForExam(readyExamList);
        }
        else if("getListGradeForTeacherIN".equals(teacherEvent.getMessage().getTitle()))
        {
            Object[] objects = (Object[]) teacherEvent.getMessage().getBody();
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
        else {
            Platform.runLater(() -> {
                Object[] objects = (Object[]) teacherEvent.getMessage().getBody();
                int id = (Integer) objects[1];
                if (id == SimpleClient.getClient().getUser().getId()) {
                    showAlertDialog(Alert.AlertType.INFORMATION, "Alert", "You got a new notification, go and check the home page");
                }
            });
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
            List<ViewGradesForTeacherController.GradeData> list = new ArrayList<>();
            list.add(new ViewGradesForTeacherController.GradeData("0-10", 0));
            list.add(new ViewGradesForTeacherController.GradeData("11-20", 0));
            list.add(new ViewGradesForTeacherController.GradeData("21-30", 0));
            list.add(new ViewGradesForTeacherController.GradeData("31-40", 0));
            list.add(new ViewGradesForTeacherController.GradeData("41-50", 0));
            list.add(new ViewGradesForTeacherController.GradeData("51-60", 0));
            list.add(new ViewGradesForTeacherController.GradeData("61-70", 0));
            list.add(new ViewGradesForTeacherController.GradeData("71-80", 0));
            list.add(new ViewGradesForTeacherController.GradeData("81-90", 0));
            list.add(new ViewGradesForTeacherController.GradeData("91<", 0));

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

            viewGradesForTeacherBoundry.flag--;
            if (viewGradesForTeacherBoundry.flag == 0) {
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
                    viewGradesForTeacherBoundry.getMedianTextField().setText(Double.toString(list1.get(list1.size()/2)));
                    CategoryAxis xAxis = new CategoryAxis();
                    NumberAxis yAxis = new NumberAxis();

                    // Create the BarChart
                    BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);
                    barChart.setTitle("Exam Grades Report");

                    for (Integer examId : map.keySet()) {
                        List<ViewGradesForTeacherController.GradeData> gradeDataList = map.get(examId);
                        XYChart.Series<String, Number> newSeries = new XYChart.Series<>();

                        // Add data points to the Series based on the 'gradeDataList'
                        for (ViewGradesForTeacherController.GradeData gradeData : gradeDataList) {
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
    public Map<Integer, List<ViewGradesForTeacherController.GradeData>> getMap() {
        return map;
    }

    public Map<List<Integer>, Integer> getMedian_map() {
        return median_map;
    }
    public void getResults(ReadyExam exam)
    {
        Message message = new Message("getReadyExamsForTeacherReportsIN", exam);
        try {
            SimpleClient.getClient().sendToServer(message);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}


