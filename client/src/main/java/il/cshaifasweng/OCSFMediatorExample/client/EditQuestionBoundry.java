package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.Controller.EditQuestionController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;

public class EditQuestionBoundry {

    @FXML
    private Button backBtn;

    @FXML
    private Button editBtn;

    @FXML
    private ListView<Question> listViewQ;

    @FXML
    private ListView<Course> courseList;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private Button showQBtn;

    EditQuestionController editQuestionController;

    public void setEditQuestionController(EditQuestionController editQuestionController) {
        this.editQuestionController = editQuestionController;
    }

    public EditQuestionController getEditQuestionController() {
        return editQuestionController;
    }

    private void populateCourseComboBox() {
        //Teacher teacher = (Teacher) SimpleClient.getClient().getUser();

        // Set the cell factory to display the course name

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
            public String toString(Subject course) {
                if (course == null) {
                    return null;
                }
                return course.getName();
            }

            @Override
            public Subject fromString(String string) {
                // This method is not used in this example, so you can leave it empty
                return null;
            }
        });
    }
    @FXML
    void initialize() throws IOException {

        editQuestionController = new EditQuestionController(this);
        this.setEditQuestionController(editQuestionController);

        System.out.println("before getting subjects");
        editQuestionController.getSubjects();
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
    @FXML
    void EditAction(ActionEvent event)
    {
        if (getListViewQ().getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select A Question!");
            });
        }
        else
        {
            Platform.runLater(() -> {
                try {
                    SimpleChatClient.switchScreen("EditSelectedQuestion");
                    Message message = new Message("editSelectedQuestion", getListViewQ().getSelectionModel().getSelectedItem());
                    SimpleClient.getClient().sendToServer(message);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }

    @FXML
    void backAction(ActionEvent event)
    {
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
        showQBtn.setVisible(true);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        editQuestionController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showQuestionsAction(ActionEvent event) throws IOException
    {
        Course course = getSelectCourse().getValue();
        Message message = new Message("showQuestions", course);
        SimpleClient.getClient().sendToServer(message);
    }

    public Button getShowQBtn() {
        return showQBtn;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ListView<Question> getListViewQ() {
        return listViewQ;
    }

    public void setListViewQ(ListView<Question> listViewQ) {
        this.listViewQ = listViewQ;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setShowQBtn(Button showQBtn) {
        this.showQBtn = showQBtn;
    }

    public ListView<Course> getCourseList() {
        return courseList;
    }
}
