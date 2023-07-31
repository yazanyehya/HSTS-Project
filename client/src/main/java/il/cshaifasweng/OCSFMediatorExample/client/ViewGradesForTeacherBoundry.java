package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.ViewGradesForTeacherController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;

public class ViewGradesForTeacherBoundry {

    @FXML
    private Button backBtn;

    @FXML
    private ListView<Exam> examList;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    private ViewGradesForTeacherController viewGradesForTeacherController;

    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(viewGradesForTeacherController);
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void selectCourseAction(ActionEvent event) throws IOException {

        examList.getItems().clear();
        Object obj = new Object[]{SimpleClient.getClient().getUser(), getSelectCourse().getSelectionModel().getSelectedItem()};
        Message message = new Message("showExamsForTeacherCourses", obj);
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    void selectSubjectAction(ActionEvent event) throws IOException {
        examList.getItems().clear();
        viewGradesForTeacherController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
        Object obj = new Object[]{SimpleClient.getClient().getUser(), getSelectSubject().getSelectionModel().getSelectedItem()};
        Message message = new Message("ShowExamsForTeacherSubjects", obj);
        SimpleClient.getClient().sendToServer(message);
    }

    @FXML
    public void initialize() throws IOException {
        viewGradesForTeacherController = new ViewGradesForTeacherController(this);
        this.setViewGradesForTeacherController(viewGradesForTeacherController);

        selectCourse.setVisible(false);


        viewGradesForTeacherController.getSubjects();


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
        examList.setOnMouseClicked(event -> {
            if (event.getClickCount() == 2) {
                Exam selectedExam = examList.getSelectionModel().getSelectedItem();
                if (selectedExam != null) {
                    // Perform your double-click action here
                    try {
                        EventBus.getDefault().unregister(viewGradesForTeacherController);
                        SimpleChatClient.switchScreen("ViewGradesForTeacherII");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Message message = new Message("ShowReadyExamsForViewGradesForTeacher", selectedExam);
                    try {
                        SimpleClient.getClient().sendToServer(message);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    // For example, you can open a new window or perform any other action you want.
                }
            }
        });
    }

    public Button getBackBtn() {
        return backBtn;
    }


    public ListView<Exam> getExamList() {
        return examList;
    }

    public ViewGradesForTeacherController getViewGradesForTeacherController() {
        return viewGradesForTeacherController;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }


    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setExamList(ListView<Exam> examList) {
        this.examList = examList;
    }

    public void setViewGradesForTeacherController(ViewGradesForTeacherController seeGradesController) {
        this.viewGradesForTeacherController = seeGradesController;
    }

    public ViewGradesForTeacherController getSeeGradesController() {
        return viewGradesForTeacherController;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }
}
