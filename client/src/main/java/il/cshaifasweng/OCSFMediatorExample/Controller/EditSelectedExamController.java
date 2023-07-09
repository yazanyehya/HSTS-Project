package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Exam;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import il.cshaifasweng.OCSFMediatorExample.entities.Teacher;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.util.converter.NumberStringConverter;
import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class EditSelectedExamController
{
    EditSelectedExamBoundry editSelectedExamBoundry;
    private HashMap<Question, Integer> questionHashMap;
    private List<Question> listQ;

    public EditSelectedExamController(EditSelectedExamBoundry editSelectedExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.editSelectedExamBoundry = editSelectedExamBoundry;
        this.questionHashMap = new HashMap<>();
    }

    public void getAllQuestions() throws IOException {
        Teacher teacher = (Teacher) (SimpleClient.getClient().getUser());
        Message message = new Message("getAllQuestions",editSelectedExamBoundry.getChooseCourse().getSelectionModel().getSelectedItem());
        SimpleClient.getClient().sendToServer(message);
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
    @Subscribe
    public void handleEditSelectedExam(EditSelectedExamEvent editSelectedExamEvent)
    {
        System.out.println("EditSelectedExamEvent");
        List<Question> list = ( List<Question>)editSelectedExamEvent.getMessage().getBody();
        listQ = new ArrayList<Question>();
        for (Question l : list)
        {
            listQ.add(l);
        }
        ObservableList<Question> questionList = FXCollections.observableArrayList(list);
        editSelectedExamBoundry.getQuestionListView().setItems(questionList);
        editSelectedExamBoundry.getQuestionListView().setCellFactory(param -> {
            return new EditSelectedExamController.ScoredQuestionListCell(false);
        });

    }
    @Subscribe
    public void handleGetAllQuestionsEvent(GetAllQuestionEvent getAllQuestionEvent) {
        List<Question> allQuestions = (List<Question>) getAllQuestionEvent.getMessage().getBody();
        //List<Question> selectedQuestions = new ArrayList<>(editSelectedExamBoundry.getQuestionListView().getSelectionModel().getSelectedItems());
        System.out.println("ramiss" + listQ.size());
        // Create a new list to store the questions that are not selected
        List<Question> remainingQuestions = new ArrayList<>();

        // Iterate over allQuestions and add only the questions that are not selected to remainingQuestions
        for (Question question : allQuestions) {
            if (!listQ.contains(question))
            {
                System.out.println("kkk");
                remainingQuestions.add(question);
            }
        }
        System.out.println(remainingQuestions);
        // Set the remainingQuestions as the items of editSelectedExamBoundry.getListOfAllQuestions()
        ObservableList<Question> questionList = FXCollections.observableArrayList(remainingQuestions);
        editSelectedExamBoundry.getListOfAllQuestions().setItems(questionList);
        editSelectedExamBoundry.getListOfAllQuestions().setCellFactory(param -> {
            return new EditSelectedExamController.ScoredQuestionListCell(false);
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
    @Subscribe
    public void handleSaveEditedExam(SaveEditedExamEvent saveEditedExamEvent)
    {
        System.out.println("notify client that editexam is saved");
        if (saveEditedExamEvent.getMessage().getBody() == null)
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "One of the Field is not assigned");
            });
        } else {
            if (saveEditedExamEvent.getMessage().getTitle().equals("saveEditedExam"))
            {
                Platform.runLater(() -> {
                    // Login success
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam Saved Successfully");
                });
            }
        }
    }
    public void saveExam(List<Question> selectedQuestions) throws IOException {
        System.out.println(editSelectedExamBoundry.getExamPeriod().getText());
        System.out.println(SimpleClient.getClient().getUser().getFirstName());

        // Clear the questionHashMap before assigning the selected questions
        //questionHashMap.clear();

        // Get the selected questions from the questionListView
        if (selectedQuestions != null)
        {
            System.out.println("selectedQuestions is null");
        }
        // Assign the selected questions to the questionHashMap
        System.out.println(selectedQuestions.size());
        for (Question question : selectedQuestions) {
            System.out.println(question.getQText() + "  score: " + question.getScore());
            questionHashMap.put(question, question.getScore()); // Set a default score of 0 for each selected question
        }

        ExamHelper examHelper = new ExamHelper(editSelectedExamBoundry.getExamPeriod().getText(), ((Teacher)SimpleClient.getClient().getUser()).getUsername(), questionHashMap, ((Teacher)SimpleClient.getClient().getUser()).getSubject(), ((Teacher)SimpleClient.getClient().getUser()).getCourses().get(0));
        Message message = new Message("saveEditedExam", examHelper);
        SimpleClient.getClient().sendToServer(message);
    }
}
