/**
 * Sample Skeleton for 'studentReportsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.CourseReportsController;
import il.cshaifasweng.OCSFMediatorExample.Controller.StudentReportsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StudentReportsBoundry {


    @FXML
    private TextField averageTextField;

    @FXML
    private Button backBtn;

    @FXML
    private ListView<ReadyExam> listViewExams;

    @FXML
    private TextField medianTextField;

    @FXML
    private Button showExams;

    @FXML
    private Button showStudents;

    @FXML
    private ListView<Student> studentsList;

    @FXML
    private BarChart<String, Integer> theChart;

    private XYChart.Series<String,Integer> series1;

    @FXML // fx:id="examPeriod"
    private TextField examPeriod; // Value injected by FXMLLoader
    @FXML // fx:id="commentStudet"
    private TextArea commentStudet; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionsBtn"
    private Button showQuestionsBtn; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExamQuestions"
    private ListView<Question> listViewExamQuestions; // Value injected by FXMLLoader
    @FXML // fx:id="commentTeacher"
    private TextArea commentTeacher; // Value injected by FXMLLoader
    @FXML
    void averageTextFieldAction(ActionEvent event) {

    }
    StudentReportsController studentReportsController;
    public void setStudentReportsController(StudentReportsController studentReportsController) {
        this.studentReportsController = studentReportsController;
    }
    public StudentReportsController getStudentReportsController() {
        return studentReportsController;
    }

    @FXML
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(studentReportsController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void medianTextFieldAction(ActionEvent event) {

    }

    @FXML
    void examPeriodAction(ActionEvent event) {

    }
    @FXML
    void showQuestionsAction(ActionEvent event) {
        if (listViewExams.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select An Exam!");
            });
        }
        else
        {
            Platform.runLater(() -> {
                try {
                    ReadyExam exam = listViewExams.getSelectionModel().getSelectedItem();
                    Message msg = new Message("getExamQuestions", exam);
                    SimpleClient.getClient().sendToServer(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }


    @FXML
    void initialize() throws IOException {

        studentReportsController = new StudentReportsController(this);
        this.setStudentReportsController(studentReportsController);

        series1 = new XYChart.Series<String,Integer>();
        series1.setName("Number Of Exams");
        series1.getData().add(new XYChart.Data("0-10", 0));
        series1.getData().add(new XYChart.Data("11-20", 0));
        series1.getData().add(new XYChart.Data("21-30", 0));
        series1.getData().add(new XYChart.Data("31-40", 0));
        series1.getData().add(new XYChart.Data("41-50", 0));
        series1.getData().add(new XYChart.Data("51-60", 0));
        series1.getData().add(new XYChart.Data("61-70", 0));
        series1.getData().add(new XYChart.Data("71-80", 0));
        series1.getData().add(new XYChart.Data("81-90", 0));
        series1.getData().add(new XYChart.Data("91<", 0));
        theChart.getData().addAll(series1);


        System.out.println("before getting students");
        listViewExams.setItems(null);
        studentsList.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        populateCoursesList();


        studentsList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) { // Check if it's a double-click
                Student student = studentsList.getSelectionModel().getSelectedItem();
                if (student != null)
                {
                    try {
                        studentReportsController.getExams(student);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        listViewExams.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listViewExams.getSelectionModel().getSelectedItems().addListener((ListChangeListener.Change<? extends ReadyExam> change) -> {

            ObservableList<? extends ReadyExam> selectedItems = change.getList();
            double avg = calculateAverage( selectedItems);
            averageTextField.setText(Double.toString(avg));
            double median = calculateMedian(selectedItems);
            medianTextField.setText(Double.toString(median));
            fillChart(selectedItems);

        });
    }
    double calculateAverage(ObservableList<? extends ReadyExam> selectedItems) {
        double avg = 0;

        for (ReadyExam readyExam : selectedItems) {
            avg += readyExam.getGrade();
        }

        return avg / selectedItems.size();
    }
    double calculateMedian(ObservableList<? extends ReadyExam> selectedItems)
    {
        List<Double> list = new ArrayList<Double>();
        for (ReadyExam readyExam : selectedItems)
        {
            double temp = readyExam.getGrade();
            list.add(temp);
        }
        int size = selectedItems.size();
        int middle = size / 2;
        if (size % 2 == 0 ) {
            // Step 3: If even number of elements, calculate the average of the two middle elements
            if (size > 0)
            {
                double median1 = list.get(middle - 1);
                double median2 = list.get(middle);
                return (median1 + median2) / 2;
            }
            else {return 0;}
        } else {
            // Step 4: If odd number of elements, return the middle element
            return list.get(middle);
        }
    }
    private class GradeData {
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
    void fillChart(ObservableList<? extends ReadyExam> selectedItems)
    {

        List<GradeData> list = new ArrayList<>();
        list.add(new GradeData("0-10",0));
        list.add(new GradeData("11-20",0));
        list.add(new GradeData("21-30",0));
        list.add(new GradeData("31-40",0));
        list.add(new GradeData("41-50",0));
        list.add(new GradeData("51-60",0));
        list.add(new GradeData("61-70",0));
        list.add(new GradeData("71-80",0));
        list.add(new GradeData("81-90",0));
        list.add(new GradeData("91<",0));

        for (ReadyExam readyExam : selectedItems)
        {
            int grade = readyExam.getGrade();
            if (grade >= 0 && grade <= 10)
            {
                list.get(0).numExams++;
            }
            else if(grade >= 11 && grade <= 20)
            {
                list.get(1).numExams++;
            }
            else if(grade >= 21 && grade <= 30)
            {
                list.get(2).numExams++;
            }
            else if(grade >= 31 && grade <= 40)
            {
                list.get(3).numExams++;
            }
            else if(grade >= 41 && grade <= 50)
            {
                list.get(4).numExams++;
            }
            else if(grade >= 51 && grade <= 60)
            {
                list.get(5).numExams++;
            }
            else if(grade >= 61 && grade <= 70)
            {
                list.get(6).numExams++;
            }
            else if(grade >= 71 && grade <= 80)
            {
                list.get(7).numExams++;
            }
            else if(grade >= 81 && grade <= 90)
            {
                list.get(8).numExams++;
            }
            else if(grade >= 91 )
            {
                list.get(9).numExams++;
            }
        }
        for (GradeData gradeData : list) {
            XYChart.Data<String, Integer> data = new XYChart.Data<>(gradeData.getGradeRange(), gradeData.getNumExams());
            series1.getData().add(data);

            // Set the bar color for the data node
            data.getNode().setStyle("-fx-bar-fill: #9400d3;");
        }

    }
    private void populateCoursesList() { // Set the cell factory to display the course name
        studentsList.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                return new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getFirstName() + item.getLastName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
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

    public ListView<ReadyExam> getListViewExams() {
        return listViewExams;
    }

    public void setListViewExams(ListView<ReadyExam> listViewExams) {
        this.listViewExams = listViewExams;
    }
    public ListView<Student> getStudentsList() {
        return studentsList;
    }

    public void setStudentsList(ListView<Student> studentsList) {
        this.studentsList = studentsList;
    }
    public TextArea getCommentTeacher() {
        return commentTeacher;
    }
    public void setCommentTeacher(TextArea commentTeacher) {
        this.commentTeacher = commentTeacher;
    }

    public void setCommentStudet(TextArea commentStudet) {
        this.commentStudet = commentStudet;
    }

    public TextArea getCommentStudet() {
        return commentStudet;
    }
    public TextField getExamPeriod() {
        return examPeriod;
    }
    public void setExamPeriod(TextField examPeriod) {
        this.examPeriod = examPeriod;
    }
    public ListView<Question> getListViewExamQuestions() {
        return listViewExamQuestions;
    }
}
