
package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ApproveExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.PreviewToApproveController;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
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

        private PreviewToApproveController previewToApproveController;

        @FXML
        void approveAction(ActionEvent event) throws IOException
        {
                System.out.println("lolllllllapprove");
                Object object = new Object[]{examID, getGrade().getText(), teacherComments.getText(), studentComments.getText()};
                Message message = new Message("approveExam", object);
                SimpleClient.getClient().sendToServer(message);
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
}
 