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
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private Button showQBtn;

    EditQuestionController editQuestionController;

    public void setEditQuestionController(EditQuestionController editQuestionController) {
        this.editQuestionController = editQuestionController;
    }

    public EditQuestionController getEditQuestionController() {
        return editQuestionController;
    }

    @FXML
    void initialize() throws IOException {

        editQuestionController = new EditQuestionController(this);
        this.setEditQuestionController(editQuestionController);

        selectCourse.setVisible(false);
        showQBtn.setVisible(false);
        //editBtn.setVisible(false);
        System.out.println("before getting subjects");
        Teacher teacher = (Teacher)SimpleClient.getClient().getUser();
        editQuestionController.getSubjects(teacher);
        System.out.println("after getting subjects");
        ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        selectCourse.setItems(courses);


        //ObservableList<Subject> subjects = FXCollections.observableArrayList(teacher.getSubject());
        //selectSubject.setItems(subjects);

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
    void selectSubjectAction(ActionEvent event)
    {
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
}
