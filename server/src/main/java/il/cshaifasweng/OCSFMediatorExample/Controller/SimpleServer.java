package il.cshaifasweng.OCSFMediatorExample.Controller;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.Controller.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.Controller.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.Controller.ocsf.SubscribedClient;
import org.hibernate.Hibernate;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public class SimpleServer extends AbstractServer {

	//private static final SessionFactory sessionFactory = getSessionFactory();
	private static Session session;
	private static ArrayList<SubscribedClient> SubscribersList = new ArrayList<>();
	public static void generate()
	{

		session.beginTransaction();

//		User user1 = new User("rami", "simaan", "ramisi7", "123", 0);
//		session.save(user1);
//		Question Q1 = new Question("what is 1+2", "0", "1","2","3");
//		session.save(Q1);
		//User user2 = new User("yazan", "yehya", "yazan9", "1234", 1);
		//User user3 = new User("ahmad", "ig", "ahmad", "1234", 1);
		//Subject math = new Subject("math");
		//Subject english = new Subject("English");
		//session.save(english);
		//User user4 = new Teacher("yazan", "yehya", "yazan9", "1234", 1,math);
		//User user5 = new Teacher("chris", "bumstead", "cbum", "5", 1,english);
		//session.save(user5);
//		Course electronics = new Course("electronic");
//		Subject physics = new Subject("physics");
//
//		Teacher teacher = new Teacher("raghad", "susan", "raghad7", "178",1,physics );
//		teacher.getCourses().add(electronics);
//		electronics.getListOfTeachers().add(teacher);
//		teacher.setSubject(physics);
//		session.save(physics);
//		session.save(electronics);
//		session.save(teacher);


		Subject sports = new Subject("Sports");
		Course football = new Course("Football");
		Course basketball = new Course("Basketball");
		Course golf = new Course("Golf");
		sports.getCourses().add(football);
		sports.getCourses().add(golf);
		sports.getCourses().add(basketball);
		football.setSubject(sports);
		basketball.setSubject(sports);
		golf.setSubject(sports);


		Subject math = new Subject("Math");
		Course lierarAlgebra = new Course("Linear Algebra");
		Course calculus = new Course("Calculus");
		Course geometry = new Course("Geometry");
		math.getCourses().add(lierarAlgebra);
		math.getCourses().add(calculus);
		math.getCourses().add(geometry);
		lierarAlgebra.setSubject(math);
		calculus.setSubject(math);
		geometry.setSubject(math);



		Teacher cr7 = new Teacher("Cristiano", "Ronaldo", "cr7", "123", 1);

		cr7.getSubjects().add(sports);
		cr7.getSubjects().add(math);
		cr7.getCourses().add(lierarAlgebra);
		cr7.getCourses().add(geometry);
		cr7.getCourses().add(football);

		sports.getListOfTeachers().add(cr7);
		football.getListOfTeachers().add(cr7);

		math.getListOfTeachers().add(cr7);
		lierarAlgebra.getListOfTeachers().add(cr7);
		geometry.getListOfTeachers().add(cr7);


		Teacher gBale = new Teacher("Gareth", "Bale", "bale11", "123", 1);
		gBale.getSubjects().add(sports);
		gBale.getCourses().add(golf);
		gBale.getCourses().add(football);
		sports.getListOfTeachers().add(gBale);
		golf.getListOfTeachers().add(gBale);
		football.getListOfTeachers().add(gBale);


		Teacher benzema = new Teacher("Karim", "Benzema", "kb9", "123", 1);
		benzema.getSubjects().add(sports);
		benzema.getCourses().add(football);
		football.getListOfTeachers().add(benzema);
		sports.getListOfTeachers().add(benzema);
		math.getListOfTeachers().add(benzema);
		benzema.getSubjects().add(math);
		benzema.getCourses().add(calculus);
		calculus.getListOfTeachers().add(benzema);


		session.save(cr7);
		session.save(gBale);
		session.save(benzema);

		session.save(sports);
		session.save(math);

		session.save(lierarAlgebra);
		session.save(calculus);
		session.save(geometry);
		session.save(football);
		session.save(basketball);
		session.save(golf);


		session.flush();
		session.getTransaction().commit();

	}
	public SimpleServer(int port)
	{
		super(port);
		SessionFactory sessionFactory = getSessionFactory();
		session = sessionFactory.openSession();
		//generate();
	}

	public User getUserByUsername(String username, String password, Message resMessage) {
		try {
			Query query = session.createQuery("FROM User WHERE username = :username", User.class);
			query.setParameter("username", username);

			User user = null;
			try {
				user = (User) query.getSingleResult();
			} catch (NoResultException ex) {
			}

			if (user != null && user.getPassword().equals(password) && !user.isLoggedIn()) {
				user.setLoggedIn(true);
				session.update(user);
				session.getTransaction().commit();
				return user;
			} else if (user != null && user.getPassword().equals(password) && user.isLoggedIn()) {
				resMessage.setTitle("already logged in");
				return null;
			} else {
				System.out.println("Incorrect username or password");
				return null;
			}
		} catch (Exception e) {
			session.getTransaction().rollback();
			throw e;
		}
	}

	public List<Question> getQuestionsForCourse(Course course) {
		try (Session session = getSessionFactory().openSession()) {
			// Retrieve the course from the database to ensure it's managed
			Course managedCourse = session.get(Course.class, course.getId());
			Hibernate.initialize(managedCourse.getListOfQuestions());
			// Get the questions associated with the course
			List<Question> questions = managedCourse.getListOfQuestions();
			return questions;
		}
	}

	public List<Exam> getExamsForCourse(Course course) {
		// Create a Hibernate session
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the questions for the given course ID
			String hql = "FROM Exam e WHERE e.course.name = :courseName";
			Query query = session.createQuery(hql, Exam.class);
			query.setParameter("courseName", course.getName());

			// Execute the query and retrieve the list of questions
			List<Exam> exams = query.getResultList();
			return exams;
		}
	}

	//	public User getUserByUsername(String username, String password) {
//		//Session session = HibernateUtil.getSessionFactory().getCurrentSession();
//
//		try {
//
//			//session.beginTransaction();
//			System.out.println("lolll");
//			Query query = session.createQuery("FROM User WHERE username = :username", User.class);
//			System.out.println("ddddd");
//			query.setParameter("username", username);
//			System.out.println("3333666");
//			User user = (User)query.getSingleResult();
//			System.out.println("3333");
//			session.getTransaction().commit();
//			System.out.println("44444");
//			if (user != null && user.getPassword().equals(password)) {
//				System.out.println("5555");
//				return user;
//			} else {
//				System.out.println("666666");
//				return null;
//			}
//		}
//		catch (Exception e) {
//			session.getTransaction().rollback();
//			throw e;
//		} finally {
//			session.close();
//	}
//		}

	public List<Subject> getSubjectsForTeacher(Teacher teacher) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the subjects for the given teacher ID
			String hql = "SELECT s FROM Subject s JOIN s.listOfTeachers t WHERE t.id = :teacherId";
			Query query = session.createQuery(hql, Subject.class);
			query.setParameter("teacherId", teacher.getId());

			// Execute the query and retrieve the list of subjects
			List<Subject> subjects = query.getResultList();
			return subjects;
		}
	}
	public List<Course> getCoursesForSubject(Subject subject) {
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the courses for the given subject
			String hql = "SELECT c FROM Course c WHERE c.subject = :subject";
			Query query = session.createQuery(hql, Course.class);
			query.setParameter("subject", subject);

			// Execute the query and retrieve the list of courses
			List<Course> courses = query.getResultList();
			return courses;
		}
	}




	@Override
	public void handleMessageFromClient(Object msg, ConnectionToClient client) throws IOException {
		System.out.println("incline bench press111");
		Message message = (Message) msg;
		System.out.println(message.getTitle());
		if ("Login".equals(message.getTitle()))
		{
			if (!session.getTransaction().isActive()) {
				session = getSessionFactory().openSession();
				session.beginTransaction();
			}
			Object[] body = (Object[]) message.getBody();
			String username = (String) body[0];
			String password = (String) body[1];
			Message msg1 = new Message("", null);
			User user = getUserByUsername(username, password, msg1);
			System.out.println(msg1.getTitle());
			if (msg1.getTitle().equals("already logged in")) {
				message.setTitle("already logged in");
			}
			System.out.println("2222");
			Message responseMessage = new Message(message.getTitle(), user);
			client.sendToClient(responseMessage);

			if (session != null && session.isOpen()) {
				session.getTransaction().commit();
				session.close();
			}
		}
		else if ("NewClient".equals(message.getTitle()))
		{
			SubscribedClient newSub = new SubscribedClient(client);
			SubscribersList.add(newSub);
		}
		else if("changeToQuestionBoundry".equals(message.getTitle()) || "pressBack".equals(message.getTitle()) || "changeToExamBoundry".equals(message.getTitle()) ||"changeToEditQuestionBoundry".equals(message.getTitle()))
		{
			client.sendToClient(message);
		}
		else if("getSubjects".equals(message.getTitle()))
		{
			Teacher teacher = (Teacher)message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Subject> list = getSubjectsForTeacher(teacher);
				for (Subject s : list)
				{
					System.out.println(s.getName() + "+ size: " + list.size());
				}
				Message resMessage = new Message("getSubjects", list);
				client.sendToClient(resMessage);
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					session.getTransaction().commit();
					session.close();
				}
			}

		}
		else if("showQuestions".equals(message.getTitle()) || "getAllQuestions".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Question> list = getQuestionsForCourse(course);
				//List<Question> questionList = new ArrayList<Question>();
				System.out.println("size of list = " + list.size());
//				if ("getAllQuestions".equals(message.getTitle()))
//				{
//					for (Question l : list)
//					{
//						l.setScore(0);
//					}
//				}
				Message resMessage = new Message(message.getTitle(), list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					session.getTransaction().commit();
					session.close();
				}
			}
		}
		else if("showExams".equals(message.getTitle()))
		{
			Course course = (Course) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				List<Exam> list = getExamsForCourse(course);
				System.out.println("size of list = " + list.size());
				Message resMessage = new Message("showExams", list);
				client.sendToClient(resMessage);

			} catch (Exception e) {
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					session.getTransaction().commit();
					session.close();
				}
			}
		}
		else if("editSelectedQuestion".equals(message.getTitle()))
		{
			client.sendToClient(message);
		}
		else if ("getSubjectsForTeacher".equals(message.getTitle()) || "getSubjectsForTeacherEQ".equals(message.getTitle()) || "getSubjectsForTeacherExam".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Teacher teacher = (Teacher) message.getBody();
				List<Subject> subjects = getSubjectsForTeacher(teacher);
				Message resMessage = new Message(message.getTitle(), subjects);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					session.getTransaction().commit();
					session.close();
				}
			}
		}
		else if ("getCoursesForSubjects".equals(message.getTitle()) || "getCoursesForSubjectsEQ".equals(message.getTitle()) || "getCoursesForSubjectsExam".equals(message.getTitle()) || "getCourses".equals(message.getTitle()))
		{
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Subject subject = (Subject) message.getBody();
				List<Course> courses = getCoursesForSubject(subject);
				Message resMessage = new Message(message.getTitle(), courses);
				client.sendToClient(resMessage);

			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally {
				if (session != null && session.isOpen()) {
					session.getTransaction().commit();
					session.close();
				}
			}
		}
		else if("editSelectedExam".equals(message.getTitle()))
		{
			try
			{
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				System.out.println("editSelectedExam");
				Exam exam = (Exam) message.getBody();
				List<Question> list = exam.getListOfQuestions();
				for (Question l : list)
				{
					System.out.println(l.getQText() + "   score: ");
				}
				Message resMessage = new Message("editSelectedExam", exam);
				client.sendToClient(resMessage);

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		else if ("Logout".equals(message.getTitle()))
		{
			SubscribersList.remove(message.getBody());
			User temp = (User) message.getBody();
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				User user = session.find(User.class, temp.getId());
				user.setLoggedIn(false);
				session.update(user);
				session.flush(); // Manually flush the session
				session.getTransaction().commit(); // Commit the transaction
				client.sendToClient(message);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				session.close();
			}
			System.out.println("logout");
		}
		else if ("createQuestion".equals(message.getTitle()) || "saveEditedQuestion".equals(message.getTitle())) {
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				List<String> list = (List<String>) message.getBody();
				Question question = null;
				if (list != null)
				{
					String hql1 = "SELECT s FROM Subject s WHERE s.name = :subject";
					Query query = session.createQuery(hql1, Subject.class);
					query.setParameter("subject", list.get(5));
					Subject subject = (Subject)query.getSingleResult();
					if (subject!= null)
					{
						System.out.println(subject.getId());
					}
					else
					{
						System.out.println("nukllllllll");
					}
					String jpql = "SELECT t FROM Teacher t WHERE t.username = :username";
					query = session.createQuery(jpql, Teacher.class);
					query.setParameter("username", list.get(6));
					Teacher teacher = (Teacher)query.getSingleResult();
					if (teacher!= null)
					{
						System.out.println(teacher.getId());
					}
					else
					{
						System.out.println("nukllllllll111");
					}
					//question = new Question(list.get(0),list.get(1), list.get(2), list.get(3),list.get(4), subject,teacher, list.get(7));
					List<Course> courses = new ArrayList<Course>();
					for (int i = 8 ; i < list.size(); i++)
					{
						String hql = "SELECT c FROM Course c WHERE c.name = :course";
						Query query1 = session.createQuery(hql, Course.class);
						query1.setParameter("course", list.get(i));
						Course course = (Course) query1.getSingleResult();
						if (course!= null)
						{
							System.out.println(course.getId());
						}
						else
						{
							System.out.println("nukllllllll222222");
						}
						courses.add(course);

						//course.getListOfQuestions().add(question);
					}
					System.out.println("here before creating quesuion");
					question = new Question(list.get(0),list.get(1), list.get(2), list.get(3),list.get(4), subject,teacher, list.get(7), courses, "no");
					System.out.println("here after creating quesuion");
					session.save(question);
					System.out.println("lolllll");
//					String hql = "SELECT c FROM Course c WHERE c.name = :course";
//					Query query1 = session.createQuery(hql, Subject.class);
//					query1.setParameter("course", list.get(4));
					session.getTransaction().commit();
				}
				Message responseMessage = new Message(message.getTitle(), question);
				client.sendToClient(responseMessage);
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		 else if ("createExam".equals(message.getTitle())) {
			try {
				if (session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				System.out.println("we are here");
				List<Question> list = getQuestionsForCourse((Course) message.getBody());
				Message responseMessage = new Message(message.getTitle(), list);
				System.out.println("gggggggggg");
				client.sendToClient(responseMessage);
				System.out.println("gggggggggg");
			} catch (Exception e) {
				if (session.getTransaction() != null && session.getTransaction().isActive()) {
					session.getTransaction().rollback();
				}
				e.printStackTrace();
			} finally {
				// Close the session
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
		else if ("saveExam".equals(message.getTitle()) || "saveEditedExam".equals(message.getTitle()))
		{
			System.out.println("incline bench press2");
			try {
				if (session == null || !session.isOpen() || session.getTransaction() == null || !session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}

				System.out.println("incline bench press3");
				if ((ExamHelper) message.getBody() == null) {
					System.out.println("null");
				} else {
					System.out.println("john cena");
				}
				ExamHelper examHelper = (ExamHelper) message.getBody();

				if (examHelper.getQuestionHashMap() != null) {
					System.out.println(examHelper.getQuestionHashMap().size());
					System.out.println("raghad");
				}
				if (examHelper.getExamPeriod() != null) {
					System.out.println("raghad");
				}
				if (examHelper.getExamPeriod() == null ||
						examHelper.getQuestionHashMap() == null)
				{
					Message newMessage = new Message(message.getTitle(), null);
					client.sendToClient(newMessage);
					System.out.println("null exam");
				}
				else
				{
					String examPeriod = examHelper.getExamPeriod();
					System.out.println("incline bench press4");
					System.out.println(examHelper.getCourse());
					String hql1 = "SELECT s FROM Subject s WHERE s.name = :subject";
					Query query = session.createQuery(hql1, Subject.class);
					query.setParameter("subject", examHelper.getSubject());
					Subject subject = (Subject)query.getSingleResult();
					String hql = "SELECT c FROM Course c WHERE c.name = :course";
					Query query1 = session.createQuery(hql, Course.class);
					query1.setParameter("course", examHelper.getCourse());
					Course course = (Course) query1.getSingleResult();
					Exam exam = new Exam(examHelper.getUsername(), examPeriod, subject, course, examHelper.teacherComments, examHelper.allComments);
					System.out.println("incline bench press5");
					List<Question> list1 = new ArrayList<Question>(examHelper.getQuestionHashMap().keySet());
					List<Question> list2 = new ArrayList<Question>();
					for (Question q : list1)
					{
						list2.add(q.clone());
					}
					List<Question> newQuestions = new ArrayList<>();
					//exam.setListOfQuestions(list);
//					for (Question question : list1) {
//						if (examHelper.getQuestionHashMap().containsKey(question))
//						{
//							int score = examHelper.getQuestionHashMap().get(question);
//							question.setScore(score);
//						}
//						// Check if the question already exists in the database
//						if (question.getId() == 0)
//						{
//							// New question, save it to generate an ID
//							//question.getExams().add(exam);
//							session.save(question);
//							newQuestions.add(question);
//						}
//
//						// Set the score for the question
//
//						// Associate the question with the exam
//					}
					for (int i = 0; i < list1.size(); i++)
					{
						if (examHelper.getQuestionHashMap().containsKey(list1.get(i)))
						{
							int score = examHelper.getQuestionHashMap().get(list1.get(i));
							list2.get(i).setScore(score);
							session.save(list2.get(i));
						}
					}
					exam.getListOfQuestions().addAll(list2);
					for (Question q : exam.getListOfQuestions())
					{
						System.out.println(q.getQText() + " de score: " + q.getScore());
					}
					System.out.println("incline bench press6");
					session.save(exam);
					System.out.println("incline bench press7");
					session.getTransaction().commit();
					System.out.println("saved");
					Message newMessage = new Message(message.getTitle(), exam);
					client.sendToClient(newMessage);
				}
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (session != null && session.isOpen()) {
					session.close();
				}
			}
		}
//		if ("Get all Students".equals(message.getOperation())) {
//			try {
//
//				session.beginTransaction();
//
//// Fetch the student data from your data source or database
//				List<Student> students = getAllStudentsFromDatabase();
//
//// Create a new Message with the student data and send it back to the client
//				int msgId = 0;
//				Message responseMessage = new Message("Get all Students", students);
//				client.sendToClient(responseMessage);
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		} else if ("get grades".equals(message.getOperation())) {
//			try {
//
//				session.beginTransaction();
//				Student studentsel = (Student) message.getMessage();
//				int studentId = studentsel.getId();
//				Student student = getStudentFromDatabase(studentId);
//				Message responseMessage = new Message("get grades", student);
//				client.sendToClient(responseMessage);
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}else if("update".equals(message.getOperation())){
//			try {
//
//
//				session.beginTransaction();
//				Student studentsel =(Student) message.getMessage();
//				int studentId = studentsel.getId();
//				Student student = updateStudentGradeInDatabase(studentId,message.getSubject(), message.getNewScore());
//				Message responseMessage = new Message("update",student);
//				client.sendToClient(responseMessage);
//
//				session.close();
//			} catch (IOException e) {
//				e.printStackTrace();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}

	}


	public static SessionFactory getSessionFactory()
	{
		Configuration configuration = new Configuration();
		configuration.addAnnotatedClass(Student.class);
		configuration.addAnnotatedClass(Exam.class);
		configuration.addAnnotatedClass(Question.class);
		configuration.addAnnotatedClass(User.class);
		configuration.addAnnotatedClass(Teacher.class);
		configuration.addAnnotatedClass(Subject.class);
		configuration.addAnnotatedClass(Course.class);

		StandardServiceRegistry serviceRegistry= new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		return configuration.buildSessionFactory(serviceRegistry);
	}
	public static List<Student> getAllStudentsFromDatabase() {
		//Session session = sessionFactory.openSession();
//open hibernate session
		session.beginTransaction();
//create criteria builder and criteria query objects to build query
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> root = query.from(Student.class);
		query.select(root);
//execute query and retrieve all students
		List<Student> students = session.createQuery(query).getResultList();
//close transaction and session
		session.getTransaction().commit();
		session.close();

		return students;
	}
	public  static Student getStudentFromDatabase(int studentId){
		//Session session = sessionFactory.openSession();
		session.beginTransaction();
		CriteriaBuilder builder = session.getCriteriaBuilder();
		CriteriaQuery<Student> query = builder.createQuery(Student.class);
		Root<Student> root = query.from(Student.class);
		query.select(root).where(builder.equal(root.get("id"),studentId));
		Student student = session.createQuery(query).uniqueResult();
		session.getTransaction().commit();
		session.close();
		return student;
	}
	public static Student updateStudentGradeInDatabase(int studentId, String subject , int newGrade) {
			//Session session = sessionFactory.openSession();
			 session.beginTransaction();

			 Student student = session.get(Student.class, studentId);
			 if (student != null) {
			 	if(subject.equals("English")){
					 //student.setEnglishScore(newGrade);
				}else if(subject.equals("Math")){
						//student.setMathScore(newGrade);
					}
				 session.update(student);
				 session.getTransaction().commit();
				 System.out.println("Student grade updated successfully.");
				}

			  else {
					 System.out.println("Student with ID " + studentId + " not found in the database.");
 }
			 session.close();
		return student;
	}
}