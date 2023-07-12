package il.cshaifasweng.OCSFMediatorExample.client;

import il.cshaifasweng.OCSFMediatorExample.entities.Message;
import il.cshaifasweng.OCSFMediatorExample.entities.User;
import org.greenrobot.eventbus.EventBus;

import il.cshaifasweng.OCSFMediatorExample.client.ocsf.AbstractClient;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

public class SimpleClient extends AbstractClient {

	private static SimpleClient client = null;
	private Socket clientSocket;
	private ObjectOutputStream outputStream;

	private static User user = null;

	private SimpleClient(String localhost, int port) {
		super(port);
		try {
			clientSocket = new Socket(localhost, port);
			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public User getUser() {
		if (user == null) {
			// Create a default user object if user is null
			user = new User();
			user.setUsername("Guest");
			user.setLoggedIn(false);
		}
		return user;
	}

	@Override
	protected void handleMessageFromServer(Object msg)
	{
		Message message = (Message) msg;

		if(message.getTitle().equals("Login")){
			user = (User) ((Message) msg).getBody();
			EventBus.getDefault().post(msg);
		}
		else if(message.getTitle().equals("already logged in")){
			user = (User) ((Message) msg).getBody();
			EventBus.getDefault().post(new AlreadyLoggedIn((Message)msg));
		}
		else if(message.getTitle().equals("createQuestion"))
		{
			EventBus.getDefault().post(new QuestionEvent((Message) msg));
		}
		else if(message.getTitle().equals("changeToQuestionBoundry"))
		{
			EventBus.getDefault().post(new ChangeToQuestionBoundry((Message) msg));
		}
		else if(message.getTitle().equals("getSubjectsForTeacher"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherEvent((Message) msg));
		}
		else if(message.getTitle().equals("getSubjectsForTeacherExam"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherExamEvent((Message) msg));
		}

		else if(message.getTitle().equals("getCoursesForSubjects"))
		{
			EventBus.getDefault().post(new GetCoursesForSubjectsEvent((Message) msg));
		}

		else if(message.getTitle().equals("getCoursesForSubjectsEQ"))
		{
			EventBus.getDefault().post(new GetCoursesForSubjectsEventEQ((Message) msg));
		}
	else if(message.getTitle().equals("getCoursesForSubjectsExam"))
	{
		EventBus.getDefault().post(new GetCoursesForSubjectsExamEvent((Message) msg));
	}
		else if(message.getTitle().equals("getSubjectsForTeacherEQ"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherEventEQ((Message) msg));
		}
		else if(message.getTitle().equals("getCoursesForSubjects"))
		{
			EventBus.getDefault().post(new GetCoursesForSubjectsEvent((Message) msg));
		}
		else if(message.getTitle().equals("getCourses"))
		{
			EventBus.getDefault().post(new GetCoursesEvent((Message) msg));
		}
		else if(message.getTitle().equals("getAllQuestions"))
		{
			System.out.println("getAllQuestionsEvent");
			EventBus.getDefault().post(new GetAllQuestionEvent((Message) msg));
		}
		else if(message.getTitle().equals("saveEditedQuestion"))
		{
			EventBus.getDefault().post(new SaveEditedQuestionEvent((Message) msg));
		}
		else if(message.getTitle().equals("saveEditedExam"))
		{
			EventBus.getDefault().post(new SaveEditedExamEvent((Message) msg));
		}
		else if(message.getTitle().equals("getSubjects"))
		{
			System.out.println("event get subjects");
			EventBus.getDefault().post(new GetSubjectsEvent((Message) msg));
		}
		else if(message.getTitle().equals("showQuestions"))
		{
			System.out.println("event get subjects");
			EventBus.getDefault().post(new ShowQuestionsEvent((Message) msg));
		}
		else if(message.getTitle().equals("showExams"))
		{
			EventBus.getDefault().post(new ShowExamsEvent((Message) msg));
		}
		else if(message.getTitle().equals("changeToEditQuestionBoundry"))
		{
			EventBus.getDefault().post(new ChangeToEditQuestionEvent((Message) msg));
		}
		else if(message.getTitle().equals("editSelectedQuestion"))
		{
			EventBus.getDefault().post(new EditSelectedQuestionEvent((Message) msg));
		}
		else if(message.getTitle().equals("editSelectedExam"))
		{
			EventBus.getDefault().post(new EditSelectedExamEvent((Message) msg));
		}
		else if(message.getTitle().equals("changeToExamBoundry"))
		{
			EventBus.getDefault().post(new ChangeToExamBoundry((Message) msg));
		}
		else if(message.getTitle().equals("pressBack"))
		{
			EventBus.getDefault().post(new PressBackEvent((Message) msg));
		}
		else if(message.getTitle().equals("Logout"))
		{
			System.out.println("event bus logout");
			EventBus.getDefault().post(new LogoutEvent((Message)msg));
		}
		else if(message.getTitle().equals("createExam"))
		{
			EventBus.getDefault().post(new SelectQuestionEvent((Message)msg));
		}
		else if(message.getTitle().equals("saveExam"))
		{
			EventBus.getDefault().post(new saveExamEvent((Message)msg));
		}
	}
	public static SimpleClient getClient() {
		if (client == null) {
			client = new SimpleClient("localhost", 3000);
		}
		return client;
	}

}
