/**
 * Sample Skeleton for 'viewExamsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.EditExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewExamsController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ViewExamsBoundry {

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="examPeriod"
    private TextField examPeriod; // Value injected by FXMLLoader
    @FXML // fx:id="commentStudet"
    private TextArea commentStudet; // Value injected by FXMLLoader

    @FXML // fx:id="commentTeacher"
    private TextArea commentTeacher; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExams"
    private ListView<Exam> listViewExams; // Value injected by FXMLLoader
    @FXML // fx:id="listViewExamQuestions"
    private ListView<Question> listViewExamQuestions; // Value injected by FXMLLoader
    @FXML // fx:id="selectCourse"
    private ComboBox<Course> selectCourse; // Value injected by FXMLLoader

    @FXML // fx:id="selectSubject"
    private ComboBox<Subject> selectSubject; // Value injected by FXMLLoader
    @FXML // fx:id="showQuestionsBtn"
    private Button showQuestionsBtn; // Value injected by FXMLLoader
    @FXML // fx:id="showExamsBtn"
    private Button showExamsBtn; // Value injected by FXMLLoader
    private ViewExamsController viewExamsController;
    @FXML
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(viewExamsController);
            } catch (IOException e) {
                e.printStackTrace();
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
    @FXML
    void selectCourseAction(ActionEvent event) {
        showExamsBtn.setVisible(true);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        viewExamsController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
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
                    Exam exam = listViewExams.getSelectionModel().getSelectedItem();
                    Message msg = new Message("getExamQuestions", exam);
                    SimpleClient.getClient().sendToServer(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
    @FXML
    void showExamsAction(ActionEvent event) throws IOException {
        Course course = getSelectCourse().getValue();
        Message message = new Message("showExamsForPrinciple", course);
        SimpleClient.getClient().sendToServer(message);
    }
    public ViewExamsController getViewExamsController() {
        return viewExamsController;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public Button getShowExamBtn() {
        return showExamsBtn;
    }

    public void setViewExamsController(ViewExamsController viewExamsController) {
        this.viewExamsController = viewExamsController;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setShowExamBtn(Button showExamBtn) {
        this.showExamsBtn = showExamBtn;
    }

    public ListView<Exam> getListViewExams() {
        return listViewExams;
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
    public void setListViewExams(ListView<Exam> listViewExams) {
        this.listViewExams = listViewExams;
    }
    public ListView<Question> getListViewExamQuestions() {
        return listViewExamQuestions;
    }

    public void setListViewExamQuestions(ListView<Question> listViewExamQuestions) {
        this.listViewExamQuestions = listViewExamQuestions;
    }
    @FXML
    public void initialize() throws IOException {
        viewExamsController = new ViewExamsController(this);
        this.setViewExamsController(viewExamsController);

        viewExamsController.getSubjects();

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

}
