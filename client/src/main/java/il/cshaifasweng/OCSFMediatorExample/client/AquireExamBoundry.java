package il.cshaifasweng.OCSFMediatorExample.client;



import il.cshaifasweng.OCSFMediatorExample.Controller.AquireExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.EditSelectedExamController;
import il.cshaifasweng.OCSFMediatorExample.Controller.QuestionController;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AquireExamBoundry {

    @FXML
    private Button backBtn;

    @FXML
    private ListView<Exam> listViewE;

    @FXML
    private ComboBox<Course> selectCourse;

    @FXML
    private ComboBox<Subject> selectSubject;

    @FXML
    private Button showExamBtn;

    private AquireExamController aquireExamController;

    @FXML
    private ListView<Question> selectedExam;

    @FXML
    private Button showEntireExam;

    @FXML
    private ComboBox<String> examType;

    @FXML
    private Button aquireBtn;

    @FXML
    private TextField executionCodeTxt;

    @FXML
    void aquireAction(ActionEvent event) throws IOException {

        List<Integer> list = new ArrayList<Integer>();
        list.add(listViewE.getSelectionModel().getSelectedItem().getId());
        if (examType.getSelectionModel().getSelectedItem() == "Manual")
        {
            list.add(-1);
        }
        else
        {
            list.add(-2);
        }
        Teacher teacher = (Teacher)SimpleClient.getClient().getUser();
        Object obj = new Object[]{list,executionCodeTxt.getText(), teacher};
        Message message = new Message("aquireExam", obj);
        SimpleClient.getClient().sendToServer(message);

    }

    @FXML
    void examTypeAction(ActionEvent event)
    {
        executionCodeTxt.setVisible(true);
    }

    @FXML
    void executionCodeAction(ActionEvent event)
    {
        aquireBtn.setVisible(true);
    }
    @FXML
    void showEntireExamAction(ActionEvent event)
    {
        if (listViewE.getSelectionModel().isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Select An Exam!");
            });
        }
        else
        {
            selectedExam.setVisible(true);
            Exam exam = listViewE.getSelectionModel().getSelectedItem();
            List<Question> list = exam.getListOfQuestions();
            ObservableList<Question> questionList = FXCollections.observableArrayList(list);
            selectedExam.setItems(questionList);
            selectedExam.setCellFactory(param -> {
                System.out.println("selecting questions");
                for (Question l : list)
                {
                    System.out.println(l.getQText()  + " after " + l.getScore());
                }
                return new AquireExamBoundry.ScoredQuestionListCell(false);
            });
        }
    }
    @FXML
    void backAction(ActionEvent event)
    {
        EventBus.getDefault().unregister(aquireExamController);
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
    void selectSubjectAction(ActionEvent event) throws IOException {
        aquireExamController.getCourses(selectSubject.getSelectionModel().getSelectedItem());
        selectCourse.setVisible(true);
    }

    @FXML
    void showExamAction(ActionEvent event) throws IOException
    {
        listViewE.setVisible(true);
        Course course = getSelectCourse().getValue();
        Message message = new Message("showExamsAE", course);
        SimpleClient.getClient().sendToServer(message);
    }

    public void setAquireExamController(AquireExamController aquireExamController) {
        this.aquireExamController = aquireExamController;
    }

    @FXML
    public void initialize() throws IOException
    {
        aquireExamController = new AquireExamController(this);
        this.setAquireExamController(aquireExamController);

        selectCourse.setVisible(false);
        showExamBtn.setVisible(false);
        aquireBtn.setVisible(false);
        executionCodeTxt.setVisible(false);
        listViewE.setVisible(false);
        selectedExam.setVisible(false);
        aquireExamController.getSubjects();
        examType.getItems().addAll("Manual", "Computerized");
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

    public ComboBox<Subject> getSelectSubject() {
        return selectSubject;
    }

    public ComboBox<Course> getSelectCourse() {
        return selectCourse;
    }

    public Button getBackBtn() {
        return backBtn;
    }

    public ListView<Exam> getListViewE() {
        return listViewE;
    }

    public Button getShowExamBtn() {
        return showExamBtn;
    }

    public AquireExamController getAquireExamController() {
        return aquireExamController;
    }

    public void setSelectSubject(ComboBox<Subject> selectSubject) {
        this.selectSubject = selectSubject;
    }

    public void setSelectCourse(ComboBox<Course> selectCourse) {
        this.selectCourse = selectCourse;
    }

    public void setBackBtn(Button backBtn) {
        this.backBtn = backBtn;
    }

    public void setListViewE(ListView<Exam> listViewE) {
        this.listViewE = listViewE;
    }

    public void setShowExamBtn(Button showExamBtn) {
        this.showExamBtn = showExamBtn;
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
    private class ScoredQuestionListCell extends ListCell<Question> {
        private TextField scoreField;
        private boolean firstRow;

        ScoredQuestionListCell(boolean firstRow) {
            this.firstRow = firstRow;
        }

        @Override
        protected void updateItem(Question question, boolean empty) {
            super.updateItem(question, empty);
            if (empty || question == null) {
                setText(null);
                setGraphic(null);
            } else {
                HBox container = new HBox();
                container.setAlignment(Pos.CENTER_LEFT);
                container.setStyle("-fx-border-width: 0 0 1 0; -fx-border-color: black;");

                Label questionTextLabel = new Label(question.getQText());
                Label answer1 = new Label(question.getAnswer1());
                Label answer2 = new Label(question.getAnswer2());
                Label answer3 = new Label(question.getAnswer3());
                Label answer4 = new Label(question.getAnswer4());
                VBox questionVBox = new VBox(questionTextLabel, answer1, answer2, answer3, answer4);

                TextField scoreField = new TextField();
                scoreField.setPrefWidth(50);
                scoreField.setText(Integer.toString(question.getScore()));
                scoreField.textProperty().addListener((observable, oldValue, newValue) -> {
                    try {
                        int score = Integer.parseInt(newValue);
                        question.setScore(score);
                    } catch (NumberFormatException e) {
                        // Handle invalid input
                    }
                });

                Region region1 = new Region();
                Region region2 = new Region();
                Region region3 = new Region();
                HBox.setHgrow(region1, Priority.ALWAYS);
                HBox.setHgrow(region2, Priority.ALWAYS);
                HBox.setHgrow(region3, Priority.ALWAYS);

                container.getChildren().addAll(region2, questionVBox, region3, scoreField);

                setGraphic(container);
            }
        }
    }
}
