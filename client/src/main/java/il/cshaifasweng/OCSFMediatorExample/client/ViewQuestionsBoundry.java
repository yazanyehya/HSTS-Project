/**
 * Sample Skeleton for 'viewQuestionsBoundry.fxml' Controller Class
 */

package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.EditQuestionController;
import il.cshaifasweng.OCSFMediatorExample.Controller.ViewQuestionsController;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Subject;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ViewQuestionsBoundry {

    @FXML // fx:id="backBtn"
    private Button backBtn; // Value injected by FXMLLoader

    @FXML // fx:id="listViewQuestions"
    private ListView<Question> listViewQuestions; // Value injected by FXMLLoader

    @FXML // fx:id="selectCourse"
    private ComboBox<Course> selectCourse; // Value injected by FXMLLoader
    @FXML
    private ListView<Course> courseList;

    @FXML // fx:id="selectSubject"
    private ComboBox<Subject> selectSubject; // Value injected by FXMLLoader

    @FXML // fx:id="showQuestionsBtn"
    private Button showQuestionsBtn; // Value injected by FXMLLoader

    @FXML
    void backAction(ActionEvent event) {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("principleBoundry");
                EventBus.getDefault().unregister(viewQuestionsController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void selectCourseAction(ActionEvent event) {
        showQuestionsBtn.setVisible(true);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        viewQuestionsController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showQuestionsAction(ActionEvent event) throws IOException {
        Course course = getSelectCourse().getValue();
        Message message = new Message("showQuestionsForPrinciple", course);
        SimpleClient.getClient().sendToServer(message);
    }

    ViewQuestionsController viewQuestionsController;
    public void setViewQuestionsController(ViewQuestionsController viewQuestionsController) {
        this.viewQuestionsController = viewQuestionsController;
    }
    public ViewQuestionsController getViewQuestionsController() {
        return viewQuestionsController;
    }

    private void populateCourseComboBox() {
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
    @FXML
    void initialize() throws IOException {

        viewQuestionsController = new ViewQuestionsController(this);
        this.setViewQuestionsController(viewQuestionsController);

        System.out.println("before getting subjects");
        viewQuestionsController.getSubjects();
        populateCourseComboBox();
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

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ListView<Question> getListViewQuestions() {
        return listViewQuestions;
    }

    public void setListViewQuestions(ListView<Question> listViewQuestions) {
        this.listViewQuestions = listViewQuestions;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public Button getShowQBtn() {
        return showQuestionsBtn;
    }
    public void setShowQBtn(Button showQuestionsBtn) {
        this.showQuestionsBtn = showQuestionsBtn;
    }

    public ListView<Course> getCourseList() {
        return courseList;
    }
}