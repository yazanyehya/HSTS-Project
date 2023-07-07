//package il.cshaifasweng.OCSFMediatorExample.client;
//import il.cshaifasweng.OCSFMediatorExample.entities.Message;
//import il.cshaifasweng.OCSFMediatorExample.entities.Student;
//import javafx.application.Platform;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import javafx.event.ActionEvent;
//import javafx.fxml.FXML;
//import javafx.scene.control.*;
//import javafx.scene.text.Text;
//import org.greenrobot.eventbus.EventBus;
//import org.greenrobot.eventbus.Subscribe;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//public class PrimaryController {
//	private Text text;
//
//	@FXML
//	private Button getAllStudentsBtn;
//
//	@FXML
//	private Button updateBtn;
//
//	@FXML
//	private Button getGradesBtn;
//
//	@FXML
//	private ListView<Student> viewAllStudents;
//
//	@FXML
//	private TextField viewGrades;
//
//	@FXML
//	private TextField viewGrades1;
//
//	@FXML
//	void getAllStudents(ActionEvent event) throws IOException {
//		Message message = new Message(getAllStudentsBtn.getText(), null) ;
//		SimpleClient.getClient().sendToServer(message);
//
//	}
//
//	@FXML
//	void getGrades(ActionEvent event) throws IOException {
//		Student studentSel =viewAllStudents.getSelectionModel().getSelectedItem();
//		if(studentSel != null){
//			Message message = new Message(getGradesBtn.getText(), studentSel) ;
//			SimpleClient.getClient().sendToServer(message);}
//		else {
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setTitle("No student selected");
//			alert.setHeaderText("Please select a student to update");
//			alert.showAndWait();
//		}
//	}
//
//	@FXML
//	void updateBtn(ActionEvent event) throws IOException {
//		Student studentSel = viewAllStudents.getSelectionModel().getSelectedItem();
//		if (studentSel != null) {
//			ChoiceDialog<String> dialog = new ChoiceDialog<>("English", "Math");
//			dialog.setTitle("Update Score");
//			dialog.setHeaderText("Choose a subject to update");
//			dialog.setContentText("Subject:");
//			Student finalStudentSel = studentSel;
//			dialog.showAndWait().ifPresent(subject -> {
//				TextInputDialog dialog2 = new TextInputDialog();
//				dialog2.setTitle("Update Score");
//				dialog2.setHeaderText("Enter the new score");
//				dialog2.setContentText("New Score:");
//				dialog2.showAndWait().ifPresent(scoreStr -> {
//					try {
//						int score = Integer.parseInt(scoreStr);
//						while (score < 0 || score > 100) {
//							Alert alert = new Alert(Alert.AlertType.ERROR);
//							alert.setTitle("Invalid Score");
//							alert.setHeaderText("Score must be between 0 and 100");
//							alert.showAndWait();
//							Optional<String> input = dialog2.showAndWait();
//							if (input.isPresent()) {
//								score = Integer.parseInt(input.get());
//							} else {
//								// Handle case when dialog is closed without input
//								score = -1; // Set an invalid score to exit the loop
//      							break;
//}
//						}
//
//						Message message = new Message(updateBtn.getText(), studentSel);
//						message.setNewScore(score);
//						message.setSubject(subject);
//						SimpleClient.getClient().sendToServer(message);
//
//
//
//					} catch (NumberFormatException e) {
//						Alert alert = new Alert(Alert.AlertType.ERROR);
//						alert.setTitle("Invalid Score");
//						alert.setHeaderText("Score must be a number");
//						alert.showAndWait();
//					} catch (Exception e) {
//						e.printStackTrace();
//					}
//				});
//			});
//		} else {
//			Alert alert = new Alert(Alert.AlertType.ERROR);
//			alert.setTitle("No student selected");
//			alert.setHeaderText("Please select a student to update");
//			alert.showAndWait();
//}
//	}
//	@Subscribe
//	public void newMessageArrived(Message message)
//	{
//		String rply = message.getOperation();
//
//		if (rply.equals("Get all Students"))
//		{
//			List<Student> students = (List<Student>)message.getMessage();
//			updateListView(students);
//		}else if(rply.equals("get grades")){
//			Student studentSel = (Student) message.getMessage();
//			if (studentSel != null)
//			{
//				Platform.runLater(()->{
//// Create a dialog box to allow the user to choose which subject they want to view
//				ChoiceDialog<String> dialog = new ChoiceDialog<>("English", "Math");
//
//				dialog.setTitle("Select Subject");
//				dialog.setHeaderText(null);
//				dialog.setContentText("Please select the subject:");
//
//// Get the user's choice and update the text field accordingly
//				Optional<String> result = dialog.showAndWait();
//				result.ifPresent(subject ->
//				{
//					if (subject.equals("English"))
//					{
//						//viewGrades.setText("English Grade : " + studentSel.getEnglishScore());
//					} else if (subject.equals("Math"))
//					{
//						//viewGrades.setText("Math Grade : " + studentSel.getMathScore());
//					}
//
//				});
//				});
//		}
//
//	} else if(rply.equals("update")) {
//			showSuccessAlert("Grade updated Successfully");
//
//		}
//	}
//	private void updateListView(List<Student> students){
//		ObservableList<Student> studentList = FXCollections.observableArrayList(students);
//		viewAllStudents.setItems(studentList);
//	}
//
//	@FXML
//	void initialize() {
//		EventBus.getDefault().register(this);
//
//	}
//	public static void showSuccessAlert(String message){
//		Platform.runLater(()->{
//				Alert alert = new Alert(Alert.AlertType.INFORMATION);
//				alert.setTitle("success");
//				alert.setHeaderText(null);
//				alert.setContentText(message);
//				alert.showAndWait();
//		});
//
//}}
