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
import java.util.List;

public class EditSelectedExamBoundry {


    @FXML
    private Button backBtn;

    @FXML
    private TextArea commentStudet;

    @FXML
    private TextArea commentTeacher;

    @FXML
    private TextField examPeriod;

    @FXML
    private ListView<Question> listOfAllQuestions;

    @FXML
    private ListView<Question> questionListView;

    @FXML
    private ListView<Question> selectedQuestions;

    @FXML
    private ComboBox<Course> chooseCourse;

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
                EventBus.getDefault().unregister(editSelectedExamController);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }

    @FXML
    void showAction(ActionEvent event) throws IOException
    {
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
        System.out.println("incline bench press");
        editSelectedExamController.saveExam(selectedQuestions.getItems());
        System.out.println("incline smith bench press");
    }

    public ListView<Question> getSelectedQuestions() {
        return selectedQuestions;
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

    public Button getSelectBtn() {
        return selectBtn;
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


    public void setEditSelectedExamController(EditSelectedExamController editSelectedExamController) {
        this.editSelectedExamController = editSelectedExamController;
    }

    public TextArea getCommentTeacher() {
        return commentTeacher;
    }

    public void setCommentStudet(TextArea commentStudet) {
        this.commentStudet = commentStudet;
    }

    public TextArea getCommentStudet() {
        return commentStudet;
    }

    public ListView<Question> getListOfAllQuestions() {
        return listOfAllQuestions;
    }

    public void setListOfAllQuestions(ListView<Question> listOfAllQuestions) {
        this.listOfAllQuestions = listOfAllQuestions;
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

                container.getChildren().addAll(questionVBox, region3, scoreField);

                setGraphic(container);
            }
        }
    }
    public void ConfirmSelect(ActionEvent actionEvent) throws IOException {
        List<Question> selectedFromQuestionList = questionListView.getSelectionModel().getSelectedItems();
        List<Question> selectedFromAllQuestionsList = listOfAllQuestions.getSelectionModel().getSelectedItems();

        List<Question> combinedList = new ArrayList<>();
        combinedList.addAll(selectedFromQuestionList);
        combinedList.addAll(selectedFromAllQuestionsList);

        ObservableList<Question> observableCombinedList = FXCollections.observableArrayList(combinedList);
        selectedQuestions.setItems(observableCombinedList);
        selectedQuestions.setCellFactory(param -> new ScoredQuestionListCell(false));
    }


    public ComboBox<Course> getChooseCourse() {
        return chooseCourse;
    }
}
