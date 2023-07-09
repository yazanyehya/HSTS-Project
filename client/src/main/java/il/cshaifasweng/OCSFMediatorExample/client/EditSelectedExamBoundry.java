package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.EditSelectedExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.LoginController;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.util.Callback;
import javafx.util.StringConverter;

import java.io.IOException;
import java.util.List;

public class EditSelectedExamBoundry {


    @FXML
    private Button backBtn;

    @FXML
    private ComboBox<Course> chooseCourse;

    @FXML
    private TextField examPeriod;

    @FXML
    private ComboBox<String> examType;

    @FXML
    private ListView<Question> questionListView;

    @FXML
    private ListView<Question> listOfAllQuestions;

    @FXML
    private Button saveBtn;

    @FXML
    private Button selectBtn;

    @FXML
    private Button showBtn;

    EditSelectedExamController editSelectedExamController;

    @FXML
    public void initialize()
    {
        editSelectedExamController = new EditSelectedExamController(this);
        this.setEditSelectedExamController(editSelectedExamController);

        questionListView.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        listOfAllQuestions.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        Teacher teacher = (Teacher) SimpleClient.getClient().getUser();
        ObservableList<Course> courses = FXCollections.observableArrayList(teacher.getCourses());
        chooseCourse.setItems(courses);
        chooseCourse.setCellFactory(new Callback<ListView<Course>, ListCell<Course>>() {
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

        chooseCourse.setConverter(new StringConverter<Course>() {
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

    }
    @FXML
    void backAction(ActionEvent event)
    {
        Platform.runLater(() -> {
            try {
                System.out.println("back edit exam");
                SimpleChatClient.switchScreen("EditExam");
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void showAction(ActionEvent event) throws IOException {
        editSelectedExamController.getAllQuestions();
    }
    @FXML
    void chooseCourseAction(ActionEvent event)
    {

    }

    @FXML
    void examPeriodAction(ActionEvent event)
    {

    }

    @FXML
    void examTypeAction(ActionEvent event)
    {

    }

    @FXML
    void saveExam(ActionEvent event) throws IOException
    {
        List<Question> selectedQuestions = (List<Question>) getQuestionListView().getSelectionModel().getSelectedItems();
        System.out.println("incline bench press");
        editSelectedExamController.saveExam(selectedQuestions);
        System.out.println("incline smith bench press");
    }


    public ListView<Question> getQuestionListView() {
        return questionListView;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public Button getSaveBtn() {
        return saveBtn;
    }

    public TextField getExamPeriod() {
        return examPeriod;
    }

    public ComboBox<Course> getChooseCourse() {
        return chooseCourse;
    }

    public Button getSelectBtn() {
        return selectBtn;
    }

    public ComboBox<String> getExamType() {
        return examType;
    }

    public EditSelectedExamController getEditSelectedExamController() {
        return editSelectedExamController;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setSaveBtn(Button saveBtn) {
        this.saveBtn = saveBtn;
    }

    public void setExamPeriod(TextField examPeriod) {
        this.examPeriod = examPeriod;
    }

    public void setSelectBtn(Button selectBtn) {
        this.selectBtn = selectBtn;
    }

    public void setQuestionListView(ListView<Question> questionListView) {
        this.questionListView = questionListView;
    }

    public void setChooseCourse(ComboBox<Course> chooseCourse) {
        this.chooseCourse = chooseCourse;
    }

    public void setEditSelectedExamController(EditSelectedExamController editSelectedExamController) {
        this.editSelectedExamController = editSelectedExamController;
    }

    public void setExamType(ComboBox<String> examType) {
        this.examType = examType;
    }

    public ListView<Question> getListOfAllQuestions() {
        return listOfAllQuestions;
    }

    public void setListOfAllQuestions(ListView<Question> listOfAllQuestions) {
        this.listOfAllQuestions = listOfAllQuestions;
    }

    public void ConfirmSelect(ActionEvent actionEvent) throws IOException
    {

    }
}
