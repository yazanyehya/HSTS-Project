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
	//private Socket clientSocket;
	//private ObjectOutputStream outputStream;

	private static User user = null;

	private SimpleClient(String localhost, int port) {
		super(localhost,port);
//		try {
//			clientSocket = new Socket(localhost, port);
//			outputStream = new ObjectOutputStream(clientSocket.getOutputStream());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
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

		if(message.getTitle().equals("Login")||message.getTitle().equals("wrongType")){
			user = (User) ((Message) msg).getBody();
			EventBus.getDefault().post(msg);
		}
		else if(message.getTitle().equals("already logged in")){
			user = (User) ((Message) msg).getBody();
			EventBus.getDefault().post(new AlreadyLoggedIn((Message)msg));
		}
		else if(message.getTitle().equals("createQuestion")  ||message.getTitle().equals("LogoutQB"))
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
		else if(message.getTitle().equals("getSubjectsForTeacherEQ") || message.getTitle().equals("getQuestionsForSubject") || message.getTitle().equals("getQuestionsForSubjectAndCourse"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherEventEQ((Message) msg));
		}
		else if(message.getTitle().equals("getSubjectsForTeacherAE"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherEventAE((Message) msg));
		}
		else if(message.getTitle().equals("getSubjectsForTeacherAPP"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherAPPEvent((Message) msg));
		}
		else if(message.getTitle().equals("getCoursesForSubjects"))
		{
			EventBus.getDefault().post(new GetCoursesForSubjectsEvent((Message) msg));
		}
		else if(message.getTitle().equals("getCourses"))
		{
			EventBus.getDefault().post(new GetCoursesEvent((Message) msg));
		}
		else if(message.getTitle().equals("showExamsAE"))
		{
			EventBus.getDefault().post(new ShowExamAEEvent((Message) msg));
		}
		else if(message.getTitle().equals("aquireExam"))
		{
			EventBus.getDefault().post(new AquireExamEvent((Message) msg));
		}
		else if(message.getTitle().equals("getCoursesSE"))
		{
			EventBus.getDefault().post(new GetCoursesSEEvent((Message) msg));
		}
		else if(message.getTitle().equals("getCoursesAPP"))
		{
			EventBus.getDefault().post(new GetCoursesAPPEvent((Message) msg));
		}
		else if(message.getTitle().equals("startExamFailed"))
		{
			EventBus.getDefault().post(new StartExamFailedEvent((Message) msg));
		}
		else if (message.getTitle().equals("showStudents"))
		{
			EventBus.getDefault().post(new ShowStudentsEvent((Message) msg));
		}
		else if (message.getTitle().equals("sendToStudent"))
		{
			EventBus.getDefault().post(new SendToStudentEvent((Message) msg));
		}
		else if (message.getTitle().equals("StartSolvingComputerizedExam"))
		{
			EventBus.getDefault().post(new StartExamEvent((Message) msg));
		}
		else if (message.getTitle().equals("changeToStartExam"))
		{
			EventBus.getDefault().post(new ChangeToStartExamEvent((Message) msg));
		}
		else if(message.getTitle().equals("showReadyExams"))
		{
			EventBus.getDefault().post(new ShowReadyExamsEvent((Message) msg));
		}
		else if(message.getTitle().equals("showReadyExamsAPP"))
		{
			EventBus.getDefault().post(new ShowReadyExamsAPPEvent((Message) msg));
		}
		else if(message.getTitle().equals("getSubjectsForTeacherSE"))
		{
			EventBus.getDefault().post(new GetSubjectsForTeacherSEEvent((Message) msg));
		}
		else if(message.getTitle().equals("getCoursesAE"))
		{
			EventBus.getDefault().post(new GetCoursesAEEvent((Message) msg));
		}
		else if(message.getTitle().equals("SendToPreview"))
		{
			EventBus.getDefault().post(new SendToPreviewEvent((Message) msg));
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
		else if(message.getTitle().equals("editSelectedQuestion") || message.getTitle().equals("getQuestionsForSubjectSE"))
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
		else if(message.getTitle().equals("Logout")||message.getTitle().equals("LogoutEB")
				||message.getTitle().equals("LogoutEEB")||message.getTitle().equals("LogoutEQB") ||message.getTitle().equals("LogoutAE")
				||message.getTitle().equals("LogoutSE")||message.getTitle().equals("LogoutET")||message.getTitle().equals("LogoutAP")||message.getTitle().equals("LogoutVG") || message.getTitle().equals("LogoutNoti"))
		{
			System.out.println("event bus logout");
			EventBus.getDefault().post(new LogoutEvent((Message)msg));
		}
		else if(message.getTitle().equals("LogoutForStudent")|| message.getTitle().equals("LogoutCE")||message.getTitle().equals("LogoutVGS"))
		{
			EventBus.getDefault().post(new LogoutForStudentEvent((Message)msg));
		}
		else if(message.getTitle().equals("NotInTime"))
		{
			EventBus.getDefault().post(new TimeIsUpEvent((Message)msg));
		}
		else if(message.getTitle().equals("InTime") || message.getTitle().equals("saveManualExam"))
		{
			EventBus.getDefault().post(new FinishExamEvent((Message)msg));
		}
		else if(message.getTitle().equals("createExam"))
		{
			EventBus.getDefault().post(new SelectQuestionEvent((Message)msg));
		}
		else if(message.getTitle().equals("saveExam"))
		{
			EventBus.getDefault().post(new saveExamEvent((Message)msg));
		}
		else if(message.getTitle().equals("approveExam"))
		{
			EventBus.getDefault().post(new ApproveExamEvent((Message)msg));
		}
		else if(message.getTitle().equals("viewGradesForStudent"))
		{
			EventBus.getDefault().post(new ViewGradesForStudentEvent((Message)msg));
		}
		else if(message.getTitle().equals("showExamsForTeacherCourses") || message.getTitle().equals("viewGradesForTeacherSubjects") || message.getTitle().equals("viewGradesForTeacherCourses") || "ShowExamsForTeacherSubjects".equals(message.getTitle()))
		{
			EventBus.getDefault().post(new ViewGradesForTeacherEvent((Message)msg));

		}
		else if(message.getTitle().equals("ShowReadyExamsForViewGradesForTeacher"))
		{
			EventBus.getDefault().post(new ViewGradesForTeacherIIBoundryEvent((Message)msg));
		}
		else if(message.getTitle().equals("StartSolvingManualExam"))
		{
			EventBus.getDefault().post(new StartSolvingManualExamEvent((Message)msg));
		}
		else if(message.getTitle().equals("Logout principle"))
		{
			System.out.println("event bus logout");
			EventBus.getDefault().post(new PrincipleLogoutEvent((Message)msg));
		}

		else if (message.getTitle().equals("showQuestionsForPrinciple"))
		{
			System.out.println("event get subjects");
			EventBus.getDefault().post(new ShowQuestionsForPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getSubjectsForPrinciple"))
		{
			EventBus.getDefault().post(new GetSubjectsForPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getCoursesForSubjectsPrinciple"))
		{
			EventBus.getDefault().post(new GetCoursesForSubjectsPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getExamQuestions"))
		{
			EventBus.getDefault().post(new GetExamQuestionsEvent((Message) msg));
		}
		else if (message.getTitle().equals("showExamsForPrinciple"))
		{
			EventBus.getDefault().post(new ShowExamsForPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getSubjectsForPrincipleExams"))
		{
			System.out.println("event get subjects");
			EventBus.getDefault().post(new GetSubjectsForPrincipleExamsEvent((Message) msg));
		}

		else if(message.getTitle().equals("GetCoursesForSubjectsPrincipleExams"))
		{
			EventBus.getDefault().post(new GetCoursesForSubjectsPrincipleExamsEvent((Message) msg));
		}

		else if(message.getTitle().equals("getStudentsGradesForPrinciple") || message.getTitle().equals("getStudentsToViewGradePrinciple"))
		{
			EventBus.getDefault().post(new GetStudentsGradesForPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getTeachersForPrinciple") || message.getTitle().equals("getListGradeForTeacher") || message.getTitle().equals("getReadyExamsForTeacherReports"))
		{
			EventBus.getDefault().post(new GetTeachersForPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("GetExamsForTeacherPrinciple"))
		{
			EventBus.getDefault().post(new GetExamsForTeacherPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getExamQuestions"))
		{
			EventBus.getDefault().post(new GetExamQuestionsEvent((Message) msg));
		}

		else if(message.getTitle().equals("getCoursesForPrinciple"))
		{
			EventBus.getDefault().post(new GetCoursesForPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("GetExamsForCoursePrinciple"))
		{
			EventBus.getDefault().post(new GetExamsForCoursePrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getExamsForStudentPrinciple"))
		{
			EventBus.getDefault().post(new GetExamsForStudentPrincipleEvent((Message) msg));
		}

		else if(message.getTitle().equals("getStudentsForPrinciple"))
		{
			EventBus.getDefault().post(new GetStudentsForPrincipleEvent((Message) msg));
		}
		else if(message.getTitle().equals("refreshTable") || message.getTitle().equals("ApproveExtraTimeRequest") || message.getTitle().equals("DenyExtraTimeRequest") || message.getTitle().equals("AskPrincipleForExtraTime") || message.getTitle().equals("GetOnGoingExamsForExtraTime") || message.getTitle().equals("refreshTablePrinciple"))
		{
			System.out.println("ahmaddd");
			EventBus.getDefault().post(new ExtraTimeEvent((Message) msg));
		}
		else if(message.getTitle().equals("ExtraTimePrinciple"))
		{
			System.out.println("here is ExtraTimePrinciple");
			EventBus.getDefault().post(new ExtraTimePrincipleEvent((Message) msg));
		}
		else if(message.getTitle().equals("getReadyExamsForCoursesReports")|| message.getTitle().equals("getListGradeForCourse"))
		{
			EventBus.getDefault().post(new GetCoursesForPrincipleEvent((Message) msg));
		}
		else if(message.getTitle().equals("getReadyExamsForStudentReports") || message.getTitle().equals("getListGradeForStudent"))
		{
			EventBus.getDefault().post(new GetStudentsForPrincipleEvent((Message) msg));
		}
		else if (message.getTitle().equals("getStudentNotificationList") ||message.getTitle().equals("setToRead") || message.getTitle().equals("RefreshStudentBell"))
		{
			EventBus.getDefault().post(new StudentEvent((Message) msg));
		}
		else if (message.getTitle().equals("getTeacherNotificationList") ||message.getTitle().equals("setToReadTeacher") || message.getTitle().equals("RefreshTeacherBell"))
		{
			EventBus.getDefault().post(new TeacherEvent((Message) msg));
		}
		else if(message.getTitle().equals("getPrincipleNotificationList") || message.getTitle().equals("RefreshPrincipleBell")||message.getTitle().equals("setToReadPrinciple"))
		{
			EventBus.getDefault().post(new PrincipleEvent((Message) msg));
		}
		else if(message.getTitle().equals("getNotificationForStudent"))
		{
			EventBus.getDefault().post(new NotificationForStudentEvent((Message) msg));
		}
		else if(message.getTitle().equals("getNotificationForTeacher"))
		{
			EventBus.getDefault().post(new NotificationForTeacherEvent((Message) msg));
		}

	}
	public static SimpleClient getClient() {
		return client;
	}
	public static boolean newClinet(String hostIP)
	{
		client = new SimpleClient(hostIP,12136);
		try {
			client.openConnection();
			return true;
		}
		catch (IOException e)
		{
			return false;
		}
	}



}
 