package il.cshaifasweng.OCSFMediatorExample.Controller;

import il.cshaifasweng.OCSFMediatorExample.client.*;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
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
    private HashMap<Question, Integer> questionHashMap1;
    private HashMap<Question, Integer> questionHashMap2;
    private List<Question> listQ;
    private String course;
    private String subject;

    public EditSelectedExamController(EditSelectedExamBoundry editSelectedExamBoundry)
    {
        EventBus.getDefault().register(this);
        this.editSelectedExamBoundry = editSelectedExamBoundry;
        this.questionHashMap1 = new HashMap<>();
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
                System.out.println(question.getQText() + "  " + question.getScore());
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
                        System.out.println(question.getQText() + "  " + question.getScore());
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
        Exam exam = (Exam)editSelectedExamEvent.getMessage().getBody();
        List<Question> list = exam.getListOfQuestions();
        listQ = new ArrayList<Question>();
        List<Question> clones = new ArrayList<Question>();
        System.out.println("viewing old exam");
        for (Question l : list)
        {
            Question q = l.clone();
            q.setScore(l.getScore());
            clones.add(q);
            listQ.add(q);
        }
        ObservableList<Question> questionList = FXCollections.observableArrayList(clones);
        editSelectedExamBoundry.getQuestionListView().setItems(questionList);
        editSelectedExamBoundry.getQuestionListView().setCellFactory(param -> {
            System.out.println("selecting questions");
            for (Question l : list)
            {
                System.out.println(l.getQText()  + " after " + l.getScore());
            }
            return new EditSelectedExamController.ScoredQuestionListCell(false);
        });
        editSelectedExamBoundry.getExamPeriod().setText(exam.getExamPeriod());
        editSelectedExamBoundry.getCommentStudet().setText(exam.getStudentComments());
        editSelectedExamBoundry.getCommentTeacher().setText(exam.getTeacherComments());
        subject = exam.getSubject().getName();
        course = exam.getCourse().getName();
        Platform.runLater(() -> {
            // Set the items for the ComboBox
            ObservableList<Course> courseObservableList = FXCollections.observableArrayList(exam.getCourse());
            editSelectedExamBoundry.getChooseCourse().setItems(courseObservableList);
            editSelectedExamBoundry.getChooseCourse().setValue(exam.getCourse());
        });
    }
    private boolean checkQuestion(Question q1)
    {
        for (Question q2 : listQ)
        {
            if (Objects.equals(q1.getqText(), q2.getqText()) && Objects.equals(q1.getAnswer1(), q2.getAnswer1())
                    && Objects.equals(q1.getAnswer2(), q2.getAnswer2()) && Objects.equals(q1.getAnswer3(), q2.getAnswer3())
                    && Objects.equals(q1.getAnswer4(), q2.getAnswer4()) && Objects.equals(q1.getCorrectAnswer(), q2.getCorrectAnswer()))
            {
                return true;
            }
        }
        return false;
    }
    @Subscribe
    public void handleGetAllQuestionsEvent(GetAllQuestionEvent getAllQuestionEvent) {
        List<Question> allQuestions = (List<Question>) getAllQuestionEvent.getMessage().getBody();
        //List<Question> selectedQuestions = new ArrayList<>(editSelectedExamBoundry.getQuestionListView().getSelectionModel().getSelectedItems());
        System.out.println("ramiss" + listQ.size());
        // Create a new list to store the questions that are not selected
        List<Question> remainingQuestions = new ArrayList<Question>();

        // Iterate over allQuestions and add only the questions that are not selected to remainingQuestions
        for (Question question : allQuestions)
        {
            if(!checkQuestion(question))
            {
                remainingQuestions.add(question.clone());
            }
        }
        if (remainingQuestions.isEmpty())
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error", "There are no more Available Questions for this course, Go create a few");
                //EventBus.getDefault().unregister(this);
            });
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
        String s = "";
        Exam exam = (Exam)saveEditedExamEvent.getMessage().getBody();
        double sum = 0;
        for (Question q : exam.getListOfQuestions())
        {
            if (q.getScore() <= 0)
            {
                exam = null;
                s = "At least one Question has 0 score";
            }
            sum += q.getScore();
        }
        System.out.println("edit selected grade "+sum);
        if (s.equals("At least one Question has 0 score"))
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error","At least one Question has 0 score!");
                editSelectedExamBoundry.getSelectedQuestions().getItems().clear();
            });

        }
        else if (sum != 100 && s.equals(""))
        {
            Platform.runLater(() -> {
                // Login failure
                showAlertDialog(Alert.AlertType.ERROR, "Error","Total sum of Question Score is not equal to 100!");
                editSelectedExamBoundry.getSelectedQuestions().getItems().clear();
            });
        }
        else
        {
            Platform.runLater(() -> {
                // Login success
                EventBus.getDefault().unregister(this);
                showAlertDialog(Alert.AlertType.INFORMATION, "Success", "Exam Saved Successfully");
                try {
                    SimpleChatClient.switchScreen("EditExam");
                } catch (IOException e) {
                    e.printStackTrace();
                }

            });
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
            questionHashMap1.put(question, question.getScore()); // Set a default score of 0 for each selected question
        }
        System.out.println(questionHashMap1.size());
        String fullName = ((Teacher)SimpleClient.getClient().getUser()).getFirstName() + " " + ((Teacher)SimpleClient.getClient().getUser()).getLastName();
        ExamHelper examHelper = new ExamHelper(editSelectedExamBoundry.getExamPeriod().getText(), ((Teacher)SimpleClient.getClient().getUser()).getUsername(), fullName,questionHashMap1, subject, course, editSelectedExamBoundry.getCommentTeacher().getText(), editSelectedExamBoundry.getCommentStudet().getText());
        Object object = new Object[]{SimpleClient.getClient().getUser(), examHelper};
        Message message = new Message("saveEditedExam", object);
        SimpleClient.getClient().sendToServer(message);
    }
}
 