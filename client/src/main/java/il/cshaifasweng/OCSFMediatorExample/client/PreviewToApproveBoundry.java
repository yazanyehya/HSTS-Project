
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ApproveExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.PreviewToApproveController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class PreviewToApproveBoundry {

        @FXML
        private Button approveBtn;

        @FXML
        private Button backBtn;

        @FXML
        private TextField grade;

        @FXML
        private AnchorPane pane;

        @FXML
        private ScrollPane scrollPane;

        @FXML
        private TextArea studentComments;

        @FXML
        private VBox studentContainer;

        @FXML
        private TextArea teacherComments;

        @FXML
        private VBox teacherContainer;

        private Integer examID;

        private Integer studentID;
        private int tempGade;
        private String old;

        private PreviewToApproveController previewToApproveController;

        public void setTempGade(int tempGade) {
                this.tempGade = tempGade;
        }

        public void setOld(String old) {
                this.old = old;
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
        @FXML
        void approveAction(ActionEvent event) throws IOException
        {
                if (tempGade != Integer.parseInt(grade.getText()) && studentComments.getText().equals(old))
                {
                        Platform.runLater(()->{
                                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please enter explanation for changing the grade");
                        });
                }
                else
                {
                        Object object = new Object[]{examID, getGrade().getText(), teacherComments.getText(), studentComments.getText(), studentID};
                        Message message = new Message("approveExam", object);
                        SimpleClient.getClient().sendToServer(message);
                }
        }

        @FXML
        void backAction(ActionEvent event)
        {
                EventBus.getDefault().unregister(previewToApproveController);
                Platform.runLater(() -> {
                        try {
                                SimpleChatClient.switchScreen("ApproveExam");
                        } catch (IOException e) {
                                e.printStackTrace();
                        }
                });
        }
        @FXML
        public void initialize()
        {
                previewToApproveController = new PreviewToApproveController(this);
                this.setPreviewToApproveController(previewToApproveController);
        }

        public void setPreviewToApproveController(PreviewToApproveController previewToApproveController) {
                this.previewToApproveController = previewToApproveController;
        }

        public void setBackBtn(Button backBtn) {
                this.backBtn = backBtn;
        }

        public void setApproveBtn(Button approveBtn) {
                this.approveBtn = approveBtn;
        }

        public Button getBackBtn() {
                return backBtn;
        }

        public Button getApproveBtn() {
                return approveBtn;
        }

        public PreviewToApproveController getPreviewToApproveController() {
                return previewToApproveController;
        }

        public AnchorPane getPane() {
                return pane;
        }

        public ScrollPane getScrollPane() {
                return scrollPane;
        }

        public TextField getGrade() {
                return grade;
        }

        public TextArea getStudentComments() {
                return studentComments;
        }

        public TextArea getTeacherComments() {
                return teacherComments;
        }

        public VBox getStudentContainer() {
                return studentContainer;
        }

        public VBox getTeacherContainer() {
                return teacherContainer;
        }

        public void setExamID(Integer examID) {
                this.examID = examID;
        }

        public Integer getExamID()
        {
                return examID;
        }

        public Integer getStudentID() {
                return studentID;
        }

        public void setStudentID(Integer studentID) {
                this.studentID = studentID;
        }
}
 