/**
 * Sample Skeleton for 'viewGradesBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewQuestionsController;
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

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="listViewGrades"
    private ListView<?> listViewGrades; // Value injected by FXMLLoader

    @FXML // fx:id="selectStudent"
    private ComboBox<Student> selectStudent; // Value injected by FXMLLoader

    @FXML // fx:id="showGradesBtn"
    private Button showGradesBtn; // Value injected by FXMLLoader

    @FXML
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(viewGradesController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void selectStudentAction(ActionEvent event) {
        showGradesBtn.setVisible(true);
    }

    @FXML
    void showGradesAction(ActionEvent event) {

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



}
