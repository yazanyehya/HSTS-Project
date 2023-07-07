package il.cshaifasweng.OCSFMediatorExample.server;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleChatClient;
import il.cshaifasweng.OCSFMediatorExample.client.SimpleClient;
import il.cshaifasweng.OCSFMediatorExample.entities.*;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.AbstractServer;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.ConnectionToClient;
import il.cshaifasweng.OCSFMediatorExample.server.ocsf.SubscribedClient;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;


import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

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
		Course Clanguage = new Course("gym");
		Subject ComputerScenice = new Subject("benchPress");
		Teacher rami = new Teacher("David", "Laid", "davidlaid7", "123", 1,ComputerScenice);
		Clanguage.getListOfTeachers().add(rami);
		rami.setSubject(ComputerScenice);
		session.save(rami);
		session.save(Clanguage);
		session.save(ComputerScenice);
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
		// Create a Hibernate session
		try (Session session = getSessionFactory().openSession()) {
			// Create a query to fetch the questions for the given course ID
			String hql = "FROM Question q WHERE q.course.name = :courseName";
			Query query = session.createQuery(hql, Question.class);
			query.setParameter("courseName", course.getName());

			// Execute the query and retrieve the list of questions
			List<Question> questions = query.getResultList();
			return questions;
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
		}
		else if ("NewClient".equals(message.getTitle()))
		{
			SubscribedClient newSub = new SubscribedClient(client);
			SubscribersList.add(newSub);
		}
		else if("changeToQuestionBoundry".equals(message.getTitle()) || "pressBack".equals(message.getTitle()) || "changeToExamBoundry".equals(message.getTitle()))
		{
			client.sendToClient(message);
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
		else if ("createQuestion".equals(message.getTitle())) {
			try {
				if (!session.getTransaction().isActive()) {
					session = getSessionFactory().openSession();
					session.beginTransaction();
				}
				Question question = (Question) message.getBody();
				System.out.println(question.getQText());
				// System.out.println(question.getSubject());
				System.out.println(question.getAnswer1());
				System.out.println(question.getAnswer2());
				System.out.println(question.getAnswer3());
				System.out.println(question.getAnswer4());

				if (Objects.equals(question.getAnswer1(), "") || Objects.equals(question.getAnswer2(), "") || Objects.equals(question.getAnswer3(), "") || Objects.equals(question.getAnswer4(), "") || question.getSubject() == null) {
					question = null;
				} else {
					session.save(question);
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
		else if ("saveExam".equals(message.getTitle())) {
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
					Message newMessage = new Message("saveExam", null);
					client.sendToClient(newMessage);
					System.out.println("null exam");
				}
				else
				{
					String examPeriod = examHelper.getExamPeriod();
					System.out.println("incline bench press4");
					Exam exam = new Exam(examHelper.getNameOfTeacher(), examPeriod);
					System.out.println("incline bench press5");
					List<Question> list = new ArrayList<>(examHelper.getQuestionHashMap().keySet());
					exam.setListOfQuestions(list);
					for (Question L : list)
					{
						if (L != null)
							System.out.println(L.getQText()+ "cccccc");
					}
					System.out.println("incline bench press6");
					session.save(exam);
					System.out.println("incline bench press7");
					session.getTransaction().commit();
					System.out.println("saved");
					Message newMessage = new Message("saveExam", exam);
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