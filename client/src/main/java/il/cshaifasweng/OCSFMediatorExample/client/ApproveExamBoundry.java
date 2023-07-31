package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ApproveExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ApproveExamBoundry {

    @FXML
    private ListView<ReadyExam> readyExamList;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private Button showExamBtn;

    @FXML
    private Button previewExamBtn;

    @FXML
    private Button backBtn;


    private ApproveExamController approveExamController;

    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(approveExamController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @FXML
    void selectCourseAction(ActionEvent event)
    {
        showExamBtn.setVisible(true);
    }

    @FXML
    void PreviewExamAction(ActionEvent event) throws IOException {
        if (readyExamList.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select an exam");
            });
        }
        else
        {
            SimpleChatClient.switchScreen("PreviewToApprove");
            ReadyExam readyExam = getReadyExamList().getSelectionModel().getSelectedItem();
            Message message = new Message("SendToPreview", readyExam);
            SimpleClient.getClient().sendToServer(message);
        }
    }
    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        approveExamController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showExamAction(ActionEvent event) throws IOException {
        readyExamList.setVisible(true);
        previewExamBtn.setVisible(true);
        Course course = getSelectCourse().getValue();
        Teacher teacher = (Teacher)SimpleClient.getClient().getUser();
        Object object = new Object[]{course, teacher};
        Message message = new Message("showReadyExamsAPP", object);
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    void initialize() throws IOException {
        approveExamController = new ApproveExamController(this);
        this.setApproveExamController(approveExamController);
        selectCourse.setVisible(false);
        showExamBtn.setVisible(false);
        previewExamBtn.setVisible(false);

        approveExamController.getSubjects();


        selectCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
            @Override
            public ListCell<Course> call(ListView<Course> param) {
                return new ListCell<Course>() {
                    @Override
                    protected void updateItem(Course item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });

        selectCourse.setConverter(new StringConverter<Course>() {
            @Override
            public String toString(Course course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Course fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });
        selectSubject.setCellFactory(new Callback<ListView<Subject>, ListCell<Subject>>() {
            @Override
            public ListCell<Subject> call(ListView<Subject> param) {
                return new ListCell<Subject>() {
                    @Override
                    protected void updateItem(Subject item, boolean empty) {
                        super.updateItem(item, empty);
                        if (item != null) {
                            setText(item.getName());
                        } else {
                            setText("");
                        }
                    }
                };
            }
        });
        selectSubject.setConverter(new StringConverter<Subject>() {
            @Override
            public String toString(Subject subject) {
                if (subject == null) {
                    return null;
                }
                return subject.getName();
            }

            @Override
            public Subject fromString(String string) {
                return null;
            }
        });

    }

    public void setApproveExamController(ApproveExamController approveExamController) {
        this.approveExamController = approveExamController;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setShowExamBtn(Button showExamBtn) {
        this.showExamBtn = showExamBtn;
    }

    public void setReadyExamList(ListView<ReadyExam> readyExamList) {
        this.readyExamList = readyExamList;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public Button getShowExamBtn() {
        return showExamBtn;
    }

    public ApproveExamController getApproveExamController() {
        return approveExamController;
    }

    public ListView<ReadyExam> getReadyExamList() {
        return readyExamList;
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
}

