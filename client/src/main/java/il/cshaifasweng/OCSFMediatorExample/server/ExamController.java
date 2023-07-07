package il.cshaifasweng.OCSFMediatorExample.server;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.Course;
import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.Question;
import javafx.application.Platform;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExamController
{
    ExamBoundry examBoundry;
    private HashMap<Question, Integer> questionHashMap;

    public ExamController(ExamBoundry examBoundry)
    {
        EventBus.getDefault().register(this);
        this.examBoundry = examBoundry;
        this.questionHashMap = new HashMap<>();
    }
    public void saveExam(List<Question> selectedQuestions) throws IOException {
        System.out.println(examBoundry.getExamPeriod().getText());
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
            System.out.println(question.getQText());
            questionHashMap.put(question, 0); // Set a default score of 0 for each selected question
        }

        ExamHelper examHelper = new ExamHelper(examBoundry.getExamPeriod().getText(), SimpleClient.getClient().getUser().getFirstName(), questionHashMap);
        Message message = new Message("saveExam", examHelper);
        SimpleClient.getClient().sendToServer(message);
    }

    public void selectAction(Course course) throws IOException
    {
        Message message = new Message("createExam", course);
        SimpleClient.getClient().sendToServer(message);
    }
    public void pressBack() throws IOException
    {
        Message message = new Message("pressBack", null);
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
                // Top row: Labels

                // First column: Select (Checkbox)
                CheckBox checkBox = new CheckBox();
                checkBox.selectedProperty().bindBidirectional(question.selectedProperty());


                // Second column: Question
                Label questionTextLabel = new Label(question.getQText());
                Label answer1 = new Label(question.getAnswer1());
                Label answer2 = new Label(question.getAnswer2());
                Label answer3 = new Label(question.getAnswer3());
                Label answer4 = new Label(question.getAnswer4());
                VBox questionVBox = new VBox(questionTextLabel, answer1, answer2, answer3, answer4);

                // Third column: Score
                TextField scoreField = new TextField();
                scoreField.setPrefWidth(50);
                scoreField.textProperty().bindBidirectional(question.scoreProperty(), new NumberStringConverter());

                Region region1 = new Region();
                Region region2 = new Region();
                Region region3 = new Region();
                HBox.setHgrow(region1, Priority.ALWAYS);
                HBox.setHgrow(region2, Priority.ALWAYS);
                HBox.setHgrow(region3, Priority.ALWAYS);

                // Add question components to container
                container.getChildren().addAll(checkBox, region2,questionVBox, region3, scoreField);

                setGraphic(container);
            }
        }

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
    public  void handlePressBack(PressBackEvent pressBackEvent)
    {
        Platform.runLater(() -> {
            try {
                SimpleChatClient.switchScreen("teacherBoundry");
                EventBus.getDefault().unregister(this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
    }
    @Subscribe
    public void handleSaveExamEvent(saveExamEvent saveExamEvent)
    {

        if (saveExamEvent.getMessage().getBody() == null) {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "One of the Field is not assigned");
            });
        } else {
            if (saveExamEvent.getMessage().getTitle().equals("saveExam"))
            {
                Platform.runLater(() -> {
                    // Login success
                    showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam Saved Successfully");
                });
            }
        }
    }
    @Subscribe
    public void handleSelectedQuestionEvent(SelectQuestionEvent selectQuestionEvent) {
        Object body = selectQuestionEvent.getMessage().getBody();
        if (body instanceof List) {
            List<?> list = (List<?>) body;
            if (!list.isEmpty() && list.get(0) instanceof Question) {
                List<Question> listOfQuestions = (List<Question>) body;
                ObservableList<Question> questionList = FXCollections.observableArrayList(listOfQuestions);
                ListView<Question> questionListView = examBoundry.getQuestionListView();
                questionListView.setItems(questionList);
                System.out.println("kkkk");
                questionListView.setCellFactory(param -> {
                    System.out.println("ffff");
                    int[] counter1 = {0};
                    boolean firstRow = (counter1[0]++ == 0);
                    System.out.println(firstRow);
                    return new ExamController.ScoredQuestionListCell(firstRow);
                });
                for (Question question : listOfQuestions) {
                    if (question.getSelected()) {
                        int grade = question.getScore();
                        questionHashMap.put(question, grade);
                    }
                }
                for (Map.Entry<Question, Integer> entry : questionHashMap.entrySet()) {
                    Question question = entry.getKey();
                    Integer value = entry.getValue();
                    System.out.println("Question: " + question.getQText() + ", Value: " + value);
                }
            }
        }
    }

    private BooleanProperty getQuestionSelectedProperty(Question question) {
        if (question != null) {
            return question.selectedProperty();
        } else {
            // Handle the case where the question is null
            // Return an appropriate BooleanProperty or handle the situation accordingly
            return new SimpleBooleanProperty(false); // Default value
        }
    }
}

