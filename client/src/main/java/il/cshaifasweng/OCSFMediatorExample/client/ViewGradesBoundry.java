
/**
 * Sample Skeleton for 'viewGradesBoundry.fxml' Controller Class
 */

 package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewQuestionsController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import il.cshaifasweng.OCSFMediatorExample.entities.ReadyExam;
import il.cshaifasweng.OCSFMediatorExample.entities.Student;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;


public class ViewGradesBoundry {

    @FXML
    private TableColumn<ReadyExam, Integer> Grade;

    @FXML
    private TableColumn<ReadyExam, String> courseCol;

    @FXML
    private Button courseReportsBtn;

    @FXML
    private TableColumn<ReadyExam, Integer> examIDCol;

    @FXML
    private Button extraTimeBtn;

    @FXML
    private Button homeBtn;

    @FXML
    private Button logoutBtn;

    @FXML
    private TableColumn<ReadyExam, Button> previewCol;

    @FXML
    private ComboBox<Student> selectStudent;

    @FXML
    private TableColumn<ReadyExam, String> studentFullNameCol;

    @FXML
    private TableColumn<ReadyExam, Integer> studentIDCol;

    @FXML
    private Button studentReportsBtn;

    @FXML
    private TableColumn<ReadyExam, String> subjectCol;

    @FXML
    private Button teacherReportsBtn;

    @FXML
    private Button viewExamsBtn;

    @FXML
    private Button viewGradesBtn;

    @FXML
    private Button viewQuestionsBtn;

    @FXML
    private TableView<ReadyExam> table;

    @FXML
    void selectStudentAction(ActionEvent event) throws IOException {
        table.getItems().clear();
        Message message = new Message("getStudentsToViewGradePrinciple", selectStudent.getSelectionModel().getSelectedItem());
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewGradesController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getTeachersForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewGradesController);
                SimpleChatClient.switchScreen("courseReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getCoursesForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewGradesController);
                SimpleChatClient.switchScreen("studentReportsBoundry");
                Principle principle = (Principle) SimpleClient.getClient().getUser();
                Message message = new Message("getStudentsForPrinciple", principle);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void reportsAction(ActionEvent event) throws IOException {
        EventBus.getDefault().unregister(viewGradesController);

        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("reportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void homeBtnAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewGradesController);
                SimpleChatClient.switchScreen("PrincipleBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        viewGradesController.logOut();
    }

    @FXML
    void extraTimeAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewGradesController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("ExtraTimePrinciple");
                Message message = new Message("ExtraTimePrinciple", null);
                SimpleClient.getClient().sendToServer(message);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void viewExamsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewGradesController);
                SimpleChatClient.switchScreen("viewExamsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
//        Platform.runLater(() -> {
//            try {
//                EventBus.getDefault().unregister(viewGradesController);
//                SimpleChatClient.switchScreen("viewGradesBoundry");
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        });
        Platform.runLater(()->{
            showAlertDialog(Alert.AlertType.ERROR, "Error", "You are already in View Grades");
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(viewGradesController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    ViewGradesController viewGradesController;
    public void setViewGradesController(ViewGradesController viewGradesController) {
        this.viewGradesController = viewGradesController;
    }
    public ViewGradesController getViewGradesController() {
        return viewGradesController;
    }
    @FXML
    void initialize() throws IOException {

        viewGradesController = new ViewGradesController(this);
        this.setViewGradesController(viewGradesController);

        System.out.println("before getting students");
        viewGradesController.getStudents();
        populateStudentsComboBox();
    }

    private void populateStudentsComboBox() {
        // Set the cell factory to display the course name
        selectStudent.setCellFactory(new Callback<ListView<Student>, ListCell<Student>>() {
            @Override
            public ListCell<Student> call(ListView<Student> param) {
                return new ListCell<Student>() {
                    @Override
                    protected void updateItem(Student item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getFirstName() + " " + item.getLastName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        selectStudent.setConverter(new StringConverter<Student>() {
            @Override
            public String toString(Student student) {
                if (student == null) {
                    return null;
                }
                return (student.getFirstName() + " " + student.getLastName());
            }

            @Override
            public Student fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
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
    public ComboBox<Student> getSelectStudent() {
        return selectStudent;
    }
    public void setSelectStudent(ComboBox<Student> selectStudent) {
        this.selectStudent = selectStudent;
    }


    public Button getCourseReportsBtn() {
        return courseReportsBtn;
    }

    public Button getExtraTimeBtn() {
        return extraTimeBtn;
    }

    public Button getHomeBtn() {
        return homeBtn;
    }

    public Button getLogoutBtn() {
        return logoutBtn;
    }

    public Button getViewGradesBtn() {
        return viewGradesBtn;
    }

    public Button getStudentReportsBtn() {
        return studentReportsBtn;
    }

    public Button getViewExamsBtn() {
        return viewExamsBtn;
    }

    public Button getTeacherReportsBtn() {
        return teacherReportsBtn;
    }

    public TableColumn<ReadyExam, Integer> getStudentIDCol() {
        return studentIDCol;
    }

    public TableColumn<ReadyExam, String> getSubjectCol() {
        return subjectCol;
    }

    public Button getViewQuestionsBtn() {
        return viewQuestionsBtn;
    }

    public TableColumn<ReadyExam, Button> getPreviewCol() {
        return previewCol;
    }

    public TableColumn<ReadyExam, Integer> getExamIDCol() {
        return examIDCol;
    }

    public TableColumn<ReadyExam, String> getCourseCol() {
        return courseCol;
    }

    public TableColumn<ReadyExam, Integer> getGrade() {
        return Grade;
    }

    public TableColumn<ReadyExam, String> getStudentFullNameCol() {
        return studentFullNameCol;
    }

    public TableView<ReadyExam> getTable() {
        return table;
    }
}
 