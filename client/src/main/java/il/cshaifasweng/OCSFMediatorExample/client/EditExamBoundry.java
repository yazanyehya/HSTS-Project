package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.Controller.EditExamController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class EditExamBoundry {

    @FXML
    private Button backBtn;

    @FXML
    private Button editBtn;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private Button showExamBtn;

    @FXML
    private ListView<Exam> listViewE;

    private EditExamController editExamController;

    @FXML
    void backAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            try {
                System.out.println("back edit exam");
                SimpleChatClient.switchScreen("teacherBoundry");
                EventBus.getDefault().unregister(editExamController);
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
    void editAction(ActionEvent event)
    {
        if (getListViewE().getSelectionModel().isEmpty())
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
                    SimpleChatClient.switchScreen("EditSelectedExam");
                    System.out.println("exam 1");
                    Exam exam = getListViewE().getSelectionModel().getSelectedItem();
                    System.out.println("exam 11");
                    Message message = new Message("editSelectedExam", exam);
                    System.out.println("exam 111");
                    //System.out.println(exam.getListOfQuestions().get(0).getQText());
                    System.out.println("exam 1111");
                    //System.out.println(exam.getListOfQuestions().get(0).getTeacher().getUsername());
                    SimpleClient.getClient().sendToServer(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    void selectCourseAction(ActionEvent event)
    {
        showExamBtn.setVisible(true);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        editExamController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showExamAction(ActionEvent event) throws IOException {
        Course course = getSelectCourse().getValue();
        Message message = new Message("showExams", course);
        SimpleClient.getClient().sendToServer(message);
    }
    @FXML
    public void initialize() throws IOException {
        editExamController = new EditExamController(this);
        this.setEditExamController(editExamController);

        selectCourse.setVisible(false);
        showExamBtn.setVisible(false);
        editExamController.getSubjects();
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

    public EditExamController getEditExamController() {
        return editExamController;
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

    public Button getEditBtn() {
        return editBtn;
    }

    public Button getShowExamBtn() {
        return showExamBtn;
    }

    public void setEditExamController(EditExamController editExamController) {
        this.editExamController = editExamController;
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

    public void setEditBtn(Button editBtn) {
        this.editBtn = editBtn;
    }

    public void setShowExamBtn(Button showExamBtn) {
        this.showExamBtn = showExamBtn;
    }

    public ListView<Exam> getListViewE() {
        return listViewE;
    }

    public void setListViewE(ListView<Exam> listViewE) {
        this.listViewE = listViewE;
    }
}
