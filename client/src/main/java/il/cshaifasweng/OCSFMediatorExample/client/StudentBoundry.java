/**
 * Sample Skeleton for 'studentBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.StudentController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class StudentBoundry {

    @FXML // fx:id="ConductAnExamBTN"
    private Button ConductAnExamBTN; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesBTN"
    private Button viewGradesBTN; // Value injected by FXMLLoader

    private StudentController studentController;
    @FXML
    void conductAnExamAction(ActionEvent event) {

    }

    @FXML
    void viewGradesAction(ActionEvent event) {

    }

    @FXML
    public void initialize()
    {
        studentController = new StudentController(this);
        this.setStudentController(studentController);
    }

    public void setStudentController(StudentController studentController) {
        this.studentController = studentController;
    }
}
