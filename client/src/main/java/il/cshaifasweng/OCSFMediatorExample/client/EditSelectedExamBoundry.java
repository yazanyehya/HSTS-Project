package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.Controller.EditSelectedExamController;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import javafx.util.StringConverter;
import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class EditSelectedExamBoundry {

    @FXML
    private Label timeLabel;
    private AnimationTimer animationTimer;

    @FXML
    private Button backBtn;

    @FXML
    private ImageView logo;

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
        Image logoImage = new Image(getClass().getResourceAsStream("/images/finallogo.png"));
        logo.setImage(logoImage);
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

        animationTimer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                updateDateTime();
            }
        };
        animationTimer.start();
    }



    private void updateDateTime() {
        // Get the current date and time
        LocalDateTime currentDateTime = LocalDateTime.now();



        // Format the date and time as desired (change the pattern as needed)
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd\n " +
                "HH:mm:ss");
        String dateTimeString = currentDateTime.format(formatter);



        // Update the label text
        timeLabel.setText(dateTimeString);
    }



    // Override the stop method to stop the AnimationTimer when the application exits
    public void stop() {
        animationTimer.stop();
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
        if (chooseCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->{
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select the course");
            });
        }
        else
        {
            editSelectedExamController.getAllQuestions();
        }
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
        if (chooseCourse.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select a course");
            });
        }
        else if(listOfAllQuestions.getSelectionModel().isEmpty() && questionListView.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no selected questions ");
            });
        }
        else if (examPeriod.getText().equals("") || !examPeriod.getText().matches("\\d+"))
        {
            Platform.runLater(() ->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please Enter a valid exam period");
            });
        }
        else
        {
            System.out.println("incline bench press");
            System.out.println(selectedQuestions.getItems().size());
            for(Question q : selectedQuestions.getItems())
            {
                System.out.println(q.getScore());
            }
            editSelectedExamController.saveExam(selectedQuestions.getItems());

            System.out.println("incline smith bench press");
        }
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
        if (questionListView.getSelectionModel().isEmpty() && listOfAllQuestions.getSelectionModel().isEmpty())
        {
            Platform.runLater(()->
            {
                showAlertDialog(Alert.AlertType.ERROR, "Error", "Please select questions!");
            });
        }
        else
        {
            List<Question> selectedFromQuestionList = questionListView.getSelectionModel().getSelectedItems();
            List<Question> selectedFromAllQuestionsList = listOfAllQuestions.getSelectionModel().getSelectedItems();

            List<Question> combinedList = new ArrayList<>();
            combinedList.addAll(selectedFromQuestionList);
            combinedList.addAll(selectedFromAllQuestionsList);
            System.out.println(combinedList.size() + "sizeeeeeee");
            ObservableList<Question> observableCombinedList = FXCollections.observableArrayList(combinedList);
            selectedQuestions.setItems(observableCombinedList);
            selectedQuestions.setCellFactory(param -> new ScoredQuestionListCell(false));
        }
    }


    public ComboBox<Course> getChooseCourse() {
        return chooseCourse;
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
