/**
 * Sample Skeleton for 'principleBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.PrincipleController;
import il.cshaifasweng.OCSFMediatorExample.entities.Principle;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class PrincipleBoundry {

    @FXML // fx:id="logoutBtn"
    private Button logoutBtn; // Value injected by FXMLLoader
    @FXML // fx:id="courseReportsBtn"
    private Button courseReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="studentReportsBtn"
    private Button studentReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="teacherReportsBtn"
    private Button teacherReportsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewExamsBtn"
    private Button viewExamsBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewGradesBtn"
    private Button viewGradesBtn; // Value injected by FXMLLoader

    @FXML // fx:id="viewQuestionsBtn"
    private Button viewQuestionsBtn; // Value injected by FXMLLoader

    private PrincipleController principleController;
    @FXML
    void reportsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("reportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void logoutAction(ActionEvent event) throws IOException {
        principleController.logOut();
    }

    @FXML
    void viewExamsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewExamsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewGradesAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewGradesBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void viewQuestionsAction(ActionEvent event) throws IOException {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("viewQuestionsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    public void setPrincipleController(PrincipleController principleController) {
        this.principleController = principleController;
    }

    @FXML
    public void initialize()
    {
        principleController = new PrincipleController(this);
        this.setPrincipleController(principleController);
    }

    @FXML
    void teacherReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("teacherReportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void courseReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("courseReportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void studentReportsAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                EventBus.getDefault().unregister(principleController);
                SimpleChatClient.switchScreen("studentReportsBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

}